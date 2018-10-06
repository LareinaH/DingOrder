package com.cotton.abmallback.web.controller.front;


import com.cotton.abmallback.enumeration.OrderStatusEnum;
import com.cotton.abmallback.manager.DistributionManager;
import com.cotton.abmallback.manager.OrdersManager;
import com.cotton.abmallback.model.Member;
import com.cotton.abmallback.model.OrderGoods;
import com.cotton.abmallback.model.Orders;
import com.cotton.abmallback.service.MemberService;
import com.cotton.abmallback.service.OrderGoodsService;
import com.cotton.abmallback.service.OrdersService;
import com.cotton.base.common.RestResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;


/**
 * WechatController
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/15
 */

@Controller
@RequestMapping("/wechat/pay")
public class WechatPayController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "wxPayService")
    private WxPayService wxPayService;

    @Resource(name = "wxMpPayService")
    private WxPayService wxMpPayService;

    private final OrdersService ordersService;

    private final OrdersManager ordersManager;

    private final OrderGoodsService orderGoodsService;

    private final DistributionManager distributionManager;

    private final MemberService memberService;

    @Value("${wechat.mp.app-id}")
    private String mpAppId;

    @Value("${wechat.pay.app-id}")
    private String openAppId;

    @Autowired
    public WechatPayController(OrdersService ordersService, OrdersManager ordersManager, OrderGoodsService orderGoodsService, DistributionManager distributionManager, MemberService memberService) {
        this.ordersService = ordersService;
        this.ordersManager = ordersManager;
        this.orderGoodsService = orderGoodsService;
        this.distributionManager = distributionManager;
        this.memberService = memberService;
    }


    /**
     * 统一下单(详见https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1)
     * 在发起微信支付前，需要调用统一下单接口，获取"预支付交易会话标识"
     * 接口地址：https://api.mch.weixin.qq.com/pay/unifiedorder
     *
     * @param orderId 订单id
     */
    @GetMapping("/unifiedOrder")
    @ResponseBody
    public RestResponse<Map<String,Object>> unifiedOrder(HttpServletRequest httpServletRequest,
                                                         long orderId, String tradeType) throws WxPayException {

        //根据orderId 获取订单信息
        Orders orders = ordersService.getById(orderId);
        OrderGoods model = new OrderGoods();
        model.setOrderId(orderId);
        OrderGoods orderGoods = orderGoodsService.selectOne(model);


        if(null == orders || null == orderGoods){
            return RestResponse.getFailedResponse(500,"订单编号不存在");
        }

        if(!OrderStatusEnum.WAIT_BUYER_PAY.equals(OrderStatusEnum.valueOf(orders.getOrderStatus()))){
            return RestResponse.getFailedResponse(500,"订单状态错误");
        }

        Member member = memberService.getById(orders.getMemberId());

        if(null == member){
            return RestResponse.getFailedResponse(500,"会员不存在");

        }

        String ip = getIpAddr(httpServletRequest);

        logger.info(ip);

        //构建WxPayUnifiedOrderRequest
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        request.setBody("订单商品" + orderGoods.getGoodName());
        request.setOutTradeNo(orders.getOrderNo());
        BigDecimal var = new BigDecimal("100");
        request.setTotalFee(orders.getTotalMoney().multiply(var).intValue());
        request.setSpbillCreateIp(ip);
        request.setNotifyUrl("http://admin.yund.live:80/api/v1/wechat/pay/parseOrderNotifyResult");
        request.setTradeType(tradeType);

        if("APP".equalsIgnoreCase(tradeType)) {

            logger.info("---------app支付---------");

            WxPayAppOrderResult wxPayAppOrderResult = this.wxPayService.createOrder(request);

            if (null != wxPayAppOrderResult) {

                Map<String, Object> result = new HashMap<>(10);
                result.put("appid", wxPayAppOrderResult.getAppId());
                result.put("partnerid", wxPayAppOrderResult.getPartnerId());
                result.put("prepayid", wxPayAppOrderResult.getPrepayId());
                result.put("noncestr", wxPayAppOrderResult.getNonceStr());
                result.put("package", "Sign=WXPay");
                result.put("sign", wxPayAppOrderResult.getSign());
                result.put("timestamp", System.currentTimeMillis() / 1000);

                return RestResponse.getSuccesseResponse(result);
            } else {
                return RestResponse.getFailedResponse(500, "微信支付失败");
            }
        }else if("JSAPI".equalsIgnoreCase(tradeType)){

            logger.info("---------公众号支付---------");
            request.setOpenid(member.getOpenId());
            WxPayMpOrderResult wxPayMpOrderResult =  this.wxMpPayService.createOrder(request);

            if (null != wxPayMpOrderResult) {

                Map<String, Object> result = new HashMap<>(10);
                result.put("nonceStr", wxPayMpOrderResult.getNonceStr());
                result.put("package",wxPayMpOrderResult.getPackageValue());
                result.put("signType",wxPayMpOrderResult.getSignType());
                result.put("paySign", wxPayMpOrderResult.getPaySign());
                result.put("timestamp", System.currentTimeMillis() / 1000);

                return RestResponse.getSuccesseResponse(result);
            } else {
                return RestResponse.getFailedResponse(500, "微信支付失败");
            }

        }else {
            return RestResponse.getFailedResponse(500, "支付方式错误");
        }
    }


    @PostMapping("/parseOrderNotifyResult")
    public RestResponse<Void> parseOrderNotifyResult(HttpServletRequest httpServletRequest) throws WxPayException, IOException {

        BufferedReader br = httpServletRequest.getReader();
        String str;
        StringBuilder xmlData = new StringBuilder();
        while((str = br.readLine()) != null){
            xmlData.append(str);
        }

        logger.info("微信支付结果通知:" + xmlData);

        WxPayOrderNotifyResult wxPayOrderNotifyResult = this.wxPayService.parseOrderNotifyResult(xmlData.toString());

        //支付成功
        if(wxPayOrderNotifyResult!= null && wxPayOrderNotifyResult.getReturnCode().equalsIgnoreCase("SUCCESS")
                && wxPayOrderNotifyResult.getResultCode().equalsIgnoreCase("SUCCESS")){

            String orderNo = wxPayOrderNotifyResult.getOutTradeNo();

            String tradeNo = wxPayOrderNotifyResult.getTransactionId();

            ordersManager.paySuccess(orderNo,tradeNo,"wechat");

            ordersManager.afterPaySuccess(orderNo);

        }
        return RestResponse.getSuccesseResponse();
    }


    /**
     * @Description: 获取客户端IP地址
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if(ip.equals("127.0.0.1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip= inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ip != null && ip.length() > 15){
            if(ip.indexOf(",")>0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        return ip;
    }

}
