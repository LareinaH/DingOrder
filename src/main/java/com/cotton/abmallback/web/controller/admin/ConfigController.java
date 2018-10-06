package com.cotton.abmallback.web.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.cotton.abmallback.enumeration.*;
import com.cotton.base.common.RestResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * ConfigController
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/25
 */
@Controller
@RequestMapping("/admin")
public class ConfigController {

    @ResponseBody
    @RequestMapping(value = "/enum")
    public RestResponse<Object> getEnum(String name) {
        Object object;

        switch (name){
            case "OrderStatus":
                object = Arrays.stream(OrderStatusEnum.values()).map(x -> {
                    JSONObject jo = new JSONObject();
                    jo.put("value", x.name());
                    jo.put("label", x.getDisplayName());
                    return jo;
                }).collect(Collectors.toList());

                break;
            case "returnStatus":
                object = Arrays.stream(OrderReturnStatusEnum.values()).map(x -> {
                    JSONObject jo = new JSONObject();
                    jo.put("value", x.name());
                    jo.put("label", x.getDisplayName());
                    return jo;
                }).collect(Collectors.toList());
                break;
            case "platformMessageLevel":
                object = Arrays.stream(PlatformMessageLevelEnum.values()).map(x -> {
                    JSONObject jo = new JSONObject();
                    jo.put("value", x.name());
                    jo.put("label", x.getDisplayName());
                    return jo;
                }).collect(Collectors.toList());
                break;

            case "platformMessageStatus":
                object = Arrays.stream(PlatformMessageStatusEnum.values()).map(x -> {
                    JSONObject jo = new JSONObject();
                    jo.put("value", x.name());
                    jo.put("label", x.getDisplayName());
                    return jo;
                }).collect(Collectors.toList());
                break;
            case "contentStudyStatus":
                object = Arrays.stream(ContentStudyStatusEnum.values()).map(x -> {
                    JSONObject jo = new JSONObject();
                    jo.put("value", x.name());
                    jo.put("label", x.getDisplayName());
                    return jo;
                }).collect(Collectors.toList());
                break;
            case "memberLevel":
                object = Arrays.stream(MemberLevelEnum.values()).map(x -> {
                    JSONObject jo = new JSONObject();
                    jo.put("value", x.name());
                    jo.put("label", x.getDisplayName());
                    return jo;
                }).collect(Collectors.toList());
                break;
            default:
                object = null;
                break;
        }

        return RestResponse.getSuccesseResponse(object);
    }
}
