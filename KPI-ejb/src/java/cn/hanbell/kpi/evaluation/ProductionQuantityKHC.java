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
public class ProductionQuantityKHC extends ProductionQuantity {

    /**
     * 离心机组
     */
    public ProductionQuantityKHC() {
        super();
        //*公司别
        queryParams.put("facno", "K");
        //*生产地
        queryParams.put("prono", "1");
        //*生产线别
        queryParams.put("linecode", " = '01' ");
        //制令种类
        queryParams.put("mankind", "");
        //制令等级
        queryParams.put("typecode", "= '01' ");
        //
        queryParams.put("itcls", " IN ('3H76','3H79','3H80')");

    }
}