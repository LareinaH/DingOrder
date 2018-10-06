package com.cotton.abmallback.web.controller.front;

import com.cotton.abmallback.enumeration.CashStatusEnum;
import com.cotton.abmallback.enumeration.RedpackStatusEnum;
import com.cotton.abmallback.model.CashPickUp;
import com.cotton.abmallback.model.Member;
import com.cotton.abmallback.model.RedpackRecord;
import com.cotton.abmallback.service.CashPickUpService;
import com.cotton.abmallback.service.MemberService;
import com.cotton.abmallback.service.RedpackRecordService;
import com.cotton.abmallback.third.wechat.JufenyunResultObject;
import com.cotton.abmallback.third.wechat.JufenyunService;
import com.cotton.abmallback.third.wechat.YaoyaolaService;
import com.cotton.abmallback.web.controller.ABMallFrontBaseController;
import com.cotton.base.common.RestResponse;
import com.github.pagehelper.PageInfo;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Cash
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/10
 */
@Controller
@RequestMapping("/cash")
public class CashController extends ABMallFrontBaseController {

    private Logger logger = LoggerFactory.getLogger(CashController.class);

    private final CashPickUpService cashPickUpService;

    private final JufenyunService jufenyunService;

    private final YaoyaolaService yaoyaolaService;

    private final MemberService memberService;

    private final RedpackRecordService redpackRecordService;

    private final WxMpService wxMpService;

    @Autowired
    public CashController(CashPickUpService cashPickUpService, JufenyunService jufenyunService, YaoyaolaService yaoyaolaService, MemberService memberService, RedpackRecordService redpackRecordService, WxMpService wxMpService) {
        this.cashPickUpService = cashPickUpService;
        this.jufenyunService = jufenyunService;
        this.yaoyaolaService = yaoyaolaService;
        this.memberService = memberService;
        this.redpackRecordService = redpackRecordService;
        this.wxMpService = wxMpService;
    }

    @ResponseBody
    @RequestMapping(value = "/example")
    public RestResponse<Map<String, Object>> example() {


        Map<String, Object> map = new HashMap<>(2);

        return RestResponse.getSuccesseResponse(map);
    }


    /**
     * 提现记录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/cashPickUpList", method = {RequestMethod.GET})
    public RestResponse<List<CashPickUp>> cashPickUpList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {

        Example example = new Example(CashPickUp.class);
        example.setOrderByClause("gmt_create desc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("memberId", getCurrentMemberId());
        criteria.andEqualTo("isDeleted", false);
        PageInfo<CashPickUp> goodsPageInfo = cashPickUpService.query(pageNum, pageSize, example);

        if (goodsPageInfo == null) {
            logger.error("读取提现记录失败");
            return RestResponse.getSystemInnerErrorResponse();
        }
        return RestResponse.getSuccesseResponse(goodsPageInfo.getList());
    }

    /**
     * 申请提现
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/applyPickUpCash", method = {RequestMethod.POST})
    public RestResponse<Void> applyPickUpCash(@RequestParam() BigDecimal money) {


        Member member = getCurrentMember();

        if (StringUtils.isBlank(member.getOpenId())) {
            return RestResponse.getFailedResponse(500, "要关注公众号才可以提现哦！");

        }
        BigDecimal availablePickUpCashAmount = member.getMoneyTotalEarn().subtract(member.getMoneyTotalTake()).subtract(member.getMoneyLock());

        //判断提现金额
        if (money.compareTo(availablePickUpCashAmount) > 0) {
            return RestResponse.getFailedResponse(500, "提现金额不能大于可提现金额。");
        }

        if (money.compareTo(new BigDecimal(0.3)) < 0) {
            return RestResponse.getFailedResponse(500, "提现金额不能小于0.3元。");
        }

        //TODO:上限金额根据等级判断
        if (money.compareTo(new BigDecimal(200)) > 0) {
            return RestResponse.getFailedResponse(500, "提现金额不能大于200元。");
        }

        //判断当日提现次数
        ZoneId zone = ZoneId.systemDefault();
        //当天零点
        Date todayStart = Date.from(LocalDateTime.of(LocalDate.now(), LocalTime.MIN).atZone(zone).toInstant());
        //当天结束
        Date todayEnd = Date.from(LocalDateTime.of(LocalDate.now(), LocalTime.MAX).atZone(zone).toInstant());


        Example example = new Example(CashPickUp.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("memberId", getCurrentMemberId());
        criteria.andEqualTo("isDeleted", false);
        criteria.andGreaterThanOrEqualTo("gmtCreate", todayStart);
        criteria.andLessThanOrEqualTo("gmtCreate", todayEnd);

        long count = cashPickUpService.count(example);

        if (count > 0) {
            return RestResponse.getFailedResponse(500, "今天已经申请过提现了，请明天再申请吧！");
        }

        //发送红包
        //JufenyunResultObject jufenyunResultObject = jufenyunService.sendRedpack(member.getOpenId(), money);

        JufenyunResultObject jufenyunResultObject = yaoyaolaService.sendRedpack(member.getOpenId(), money);

        if (null == jufenyunResultObject) {
            return RestResponse.getFailedResponse(500, "提现异常，请联系客服人员。");
        }


        CashPickUp cashPickUp = new CashPickUp();
        cashPickUp.setMemberId(getCurrentMemberId());
        cashPickUp.setMoney(money);
        cashPickUp.setCashStatus(CashStatusEnum.APPLY.name());

        if (cashPickUpService.insert(cashPickUp)) {

            //创建红包记录
            RedpackRecord redpackRecord = new RedpackRecord();
            redpackRecord.setCashPickUpId(cashPickUp.getId());
            redpackRecord.setRedpackSn(jufenyunResultObject.getRedpack_sn());
            redpackRecord.setRedpackUrl(jufenyunResultObject.getRedpack_url());
            redpackRecord.setRedpeckSource("jufenyun");
            redpackRecord.setStatus(RedpackStatusEnum.WAIT_SEND.name());
            redpackRecord.setTotalMoney(money);

            redpackRecordService.insert(redpackRecord);

            member.setMoneyLock(member.getMoneyLock().add(money));
            memberService.update(member);

            //如果消息发送失败 红包会退回
            if (!sendWxMessage(member.getOpenId(), jufenyunResultObject.getRedpack_url(), money)) {

                return RestResponse.getFailedResponse(500, "申请提现失败,消息发送失败");
            }

            return RestResponse.getSuccesseResponse();
        } else

        {
            return RestResponse.getFailedResponse(500, "申请提现失败");
        }


    }

    private boolean sendWxMessage(String memberOpenId, String url, BigDecimal money) {
        WxMpTemplateMessage mpTemplateMessage = new WxMpTemplateMessage();
        mpTemplateMessage.setToUser(memberOpenId);
        mpTemplateMessage.setTemplateId("tT2nGgVk-m4R-oCylqHHmbSsSRNJVFy2tnJvqklOgYY");
        mpTemplateMessage.setUrl(url);
        List<WxMpTemplateData> list = new ArrayList<>();
        WxMpTemplateData data1 = new WxMpTemplateData("first", "客官您好,您在云鼎绿色的返利提现，已经飞奔而来了。");
        list.add(data1);
        WxMpTemplateData data2 = new WxMpTemplateData("keyword1", LocalDateTime.now().toString());
        list.add(data2);
        String moneyStr = "￥" + money.toString() + "元";
        WxMpTemplateData data3 = new WxMpTemplateData("keyword2", moneyStr);
        list.add(data3);
        WxMpTemplateData data4 = new WxMpTemplateData("remark", "点击查看,赶快领取吧！（60s内有效哦，抓紧时间）");
        list.add(data4);
        mpTemplateMessage.setData(list);

        try {
            String string = wxMpService.getTemplateMsgService().sendTemplateMsg(mpTemplateMessage);
            logger.info(string);

            return true;
        } catch (WxErrorException e) {
            logger.error("发送消息失败", e);
        }
        return false;
    }
}