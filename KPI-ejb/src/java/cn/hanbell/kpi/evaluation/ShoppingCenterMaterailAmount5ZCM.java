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
public class ShoppingCenterMaterailAmount5ZCM extends ShoppingCenterMaterailAmount {

    public ShoppingCenterMaterailAmount5ZCM() {
        super();
        queryParams.put("facno", "E");
        queryParams.put("type", " ='进口1'");
    }
}
