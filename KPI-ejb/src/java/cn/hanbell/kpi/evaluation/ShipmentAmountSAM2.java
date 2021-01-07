/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.kpi.evaluation;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 *
 * @author C1879
 */
public class ShipmentAmountSAM2 extends ShipmentAmount {

    public ShipmentAmountSAM2() {
        super();
        queryParams.put("facno", "C");
        queryParams.put("deptno", " '1G100' ");
        queryParams.put("n_code_DA", "='AH' ");
        queryParams.put("n_code_CD", " LIKE 'WX%' ");
        queryParams.put("n_code_DC", " IN ('SAM-5HP','SAM-7HP') ");
        queryParams.put("n_code_DD", " IN ('00','02') ");
    }

    @Override
    public BigDecimal getARM270Value(int y, int m, Date d, int type, LinkedHashMap<String, Object> map) {
        return BigDecimal.ZERO;
    }

}
