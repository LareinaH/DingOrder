package com.cotton.abmallback.third.wechat;

/**
 * JufenyunResultObject
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/7/12
 */
public class JufenyunResultObject {

    /**
     * 错误代码。详情参考下文错误代码列表
     */
    private int code;

    /**
     * 错误消息:如红包余额不足
     */
    private String message;

    /**
     *红包编号	红包编号。此编号为唯一存在。发放通知结果也返回此编号
     */
    private String redpack_sn;

    /**
     *  红包的链接，您可以用微信对话消息封装    https://www.jufenyun.com/XXXX
     */
    private String redpack_url;


    private Redpack redpack;

    public Redpack getRedpack() {
        return redpack;
    }

    public void setRedpack(Redpack redpack) {
        this.redpack = redpack;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRedpack_sn() {
        return redpack_sn;
    }

    public void setRedpack_sn(String redpack_sn) {
        this.redpack_sn = redpack_sn;
    }

    public String getRedpack_url() {
        return redpack_url;
    }

    public void setRedpack_url(String redpack_url) {
        this.redpack_url = redpack_url;
    }

    public static class Redpack{

        /**
         *红包编号	红包编号。此编号为唯一存在。发放通知结果也返回此编号
         */
        private String redpack_sn;


        /**
         *   发放金额	单位为分
         */
        private int money;

        /**
         * 	您公众号的粉丝openid
         */
        private String openid;

        /**
         *  昵称
         */
        private String nickname;

        /**
         * 	支付流水号
         */
        private String consume_sn;

        /**
         *  0:等待发放；1：已发放；2：等待退回；3：已退回；4：发放成功
         */
        private int status;

        public String getRedpack_sn() {
            return redpack_sn;
        }

        public void setRedpack_sn(String redpack_sn) {
            this.redpack_sn = redpack_sn;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getConsume_sn() {
            return consume_sn;
        }

        public void setConsume_sn(String consume_sn) {
            this.consume_sn = consume_sn;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

    }
}
