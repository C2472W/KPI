/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.kpi.evaluation;

/**
 *
 * @author C0160
 */
public class ShipmentAmountP9 extends ShipmentAmount9 {

    public ShipmentAmountP9() {
        super();
        queryParams.put("facno", "C");
        queryParams.put("ogdkid", " IN('RL01','RL03') ");
        queryParams.put("n_code_DA", " ='P' ");
    }

}
