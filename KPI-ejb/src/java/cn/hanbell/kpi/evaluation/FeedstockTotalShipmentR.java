/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.kpi.evaluation;

/**
 *
 * @author C1749 实际值 %的
 */
public class FeedstockTotalShipmentR extends FeedstockTotalShipment{
    

    public FeedstockTotalShipmentR() {
        super();
        queryParams.put("LINE", "'冷媒'");
    }

}
