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
public class ShoppingCenterMaterailAmount3THB extends ShoppingCenterMaterailAmount {

    public ShoppingCenterMaterailAmount3THB() {
        super();
        queryParams.put("facno", "A");
        queryParams.put("material", "select vdrno from shoppingmanufacturer where facno='A' and materialTypeName='轴承'");
    }
}