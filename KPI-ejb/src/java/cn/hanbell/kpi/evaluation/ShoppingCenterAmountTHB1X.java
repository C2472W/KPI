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
public class ShoppingCenterAmountTHB1X extends ShoppingCenterAmountTHB{
    
       public ShoppingCenterAmountTHB1X() {
        super();
        queryParams.put("vdrno", " select vdrno from shoppingmanufacturer where facno='A' ");  
    }
    
}