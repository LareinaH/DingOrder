package com.cotton.abmallback.model.vo.front;

import java.math.BigDecimal;

/**
 * MyTeamVO
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/14
 */
public class MyTeamVO {

    /**
     * 会员名
     */
    private String name;

    /**
     * 头像
     */
    private String photo;


    /**
     * 会员等级
     */
    private String level;


    /**
     * 直推人数
     */
    private Integer referTotalCount;

    /**
     * 推荐的 代言人及以上级别人数
     */
    private Integer referTotalAgentCount;

    /**
     * 总消费
     */
    private BigDecimal moneyTotalSpend;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getReferTotalCount() {
        return referTotalCount;
    }

    public void setReferTotalCount(Integer referTotalCount) {
        this.referTotalCount = referTotalCount;
    }

    public BigDecimal getMoneyTotalSpend() {
        return moneyTotalSpend;
    }

    public void setMoneyTotalSpend(BigDecimal moneyTotalSpend) {
        this.moneyTotalSpend = moneyTotalSpend;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getReferTotalAgentCount() {
        return referTotalAgentCount;
    }

    public void setReferTotalAgentCount(Integer referTotalAgentCount) {
        this.referTotalAgentCount = referTotalAgentCount;
    }
}
