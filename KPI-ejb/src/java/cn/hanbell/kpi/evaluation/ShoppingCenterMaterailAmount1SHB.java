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
public class ShoppingCenterMaterailAmount1SHB extends ShoppingCenterMaterailAmount {

    public ShoppingCenterMaterailAmount1SHB(){
        queryParams.put("facno", "'C'");
        queryParams.put("prono", "'1'");
         queryParams.put("material", "select vdrno from shoppingmanufacturer where facno='C' and materialTypeName in ('铸件','加工')");
    }

}
