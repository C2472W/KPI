/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.kpi.evaluation;

/**
 *
 * @author C2082
 */
public class ShipmentQuantityOVN extends ShipmentQuantityVN {

    public ShipmentQuantityOVN() {
        super();
        queryParams.put("facno", "V");
        queryParams.put("hmark1", " ='O'");
        queryParams.put("hmark2", " ='SA'");
    }
}
