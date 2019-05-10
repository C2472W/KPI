/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.kpi.rpt;

import cn.hanbell.kpi.control.UserManagedBean;
import cn.hanbell.kpi.ejb.BalanceSheetBean;
import cn.hanbell.kpi.ejb.IndicatorChartBean;
import cn.hanbell.kpi.entity.IndicatorChart;
import cn.hanbell.kpi.web.BscQueryTableManageBean;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author C1879
 */
@ManagedBean(name = "cashFlowSheetReportBean")
@ViewScoped
public class CashFlowSheetReportBean extends BscQueryTableManageBean implements Serializable {

    @EJB
    protected BalanceSheetBean balanceSheetBean;

    @EJB
    protected IndicatorChartBean indicatorChartBean;

    protected Date btndate;
    protected LinkedHashMap<String, String[]> map;
    protected LinkedHashMap<String, String> statusMap;

    @ManagedProperty(value = "#{userManagedBean}")
    protected UserManagedBean userManagedBean;

    FacesContext fc;
    ExternalContext ec;
    protected IndicatorChart indicatorChart;

    public CashFlowSheetReportBean() {
    }

    public Calendar settlementDate() {
        Calendar c = Calendar.getInstance();
        c.setTime(getUserManagedBean().getBaseDate());
        return c;
    }

    public Calendar getdate() {
        Calendar c = Calendar.getInstance();
        c.setTime(getBtndate());
        return c;
    }

    @PostConstruct
    public void construct() {
        fc = FacesContext.getCurrentInstance();
        ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        String id = request.getParameter("id");
        if (id == null) {
            fc.getApplication().getNavigationHandler().handleNavigation(fc, null, "error");
        }
        indicatorChart = indicatorChartBean.findById(Integer.valueOf(id));
        if (indicatorChart == null) {
            fc.getApplication().getNavigationHandler().handleNavigation(fc, null, "error");
        }
        setMap(new LinkedHashMap<>());
        statusMap = new LinkedHashMap<>();
        statusMap.put("displaydiv1", "block");
        statusMap.put("displaydiv2", "none");
        setBtndate(settlementDate().getTime());
    }

    public void btnreset() {;
        setBtndate(settlementDate().getTime());
    }

    public void btnquery() {
        map = new LinkedHashMap<>();
        statusMap.put("displaydiv1", "block");
        statusMap.put("displaydiv2", "none");
        boolean aa = true;
        if (getBtndate().after(settlementDate().getTime())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "日期选择不能超过系统结算日期！"));
            aa = false;
        }
        if (aa) {
            map = balanceSheetBean.getCashFlowMap(btndate);
            if (getMap() != null && !map.isEmpty()) {
                statusMap.put("displaydiv1", "none");
                statusMap.put("displaydiv2", "block");
                statusMap.put("th1title", (getdate().get(Calendar.MONTH) + 1)==1?getdate().get(Calendar.YEAR)+"年 1月":getTitle(getdate().get(Calendar.YEAR), getdate().get(Calendar.MONTH) + 1));
                statusMap.put("th2title", (getdate().get(Calendar.MONTH) + 1)==1?getdate().get(Calendar.YEAR)-1+"年 1月":getTitle(getdate().get(Calendar.YEAR) - 1, getdate().get(Calendar.MONTH) + 1));
                super.getRemarkOne(indicatorChart, getdate().get(Calendar.YEAR), getdate().get(Calendar.MONTH) + 1);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "无法查询到该日期的数据，请重新查询！"));
            }
        }
    }

    private String getTitle(int y, int m) {
        return y + "年 1~" + m + " 月";
    }

    /**
     * @return the btndate
     */
    public Date getBtndate() {
        return btndate;
    }

    /**
     * @param btndate the btndate to set
     */
    public void setBtndate(Date btndate) {
        this.btndate = btndate;
    }

    /**
     * @return the statusMap
     */
    public LinkedHashMap<String, String> getStatusMap() {
        return statusMap;
    }

    /**
     * @param statusMap the statusMap to set
     */
    public void setStatusMap(LinkedHashMap<String, String> statusMap) {
        this.statusMap = statusMap;
    }

    /**
     * @return the userManagedBean
     */
    public UserManagedBean getUserManagedBean() {
        return userManagedBean;
    }

    /**
     * @param userManagedBean the userManagedBean to set
     */
    public void setUserManagedBean(UserManagedBean userManagedBean) {
        this.userManagedBean = userManagedBean;
    }

    /**
     * @return the map
     */
    public LinkedHashMap<String, String[]> getMap() {
        return map;
    }

    /**
     * @param map the map to set
     */
    public void setMap(LinkedHashMap<String, String[]> map) {
        this.map = map;
    }

}