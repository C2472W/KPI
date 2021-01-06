/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanson.kpi.evaluation;

import com.lightshell.comm.BaseLib;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import javax.persistence.Query;

/**
 *
 * @author C0160
 */
public abstract class ShipmentTonHY extends Shipment {

    public ShipmentTonHY() {
        super();
    }

    @Override
    public BigDecimal getValue(int y, int m, Date d, int type, LinkedHashMap<String, Object> map) {
        //获得查询参数
        String facno = map.get("facno") != null ? map.get("facno").toString() : "";
        String protype = map.get("protype") != null ? map.get("protype").toString() : "";//种类
        String cusno = map.get("cusno") != null ? map.get("cusno").toString() : "";//客户

        BigDecimal shpton = BigDecimal.ZERO;
        BigDecimal bakton = BigDecimal.ZERO;
        BigDecimal armton = BigDecimal.ZERO;

        StringBuilder sb = new StringBuilder();
        //出货
        sb.append(" select isnull(sum(cast((case substring(s.judco,1,1)+s.fvco ");
        sb.append(" when '4F' then d.shpqy1*s.rate2 else d.shpqy1 end) as decimal(17,2))),0) ");
        sb.append(" from cdrdta d,cdrhad h,invmas s ");
        sb.append(" where h.facno=d.facno and h.shpno=d.shpno and d.itnbr=s.itnbr ");
        sb.append(" and h.houtsta not in ('N','W') ");
        if (!"".equals(protype)) {
            sb.append(" and left(s.spdsc,2) ").append(protype);
        }
        if (!"".equals(cusno)) {
            sb.append(" and h.cusno ").append(cusno);
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
        String cdrdta = sb.toString().replace("${y}", String.valueOf(y)).replace("${m}", String.valueOf(m)).replace("${d}", BaseLib.formatDate("yyyyMMdd", d))
                .replace("${facno}", facno);

        sb.setLength(0);
        //未开票销退
        sb.append(" select isnull(sum(cast((case substring(s.judco,1,1)+s.fvco ");
        sb.append(" when '4F' then d.bshpqy1*s.rate2 else d.bshpqy1 end) as decimal(17,2))),0) ");
        sb.append(" from cdrbdta d,cdrbhad h,invmas s ");
        sb.append(" where h.facno=d.facno and h.bakno=d.bakno and d.itnbr=s.itnbr ");
        sb.append(" and h.baksta not in ('N','W') and h.owarehyn='Y' ");
        if (!"".equals(protype)) {
            sb.append(" and left(s.spdsc,2) ").append(protype);
        }
        if (!"".equals(cusno)) {
            sb.append(" and h.cusno ").append(cusno);
        }
        sb.append(" and year(h.bakdate) = ${y} and month(h.bakdate)= ${m} ");
        switch (type) {
            case 2:
                //月
                sb.append(" and h.bakdate<= '${d}' ");
                break;
            case 5:
                //日
                sb.append(" and h.bakdate= '${d}' ");
                break;
            default:
                sb.append(" and h.bakdate<= '${d}' ");
        }
        String cdrbdta = sb.toString().replace("${y}", String.valueOf(y)).replace("${m}", String.valueOf(m)).replace("${d}", BaseLib.formatDate("yyyyMMdd", d))
                .replace("${facno}", facno);

        sb.setLength(0);
        //已开票销退
        sb.append(" select isnull(sum(cast((case substring(s.judco,1,1)+s.fvco ");
        sb.append(" when '4F' then d.bshpqy1*s.rate2 else d.bshpqy1 end) as decimal(17,2))),0) ");
        sb.append(" from armblos a,cdrbdta d,invmas s ");
        sb.append(" where a.facno=d.facno ");
        sb.append(" and a.bakno=d.bakno and a.trseq=d.trseq and s.itnbr=d.itnbr ");
        sb.append(" and a.facno='${facno}' ");
        if (!"".equals(protype)) {
            sb.append(" and left(s.spdsc,2) ").append(protype);
        }
        if (!"".equals(cusno)) {
            sb.append(" and a.ivocus ").append(cusno);
        }
        sb.append(" and year(a.bildat) = ${y} and month(a.bildat)= ${m} ");
        switch (type) {
            case 2:
                //月
                sb.append(" and a.bildat<= '${d}' ");
                break;
            case 5:
                //日
                sb.append(" and a.bildat= '${d}' ");
                break;
            default:
                sb.append(" and a.bildat<= '${d}' ");
        }
        String armblos = sb.toString().replace("${y}", String.valueOf(y)).replace("${m}", String.valueOf(m)).replace("${d}", BaseLib.formatDate("yyyyMMdd", d))
                .replace("${facno}", facno);
        //获取数值
        superEJB.setCompany(facno);
        Query query1 = superEJB.getEntityManager().createNativeQuery(cdrdta);
        Query query2 = superEJB.getEntityManager().createNativeQuery(cdrbdta);
        Query query3 = superEJB.getEntityManager().createNativeQuery(armblos);
        try {
            Object shp = query1.getSingleResult();
            Object shpbak = query2.getSingleResult();
            Object armbak = query3.getSingleResult();
            shpton = (BigDecimal) shp;
            bakton = (BigDecimal) shpbak;
            armton = (BigDecimal) armbak;
            return shpton.subtract(bakton).subtract(armton);
        } catch (Exception ex) {
            log4j.error("ShipmentTonHTHY.getValue()异常", ex);
        }
        return BigDecimal.ZERO;
    }

}
