/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.kpi.lazy;

import cn.hanbell.kpi.entity.MailSetting;
import cn.hanbell.kpi.entity.ShoppingMenuWeight;
import com.lightshell.comm.BaseLazyModel;
import com.lightshell.comm.SuperEJB;

/**
 *
 * @author C2082
 */
public class ShoppingMenuWeightModel extends BaseLazyModel<ShoppingMenuWeight> {

    public ShoppingMenuWeightModel(SuperEJB superEJB) {
        this.superEJB = superEJB;
    }
}
