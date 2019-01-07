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
public class FreeServiceOuterFWRT extends FreeServiceOuterFW {

    public FreeServiceOuterFWRT() {
        super();
        queryParams.put("facno", "C");
        queryParams.put("hmark1", " ='HD' ");
        queryParams.put("hmark2", " ='CM' ");
    }

    @Override
    public BigDecimal getValue(int y, int m, Date d, int type, LinkedHashMap<String, Object> map) {
        BigDecimal temp1, temp2, temp3, temp4, temp5;
        temp1 = super.getValue(y, m, d, type, map);
        queryParams.remove("facno");
        queryParams.remove("hmark1");
        queryParams.put("facno", "J");
        temp2 = super.getValue(y, m, d, type, queryParams);
        queryParams.remove("facno");
        queryParams.put("facno", "G");
        temp3 = super.getValue(y, m, d, type, queryParams);
        queryParams.remove("facno");
        queryParams.put("facno", "C4");
        temp4 = super.getValue(y, m, d, type, queryParams);
        queryParams.remove("facno");
        queryParams.put("facno", "N");
        temp5 = super.getValue(y, m, d, type, queryParams);

        return temp1.add(temp2).add(temp3).add(temp4).add(temp5);
    }
}
