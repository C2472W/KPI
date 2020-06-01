/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.kpi.control;

import cn.hanbell.kpi.ejb.ScorecardBean;
import cn.hanbell.kpi.ejb.ScorecardContentBean;
import cn.hanbell.kpi.entity.Scorecard;
import cn.hanbell.kpi.entity.ScorecardContent;
import cn.hanbell.kpi.lazy.ScorecardContentModel;
import cn.hanbell.kpi.web.SuperSingleBean;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author C0160
 */
@ManagedBean(name = "scorecardManagedBean")
@ViewScoped
public class ScorecardManagedBean extends SuperSingleBean<ScorecardContent> {

    @EJB
    private ScorecardBean scorecardBean;
    @EJB
    private ScorecardContentBean scorecardContentBean;

    protected Calendar c;
    private Scorecard scorecard;

    public ScorecardManagedBean() {
        super(ScorecardContent.class);
        c = Calendar.getInstance();
    }

    public void calcScore() {
        if (scorecard != null) {
            String col;
            BigDecimal value;
            col = scorecardBean.getColumn("sq", userManagedBean.getQ());
            List<ScorecardContent> detail = scorecardContentBean.findByPId(scorecard.getId());
            if (detail != null && !detail.isEmpty()) {
                for (ScorecardContent c : detail) {

                }
            }
            try {
                value = scorecardBean.getScore(detail, col);
                switch (userManagedBean.getQ()) {
                    case 1:
                        scorecard.setSq1(value);
                        break;
                    case 2:
                        scorecard.setSq2(value);
                        value = scorecardBean.getScore(detail, "sh1");
                        scorecard.setSh1(value);
                        break;
                    case 3:
                        scorecard.setSq3(value);
                        break;
                    case 4:
                        scorecard.setSq4(value);
                        value = scorecardBean.getScore(detail, "sh2");
                        scorecard.setSh2(value);
                        value = scorecardBean.getScore(detail, "sfy");
                        scorecard.setSfy(value);
                        break;
                }
                scorecardBean.update(scorecard);
                showInfoMsg("Info", "更新成功");
            } catch (Exception ex) {
                showErrorMsg("Error", ex.getMessage());
            }
        }
    }

    @Override
    protected boolean doBeforeUpdate() throws Exception {
        if (currentEntity != null && (currentEntity.getScoreJexl() == null || "".equals(currentEntity.getScoreJexl()))) {
            if (currentEntity.getDeptScore().getSfy().compareTo(BigDecimal.ZERO) != 0) {
                currentEntity.setSfy(currentEntity.getDeptScore().getSfy());
            }
            if (currentEntity.getDeptScore().getSh2().compareTo(BigDecimal.ZERO) != 0) {
                currentEntity.setSh2(currentEntity.getDeptScore().getSh2());
            }
            if (currentEntity.getDeptScore().getSh1().compareTo(BigDecimal.ZERO) != 0) {
                currentEntity.setSh1(currentEntity.getDeptScore().getSh1());
            }
            if (currentEntity.getDeptScore().getSq1().compareTo(BigDecimal.ZERO) != 0) {
                currentEntity.setSq1(currentEntity.getDeptScore().getSq1());
            }
            if (currentEntity.getDeptScore().getSq2().compareTo(BigDecimal.ZERO) != 0) {
                currentEntity.setSq2(currentEntity.getDeptScore().getSq2());
            }
            if (currentEntity.getDeptScore().getSq3().compareTo(BigDecimal.ZERO) != 0) {
                currentEntity.setSq3(currentEntity.getDeptScore().getSq3());
            }
            if (currentEntity.getDeptScore().getSq4().compareTo(BigDecimal.ZERO) != 0) {
                currentEntity.setSq4(currentEntity.getDeptScore().getSq4());
            }
            if (currentEntity.getGeneralScore().getSfy().compareTo(BigDecimal.ZERO) != 0) {
                currentEntity.setSfy(currentEntity.getGeneralScore().getSfy());
            } else if (currentEntity.getWeight() == 0) {
                currentEntity.setSfy(currentEntity.getGeneralScore().getSfy());
            }
            if (currentEntity.getGeneralScore().getSh2().compareTo(BigDecimal.ZERO) != 0) {
                currentEntity.setSh2(currentEntity.getGeneralScore().getSh2());
            } else if (currentEntity.getWeight() == 0) {
                currentEntity.setSh2(currentEntity.getGeneralScore().getSh2());
            }
            if (currentEntity.getGeneralScore().getSh1().compareTo(BigDecimal.ZERO) != 0) {
                currentEntity.setSh1(currentEntity.getGeneralScore().getSh1());
            } else if (currentEntity.getWeight() == 0) {
                currentEntity.setSh1(currentEntity.getGeneralScore().getSh1());
            }
            if (currentEntity.getGeneralScore().getSq1().compareTo(BigDecimal.ZERO) != 0) {
                currentEntity.setSq1(currentEntity.getGeneralScore().getSq1());
            } else if (currentEntity.getWeight() == 0) {
                currentEntity.setSq1(currentEntity.getGeneralScore().getSq1());
            }
            if (currentEntity.getGeneralScore().getSq2().compareTo(BigDecimal.ZERO) != 0) {
                currentEntity.setSq2(currentEntity.getGeneralScore().getSq2());
            } else if (currentEntity.getWeight() == 0) {
                currentEntity.setSq2(currentEntity.getGeneralScore().getSq2());
            }
            if (currentEntity.getGeneralScore().getSq3().compareTo(BigDecimal.ZERO) != 0) {
                currentEntity.setSq3(currentEntity.getGeneralScore().getSq3());
            } else if (currentEntity.getWeight() == 0) {
                currentEntity.setSq3(currentEntity.getGeneralScore().getSq3());
            }
            if (currentEntity.getGeneralScore().getSq4().compareTo(BigDecimal.ZERO) != 0) {
                currentEntity.setSq4(currentEntity.getGeneralScore().getSq4());
            } else if (currentEntity.getWeight() == 0) {
                currentEntity.setSq4(currentEntity.getGeneralScore().getSq4());
            }
        }
        return super.doBeforeUpdate();
    }

    @Override
    public void init() {
        fc = FacesContext.getCurrentInstance();
        ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        String id = request.getParameter("id");
        if (id == null) {
            fc.getApplication().getNavigationHandler().handleNavigation(fc, null, "error");
        }
        setScorecard(scorecardBean.findById(Integer.valueOf(id)));
        if (getScorecard() == null) {
            fc.getApplication().getNavigationHandler().handleNavigation(fc, null, "error");
        }
        c.setTime(userManagedBean.getBaseDate());
        superEJB = scorecardContentBean;
        model = new ScorecardContentModel(scorecardContentBean, this.userManagedBean);
        model.getSortFields().put("seq", "ASC");
        model.getSortFields().put("deptno", "ASC");
        model.getFilterFields().put("parent.seq", c.get(Calendar.YEAR));
        model.getFilterFields().put("pid", getScorecard().getId());
        super.init();
    }

    /**
     * @return the scorecard
     */
    public Scorecard getScorecard() {
        return scorecard;
    }

    /**
     * @param scorecard the scorecard to set
     */
    public void setScorecard(Scorecard scorecard) {
        this.scorecard = scorecard;
    }

}
