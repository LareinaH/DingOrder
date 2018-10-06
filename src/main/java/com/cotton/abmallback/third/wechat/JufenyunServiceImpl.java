package com.cotton.abmallback.third.wechat;

import com.alibaba.fastjson.JSON;
import com.cotton.base.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * JufenyunServiceImpl
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/7/12
 */
@Service
public class JufenyunServiceImpl  implements  JufenyunService{

    private Logger logger = LoggerFactory.getLogger(JufenyunServiceImpl.class);

    private  String  appKey = "a62fcd52-dd14-4e94-b824-a579f3774c8a";

    private String url = "https://www.jufenyun.com/openapi/gateway";


    @Override
    public JufenyunResultObject sendRedpack(String openId, BigDecimal money) {

        Map<String,Object> params = new HashMap<>(10);
        params.put("openid",openId);
        params.put("appkey",appKey);
        params.put("method","jfy.redpacks.send");
        params.put("money",money.multiply(new BigDecimal(100)).toBigInteger());

        String result = HttpUtil.doPost(url,params);

        JufenyunResultObject jsonObject = JSON.parseObject(result,JufenyunResultObject.class);

        if(null != jsonObject && jsonObject.getCode() == 0){
            return jsonObject;
        }

        logger.error(result);
        return null;
    }

    @Override
    public JufenyunResultObject getRedpackInfo(String redpack_sn) {

        Map<String,Object> params = new HashMap<>(10);
        params.put("appkey",appKey);
        params.put("method","jfy.redpacks.get");
        params.put("redpack_sn",redpack_sn);

        String result = HttpUtil.doPost(url,params);

        JufenyunResultObject jsonObject = JSON.parseObject(result,JufenyunResultObject.class);

        if(null != jsonObject && jsonObject.getCode() == 0){
            return jsonObject;
        }

        logger.error(result);
        return null;
    }
}
