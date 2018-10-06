package com.cotton.abmallback.manager.impl;

import com.cotton.abmallback.enumeration.AccountMoneyTypeEnum;
import com.cotton.abmallback.enumeration.DistributionItemEnum;
import com.cotton.abmallback.enumeration.MemberLevelEnum;
import com.cotton.abmallback.enumeration.OrderStatusEnum;
import com.cotton.abmallback.manager.MessageManager;
import com.cotton.abmallback.manager.PromotionManager;
import com.cotton.abmallback.model.AccountMoneyFlow;
import com.cotton.abmallback.model.DistributionConfig;
import com.cotton.abmallback.model.Member;
import com.cotton.abmallback.model.Orders;
import com.cotton.abmallback.service.AccountMoneyFlowService;
import com.cotton.abmallback.service.DistributionConfigService;
import com.cotton.abmallback.service.MemberService;
import com.cotton.abmallback.service.OrdersService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * PromotionManagerImpl
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/7/3
 */
@Service
public class PromotionManagerImpl implements PromotionManager {

    private final DistributionConfigService distributionConfigService;

    private final MemberService memberService;

    private final AccountMoneyFlowService accountMoneyFlowService;

    private final MessageManager messageManager;

    private final OrdersService ordersService;

    public PromotionManagerImpl(DistributionConfigService distributionConfigService, MemberService memberService, AccountMoneyFlowService accountMoneyFlowService, MessageManager messageManager, OrdersService ordersService) {
        this.distributionConfigService = distributionConfigService;
        this.memberService = memberService;
        this.accountMoneyFlowService = accountMoneyFlowService;
        this.messageManager = messageManager;
        this.ordersService = ordersService;
    }


    @Override
    public void memberPromotion(Member member, long orderId) {

        //对于达到未v3级别的 晋级
        if (!MemberLevelEnum.V3.name().equalsIgnoreCase(member.getLevel())) {
            //晋级
            MemberLevelEnum newLevel = MemberLevelEnum.getNextLevel(MemberLevelEnum.valueOf(member.getLevel()));

            if (null != newLevel) {

                //判断是否达到晋级条件
                if (checkCanBePromoted(member, newLevel)) {

                    member.setLevel(newLevel.toString());
                    memberService.update(member);
                    //发放奖励
                    String money = sendPromotionReward(member, newLevel, orderId);
                    //发送消息通知
                    sendPromotionMessage(member, newLevel, BigDecimal.valueOf(Double.valueOf(money)));

                    //校验自己的上级是否升级
                    if (member.getReferrerId() != null) {

                        Member referrerMember = memberService.getById(member.getReferrerId());

                        if (referrerMember != null) {

                            //晋级为代理的时候，上级的统计数 +1
                            if(newLevel.equals(MemberLevelEnum.AGENT)){

                                referrerMember.setReferTotalAgentCount(referrerMember.getReferTotalAgentCount() +1);
                                memberService.update(referrerMember);
                            }
                            memberPromotion(referrerMember, 0);
                        }
                    }
                }
            }
        } else {
            //达到v3级别的   不晋级，仅发放额外奖励
        }
    }

    @Override
    public void memberPromotionAll() {

        Member model = new Member();
        model.setIsDeleted(false);
        model.setLevel(MemberLevelEnum.V2.name());
        List<Member> members = memberService.queryList(model);
        for (Member member : members) {
            memberPromotion(member, 0);
        }
    }


    private boolean checkCanBePromoted(Member member, MemberLevelEnum newLevel) {

        Map<String, DistributionConfig> map = distributionConfigService.getAllDistributionConfig();

        String totalMoney = "0";
        String totalMemberCount = "0";
        String totalV1Count = "0";
        String shopTimes = "0";

        //获取newLevel的晋级条件
        switch (newLevel) {
            case AGENT:
                totalMoney = map.get(DistributionItemEnum.PROMOTION_AGENT_MONEY.name()).getValue();
                shopTimes = map.get(DistributionItemEnum.PROMOTION_AGENT_TIMES.name()).getValue();
                long count = getOrdersCount(member.getId());


                return member.getMoneyTotalSpend().compareTo(BigDecimal.valueOf(Double.valueOf(totalMoney))) >= 0 && count >= Long.valueOf(shopTimes);

            case V1:
                // 晋级v1的条件是：直接分享XXX代言人。
                //totalMoney = map.get(DistributionItemEnum.PROMOTION_V1_MONEY.name()).getValue();
                totalMemberCount = map.get(DistributionItemEnum.PROMOTION_V1_SHARE_PEOPLE.name()).getValue();
                return getAgentPeopleCount(member.getId()) >= Long.valueOf(totalMemberCount);
            case V2:
                //晋级V2的条件是：团队中有XXX个v1 并且直接分享XXX代言人。
                //totalMoney = map.get(DistributionItemEnum.PROMOTION_V2_MONEY.name()).getValue();
                totalMemberCount = map.get(DistributionItemEnum.PROMOTION_V2_SHARE_PEOPLE.name()).getValue();
                totalV1Count = map.get(DistributionItemEnum.PROMOTION_V2_SHARE_V1_PEOPLE.name()).getValue();

                return (getAgentPeopleCount(member.getId()) >= Long.valueOf(totalMemberCount)) && (getV1PeopleCount(member.getId()) >= Long.valueOf(totalV1Count));

            case V3:
                // 晋级V3的条件是：直接分享XXX代言人，V3自己获得返佣XXXX元
                totalMoney = map.get(DistributionItemEnum.PROMOTION_V3_MONEY.name()).getValue();
                totalMemberCount = map.get(DistributionItemEnum.PROMOTION_V3_SHARE_PEOPLE.name()).getValue();

                return (getAgentPeopleCount(member.getId()) >= Long.valueOf(totalMemberCount)) && (member.getMoneyTotalEarn().compareTo(BigDecimal.valueOf(Double.valueOf(totalMoney))) >= 0);

            default:
                return false;
        }
    }

    private long getOrdersCount(long memberId) {
        //获取有效订单数
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("memberId", memberId);
        criteria.andEqualTo("isDeleted", false);

        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add(OrderStatusEnum.CANCEL.name());
        orderStatusList.add(OrderStatusEnum.WAIT_BUYER_PAY.name());
        criteria.andNotIn("orderStatus", orderStatusList);

        return ordersService.count(example);
    }


    private long getV1PeopleCount(long referrerId) {
        Example example = new Example(Member.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("referrerId", referrerId);
        criteria.andEqualTo("isDeleted", false);

        List<String> levelList = new ArrayList<>();
        levelList.add(MemberLevelEnum.WHITE.name());
        levelList.add(MemberLevelEnum.AGENT.name());
        criteria.andNotIn("level", levelList);

        return memberService.count(example);
    }


    private long getAgentPeopleCount(long referrerId) {
        Example example = new Example(Member.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("referrerId", referrerId);
        criteria.andEqualTo("isDeleted", false);
        criteria.andNotEqualTo("level", MemberLevelEnum.WHITE);
        return memberService.count(example);
    }

    /**
     * 给升级的会员发放奖励
     */
    private String sendPromotionReward(Member member, MemberLevelEnum newLevel, long orderId) {

        Map<String, DistributionConfig> map = distributionConfigService.getAllDistributionConfig();

        String awardMoney = null;

        //获取newLevel的晋级奖励
        switch (newLevel) {
            case AGENT:
                //awardMoney = map.get(DistributionItemEnum.PROMOTION_AGENT_TIMES).getValue();
                awardMoney = "0";
                break;
            case V1:
                awardMoney = map.get(DistributionItemEnum.PROMOTION_AWARD_V1.name()).getValue();
                break;
            case V2:
                awardMoney = map.get(DistributionItemEnum.PROMOTION_AWARD_V2.name()).getValue();
                break;
            case V3:
                awardMoney = map.get(DistributionItemEnum.PROMOTION_AWARD_V3.name()).getValue();
                break;
            default:
                break;
        }

        if (null != awardMoney) {
            AccountMoneyFlow accountMoneyFlow = new AccountMoneyFlow();
            accountMoneyFlow.setMemberId(member.getId());
            accountMoneyFlow.setDistributMoney(new BigDecimal(awardMoney));
            accountMoneyFlow.setAccountMoneyType(AccountMoneyTypeEnum.PROMOTION_AWARD.name());
            accountMoneyFlow.setOrderId(orderId);
            accountMoneyFlow.setPromotionLevel(newLevel.name());

            if (accountMoneyFlowService.insert(accountMoneyFlow)) {

                member.setMoneyTotalEarn(member.getMoneyTotalEarn().add(accountMoneyFlow.getDistributMoney()));
                memberService.update(member);
            }
        }

        return awardMoney;

    }

    /**
     * 发送消息通知
     */
    private void sendPromotionMessage(Member member, MemberLevelEnum newLevel, BigDecimal money) {

        messageManager.sendPromotionAward(member.getId(), newLevel.name(), money);
    }

}
