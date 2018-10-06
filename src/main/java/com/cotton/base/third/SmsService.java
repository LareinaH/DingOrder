package com.cotton.base.third;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * SmsService
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/28
 */
@Component
public class SmsService {

    private Logger logger = LoggerFactory.getLogger(SmsService.class);

    private final String product = "Dysmsapi";
    private final String domain = "dysmsapi.aliyuncs.com";
    private final String OK = "OK";

    @Value("${sms.accessKeyId}")
    private String accessKeyId = "sms.accessKeyId";
    @Value("${sms.accessKeySecret}")
    private String accessKeySecret = "sms.accessKeySecret";

    public  boolean sendSms(String phoneNum, String signName, String templateCode, Map<String,String> paramMap) {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            logger.error("发送短信失败:",e);
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phoneNum);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);

        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为 "{\"name\":\"Tom\", \"code\":\"123\"}"
        String jsonStr = JSON.toJSONString(paramMap);
        request.setTemplateParam(jsonStr);

        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            logger.error("发送短信失败:",e);
        }

        if(null != sendSmsResponse && sendSmsResponse.getCode() != null && OK.equals(sendSmsResponse.getCode())) {
            return true;
        }
        logger.error("发送短信失败");
        return false;
    }
}
