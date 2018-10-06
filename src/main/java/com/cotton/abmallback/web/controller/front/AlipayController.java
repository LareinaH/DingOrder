package com.cotton.abmallback.web.controller.front;

import com.cotton.abmallback.enumeration.OrderStatusEnum;
import com.cotton.abmallback.manager.DistributionManager;
import com.cotton.abmallback.manager.OrdersManager;
import com.cotton.abmallback.model.OrderGoods;
import com.cotton.abmallback.model.Orders;
import com.cotton.abmallback.service.OrderGoodsService;
import com.cotton.abmallback.service.OrdersService;
import com.cotton.abmallback.third.alibaba.alipay.AlipayService;
import com.cotton.base.common.RestResponse;
import me.hao0.alipay.model.enums.AlipayField;
import me.hao0.alipay.model.enums.TradeStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * AlipayController
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/21
 */
@Controller
@RequestMapping("/alipay")
public class AlipayController {

    private static final Logger logger = LoggerFactory.getLogger(AlipayController.class);

    private final AlipayService alipayService;

    private final OrdersService ordersService;

    private final OrderGoodsService orderGoodsService;

    private final OrdersManager ordersManager;

    private final DistributionManager distributionManager;


    @Autowired
    public AlipayController(AlipayService alipayService, OrdersService ordersService, OrderGoodsService orderGoodsService, OrdersManager ordersManager, DistributionManager distributionManager) {
        this.alipayService = alipayService;
        this.ordersService = ordersService;
        this.orderGoodsService = orderGoodsService;
        this.ordersManager = ordersManager;
        this.distributionManager = distributionManager;
    }


    /**
     * APP支付
     */
    @ResponseBody
    @RequestMapping("/app")
    public RestResponse<Object> appPay2(@RequestParam("orderId") long orderId){

        //根据订单号获取订单信息
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


        Map<String, Object> result = alipayService.payWithAlipay(orders.getOrderNo(),orders.getTotalMoney());
        logger.info("app pay form: {}", result);

        return RestResponse.getSuccesseResponse(result);
    }


    /**
     * 支付宝服务器通知
     */
    @RequestMapping("/backend")
    public String backend(HttpServletRequest request){

        Map<String,String> params = new HashMap<>(10);
        Map requestParams = request.getParameterMap();
        for (Object o : requestParams.keySet()) {
            String name = (String) o;
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        logger.info("backend notify params: {}", params);

        if(!alipayService.notifyVerify(params)) {

            logger.info("backend notify failed");
            return "FAIL";

        }

        String tradeStatus = params.get(AlipayField.TRADE_STATUS.field());
        if (TradeStatus.TRADE_FINISHED.value().equals(tradeStatus) || TradeStatus.TRADE_SUCCESS.value().equals(tradeStatus)) {

            //获取订单号
            String orderNo = params.get(AlipayField.OUT_TRADE_NO.field());

            String tradeNo = params.get(AlipayField.TRADE_NO.field());

            if (ordersManager.paySuccess(orderNo, tradeNo, "Alipay")) {

                logger.info("backend notify success");

                ordersManager.afterPaySuccess(orderNo);
                return "SUCCESS";
            }
        }
        logger.info("backend notify failed");

        return "FAIL";
    }

}

