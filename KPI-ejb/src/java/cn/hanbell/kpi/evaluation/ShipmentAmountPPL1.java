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
public abstract class ShipmentAmountPPL1 extends ShipmentAmount {

    public ShipmentAmountPPL1() {
        super();
        queryParams.put("facno", "C");
        queryParams.put("decode", "1");
        queryParams.put("deptno", " '1H000' ");
        queryParams.put("ogdkid", "RLXX");
        queryParams.put("n_code_DA", " ='P' ");
        queryParams.put("n_code_DC", " ='PL' ");
        queryParams.put("n_code_DD", " ='00' ");
    }

}
