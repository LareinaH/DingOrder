package com.cotton.abmallback.model;

import com.cotton.base.model.BaseModel;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sms_captcha")
public class SmsCaptcha extends BaseModel {
    /**
     * 手机号码
     */
    @Column(name = "phone_num")
    private String phoneNum;

    /**
     * 验证码
     */
    private String captcha;

    /**
     * 已经验证次数
     */
    private Integer times;

    /**
     * 验证码有效期
     */
    @Column(name = "gmt_expires")
    private Date gmtExpires;

    /**
     * 获取手机号码
     *
     * @return phone_num - 手机号码
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * 设置手机号码
     *
     * @param phoneNum 手机号码
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    /**
     * 获取验证码
     *
     * @return captcha - 验证码
     */
    public String getCaptcha() {
        return captcha;
    }

    /**
     * 设置验证码
     *
     * @param captcha 验证码
     */
    public void setCaptcha(String captcha) {
        this.captcha = captcha == null ? null : captcha.trim();
    }

    /**
     * 获取已经验证次数
     *
     * @return times - 已经验证次数
     */
    public Integer getTimes() {
        return times;
    }

    /**
     * 设置已经验证次数
     *
     * @param times 已经验证次数
     */
    public void setTimes(Integer times) {
        this.times = times;
    }

    /**
     * 获取验证码有效期
     *
     * @return gmt_expires - 验证码有效期
     */
    public Date getGmtExpires() {
        return gmtExpires;
    }

    /**
     * 设置验证码有效期
     *
     * @param gmtExpires 验证码有效期
     */
    public void setGmtExpires(Date gmtExpires) {
        this.gmtExpires = gmtExpires;
    }
}