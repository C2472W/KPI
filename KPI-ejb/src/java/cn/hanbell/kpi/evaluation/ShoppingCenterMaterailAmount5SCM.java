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
public class ShoppingCenterMaterailAmount5SCM extends ShoppingCenterMaterailAmount {

    public ShoppingCenterMaterailAmount5SCM() {
        super();
        queryParams.put("facno", "K");
        queryParams.put("type", " ='进口1'");
    }
}
