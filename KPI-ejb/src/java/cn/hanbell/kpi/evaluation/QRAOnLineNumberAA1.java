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
 * @author C1749 空压机组上线合格率
 */
public class QRAOnLineNumberAA1 extends QRABadFeedRate {

    public QRAOnLineNumberAA1() {
        super();
        queryParams.put("SYSTEMID", "'QC_SXBLReport'");
        queryParams.put("SEQUENCE", " in ('7','8') ");
    }

    @Override
    public BigDecimal getValue(int y, int m, Date d, int type, LinkedHashMap<String, Object> map) {
        return super.getValue(y, m, d, type, map).multiply(BigDecimal.valueOf(100));
    }

}
