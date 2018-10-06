package com.cotton.abmallback.web.controller.front;

import com.cotton.abmallback.third.wechat.mp.config.WechatMpProperties;
import com.cotton.base.common.RestResponse;
import com.cotton.base.controller.BaseController;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * WechatMpController
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/20
 */
@Controller
@RequestMapping("/wechat/portal")
public class WechatMpController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final WxMpService wxService;

    private final WxMpMessageRouter router;

    private final WechatMpProperties wechatMpProperties;

    @Autowired
    public WechatMpController(WxMpService wxService, WxMpMessageRouter router, WechatMpProperties wechatMpProperties) {
        this.wxService = wxService;
        this.router = router;
        this.wechatMpProperties = wechatMpProperties;
    }

    @GetMapping(produces = "text/plain;charset=utf-8")
    public void authGet(HttpServletResponse httpServletResponse,
            @RequestParam(name = "signature",
                    required = false) String signature,
            @RequestParam(name = "timestamp",
                    required = false) String timestamp,
            @RequestParam(name = "nonce", required = false) String nonce,
            @RequestParam(name = "echostr", required = false) String echostr) {

        this.logger.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature,
                timestamp, nonce, echostr);

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        try {
            PrintWriter printWriter =httpServletResponse.getWriter();


            if (this.wxService.checkSignature(timestamp, nonce, signature)) {

                logger.info("\n 输出的参数为: " + echostr);


                printWriter.write(echostr);
                return;
            }

            logger.error("\n 校验失败");

            printWriter.write("校验失败");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping(produces = "text/html; charset=UTF-8")
    public void post(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam(name = "encrypt_type",
                               required = false) String encType,
                       @RequestParam(name = "msg_signature",
                               required = false) String msgSignature) {


        String requestBody = null;
        try {
            BufferedReader br = null;
            br = httpServletRequest.getReader();
            String str;
            StringBuilder xmlData = new StringBuilder();
            while((str = br.readLine()) != null){
                xmlData.append(str);
            }
            requestBody =xmlData.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.logger.info(
                "\n接收微信请求：[signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                signature, encType, msgSignature, timestamp, nonce, requestBody);

        if (!this.wxService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        String out = "";
        if (encType == null) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            WxMpXmlOutMessage outMessage = this.route(inMessage);
            if (outMessage != null) {
                out = outMessage.toXml();
            }
        } else if ("aes".equals(encType)) {
            // aes加密的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(
                    requestBody, this.wxService.getWxMpConfigStorage(), timestamp,
                    nonce, msgSignature);
            this.logger.info("\n消息解密后内容为：\n{} ", inMessage.toString());
            WxMpXmlOutMessage outMessage = this.route(inMessage);
            if (outMessage != null) {
                out = outMessage.toEncryptedXml(this.wxService.getWxMpConfigStorage());
            }
        }

        logger.info("\n组装回复信息：{}", out);

        try {
            httpServletResponse.getOutputStream().write(out.getBytes("UTF-8"));
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
    }


    @ResponseBody
    @RequestMapping(value = "/config", method = {RequestMethod.GET})
    public RestResponse<WxJsapiSignature> getConfig(String url){
        try {
            WxJsapiSignature signature = wxService.createJsapiSignature(url);
            return RestResponse.getSuccesseResponse(signature);
        } catch (WxErrorException e) {
            this.logger.error(e.getMessage(), e);
            return RestResponse.getFailedResponse(500,"获取配置失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/createMenu", method = {RequestMethod.GET})
    public RestResponse<Void> createMenu(){

        try {


            //微信商城
            WxMenu wxMenu = new WxMenu();
            List<WxMenuButton> wxMenuButtonList = new ArrayList<>();
            WxMenuButton wxMenuButton = new WxMenuButton();
            wxMenuButton.setType("view");
            wxMenuButton.setName("走进云鼎");
            wxMenuButtonList.add(wxMenuButton);
            List<WxMenuButton> subButton = new ArrayList<>();

            WxMenuButton sub1 = new WxMenuButton();
            sub1.setType("view");
            sub1.setName("云鼎官网");
            sub1.setUrl("http://yund.live/");
            subButton.add(sub1);

            WxMenuButton sub2 = new WxMenuButton();
            sub2.setType("view");
            sub2.setName("点我尝新");
            sub2.setUrl("http://56048477.m.weimob.com/vshop_fx/index.php?c=newgoods&m=newgoodsdetails&goodsid=1299261&aid=56048477&share_openid=oFPye0kolGp7GCpX0KZa7YgcIpn4&t=1535954027&fid=0&from=singlemessage");
            subButton.add(sub2);

            wxMenuButton.setSubButtons(subButton);

            WxMenuButton wxMenuButton2 = new WxMenuButton();
            wxMenuButton2.setType("view");
            wxMenuButton2.setName("云鼎商城");
            wxMenuButton2.setUrl("http://wx.yund.live/wxindex.html");
            wxMenuButtonList.add(wxMenuButton2);
            wxMenu.setButtons(wxMenuButtonList);
            wxService.getMenuService().menuCreate(wxMenu);

            WxMpMenu wxMpMenu = wxService.getMenuService().menuGet();

            logger.info(wxMpMenu.toJson());

        } catch (WxErrorException e) {

            this.logger.debug("\n创建菜单失败：{}", e);
        }

        return RestResponse.getSuccesseResponse();
    }

    private WxMpXmlOutMessage route(WxMpXmlMessage message) {
        try {
            return this.router.route(message);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        return null;
    }
}
