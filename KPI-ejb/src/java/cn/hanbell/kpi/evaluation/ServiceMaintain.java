/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.kpi.evaluation;

import cn.hanbell.kpi.comm.Actual;
import cn.hanbell.kpi.comm.SuperEJBForMES;
import java.util.LinkedHashMap;
import javax.naming.InitialContext;

/**
 *
 * @author C1879 服务维修
 */
public abstract class ServiceMaintain implements Actual {

    protected SuperEJBForMES superEJB;

    protected LinkedHashMap<String, Object> queryParams;

    public ServiceMaintain() {
        queryParams = new LinkedHashMap<>();
    }

    public SuperEJBForMES getEJB() {
        return superEJB;
    }

    @Override
    public void setEJB(String JNDIName) throws Exception {
        InitialContext c = new InitialContext();
        Object objRef = c.lookup(JNDIName);
        superEJB = (SuperEJBForMES) objRef;
    }

    @Override
    public LinkedHashMap<String, Object> getQueryParams() {
        return queryParams;
    }

    @Override
    public int getUpdateMonth(int y, int m) {
        return m;
    }

    @Override
    public int getUpdateYear(int y, int m) {
        return y;
    }
}
