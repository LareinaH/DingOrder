package com.cotton.abmallback.service.impl;

import com.cotton.abmallback.enumeration.CashStatusEnum;
import com.cotton.abmallback.enumeration.RedpackStatusEnum;
import com.cotton.abmallback.model.Member;
import com.cotton.abmallback.model.RedpackRecord;
import com.cotton.abmallback.service.MemberService;
import com.cotton.abmallback.service.RedpackRecordService;
import com.cotton.abmallback.third.wechat.JufenyunResultObject;
import com.cotton.abmallback.third.wechat.JufenyunService;
import com.cotton.abmallback.third.wechat.YaoyaolaService;
import com.cotton.base.service.impl.BaseServiceImpl;
import com.cotton.abmallback.model.CashPickUp;
import com.cotton.abmallback.service.CashPickUpService;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CashPickUp
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/10
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CashPickUpServiceImpl extends BaseServiceImpl<CashPickUp> implements CashPickUpService {


    private final WxPayService wxPayService;

    private final RedpackRecordService redpackRecordService;

    private final JufenyunService jufenyunService;

    private final YaoyaolaService yaoyaolaService;

    private final MemberService memberService;

    public CashPickUpServiceImpl(WxPayService wxPayService, RedpackRecordService redpackRecordService, JufenyunService jufenyunService, YaoyaolaService yaoyaolaService, MemberService memberService) {
        this.wxPayService = wxPayService;
        this.redpackRecordService = redpackRecordService;
        this.jufenyunService = jufenyunService;
        this.yaoyaolaService = yaoyaolaService;
        this.memberService = memberService;
    }

    @Override
    public void checkRedpack() {

        Example example = new Example(RedpackRecord.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("gmt_create desc");
        criteria.andEqualTo("isDeleted", false);

        List<String> statusList = new ArrayList<>();
        statusList.add(RedpackStatusEnum.WAIT_SEND.name());
        statusList.add(RedpackStatusEnum.SEND.name());
        criteria.andIn("status", statusList);

        Date now = new Date();
        now = DateUtils.addMinutes(now,-1);
        criteria.andLessThan("gmtCreate",now);

        PageInfo<RedpackRecord> redpackRecordPageInfo = redpackRecordService.query(1, 20, example);

        if (null != redpackRecordPageInfo && redpackRecordPageInfo.getList().size() > 0) {

            for (RedpackRecord redpackRecord : redpackRecordPageInfo.getList()) {

                JufenyunResultObject jufenyunResultObject = yaoyaolaService.getRedpackInfo(redpackRecord.getRedpackSn());

                if (null != jufenyunResultObject) {
                    switch (jufenyunResultObject.getRedpack().getStatus()) {
                        case 1:
                        case 4:
                            cashPickUpSuccess(redpackRecord);
                            break;
                        case 0:
                        case 3:
                            cashPickUpFailed(redpackRecord,RedpackStatusEnum.RETURN);
                            break;
                        case 2:
                            cashPickUpFailed(redpackRecord,RedpackStatusEnum.WAIT_RETURN);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    private void cashPickUpSuccess(RedpackRecord redpackRecord){

        redpackRecord.setStatus(RedpackStatusEnum.SEND_SCUUESS.name());

        redpackRecordService.update(redpackRecord);

        //更新提现状态
        CashPickUp cashPickUp = getById(redpackRecord.getCashPickUpId());

        if(null != cashPickUp){
            cashPickUp.setCashStatus(CashStatusEnum.ARRIVAL.name());

            //解除锁定金额
            Member member = memberService.getById(cashPickUp.getMemberId());
            member.setMoneyTotalTake(member.getMoneyTotalTake().add(cashPickUp.getMoney()));
            member.setMoneyLock(member.getMoneyLock().subtract(cashPickUp.getMoney()));
            memberService.update(member);
            update(cashPickUp);
        }
    }

    private void cashPickUpFailed(RedpackRecord redpackRecord, RedpackStatusEnum status){

        redpackRecord.setStatus(status.name());

        redpackRecordService.update(redpackRecord);

        //更新提现状态
        CashPickUp cashPickUp = getById(redpackRecord.getCashPickUpId());

        if(null != cashPickUp){
            cashPickUp.setCashStatus(CashStatusEnum.TIME_OUT.name());

            //解除锁定金额
            Member member = memberService.getById(cashPickUp.getMemberId());
            member.setMoneyLock(member.getMoneyLock().subtract(cashPickUp.getMoney()));
            memberService.update(member);
            update(cashPickUp);
        }

    }

}