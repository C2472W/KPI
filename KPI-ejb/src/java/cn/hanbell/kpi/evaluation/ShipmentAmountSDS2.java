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
public class ShipmentAmountSDS2 extends ShipmentAmountSDS {

    public ShipmentAmountSDS2() {
        super();
        queryParams.put("decode", "2");
        queryParams.put("ogdkid", "RL03");
    }

}
