package com.cotton.abmallback.third.wechat.mp.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cotton.abmallback.enumeration.MemberLevelEnum;
import com.cotton.abmallback.manager.PromotionManager;
import com.cotton.abmallback.model.Member;
import com.cotton.abmallback.service.MemberService;
import com.cotton.abmallback.third.wechat.mp.builder.TextBuilder;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class ScanHandler extends AbstractHandler {

    private final MemberService memberService;

    private final PromotionManager promotionManager;

    public ScanHandler(MemberService memberService, PromotionManager promotionManager) {
        this.memberService = memberService;
        this.promotionManager = promotionManager;
    }

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService weixinService, WxSessionManager sessionManager) throws WxErrorException {

        this.logger.info("处理已关注的扫码事件 : {}",wxMessage);
        // 获取微信用户基本信息
        WxMpUser userWxInfo = weixinService.getUserService().userInfo(wxMessage.getFromUser(), null);

        if (userWxInfo != null) {
            //查看用户是否存在
            Member model = new Member();
            model.setUnionId(userWxInfo.getUnionId());
            model.setIsDeleted(false);

            List<Member> memberList = memberService.queryList(model);

            if (null != memberList && memberList.size() > 0) {

                this.logger.info("该用户已经存在: " + wxMessage.getFromUser());

                Member member = memberList.get(0);
                if (null == member.getReferrerId()) {
                    getReferUser(wxMessage, member);
                    countReferUser(member.getReferrerId());
                    member.setOpenId(userWxInfo.getOpenId());
                    memberService.update(member);
                }
            } else {

                //注册新用户
                Member newMember = new Member();
                newMember.setUnionId(userWxInfo.getUnionId());
                newMember.setOpenId(userWxInfo.getOpenId());
                newMember.setName(userWxInfo.getNickname());
                newMember.setWechatName(userWxInfo.getNickname());
                newMember.setIsDeleted(false);
                newMember.setLevel(MemberLevelEnum.WHITE.name());
                newMember.setPhoto(userWxInfo.getHeadImgUrl());

                //获取引荐人信息
                getReferUser(wxMessage, newMember);

                memberService.insert(newMember);
                countReferUser(newMember.getReferrerId());

            }

            try {
                return new TextBuilder().build("感谢关注绿色云鼎公众号！", wxMessage, weixinService);
            } catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        }

        return new TextBuilder().build("感谢关注绿色云鼎公众号！", wxMessage, weixinService);
    }


    private void getReferUser(WxMpXmlMessage wxMessage, Member member) {
        String eventKey = wxMessage.getEventKey();

        this.logger.info("eventKey: " + eventKey);

        //默认设置为云鼎的id
        Long referrerId = null;

        if (!StringUtils.isBlank(eventKey)) {

            String jsonStr = eventKey.substring(eventKey.indexOf("{"),eventKey.indexOf("}") + 1);

            JSONObject jsonObject = JSON.parseObject(jsonStr);

            if (null != jsonObject && jsonObject.get("referrerId") != null) {
                referrerId = Long.valueOf(jsonObject.get("referrerId").toString());
            }
        }
        member.setReferrerId(referrerId);
    }

    private void countReferUser(Long referMemberId) {

        Member referMember = memberService.getById(referMemberId);

        if(referMember != null) {
            referMember.setReferTotalCount(referMember.getReferTotalCount() + 1);

            promotionManager.memberPromotion(referMember, 0L);

            memberService.update(referMember);
        }
    }
}
