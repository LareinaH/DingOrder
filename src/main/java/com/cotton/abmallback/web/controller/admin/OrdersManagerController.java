package com.cotton.abmallback.web.controller.admin;

import com.cotton.abmallback.enumeration.OrderReturnStatusEnum;
import com.cotton.abmallback.enumeration.OrderStatusEnum;
import com.cotton.abmallback.model.OrderGoods;
import com.cotton.abmallback.model.OrderReplenish;
import com.cotton.abmallback.model.Orders;
import com.cotton.abmallback.model.vo.OrdersWithGoodsInfo;
import com.cotton.abmallback.service.OrderGoodsService;
import com.cotton.abmallback.service.OrderReplenishService;
import com.cotton.abmallback.service.OrdersService;
import com.cotton.abmallback.web.controller.ABMallAdminBaseController;
import com.cotton.base.common.RestResponse;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * OrdersManager
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/25
 */
@Controller
@RequestMapping("/admin/orders")
public class OrdersManagerController extends ABMallAdminBaseController {

    private Logger logger = LoggerFactory.getLogger(OrdersManagerController.class);

    private OrdersService ordersService;

    private OrderReplenishService replenishService;

    @Autowired
    OrderGoodsService orderGoodsService;

    @Autowired
    public OrdersManagerController(OrdersService ordersService, OrderReplenishService replenishService) {
        this.ordersService = ordersService;
        this.replenishService = replenishService;
    }

    @ResponseBody
    @RequestMapping(value = "/example")
    public RestResponse<Map<String, Object>> example() {

        Map<String, Object> map = new HashMap<>(2);

        return RestResponse.getSuccesseResponse(map);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public RestResponse<Void> add(@RequestBody Orders orders) {

        if (ordersService.insert(orders)) {
            return RestResponse.getSuccesseResponse();
        } else {
            return RestResponse.getFailedResponse(500, "增加失败");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public RestResponse<Void> update(@RequestBody Orders orders) {

        if (!ordersService.update(orders)) {
            return RestResponse.getFailedResponse(500, "更新数据失败,Orders为:" + orders.toString());
        }

        return RestResponse.getSuccesseResponse();
    }


    @ResponseBody
    @RequestMapping(value = "/delivery", method = {RequestMethod.POST})
    public RestResponse<Void> delivery(@RequestParam long orderId, @RequestParam String logisticCode) {

        Orders orders = ordersService.getById(orderId);
        if (null == orders) {
            return RestResponse.getFailedResponse(500, "订单编号不存在");
        }

        if (!orders.getOrderStatus().equalsIgnoreCase(OrderStatusEnum.WAIT_DELIVER.name())) {
            return RestResponse.getFailedResponse(500, "该订单不处于待发货状态");
        }

        orders.setOrderStatus(OrderStatusEnum.WAIT_CONFIRM.name());
        orders.setLogisticCode(logisticCode);
        orders.setDeliveryTime(new Date());

        if (!ordersService.update(orders)) {
            return RestResponse.getFailedResponse(500, "发货失败,Orders为:" + orders.toString());
        }
        return RestResponse.getSuccesseResponse();
    }

    @ResponseBody
    @RequestMapping(value = "/replenish", method = {RequestMethod.POST})
    public RestResponse<Void> replenish(@RequestParam long orderId, @RequestParam String logisticCode) {

        Orders orders = ordersService.getById(orderId);
        if (null == orders) {
            return RestResponse.getFailedResponse(500, "订单编号不存在");
        }

        OrderReplenish model = new OrderReplenish();
        model.setOrderId(orderId);
        model.setLogisticCode(logisticCode);

        if (replenishService.queryList(model).size() > 0) {

            return RestResponse.getFailedResponse(500, "该补货运单号已经存在");
        }

        if (!orders.getReturnStatus().equalsIgnoreCase(OrderReturnStatusEnum.REPLENISHMENT.name())) {

            orders.setReturnStatus(OrderReturnStatusEnum.REPLENISHMENT.name());

            if (!ordersService.update(orders)) {
                return RestResponse.getFailedResponse(500, "补货失败,Orders为:" + orders.toString());
            }

        }
        if (replenishService.insert(model)) {

            return RestResponse.getSuccesseResponse();
        } else {

            return RestResponse.getFailedResponse(500, "补货失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/systemCancel", method = {RequestMethod.POST})
    public RestResponse<Void> systemCancel(@RequestParam long orderId) {

        Orders orders = ordersService.getById(orderId);
        if (null == orders) {
            return RestResponse.getFailedResponse(500, "订单编号不存在");
        }

        orders.setOrderStatus(OrderStatusEnum.SYSTEM_CANCEL.name());

        if (!ordersService.update(orders)) {
            return RestResponse.getFailedResponse(500, "系统取消失败,Orders为:" + orders.toString());
        }

        return RestResponse.getSuccesseResponse();

    }

    @ResponseBody
    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    public RestResponse<Orders> get(long ordersId) {

        Orders orders = ordersService.getById(ordersId);

        if (null == orders) {
            return RestResponse.getFailedResponse(500, "无法查找数据,请检查ordersId是否正确");

        }
        return RestResponse.getSuccesseResponse(orders);
    }

    @ResponseBody
    @RequestMapping(value = "/queryList", method = {RequestMethod.GET})
    public RestResponse<List<Orders>> queryList() {

        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);

        List<Orders> ordersList = ordersService.queryList(example);

        if (ordersList == null) {
            logger.error("读取列表失败");
            return RestResponse.getSystemInnerErrorResponse();
        }
        return RestResponse.getSuccesseResponse(ordersList);
    }

    @ResponseBody
    @RequestMapping(value = "/queryPageList", method = {RequestMethod.POST})
    public RestResponse<PageInfo<OrdersWithGoodsInfo>> queryPageList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "4") int pageSize, @RequestBody() Map<String, Object> conditions) {

        Example example = new Example(Orders.class);
        example.setOrderByClause("gmt_create desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);

        if (null != conditions) {
            if (null != conditions.get("timeBegin")) {

                Date date;
                try {
                    String begin = conditions.get("timeBegin").toString() + " 00:00:00";
                    date = DateUtils.parseDate(begin, "yyyy-MM-dd hh:mm:ss");
                } catch (ParseException e) {
                    return RestResponse.getFailedResponse(500, "时间格式错误");
                }
                criteria.andGreaterThanOrEqualTo("gmtCreate", date);
            }

            if (null != conditions.get("timeEnd")) {

                Date date;
                try {
                    String end = conditions.get("timeEnd").toString() + " 23:59:59";
                    date = DateUtils.parseDate(end, "yyyy-MM-dd hh:mm:ss");
                } catch (ParseException e) {
                    return RestResponse.getFailedResponse(500, "时间格式错误");
                }
                criteria.andLessThanOrEqualTo("gmtCreate", date);
            }

            if (null != conditions.get("orderStatus")) {
                criteria.andEqualTo("orderStatus", conditions.get("orderStatus").toString());
            }

            if (null != conditions.get("returnStatus")) {
                criteria.andEqualTo("returnStatus", conditions.get("returnStatus").toString());
            }

            if (null != conditions.get("orderNo")) {
                criteria.andEqualTo("orderNo", conditions.get("orderNo").toString());
            }
        }

        PageInfo<Orders> ordersPageInfo = ordersService.query(pageNum, pageSize, example);

        Example e2 = new Example(OrderGoods.class);
        Example.Criteria c2 = e2.createCriteria();
        c2.andIn("orderId", ordersPageInfo.getList().stream().map(x -> x.getId()).collect(Collectors.toList()));

        List<OrderGoods> orderGoodsList = orderGoodsService.queryList(e2);
        Map<Long, OrderGoods> orderGoodsMap = orderGoodsList.stream().collect(Collectors.toMap(OrderGoods::getOrderId, Function.identity()));

        PageInfo<OrdersWithGoodsInfo> pageInfo = new PageInfo<>();

        BeanUtils.copyProperties(ordersPageInfo, pageInfo);

        List<OrdersWithGoodsInfo> ordersWithGoodsInfoList = new ArrayList<>();
        ordersPageInfo.getList().forEach(x -> {
            if (orderGoodsMap.containsKey(x.getId())) {
                OrdersWithGoodsInfo owgi = new OrdersWithGoodsInfo();
                BeanUtils.copyProperties(x, owgi);
                OrderGoods orderGoods = orderGoodsMap.get(x.getId());
                owgi.setGoodsSpecificationNo(orderGoods.getGoodsSpecificationNo());
                owgi.setGoodsSpecificationName(orderGoods.getGoodsSpecificationName());
                owgi.setGoodName(orderGoods.getGoodName());
                owgi.setGoodNum(orderGoods.getGoodNum());
                ordersWithGoodsInfoList.add(owgi);
            } else {
                logger.warn("order {} could not find goods info", x.getId());
            }
        });

        pageInfo.setList(ordersWithGoodsInfoList);

        return RestResponse.getSuccesseResponse(pageInfo);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE})
    public RestResponse<Map<String, Object>> delete(long ordersId) {

        Orders orders = ordersService.getById(ordersId);

        if (null == orders) {
            return RestResponse.getFailedResponse(500, "无法查找数据,请检查ordersId 是否正确");

        }

        orders.setIsDeleted(true);

        if (!ordersService.update(orders)) {
            return RestResponse.getFailedResponse(500, "删除数据失败,ordersId为:" + ordersId);
        }

        return RestResponse.getSuccesseResponse();
    }
}