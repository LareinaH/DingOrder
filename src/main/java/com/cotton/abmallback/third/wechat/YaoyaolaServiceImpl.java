package com.cotton.abmallback.third.wechat;

import com.alibaba.fastjson.JSONObject;
import com.cotton.base.utils.HttpUtil;
import com.gexin.rp.sdk.base.uitls.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * YaoyaolaServiceImpl
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/9/23
 */
@Service
public class YaoyaolaServiceImpl implements YaoyaolaService {

    private Logger logger = LoggerFactory.getLogger(YaoyaolaServiceImpl.class);

    private  String  apikey = "1qaz2wsx";

    private String url = "http://www.yaoyaola.cn/index.php/exapi/hbticket";

    private String uid = "24666";

    @Override
    public JufenyunResultObject sendRedpack(String openId, BigDecimal money) {


        Map<String,Object> params = new HashMap<>(10);
        params.put("uid",uid);
        params.put("type",0);
        BigInteger moneyInt = money.multiply(new BigDecimal(100)).toBigInteger();
        params.put("money",moneyInt);
        String reqtick = String.valueOf(System.currentTimeMillis()/1000);
        params.put("reqtick",reqtick);
        String orderId = "red" + reqtick;
        params.put("orderid",orderId);
        params.put("sign",sign(uid,"0",orderId,String.valueOf(moneyInt),reqtick,apikey));
        params.put("title","提现红包");
        params.put("sendname","绿色云鼎");
        params.put("wishing","恭喜领取红包");

        String result = HttpUtil.doGet(url,params);

        JSONObject jsonObject = JSONObject.parseObject(result);

        if(null != jsonObject && jsonObject.get("errcode").toString().equals("0")){

            String ticket = jsonObject.get("ticket").toString();

            if(StringUtils.isBlank(ticket)){
                return null;
            }else {
                JufenyunResultObject  jufenyunResultObject = new JufenyunResultObject();

                String redpack_url =  "http://www.yaoyaola.cn/index.php/exapi/gethb?uid=24666&ticket=" + ticket;
                jufenyunResultObject.setRedpack_url(redpack_url);
                jufenyunResultObject.setRedpack_sn(ticket);

                return jufenyunResultObject;

            }
        }

        logger.error(result);
        return null;
    }

    @Override
    public JufenyunResultObject getRedpackInfo(String ticket) {

        String url = "http://www.yaoyaola.cn/index.php/exapi/checkhb";
        Map<String,Object> params = new HashMap<>(10);
        params.put("uid",uid);
        params.put("ticket",ticket);

        String result = HttpUtil.doGet(url,params);

        JSONObject jsonObject = JSONObject.parseObject(result);

        if(null != jsonObject && jsonObject.get("errcode").toString().equals("0")) {

            JufenyunResultObject jufenyunResultObject = new JufenyunResultObject();
            JufenyunResultObject.Redpack redpack = new JufenyunResultObject.Redpack();
            jufenyunResultObject.setRedpack(redpack);
            int status = Integer.valueOf(jsonObject.get("status").toString());

            redpack.setStatus(status);

            return jufenyunResultObject;
        }
        return null;
    }


    private String sign(String uid,String type,String orderid,String money,String reqtick,String apikey){
        return MD5Util.getMD5Format(uid + type + orderid + money + reqtick + apikey);
    }
}
