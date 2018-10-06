package com.cotton.abmallback.manager;

import com.cotton.abmallback.model.SmsCaptcha;
import com.cotton.abmallback.service.SmsCaptchaService;
import com.cotton.base.third.SmsService;
import com.cotton.base.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * SmsManager
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/28
 */

@Service
public class SmsManager {

    private SmsService smsService;
    private SmsCaptchaService smsCaptchaService;

    @Value("${sms.captcha.template.code}")
    private final String captchaTemplateCode = "SMS_139239325";
    @Value("${sms.sign.name}")
    private final String signName="云鼎绿色";

    @Autowired
    public SmsManager(SmsService smsService, SmsCaptchaService smsCaptchaService) {
        this.smsService = smsService;
        this.smsCaptchaService = smsCaptchaService;
    }

    public boolean sendCaptcha(String phoneNum){

        //生成验证码
        String captcha = RandomUtil.getRandomNumCode(6);

        if(sendCheckCode(phoneNum,captcha)){
            SmsCaptcha model = new SmsCaptcha();
            model.setPhoneNum(phoneNum);
            model.setCaptcha(captcha);
            //设置过期时间为三分钟
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.now();
            localDateTime = localDateTime.plusMinutes(3);
            ZonedDateTime zdt = localDateTime.atZone(zoneId);

            model.setGmtExpires(Date.from(zdt.toInstant()));
            smsCaptchaService.insert(model);

            return true;
        }

        return false;
    }

    public boolean checkCaptcha(String phoneNum,String captcha){

        Example example = new Example(SmsCaptcha.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phoneNum",phoneNum);
        criteria.andEqualTo("captcha",captcha);
        criteria.andGreaterThanOrEqualTo("gmtExpires",new Date());

        List<SmsCaptcha> smsCaptchaList = smsCaptchaService.queryList(example);

        return null != smsCaptchaList && !smsCaptchaList.isEmpty();
    }


    private boolean sendCheckCode (String phoneNum, String captcha){
        Map<String ,String> paramMap = new HashMap<>(2);
       // paramMap.put("name","");
        paramMap.put("code",captcha);

        return smsService.sendSms(phoneNum,signName, captchaTemplateCode,paramMap);
    }

}
