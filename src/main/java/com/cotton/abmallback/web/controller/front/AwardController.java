package com.cotton.abmallback.web.controller.front;

import com.cotton.abmallback.model.*;
import com.cotton.abmallback.service.AccountMoneyFlowService;
import com.cotton.abmallback.service.MemberService;
import com.cotton.abmallback.service.OrderGoodsService;
import com.cotton.abmallback.service.OrdersService;
import com.cotton.abmallback.web.controller.ABMallFrontBaseController;
import com.cotton.base.common.RestResponse;
import com.cotton.base.controller.BaseController;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Award
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/24
 */
@Controller
@RequestMapping("/accountMoneyFlow")
public class AwardController extends ABMallFrontBaseController {

    private Logger logger = LoggerFactory.getLogger(AwardController.class);

    private AccountMoneyFlowService accountMoneyFlowService;
    private OrdersService ordersService;
    private MemberService memberService;
    private OrderGoodsService orderGoodsService;

    @Autowired
    public AwardController(AccountMoneyFlowService accountMoneyFlowService, OrdersService ordersService, MemberService memberService, OrderGoodsService orderGoodsService) {
        this.accountMoneyFlowService = accountMoneyFlowService;
        this.ordersService = ordersService;
        this.memberService = memberService;
        this.orderGoodsService = orderGoodsService;
    }

    @ResponseBody
    @RequestMapping(value = "/example")
    public RestResponse<Map<String, Object>> example() {

        Map<String, Object> map = new HashMap<>(2);

        return RestResponse.getSuccesseResponse(map);
    }

    /**
     * 列表
     *
     * @return List<AccountMoneyFlow>
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public RestResponse<List<AccountMoneyFlowVO>> queryList(@RequestParam(defaultValue = "1") int pageNum,
                                                            @RequestParam(defaultValue = "4") int pageSize,
                                                            @RequestParam(required = false) String type) {

        Example example = new Example(AccountMoneyFlow.class);
        example.setOrderByClause("gmt_create desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", "0");

        if(StringUtils.isNotBlank(type)){
            criteria.andEqualTo("accountMoneyType",type);
        }

        criteria.andEqualTo("memberId",getCurrentMemberId());

        PageInfo<AccountMoneyFlow> accountMoneyFlowPageInfo = accountMoneyFlowService.query(pageNum, pageSize, example);
        if (null != accountMoneyFlowPageInfo) {

            //组装返回信息
            List<AccountMoneyFlowVO> accountMoneyFlowVOList = new ArrayList<>();

            for(AccountMoneyFlow accountMoneyFlow : accountMoneyFlowPageInfo.getList()){
                AccountMoneyFlowVO accountMoneyFlowVO = new AccountMoneyFlowVO();
                BeanUtils.copyProperties(accountMoneyFlow,accountMoneyFlowVO);

                //查找订单信息
                Orders orders = ordersService.getById(accountMoneyFlow.getOrderId());
                if( null != orders){
                    Member member = memberService.getById(orders.getMemberId());
                    OrderGoods model = new OrderGoods();
                    model.setOrderId(orders.getId());
                    OrderGoods orderGoods = orderGoodsService.selectOne(model);

                    if(null != member) {
                        //获取用户引荐人
                        if(member.getReferrerId() != null) {
                            Member referrerMember = memberService.getById(member.getReferrerId());

                            if (referrerMember != null){
                                accountMoneyFlowVO.setBuyerName(referrerMember.getName() + "中的" + member.getName());
                                accountMoneyFlowVO.setBuyerPhoto(member.getPhoto());

                            }else {
                                accountMoneyFlowVO.setBuyerName(member.getName());
                                accountMoneyFlowVO.setBuyerPhoto(member.getPhoto());
                            }
                        }else {
                            accountMoneyFlowVO.setBuyerName(member.getName());
                            accountMoneyFlowVO.setBuyerPhoto(member.getPhoto());
                        }
                    }
                    if(null != orderGoods) {
                        accountMoneyFlowVO.setOrderGoodName(orderGoods.getGoodName());
                        accountMoneyFlowVO.setOrderGoodsSpecificationName(orderGoods.getGoodsSpecificationName());
                        accountMoneyFlowVO.setOrderGoodThum(orderGoods.getGoodThum());
                    }
                    accountMoneyFlowVO.setOrderTotalMoney(orders.getTotalMoney());
                }
                accountMoneyFlowVOList.add(accountMoneyFlowVO);
            }
            return RestResponse.getSuccesseResponse(accountMoneyFlowVOList);
        } else {
            logger.error("读取列表失败");
            return RestResponse.getFailedResponse(500, "读取列表失败");
        }
    }
}