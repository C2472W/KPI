/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.kpi.control;

import cn.hanbell.kpi.ejb.CompanyGrantBean;
import cn.hanbell.kpi.entity.CompanyGrant;
import cn.hanbell.eap.ejb.CompanyBean;
import cn.hanbell.eap.ejb.SystemUserBean;
import cn.hanbell.eap.entity.Company;
import cn.hanbell.eap.entity.SystemGrantPrg;
import cn.hanbell.eap.entity.SystemUser;
import cn.hanbell.kpi.entity.RoleGrantModule;
import com.lightshell.comm.BaseLib;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author C0160
 */
@ManagedBean(name = "userManagedBean")
@SessionScoped
public class UserManagedBean implements Serializable {

    @EJB
    private CompanyBean companyBean;

    @EJB
    private SystemUserBean systemUserBean;

    @EJB
    private CompanyGrantBean companyGrantBean;

    private Company currentCompany;
    private SystemUser currentUser;
    private boolean status;
    private Date baseDate;

    private String company;
    private String userid;
    private String mobile;
    private String email;
    private String pwd;
    private String newpwd;
    private String secpwd;

    private List<SystemGrantPrg> systemGrantPrgList;
    private List<RoleGrantModule> roleGrantDeptList;
    private List<Company> companyList;

    public UserManagedBean() {
        status = false;
    }

    @PostConstruct
    public void construct() {
        companyList = companyBean.findAll();
        Calendar c = Calendar.getInstance();
        c.setTime(BaseLib.getDate());
        c.add(Calendar.DATE, 0 - c.get(Calendar.DATE));
        baseDate = c.getTime();
    }

    public boolean checkUser() {
        return true;
    }

    public SystemUser findById(int id) {
        return systemUserBean.findById(id);
    }

    public String login() {
        if (getUserid().length() == 0 || getPwd().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "请输入用户名和密码"));
            return "";
        }
        secpwd = BaseLib.securityMD5(getPwd());
        try {
            SystemUser u = null;
            if ("Admin".equals(userid)) {
                u = systemUserBean.findByUserIdAndPwd(userid, secpwd);
                if (u == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "用户名或密码错误"));
                    status = false;
                    return "";
                }
            } else if (cn.hanbell.util.BaseLib.ADAuth("172.16.10.6:389", userid + "@hanbell.com.cn", pwd)) {
                u = systemUserBean.findByUserId(getUserid());
            }
            if (u != null) {
                this.company = "C";
                this.currentCompany = companyBean.findByCompany("C");
                if ("Admin".equals(u.getUserid())) {
                    currentCompany = companyBean.findByCompany(company);
                    if (currentCompany == null) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "请维护公司信息"));
                    }
                } else {
                    //此处加入公司授权检查
                    CompanyGrant cg = companyGrantBean.findByCompanyAndUserid(company, userid);
                    if (cg == null) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "您无权访问此公司别"));
                        status = false;
                        return "";
                    }
                    currentCompany = companyBean.findByCompany(company);
                    if (currentCompany == null) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "请维护公司信息"));
                        status = false;
                        return "";
                    }
                }
                currentUser = u;
                status = true;
                mobile = u.getUserid();
                updateLoginTime();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "用户名或密码不正确！"));
            status = false;
            return "login";
        }
        return "home";
    }

    public String logout() {
        if (status) {
            currentUser = null;
            status = false;
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            if (session != null) {
                session.invalidate();
            }
            return "login";
        } else {
            return "home";
        }
    }

    public void update() {
        if (currentUser != null) {
            if (mobile != null && !mobile.equals("") && !mobile.equals(currentUser.getUserid())) {
                currentUser.setUserid(mobile);
            }
            if (email != null && !email.equals("") && !email.equals(currentUser.getEmail())) {
                currentUser.setEmail(email);
            }
            try {
                systemUserBean.update(currentUser);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "更新成功"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "更新失败！"));
            }
        }
    }

    public void updateLoginTime() {
        if (currentUser != null) {
            currentUser.setLastlogin(currentUser.getNewestlogin());
            currentUser.setNewestlogin(BaseLib.getDate());
            update();
        }
    }

    public void updatePwd() {
        secpwd = BaseLib.securityMD5(getPwd());
        SystemUser u = systemUserBean.findByUserIdAndPwd(getUserid(), getSecpwd());
        if (u != null) {
            secpwd = BaseLib.securityMD5(newpwd);
            currentUser.setPassword(secpwd);
            update();
            pwd = "";
            newpwd = "";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "更新密码成功"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn", "原密码错误"));
        }
    }

    /**
     * @return the currentCompany
     */
    public Company getCurrentCompany() {
        return currentCompany;
    }

    /**
     * @return the currentUser
     */
    public SystemUser getCurrentUser() {
        return currentUser;
    }

    public boolean getStatus() {
        return status;
    }

    /**
     * @return the baseDate
     */
    public Date getBaseDate() {
        return baseDate;
    }

    /**
     * @param baseDate the baseDate to set
     */
    public void setBaseDate(Date baseDate) {
        this.baseDate = baseDate;
    }

    /**
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * @return the userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid the userid to set
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * @return the pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd the pwd to set
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * @return the newpwd
     */
    public String getNewpwd() {
        return newpwd;
    }

    /**
     * @param newpwd the newpwd to set
     */
    public void setNewpwd(String newpwd) {
        this.newpwd = newpwd;
    }

    /**
     * @return the secpwd
     */
    public String getSecpwd() {
        return secpwd;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the systemGrantPrgList
     */
    public List<SystemGrantPrg> getSystemGrantPrgList() {
        return systemGrantPrgList;
    }

    /**
     * @param systemGrantPrgList the systemGrantPrgList to set
     */
    public void setSystemGrantPrgList(List<SystemGrantPrg> systemGrantPrgList) {
        this.systemGrantPrgList = systemGrantPrgList;
    }

    /**
     * @return the companyList
     */
    public List<Company> getCompanyList() {
        return companyList;
    }

    /**
     * @return the roleGrantDeptList
     */
    public List<RoleGrantModule> getRoleGrantDeptList() {
        return roleGrantDeptList;
    }

    /**
     * @param roleGrantDeptList the roleGrantDeptList to set
     */
    public void setRoleGrantDeptList(List<RoleGrantModule> roleGrantDeptList) {
        this.roleGrantDeptList = roleGrantDeptList;
    }

}
