/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.kpi.evaluation;

import com.lightshell.comm.BaseLib;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;

/**
 *
 * @author C2082
 */
public class ShipmentAmountS19 extends ShipmentAmount9 {
    
    public ShipmentAmountS19() {
        super();
        queryParams.put("facno", "C");
        queryParams.put("ogdkid", "RL01");
        queryParams.put("n_code_DA", " ='AH' ");
        queryParams.put("n_code_DC", " in ('SAM-5HP','SAM-7HP') ");
    }
    
     @Override
    public BigDecimal getValue(int y, int m, Date d, int type, LinkedHashMap<String, Object> map) {
        //获得查询参数
        String facno = map.get("facno") != null ? map.get("facno").toString() : "";
        String decode = map.get("decode") != null ? map.get("decode").toString() : "";
        String n_code_DA = map.get("n_code_DA") != null ? map.get("n_code_DA").toString() : "";
        String n_code_CD = map.get("n_code_CD") != null ? map.get("n_code_CD").toString() : "";
        String n_code_DC = map.get("n_code_DC") != null ? map.get("n_code_DC").toString() : "";
        String n_code_DD = map.get("n_code_DD") != null ? map.get("n_code_DD").toString() : "";
        BigDecimal shp1 = BigDecimal.ZERO;
        BigDecimal bshp1 = BigDecimal.ZERO;
        //机体维修收费金额
        StringBuilder sb = new StringBuilder();
        sb.append("select isnull(sum(case h.tax when '1' then (d.shpamts * h.ratio) else (d.shpamts * h.ratio)/(h.taxrate + 1) end),0) from cdrhad h,cdrdta d,cdrhmas c where h.facno = d.facno and h.shpno = d.shpno and c.cdrno = d.cdrno and h.houtsta <> 'W'");
        sb.append(" and h.cusno NOT IN ('SSD00107','SGD00088','SJS00254','SCQ00146','KZJ00029') ");
        sb.append(" and d.issevdta='Y' and h.facno='${facno}' ");
        if (!"".equals(decode)) {
            sb.append(" and h.decode ='").append(decode).append("' ");
        }
        if (!"".equals(n_code_DA)) {
            sb.append(" and d.n_code_DA ").append(n_code_DA);
        }
        if (!"".equals(n_code_CD)) {
            sb.append(" and d.n_code_CD ").append(n_code_CD);
        }
        if (!"".equals(n_code_DC)) {
            sb.append(" and d.n_code_DC ").append(n_code_DC);
        }
        if (!"".equals(n_code_DD)) {
            sb.append(" and d.n_code_DD ").append(n_code_DD);
        } else {
            sb.append(" and d.n_code_DD <> 'ZZ' ");
        }
        sb.append(" and isnull(c.hmark2, '') = 'WX'");
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
        String cdrdta = sb.toString().replace("${y}", String.valueOf(y)).replace("${m}", String.valueOf(m)).replace("${d}", BaseLib.formatDate("yyyyMMdd", d))
                .replace("${facno}", facno);
        superEJB.setCompany(facno);
        Query query1 = superEJB.getEntityManager().createNativeQuery(cdrdta);
        try {
            Object o1 = query1.getSingleResult();
            shp1 = (BigDecimal) o1;
        } catch (Exception ex) {
            Logger.getLogger(Shipment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return shp1;
    }

}
