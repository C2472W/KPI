/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.kpi.evaluation;

/**
 *
 * @author C1749
 * 旋片泵机体的客诉笔数
 */
public class ComplaintsQualityPenpzkPjt1 extends ComplaintsQualityPenpzk {

    public ComplaintsQualityPenpzkPjt1() {
        super();
        queryParams.put("BQ197", "%P%");
        queryParams.put("BQ003", " in ('PZK') ");
        queryParams.put("BQ134", " in ('YX','-1') ");
        queryParams.put("BQ110", " in ('Y') ");
        //queryParams.put("BQ002", " in ('STW00012') ");
        queryParams.put("CA002", " in ('P机体') ");
    }

}
