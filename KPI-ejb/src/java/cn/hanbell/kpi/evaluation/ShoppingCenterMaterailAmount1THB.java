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
public class ShoppingCenterMaterailAmount1THB extends ShoppingCenterMaterailAmount {

    public ShoppingCenterMaterailAmount1THB() {
        queryParams.put("facno", "A");
        queryParams.put("material", "select vdrno from shoppingmanufacturer where facno='A' and materialTypeName in ('铸件','托外加工')");
    }
}
