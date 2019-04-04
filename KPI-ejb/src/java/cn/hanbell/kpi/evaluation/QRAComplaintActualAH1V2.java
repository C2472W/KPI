/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.kpi.evaluation;

import cn.hanbell.kpi.comm.Actual;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 *
 * @author C1749
 */
public class QRAComplaintActualAH1V2 extends QRA {

    public QRAComplaintActualAH1V2() {
        super();
    }

    @Override
    public BigDecimal getValue(int y, int m, Date d, int type, LinkedHashMap<String, Object> map) {
        try {
            BigDecimal result = BigDecimal.ZERO;
            //CRM的客诉台数
            Actual crm = (Actual) QRAComplaintCountAH2.class.newInstance();
            BigDecimal ev = crm.getValue(y, m, d, type, crm.getQueryParams());
            //KPI的移动平均出货台数
            Actual kpi = (Actual) QRAShipmentAvgAH2.class.newInstance();
            BigDecimal ov = kpi.getValue(y, m, d, type, kpi.getQueryParams());
            //PPM的客诉率 乘以100万
            if (ov != null && ov.compareTo(BigDecimal.ZERO) != 0) {
                result = ev.divide(ov, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(1000000));
            }
            return result;
        } catch (Exception ex) {
            log4j.error("数据为0！", ex);
        }
        return BigDecimal.ZERO;
    }

}

class QRAComplaintCountAH2 extends QRAComplaintCount {

    public QRAComplaintCountAH2() {
        super();
        queryParams.put("BQ197", " ='AH' ");
        queryParams.put("BQ003"," in ('AJT') ");
        queryParams.put("BQ505", " in ('YX','-1') ");
        queryParams.put("BQ110"," in ('Y') ");
    }

    @Override
    public BigDecimal getValue(int y, int m, Date d, int type, LinkedHashMap<String, Object> map) {
        return super.getValue(y, m, d, type, map); 
    }
    

}

class QRAShipmentAvgAH2 extends QRAShipmentAvg {

    public QRAShipmentAvgAH2() {
        super();
        queryParams.put("n_code_DA", "AH");
    }

    @Override
    public BigDecimal getValue(int y, int m, Date d, int type, LinkedHashMap<String, Object> map) {
        return super.getValue(y, m, d, type, map);
    }
    
}
