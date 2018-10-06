package com.cotton.abmallback.manager.impl;

import com.cotton.abmallback.enumeration.MemberLevelEnum;
import com.cotton.abmallback.enumeration.MessageTypeEnum;
import com.cotton.abmallback.enumeration.PlatformMessageStatusEnum;
import com.cotton.abmallback.manager.MessageManager;
import com.cotton.abmallback.model.*;
import com.cotton.abmallback.service.*;
import com.cotton.base.third.GeTuiService;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MessageManagerImpl
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/26
 */
@Service
public class MessageManagerImpl implements MessageManager {

    private final MsgMemberMessageService msgMemberMessageService;

    private final MsgPlatformMessageService msgPlatformMessageService;

    private final MsgMessageTemplateService msgMessageTemplateService;

    private final DistributionConfigService distributionConfigService;

    private final GeTuiService geTuiService;

    private final MemberService memberService;

    //private final WxMpService wxMpService;

    public MessageManagerImpl(MsgMemberMessageService msgMemberMessageService, MsgPlatformMessageService msgPlatformMessageService, MsgMessageTemplateService msgMessageTemplateService, DistributionConfigService distributionConfigService, GeTuiService geTuiService, MemberService memberService) {
        this.msgMemberMessageService = msgMemberMessageService;
        this.msgPlatformMessageService = msgPlatformMessageService;
        this.msgMessageTemplateService = msgMessageTemplateService;
        this.distributionConfigService = distributionConfigService;
        this.geTuiService = geTuiService;
        this.memberService = memberService;
    }

    @Override
    public void sendSystemNotice() {
        //查找尚未发送的系统消息
        Example example = new Example(MsgPlatformMessage.class);

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);

        criteria.andLessThanOrEqualTo("gmtPublish", new Date());
        criteria.andEqualTo("messageStatus", PlatformMessageStatusEnum.WAIT_PUBLISH.name());

        List<MsgPlatformMessage> msgPlatformMessageList = msgPlatformMessageService.queryList(example);

        for (MsgPlatformMessage msgPlatformMessage : msgPlatformMessageList) {
            sendSystemNotice(msgPlatformMessage);
        }
    }

    @Override
    public void sendSystemNotice(MsgPlatformMessage msgPlatformMessage) {

        msgPlatformMessage.setMessageStatus(PlatformMessageStatusEnum.PUBLISHED.name());


        //发送用户消息
        Member model = new Member();
        model.setIsDeleted(false);
        List<Member> members = memberService.queryList(model);

        for (Member member : members) {

            MsgMemberMessage msgMemberMessage = new MsgMemberMessage();
            msgMemberMessage.setMemberId(member.getId());
            msgMemberMessage.setType(MessageTypeEnum.SYSTEM_NOTICE.name());
            msgMemberMessage.setSystemMessageId(msgPlatformMessage.getId());
            msgMemberMessage.setTitle(msgPlatformMessage.getTitle());
            msgMemberMessage.setContent(msgPlatformMessage.getBrief());
            msgMemberMessageService.insert(msgMemberMessage);

            //发送个推消息
            geTuiService.pushMessage(msgPlatformMessage.getTitle(), msgPlatformMessage.getBrief(), member.getId());
        }

        msgPlatformMessageService.update(msgPlatformMessage);
    }

    @Override
    public void sendRepurchaseAward(long memberId, BigDecimal money) {
        String context = buildContext(MessageTypeEnum.REPURCHASE_AWARD,memberId,money);
        insertMessage(memberId, "复购奖励", context, MessageTypeEnum.REPURCHASE_AWARD,  null);
    }

    @Override
    public void sendShareAward(long memberId, BigDecimal money) {

        String context = buildContext(MessageTypeEnum.SHARE_AWARD,memberId,money);
        insertMessage(memberId, "分享奖励", context, MessageTypeEnum.SHARE_AWARD,  null);

    }

    @Override
    public void sendExecutiveAward(long memberId, BigDecimal money) {

        String context = buildContext(MessageTypeEnum.EXECUTIVE_AWARD,memberId,money);
        insertMessage(memberId, "高管奖励", context, MessageTypeEnum.EXECUTIVE_AWARD,  null);


    }

    @Override
    public void sendPromotionAward(long memberId, String promotionLevel,BigDecimal money) {

        String context = buildContext(MessageTypeEnum.PROMOTION_AWARD,memberId,money);
        insertMessage(memberId, "升级奖励", context, MessageTypeEnum.PROMOTION_AWARD, promotionLevel);
    }


    private void insertMessage(long memberId, String title, String content, MessageTypeEnum messageTypeEnum,  String promotionLevel) {

        //发送系统内部消息
        MsgMemberMessage msgMemberMessage = new MsgMemberMessage();
        msgMemberMessage.setMemberId(memberId);
        msgMemberMessage.setTitle(title);
        msgMemberMessage.setContent(content);
        msgMemberMessage.setType(messageTypeEnum.name());

        if (messageTypeEnum.equals(MessageTypeEnum.SYSTEM_NOTICE)) {
            msgMemberMessage.setSystemMessageId(1L);
        } else if (messageTypeEnum.equals(MessageTypeEnum.PROMOTION_AWARD)) {
            msgMemberMessage.setPromotionLevel(promotionLevel);
        }

        msgMemberMessageService.insert(msgMemberMessage);

        //发送个推消息

       // geTuiService.pushMessage(title, content, memberId);

        //TODO:发送微信消息

    }

    private String buildContext(MessageTypeEnum messageTypeEnum, long memberId, BigDecimal money) {

        MsgMessageTemplate model = new MsgMessageTemplate();
        model.setType(messageTypeEnum.name());
        model.setIsDeleted(false);
        List<MsgMessageTemplate> msgMessageTemplates = msgMessageTemplateService.queryList(model);

        Map<String, String> map = new HashMap<>(10);

        for (MsgMessageTemplate msgMessageTemplate : msgMessageTemplates) {
            map.put(msgMessageTemplate.getItem(), msgMessageTemplate.getValue());
        }

        Member member = memberService.getById(memberId);

        switch (messageTypeEnum) {

            case REPURCHASE_AWARD:

                return map.get("REPURCHASE_AWARD_1") + money.toString()  + "元" + map.get("REPURCHASE_AWARD_2");

            case SHARE_AWARD:

                return map.get("SHARE_AWARD_1") + money.toString()  + "元" + map.get("SHARE_AWARD_2");

            case PROMOTION_AWARD:
                switch (MemberLevelEnum.valueOf(member.getLevel())) {
                    case AGENT:
                        return map.get("PROMOTION_AWARD_1") + MemberLevelEnum.AGENT.name()  + "级，" + map.get("PROMOTION_AWARD_2");
                    case V1:
                        return map.get("PROMOTION_AWARD_1") + MemberLevelEnum.V1.name()  + "级，" + map.get("PROMOTION_AWARD_2") ;
                    case V2:
                        return map.get("PROMOTION_AWARD_1") + MemberLevelEnum.V2.name()  + "级，" + map.get("PROMOTION_AWARD_2");
                    case V3:
                        return map.get("PROMOTION_AWARD_1") + MemberLevelEnum.V3.name()  + "级，" + map.get("PROMOTION_AWARD_2");
                    default:
                        return "恭喜成功晋级，" + map.get("PROMOTION_AWARD_2");
                }
            case EXECUTIVE_AWARD:
                return map.get("EXECUTIVE_AWARD") + money.toString()  + "元。";

            case ACTIVITY_AWARD:
                break;
            default:
                break;
        }

        return "";
    }
}
