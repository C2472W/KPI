/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.kpi.evaluation;

/**
 *
 * @author C1749
 */
public class InventoryAmountA4E50 extends InventoryAmountA4 {

    public InventoryAmountA4E50() {
        super();
        queryParams.put("facno", "C");
        queryParams.put("trtype", "CA");
        queryParams.put("deptno", "1D");

    }

}
