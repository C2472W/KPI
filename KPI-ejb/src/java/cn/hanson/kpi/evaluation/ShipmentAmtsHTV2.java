/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose
 * Tools | Templates and open the template in the editor.
 */
package cn.hanson.kpi.evaluation;

/**
 *
 * @author C1749
 */
public class ShipmentAmtsHTV2 extends ShipmentAmount {

    public ShipmentAmtsHTV2() {
        super();
        queryParams.put("facno", "H");
        queryParams.put("protype", "in ('HT')");
        queryParams.put("cusno", "in ('HTW00001')");
    }

}
