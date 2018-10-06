package com.cotton.abmallback.web.controller.admin;

import com.cotton.abmallback.model.MsgPlatformMessage;
import com.cotton.abmallback.service.MsgPlatformMessageService;
import com.cotton.base.common.RestResponse;
import com.cotton.base.controller.BaseController;
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
 * MsgPlatformMessageManager
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/7/3
 */
@Controller
@RequestMapping("/admin/msgPlatformMessage")
public class MsgPlatformMessageManagerController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(MsgPlatformMessageManagerController.class);

    private MsgPlatformMessageService msgPlatformMessageService;

    @Autowired
    public MsgPlatformMessageManagerController(MsgPlatformMessageService msgPlatformMessageService) {
        this.msgPlatformMessageService = msgPlatformMessageService;
    }

    @ResponseBody
    @RequestMapping(value = "/example")
    public RestResponse<Map<String, Object>> example() {

        Map<String, Object> map = new HashMap<>(2);

        return RestResponse.getSuccesseResponse(map);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public RestResponse<Void> add(@RequestBody MsgPlatformMessage msgPlatformMessage) {

        if (msgPlatformMessageService.insert(msgPlatformMessage)) {
            return RestResponse.getSuccesseResponse();
        } else {
            return RestResponse.getFailedResponse(500, "增加失败");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public RestResponse<Void> update(@RequestBody MsgPlatformMessage msgPlatformMessage) {

        if (!msgPlatformMessageService.update(msgPlatformMessage)) {
            return RestResponse.getFailedResponse(500, "更新数据失败,MsgPlatformMessage为:" + msgPlatformMessage.toString());
        }

        return RestResponse.getSuccesseResponse();
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    public RestResponse<MsgPlatformMessage> get(long msgPlatformMessageId) {

        MsgPlatformMessage msgPlatformMessage = msgPlatformMessageService.getById(msgPlatformMessageId);

        if (null == msgPlatformMessage) {
            return RestResponse.getFailedResponse(500, "无法查找数据,请检查msgPlatformMessageId是否正确");

        }
        return RestResponse.getSuccesseResponse(msgPlatformMessage);
    }

    @ResponseBody
    @RequestMapping(value = "/queryList", method = {RequestMethod.GET})
    public RestResponse<List<MsgPlatformMessage>> queryList() {

        Example example = new Example(MsgPlatformMessage.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);

        List<MsgPlatformMessage> msgPlatformMessageList = msgPlatformMessageService.queryList(example);

        if (msgPlatformMessageList == null) {
            logger.error("读取列表失败");
            return RestResponse.getSystemInnerErrorResponse();
        }
        return RestResponse.getSuccesseResponse(msgPlatformMessageList);
    }

    @ResponseBody
    @RequestMapping(value = "/queryPageList", method = {RequestMethod.POST})
    public RestResponse<PageInfo<MsgPlatformMessage>> queryPageList(@RequestParam(defaultValue = "1") int pageNum,
                                                                    @RequestParam(defaultValue = "4") int pageSize,
                                                                    @RequestBody(required = false)Map<String,Object> conditions) {

        Example example = new Example(MsgPlatformMessage.class);
        example.setOrderByClause("gmt_create desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);

        if(null != conditions){

        }

        PageInfo<MsgPlatformMessage> msgPlatformMessagePageInfo = msgPlatformMessageService.query(pageNum, pageSize, example);

        if (msgPlatformMessagePageInfo == null) {
            logger.error("读取列表失败");
            return RestResponse.getSystemInnerErrorResponse();
        }
        return RestResponse.getSuccesseResponse(msgPlatformMessagePageInfo);
    }


    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE})
    public RestResponse<Map<String, Object>> delete(long msgPlatformMessageId) {

        MsgPlatformMessage msgPlatformMessage = msgPlatformMessageService.getById(msgPlatformMessageId);

        if (null == msgPlatformMessage) {
            return RestResponse.getFailedResponse(500, "无法查找数据,请检查msgPlatformMessageId 是否正确");

        }

        msgPlatformMessage.setIsDeleted(true);

        if (!msgPlatformMessageService.update(msgPlatformMessage)) {
            return RestResponse.getFailedResponse(500, "删除数据失败,msgPlatformMessageId为:" + msgPlatformMessageId);
        }

        return RestResponse.getSuccesseResponse();
    }
}