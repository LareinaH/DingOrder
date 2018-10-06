package com.cotton.abmallback.web.controller.admin;

import com.cotton.abmallback.enumeration.MemberLevelEnum;
import com.cotton.abmallback.enumeration.OrderStatusEnum;
import com.cotton.abmallback.mapper.StatMapper;
import com.cotton.abmallback.model.Member;
import com.cotton.abmallback.model.OrderGoods;
import com.cotton.abmallback.model.Orders;
import com.cotton.abmallback.model.vo.admin.MemberVO;
import com.cotton.abmallback.service.MemberService;
import com.cotton.abmallback.service.OrderGoodsService;
import com.cotton.abmallback.service.OrdersService;
import com.cotton.base.common.RestResponse;
import com.cotton.base.service.ServiceException;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class ExportController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    OrderGoodsService orderGoodsService;

    @Autowired
    StatController statController;

    @Autowired
    MemberService memberService;

    @Autowired
    StatMapper statMapper;

    @RequestMapping(value = "/exportOrder", method = RequestMethod.GET)
    public ModelAndView exportOrder(
            @RequestParam(value = "orderStatus", required = false) String orderStatus,
            @RequestParam(value = "returnStatus", required = false) String returnStatus,
            @RequestParam(value = "timeBegin", required = false) String timeBegin,
            @RequestParam(value = "timeEnd", required = false) String timeEnd,
            @RequestParam(value = "orderNo", required = false) String orderNo
    ) {
        Example example = new Example(Orders.class);
        example.setOrderByClause("gmt_create desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);

        if (StringUtils.isNotBlank(timeBegin)) {
            criteria.andGreaterThanOrEqualTo("gmtCreate", timeBegin + " 00:00:00");
        }

        if (StringUtils.isNotBlank(timeEnd)) {
            criteria.andLessThanOrEqualTo("gmtCreate", timeEnd + " 23:59:59");
        }

        if (StringUtils.isNotBlank(orderStatus)) {
            criteria.andEqualTo("orderStatus", orderStatus);
        }

        if (StringUtils.isNotBlank(returnStatus)) {
            criteria.andEqualTo("returnStatus", returnStatus);
        }

        if (StringUtils.isNotBlank(orderNo)) {
            criteria.andEqualTo("orderNo", orderNo);
        }

        List<Orders> ordersList = ordersService.queryList(example);

        Example e2 = new Example(OrderGoods.class);
        Example.Criteria c2 = e2.createCriteria();
        c2.andIn("orderId", ordersList.stream().map(x -> x.getId()).collect(Collectors.toList()));

        List<OrderGoods> orderGoodsList = orderGoodsService.queryList(e2);
        Map<Long, OrderGoods> orderGoodsMap = orderGoodsList.stream().collect(Collectors.toMap(OrderGoods::getOrderId, Function.identity()));

        Map<String, Object> map = new HashMap<>();
        map.put("detail", ordersList);
        map.put("orderGoods", orderGoodsMap);
        map.put("name", String.format("%s~%s订单列表-%d", timeBegin, timeEnd, System.currentTimeMillis()));
        map.put("sheetName", "订单列表");

        return new ModelAndView(new ExportOrderView(), map);
    }

    @RequestMapping(value = "/exportSales", method = RequestMethod.GET)
    public ModelAndView exportSales(
            @RequestParam(value = "year") Integer year
    ) throws Exception {

        RestResponse<List<Map<String, Object>>> response = statController.getYearStat(year);
        if (!response.getSuccessed()) {
            throw new Exception("获取导出数据异常");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("detail", response.getData());
        map.put("name", String.format("%d年销售额统计-%d", year, System.currentTimeMillis()));
        map.put("sheetName", "销售额统计");

        return new ModelAndView(new ExportSalesView(), map);
    }

    @RequestMapping(value = "/exportUsers", method = {RequestMethod.GET})
    public ModelAndView exportUsers(
            int pageNum,
            int pageSize,
            String gmtStart,
            String gmtEnd,
            String name,
            String phoneNum,
            String level
    ) throws ParseException {
        Example example = new Example(Member.class);
        example.setOrderByClause("gmt_create desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);

        if (StringUtils.isNotBlank(gmtStart)) {
            criteria.andGreaterThanOrEqualTo("gmtCreate", DateUtils.parseDate(gmtStart, "yyyy-MM-dd"));
        }

        if (StringUtils.isNotBlank(gmtEnd)) {
            criteria.andLessThanOrEqualTo("gmtCreate", DateUtils.parseDate(gmtEnd, "yyyy-MM-dd"));
        }

        if (StringUtils.isNotBlank(name)) {
            criteria.andLike("name","%" + name + "%");
        }

        if (StringUtils.isNotBlank(phoneNum)) {
            criteria.andLike("phoneNum","%" + phoneNum + "%");
        }

        if (StringUtils.isNotBlank(level)) {
            criteria.andLike("level","%" + level + "%");
        }

        PageInfo<Member> memberPageInfo = memberService.query(pageNum, pageSize, example);

        PageInfo<MemberVO> memberVOPageInfo = new PageInfo<>();

        BeanUtils.copyProperties(memberPageInfo,memberVOPageInfo);

        List<MemberVO> memberVOS = new ArrayList<>();
        if(memberPageInfo.getList() != null && memberPageInfo.getList().size() >0){

            for(Member member : memberPageInfo.getList()){
                MemberVO memberVO = new MemberVO();
                BeanUtils.copyProperties(member,memberVO);

                //获取引荐人姓名
                if(null != member.getReferrerId()){
                    Member refferMember = memberService.getById(member.getReferrerId());

                    if(refferMember != null){
                        memberVO.setReferrerName(refferMember.getName());
                    }
                }

                //获取订单总数
                Example example2 = new Example(Orders.class);
                Example.Criteria criteria2 = example2.createCriteria();
                criteria2.andEqualTo("memberId", member.getId());
                criteria2.andEqualTo("isDeleted", false);

                List<String> orderStatusList = new ArrayList<>();
                orderStatusList.add(OrderStatusEnum.CANCEL.name());
                orderStatusList.add(OrderStatusEnum.WAIT_BUYER_PAY.name());
                criteria2.andNotIn("orderStatus", orderStatusList);

                long count = ordersService.count(example2);

                memberVO.setOrdersCount(count);

                //获取团队信息
                List<Map<String,Object>> teamInfoList = statMapper.getMemberTeamCountGroupByLevel(member.getId());

                if(teamInfoList.size() > 0) {

                    for(Map<String,Object> teamInfo : teamInfoList) {

                        if (null != teamInfo.get("level") &&teamInfo.get("level").equals(MemberLevelEnum.WHITE.name())) {
                            memberVO.setTeamWhiteCount((long)teamInfo.get("count"));
                        }else if (null != teamInfo.get("level") &&teamInfo.get("level").equals(MemberLevelEnum.AGENT.name())) {
                            memberVO.setTeamAgentCount((long)teamInfo.get("count"));
                        }else if (null != teamInfo.get("level") &&teamInfo.get("level").equals(MemberLevelEnum.V1.name())) {
                            memberVO.setTeamV1Count((long)teamInfo.get("count"));
                        }else if (null != teamInfo.get("level") &&teamInfo.get("level").equals(MemberLevelEnum.V2.name())) {
                            memberVO.setTeamV2Count((long)teamInfo.get("count"));
                        }else if (null != teamInfo.get("level") &&teamInfo.get("level").equals(MemberLevelEnum.V3.name())) {
                            memberVO.setTeamV3Count((long)teamInfo.get("count"));
                        }
                    }
                }

                memberVOS.add(memberVO);
            }
            memberVOPageInfo.setList(memberVOS);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("detail", memberVOPageInfo.getList());
        map.put("name", String.format("用户数据-%d", System.currentTimeMillis()));
        map.put("sheetName", "用户数据");

        return new ModelAndView(new ExportUsersView(), map);
    }
}
