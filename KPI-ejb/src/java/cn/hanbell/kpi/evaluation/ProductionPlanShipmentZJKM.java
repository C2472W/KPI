/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.kpi.evaluation;

import cn.hanbell.kpi.entity.Indicator;
import cn.hanbell.kpi.entity.IndicatorDaily;
import cn.hanbell.kpi.entity.IndicatorDetail;
import com.lightshell.comm.BaseLib;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;

/**
 *
 * @author C1879
 */
public class ProductionPlanShipmentZJKM extends ProductionPlanShipment {

    public ProductionPlanShipmentZJKM() {
        super();
        queryParams.put("facno", "E");
        //#ITCLS CHANGE TODO #
        queryParams.put("itcls", " in ('3W76','3W80','3W79','3Y76','3Y79','3Y80','3X76','3X79','3X80')");
        //#ITCLS CHANGE TODO #
        queryParams.put("itnbrf", " in ('3J76','3J79','3J80') ");
    }

    @Override
    public BigDecimal getValue(int y, int m, Date d, int type, LinkedHashMap<String, Object> map) {
        String facno = map.get("facno") != null ? map.get("facno").toString() : "";
        String n_code_DA = map.get("n_code_DA") != null ? map.get("n_code_DA").toString() : "";
        String itcls = map.get("itcls") != null ? map.get("itcls").toString() : "";
        String itnbrf = map.get("itnbrf") != null ? map.get("itnbrf").toString() : "";
        String id = map.get("id") != null ? map.get("id").toString() : "";

        BigDecimal value = BigDecimal.ZERO;

        StringBuilder sb = new StringBuilder();
        sb.append(" select isnull(sum(d.shpqy1),0) as totshpqy from cdrhad h, cdrdta d ");
        sb.append(" where  h.houtsta<>'W' and h.shpno=d.shpno and  h.facno=d.facno  and h.facno='${facno}' ");
        if (!"".equals(n_code_DA)) {
            sb.append(" and d.n_code_DA ").append(n_code_DA);
        }
        if (!"".equals(itcls)) {
            sb.append(" and (d.itnbr in(select itnbr from invmas where itcls ").append(itcls).append(") ");
            sb.append(" and d.itnbr NOT in(select itnbr from invmas where itcls ").append(itnbrf).append(")) ");
        }
        sb.append(" and year(h.shpdate) = ${y} and month(h.shpdate)= ${m} ");
        switch (type) {
            case 2:
                //月
                sb.append(" and h.shpdate<= '${d}' ");
                break;
            case 5:
                //日
                sb.append(" and h.shpdate= '${d}' ");
                break;
            default:
                sb.append(" and h.shpdate<= '${d}' ");
        }
        String sql = sb.toString().replace("${y}", String.valueOf(y)).replace("${m}", String.valueOf(m)).replace("${d}", BaseLib.formatDate("yyyyMMdd", d))
                .replace("${facno}", facno);

        superEJB.setCompany(facno);
        Query query = superEJB.getEntityManager().createNativeQuery(sql);
        try {
            Object o = query.getSingleResult();
            value = (BigDecimal) o;
            //更新往日出货
            if (type == 5) {
                List list = getLastValue(y, m, d, map);
                if (list != null && !list.isEmpty()) {
                    if (!"".equals(id)) {
                        Indicator entity = indicatorBean.findById(Integer.valueOf(id));
                        if (entity != null && entity.getOther3Indicator() != null) {
                            IndicatorDetail salesOrder = entity.getOther3Indicator();
                            IndicatorDaily daily = indicatorDailyBean.findByPIdDateAndType(salesOrder.getId(), salesOrder.getSeq(), m, salesOrder.getType());
                            daily.clearDate();
                            for (int i = 0; i < list.size(); i++) {
                                Object[] row = (Object[]) list.get(i);
                                updateValue(Integer.valueOf(row[0].toString()), BigDecimal.valueOf(Double.valueOf(row[1].toString())), daily);
                            }
                            indicatorDailyBean.update(daily);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.getLogger(Shipment.class.getName()).log(Level.SEVERE, null, e);
        }
        return value;
    }

    @Override
    public List getLastValue(int y, int m, Date d, LinkedHashMap<String, Object> map) {
        String facno = map.get("facno") != null ? map.get("facno").toString() : "";
        String n_code_DA = map.get("n_code_DA") != null ? map.get("n_code_DA").toString() : "";
        String itcls = map.get("itcls") != null ? map.get("itcls").toString() : "";
        String itnbrf = map.get("itnbrf") != null ? map.get("itnbrf").toString() : "";

        StringBuilder sb = new StringBuilder();
        sb.append(" select day(h.shpdate),isnull(sum(d.shpqy1),0) as totshpqy from cdrhad h, cdrdta d ");
        sb.append(" where  h.houtsta<>'W' and h.shpno=d.shpno and  h.facno=d.facno  and h.facno='${facno}' ");
        if (!"".equals(n_code_DA)) {
            sb.append(" and d.n_code_DA ").append(n_code_DA);
        }
        if (!"".equals(itcls)) {
            sb.append(" and (d.itnbr in(select itnbr from invmas where itcls ").append(itcls).append(") ");
            sb.append(" and d.itnbr NOT in(select itnbr from invmas where itcls ").append(itnbrf).append(")) ");
        }
        sb.append(" and year(h.shpdate) = ${y} and month(h.shpdate)= ${m} and h.shpdate< '${d}'  GROUP BY day(h.shpdate)");

        String sql = sb.toString().replace("${y}", String.valueOf(y)).replace("${m}", String.valueOf(m)).replace("${d}", BaseLib.formatDate("yyyyMMdd", d))
                .replace("${facno}", facno);

        superEJB.setCompany(facno);
        Query query = superEJB.getEntityManager().createNativeQuery(sql);
        try {
            List list = query.getResultList();
            return list;
        } catch (Exception e) {
            Logger.getLogger(Shipment.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

}
