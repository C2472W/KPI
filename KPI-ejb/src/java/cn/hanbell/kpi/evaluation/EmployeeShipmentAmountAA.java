/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.kpi.evaluation;

/**
 *
 * @author C1879
 */
public class EmployeeShipmentAmountAA extends EmployeeShipmentAmount {

    public EmployeeShipmentAmountAA() {
        super();
        queryParams.put("facno", "C");
        queryParams.put("deptno", "('1Q000','1Q100')");
        queryParams.put("n_code_DA", " ='AA' ");
        queryParams.put("n_code_DD", " IN ('00','02') ");
        queryParams.put("ogdkid", "IN ('RL01','RL03')");
    }

}
