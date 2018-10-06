package com.cotton.base.third;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.dto.GtReq;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.AbstractNotifyStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * GeTuiService
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/29
 */
@Component
public class GeTuiService {

    @Value("${getui.appId}")
    private  String appId = "";
    @Value("${getui.appKey}")
    private  String appKey = "";
    @Value("${getui.masterSecret}")
    private  String masterSecret = "";
    @Value("${getui.url}")
    private  String url = "http://sdk.open.api.igexin.com/apiex.htm";

    public  void pushMessage(String title,String text,long memberId) {

        IGtPush push = new IGtPush(url, appKey, masterSecret);

        NotificationTemplate template = new NotificationTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTitle(title);
        template.setText(text);
        template.setTransmissionType(1);

        List<String> appIds = new ArrayList<>();
        appIds.add(String.valueOf(appId));

        // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
        SingleMessage message = new SingleMessage();
        message.setData(template);
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 600);

        Target target = new Target();
        target.setAppId(appId);
        target.setAlias(String.valueOf(memberId));


        IPushResult ret = push.pushMessageToSingle(message,target);
        System.out.println(ret.getResponse().toString());
    }
}
