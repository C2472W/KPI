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
public class ShoppingCenterMaterailAmount17ZCM extends ShoppingCenterMaterailAmount {

    public ShoppingCenterMaterailAmount17ZCM() {
        super();
        queryParams.put("facno", "E");
        queryParams.put("type", " ='空压机'");
    }
}

