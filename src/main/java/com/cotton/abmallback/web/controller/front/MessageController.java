package com.cotton.abmallback.web.controller.front;

import com.cotton.abmallback.enumeration.MessageTypeEnum;
import com.cotton.abmallback.manager.MessageManager;
import com.cotton.abmallback.model.MsgMemberMessage;
import com.cotton.abmallback.model.MsgPlatformMessage;
import com.cotton.abmallback.service.MsgMemberMessageService;
import com.cotton.abmallback.service.MsgPlatformMessageService;
import com.cotton.abmallback.web.controller.ABMallFrontBaseController;
import com.cotton.base.common.RestResponse;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Message
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/24
 */
@Controller
@RequestMapping("/message")
public class MessageController extends ABMallFrontBaseController {

    private Logger logger = LoggerFactory.getLogger(MessageController.class);

    private final MsgMemberMessageService msgMemberMessageService;

    private final MsgPlatformMessageService msgPlatformMessageService;

    private final MessageManager messageManager;

    @Autowired
    public MessageController(MsgMemberMessageService msgMemberMessageService, MsgPlatformMessageService msgPlatformMessageService, MessageManager messageManager) {
        this.msgMemberMessageService = msgMemberMessageService;
        this.msgPlatformMessageService = msgPlatformMessageService;
        this.messageManager = messageManager;
    }

    @ResponseBody
    @RequestMapping(value = "/example")
    public RestResponse<Map<String, Object>> example() {

        Map<String, Object> map = new HashMap<>(2);

        return RestResponse.getSuccesseResponse(map);
    }

    @ResponseBody
    @RequestMapping(value = "/index",method = {RequestMethod.GET})
    public RestResponse<Map<String, Object>> index() {

        Map<String, Object> map = new HashMap<>(2);

        //晋级奖励
        Example example = new Example(MsgMemberMessage.class);
        example.setOrderByClause("gmt_create desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted",false);
        criteria.andEqualTo("memberId",getCurrentMemberId());
        criteria.andEqualTo("type",MessageTypeEnum.PROMOTION_AWARD.name());

        PageInfo<MsgMemberMessage> msgMemberMessagePageInfo = msgMemberMessageService.query(1,1,example);
        if(msgMemberMessagePageInfo != null && msgMemberMessagePageInfo.getList() != null
                && msgMemberMessagePageInfo.getList().size()>0) {
            Map<String,Object> subMap = new HashMap<>(2);
            subMap.put("msg",msgMemberMessagePageInfo.getList().get(0));

            criteria.andEqualTo("isRead",false);
            long count = msgMemberMessageService.count(example);
            subMap.put("unReadCount",count);

            map.put(MessageTypeEnum.PROMOTION_AWARD.name(),subMap);
        }

        //分享奖励
        Example example1 = new Example(MsgMemberMessage.class);
        example1.setOrderByClause("gmt_create desc");

        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("isDeleted",false);
        criteria1.andEqualTo("memberId",getCurrentMemberId());
        criteria1.andEqualTo("type",MessageTypeEnum.SHARE_AWARD.name());

        msgMemberMessagePageInfo = msgMemberMessageService.query(1,1,example1);
        if(msgMemberMessagePageInfo != null && msgMemberMessagePageInfo.getList() != null
                && msgMemberMessagePageInfo.getList().size()>0) {
            Map<String,Object> subMap = new HashMap<>(2);
            subMap.put("msg",msgMemberMessagePageInfo.getList().get(0));

            criteria1.andEqualTo("isRead",false);
            long count = msgMemberMessageService.count(example1);
            subMap.put("unReadCount",count);

            map.put(MessageTypeEnum.SHARE_AWARD.name(),subMap);
        }

        //复购奖励
        Example example2 = new Example(MsgMemberMessage.class);
        example2.setOrderByClause("gmt_create desc");

        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("isDeleted",false);
        criteria2.andEqualTo("memberId",getCurrentMemberId());
        criteria2.andEqualTo("type",MessageTypeEnum.REPURCHASE_AWARD.name());

        msgMemberMessagePageInfo = msgMemberMessageService.query(1,1,example2);
        if(msgMemberMessagePageInfo != null && msgMemberMessagePageInfo.getList() != null
                && msgMemberMessagePageInfo.getList().size()>0) {
            Map<String,Object> subMap = new HashMap<>(2);
            subMap.put("msg",msgMemberMessagePageInfo.getList().get(0));

            criteria2.andEqualTo("isRead",false);
            long count = msgMemberMessageService.count(example2);
            subMap.put("unReadCount",count);

            map.put(MessageTypeEnum.REPURCHASE_AWARD.name(),subMap);
        }

        //高管奖励
        Example example3 = new Example(MsgMemberMessage.class);
        example3.setOrderByClause("gmt_create desc");

        Example.Criteria criteria3 = example3.createCriteria();
        criteria3.andEqualTo("isDeleted",false);
        criteria3.andEqualTo("memberId",getCurrentMemberId());
        criteria3.andEqualTo("type",MessageTypeEnum.EXECUTIVE_AWARD.name());

        msgMemberMessagePageInfo = msgMemberMessageService.query(1,1,example3);
        if(msgMemberMessagePageInfo != null && msgMemberMessagePageInfo.getList() != null
                && msgMemberMessagePageInfo.getList().size()>0) {
            Map<String,Object> subMap = new HashMap<>(2);
            subMap.put("msg",msgMemberMessagePageInfo.getList().get(0));

            criteria3.andEqualTo("isRead",false);
            long count = msgMemberMessageService.count(example3);
            subMap.put("unReadCount",count);

            map.put(MessageTypeEnum.EXECUTIVE_AWARD.name(),subMap);
        }

        // 平台通知
        Example example4 = new Example(MsgMemberMessage.class);
        example4.setOrderByClause("gmt_create desc");

        Example.Criteria criteria4 = example4.createCriteria();
        criteria4.andEqualTo("isDeleted",false);
        criteria4.andEqualTo("memberId",getCurrentMemberId());
        criteria4.andEqualTo("type",MessageTypeEnum.SYSTEM_NOTICE.name());

        msgMemberMessagePageInfo = msgMemberMessageService.query(1,1,example4);
        if(msgMemberMessagePageInfo != null && msgMemberMessagePageInfo.getList() != null
                && msgMemberMessagePageInfo.getList().size()>0) {
            Map<String,Object> subMap = new HashMap<>(2);
            subMap.put("msg",msgMemberMessagePageInfo.getList().get(0));

            criteria4.andEqualTo("isRead",false);
            long count = msgMemberMessageService.count(example4);
            subMap.put("unReadCount",count);

            map.put(MessageTypeEnum.SYSTEM_NOTICE.name(),subMap);
        }

        // 活动奖励
        Example example5 = new Example(MsgMemberMessage.class);
        example5.setOrderByClause("gmt_create desc");

        Example.Criteria criteria5 = example5.createCriteria();
        criteria5.andEqualTo("isDeleted",false);
        criteria5.andEqualTo("memberId",getCurrentMemberId());
        criteria5.andEqualTo("type",MessageTypeEnum.ACTIVITY_AWARD.name());

        msgMemberMessagePageInfo = msgMemberMessageService.query(1,1,example5);
        if(msgMemberMessagePageInfo != null && msgMemberMessagePageInfo.getList() != null
                && msgMemberMessagePageInfo.getList().size()>0) {
            Map<String,Object> subMap = new HashMap<>(2);
            subMap.put("msg",msgMemberMessagePageInfo.getList().get(0));

            criteria5.andEqualTo("isRead",false);
            long count = msgMemberMessageService.count(example5);
            subMap.put("unReadCount",count);

            map.put(MessageTypeEnum.ACTIVITY_AWARD.name(),subMap);
        }

        return RestResponse.getSuccesseResponse(map);
    }

    @ResponseBody
    @RequestMapping(value = "/list",method = {RequestMethod.GET})
    public RestResponse<List<MsgMemberMessage>> list(@RequestParam(defaultValue = "1") int pageNum,
                                                     @RequestParam(defaultValue = "4") int pageSize,
                                                     @RequestParam(defaultValue = "PROMOTION_AWARD")String type) {

        Example example = new Example(MsgMemberMessage.class);
        example.setOrderByClause("gmt_create desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted",false);
        criteria.andEqualTo("memberId",getCurrentMemberId());
        criteria.andEqualTo("type",type);
        PageInfo<MsgMemberMessage> msgMemberMessagePageInfo = msgMemberMessageService.query(pageNum,pageSize,example);

        if(msgMemberMessagePageInfo == null ){
            logger.error("读取消息列表失败");
            return RestResponse.getSystemInnerErrorResponse();
        }
        return RestResponse.getSuccesseResponse(msgMemberMessagePageInfo.getList());
    }


    @ResponseBody
    @RequestMapping(value = "/readMessage",method = {RequestMethod.POST})
    public RestResponse<Void> readMessage(@RequestParam()long messageId) {

        MsgMemberMessage msgMemberMessage = new MsgMemberMessage();
        msgMemberMessage.setId(messageId);
        msgMemberMessage.setIsRead(true);

        if(msgMemberMessageService.update(msgMemberMessage)){
            return RestResponse.getSuccesseResponse();
        }else {
            return RestResponse.getFailedResponse(500,"更新阅读状态失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/platformMessage",method = {RequestMethod.GET})
    public RestResponse<MsgPlatformMessage> platformMessage(@RequestParam()long systemMessageId) {

        MsgPlatformMessage msgPlatformMessage = msgPlatformMessageService.getById(systemMessageId);

        if(null != msgPlatformMessage){
            return RestResponse.getSuccesseResponse(msgPlatformMessage);
        }else {
            return RestResponse.getFailedResponse(500,"读取消息失败");
        }
    }

}