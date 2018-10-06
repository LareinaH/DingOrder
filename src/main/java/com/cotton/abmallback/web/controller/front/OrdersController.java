package com.cotton.abmallback.web.controller.front;

import com.cotton.abmallback.enumeration.OrderStatusEnum;
import com.cotton.abmallback.model.*;
import com.cotton.abmallback.model.vo.OrdersVO;
import com.cotton.abmallback.service.*;
import com.cotton.abmallback.web.controller.ABMallFrontBaseController;
import com.cotton.base.common.RestResponse;
import com.cotton.base.third.KdniaoService;
import com.cotton.base.utils.RandomUtil;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * OrdersController
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/10
 */

@Controller
@RequestMapping("/orders")
public class OrdersController extends ABMallFrontBaseController {

    private Logger logger = LoggerFactory.getLogger(OrdersController.class);

    private final OrdersService ordersService;

    private final GoodsService goodsService;

    private final GoodsSpecificationService goodsSpecificationService;

    private final MemberAddressService memberAddressService;

    private final OrderGoodsService orderGoodsService;

    private final KdniaoService kdniaoService;

    @Autowired
    public OrdersController(OrdersService ordersService, GoodsService goodsService, OrderGoodsService orderGoodsService, GoodsSpecificationService goodsSpecificationService, MemberAddressService memberAddressService, KdniaoService kdniaoService) {
        this.ordersService = ordersService;
        this.goodsService = goodsService;
        this.orderGoodsService = orderGoodsService;
        this.goodsSpecificationService = goodsSpecificationService;
        this.memberAddressService = memberAddressService;
        this.kdniaoService = kdniaoService;
    }

    /**
     * 创建订单
     * @param params 订单信息
     * @return RestResponse
     */
    @ResponseBody
    @RequestMapping(value = "/createOrder")
    @Transactional(rollbackFor = Exception.class)
    public RestResponse<Orders> createOrder(@RequestBody Map<String,Object> params) {

        if (params.get("goodsSpecificationServiceId") == null ||
                params.get("count") == null || params.get("addressId") == null) {

            return RestResponse.getFailedResponse(500,"入参为空");
        }

        long goodsSpecificationServiceId = Long.valueOf(params.get("goodsSpecificationServiceId").toString()) ;
        int count = Integer.valueOf(params.get("count").toString());
        long addressId = Long.valueOf(params.get("addressId").toString());
        String orderSource = "APP";
        if(params.get("orderSource") != null){
            orderSource = params.get("orderSource").toString();
        }

        //根据goodsSpecificationServiceId查找商品
        GoodsSpecification goodsSpecification = goodsSpecificationService.getById(goodsSpecificationServiceId);
        if(null == goodsSpecification || goodsSpecification.getIsDeleted() || !goodsSpecification.getIsOnSell()){
            return RestResponse.getFailedResponse(500,"商品不存在或者已经下架");
        }

        Goods goods = goodsService.getById(goodsSpecification.getGoodsId());

        if(null == goods){
            return RestResponse.getFailedResponse(500,"商品不存在");
        }

        if(goodsSpecification.getStock() < count){
            return RestResponse.getFailedResponse(500,"商品库存不足");
        }

        MemberAddress memberAddress = memberAddressService.getById(addressId);

        if(null == memberAddress || memberAddress.getIsDeleted()){
            return RestResponse.getFailedResponse(500,"收货地址不存在");
        }

        //创建订单
        Orders orders = new Orders();
        orders.setOrderStatus(OrderStatusEnum.WAIT_BUYER_PAY.name());
        orders.setMemberId(getCurrentMemberId());
        //根据时间戳生成订单编号 + 3位随机数
        SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String strDate = sfDate.format(new Date());
        String random = RandomUtil.getRandomNumCode(3);
        orders.setOrderNo(strDate + random);
        orders.setTotalMoney(goodsSpecification.getPreferentialPrice().multiply(new BigDecimal(Double.valueOf(count))));
        orders.setOrderSource(orderSource);

        //收货人信息
        orders.setReceiverName(memberAddress.getReceiverName());
        orders.setReceiverPhone(memberAddress.getReceiverPhone());
        orders.setReceiverProvinceName(memberAddress.getReceiverProvinceName());
        orders.setReceiverProvinceCode(memberAddress.getReceiverProvinceCode());
        orders.setReceiverCityName(memberAddress.getReceiverCityName());
        orders.setReceiverCityCode(memberAddress.getReceiverCityCode());
        orders.setReceiverCountyName(memberAddress.getReceiverCountyName());
        orders.setReceiverCountyCode(memberAddress.getReceiverCountyCode());
        orders.setReceiverAddress(memberAddress.getReceiverAddress());

        if(ordersService.insert(orders)){

            //增加订单商品
            OrderGoods orderGoods = new OrderGoods();
            orderGoods.setGoodName(goods.getGoodsName());
            orderGoods.setGoodsSpecificationName(goodsSpecification.getGoodsSpecificationName());
            orderGoods.setGoodId(goods.getId());
            orderGoods.setOrderId(orders.getId());
            orderGoods.setGoodSpecificationId(goodsSpecification.getId());
            orderGoods.setGoodThum(goods.getThums());
            orderGoods.setGoodNum(count);
            orderGoods.setGoodPrice(goodsSpecification.getPreferentialPrice());
            orderGoods.setGoodsSpecificationNo(goodsSpecification.getGoodsSpecificationNo());

            orderGoodsService.insert(orderGoods);

            //扣库存  加销量
            goods.setStock(goods.getStock() - count);
            goods.setSalesAmount(goods.getSalesAmount() + count);

            goodsSpecification.setStock(goodsSpecification.getStock() - count);
            goodsSpecification.setSalesAmount(goodsSpecification.getSalesAmount() + count);

            goodsService.update(goods);
            goodsSpecificationService.update(goodsSpecification);


            return RestResponse.getSuccesseResponse(orders);
        }

        return RestResponse.getFailedResponse(500,"内部错误");

    }

    /**
     * 订单列表
     * @return RestResponse
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public RestResponse<List<OrdersVO>> ordersList(@RequestParam(defaultValue = "1") int pageNum,
                                                   @RequestParam(defaultValue = "4") int pageSize) {

        Example example = new Example(Orders.class);
        example.setOrderByClause("gmt_create desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted",false);
        criteria.andEqualTo("memberId",getCurrentMemberId());
        PageInfo<Orders> ordersPageInfo = ordersService.query(pageNum,pageSize,example);

        if(ordersPageInfo == null ){
            logger.error("读取订单列表失败");
            return RestResponse.getSystemInnerErrorResponse();
        }

        List<OrdersVO> ordersVOList = new ArrayList<>();
        for(Orders orders : ordersPageInfo.getList()){
            OrdersVO ordersVO = new OrdersVO();
            BeanUtils.copyProperties(orders,ordersVO);
            OrderGoods model = new OrderGoods();
            model.setOrderId(orders.getId());
            List<OrderGoods> orderGoodsList = orderGoodsService.queryList(model);
            ordersVO.setOrderGoodsList(orderGoodsList);
            ordersVOList.add(ordersVO);
        }
        return RestResponse.getSuccesseResponse(ordersVOList);
    }


    /**
     * 确认收货
     * @return RestResponse
     */
    @ResponseBody
    @RequestMapping(value = "/confirmReceipt")
    public RestResponse<Void> confirmReceipt(@RequestParam long orderId) {


        Orders orders = ordersService.getById(orderId);
        if(null == orders){
            return RestResponse.getFailedResponse(500,"订单编号不存在");
        }
        orders.setOrderStatus(OrderStatusEnum.CONFIRMED.name());
        orders.setReceiveTime(new Date());

        if(ordersService.update(orders)){

            return RestResponse.getSuccesseResponse();
        }

        return RestResponse.getFailedResponse(500,"确认收获失败!");
    }


    /**
     * 取消订单
     * @return RestResponse
     */
    @ResponseBody
    @RequestMapping(value = "/cancelOrder")
    public RestResponse<Void> cancelOrder(@RequestParam long orderId) {

        Orders orders = ordersService.getById(orderId);
        if(null == orders){
            return RestResponse.getFailedResponse(500,"订单编号不存在");
        }

        orders.setOrderStatus(OrderStatusEnum.CANCEL.name());

        OrderGoods model = new OrderGoods();
        model.setOrderId(orderId);
        List<OrderGoods> orderGoodsList = orderGoodsService.queryList(model);

        if(CollectionUtils.isNotEmpty(orderGoodsList)) {

            OrderGoods orderGoods = orderGoodsList.get(0);

            GoodsSpecification goodsSpecification = goodsSpecificationService.getById(orderGoods.getGoodSpecificationId());

            Goods goods = goodsService.getById(goodsSpecification.getGoodsId());

            if (ordersService.update(orders)) {

                //处理库存和销量
                goods.setStock(goods.getStock() + orderGoods.getGoodNum());
                goods.setSalesAmount(goods.getSalesAmount() - orderGoods.getGoodNum());

                goodsSpecification.setStock(goodsSpecification.getStock() + orderGoods.getGoodNum());
                goodsSpecification.setSalesAmount(goodsSpecification.getSalesAmount() - orderGoods.getGoodNum());

                return RestResponse.getSuccesseResponse();
            }
        }

        return RestResponse.getFailedResponse(500,"取消订单失败!");
    }

    /**
     * 查看物流
     * @return RestResponse
     */
    @ResponseBody
    @RequestMapping(value = "/showLogistics")
    public RestResponse<Map<String,Object>> showLogistics(@RequestParam long orderId) {

        Map<String, Object> map = new HashMap<>(2);

        Orders orders = ordersService.getById(orderId);
        if(null == orders){
            return RestResponse.getFailedResponse(500,"订单编号不存在");
        }

        String logisticCode = orders.getLogisticCode();

        if(StringUtils.isBlank(logisticCode)){
            return RestResponse.getFailedResponse(500,"物流编号不存在");
        }

        String traces = kdniaoService.orderTracesSubByJson("YZPY",orders.getLogisticCode());

        map.put("address",orders.getReceiverProvinceName() + orders.getReceiverCityName() + orders.getReceiverCountyName() + orders.getReceiverAddress());
        map.put("tracks",traces);
        map.put("logisticsCompany","中国邮政");
        map.put("logisticsCode",orders.getLogisticCode());

        return RestResponse.getSuccesseResponse(map);

    }
}