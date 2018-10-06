package com.cotton.abmallback.web.controller.admin;

import com.cotton.abmallback.model.DistributionConfig;
import com.cotton.abmallback.model.MsgMessageTemplate;
import com.cotton.abmallback.model.vo.ConfigObject;
import com.cotton.abmallback.service.MsgMessageTemplateService;
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
 * MsgMessageTemplateManager
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/6
 */
@Controller
@RequestMapping("/admin/msgMessageTemplate")
public class MsgMessageTemplateManagerController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(MsgMessageTemplateManagerController.class);

    private MsgMessageTemplateService msgMessageTemplateService;

    @Autowired
    public MsgMessageTemplateManagerController(MsgMessageTemplateService msgMessageTemplateService) {
        this.msgMessageTemplateService = msgMessageTemplateService;
    }

    @ResponseBody
    @RequestMapping(value = "/example")
    public RestResponse<Map<String, Object>> example() {

        Map<String, Object> map = new HashMap<>(2);

        return RestResponse.getSuccesseResponse(map);
    }

    @ResponseBody
    @RequestMapping(value = "/config")
    public RestResponse<Map<String, Object>> getConfig() {

        Map<String, Object> map = new HashMap<>(2);

        MsgMessageTemplate model = new MsgMessageTemplate();
        model.setIsDeleted(false);

        List<MsgMessageTemplate> msgMessageTemplateList =  msgMessageTemplateService.queryList(model);

        for(MsgMessageTemplate msgMessageTemplate : msgMessageTemplateList){

            ConfigObject.ConfigItem obj = ConfigObject.createConfigItem();
            obj.setId(msgMessageTemplate.getId());
            obj.setItem(msgMessageTemplate.getItem());
            obj.setValue(msgMessageTemplate.getValue());
            obj.setDefaultVaule(msgMessageTemplate.getDefaultValue());
            map.put(msgMessageTemplate.getItem(),obj);
        }
        return RestResponse.getSuccesseResponse(map);
    }


    @ResponseBody
    @RequestMapping(value = "/updateConfig", method = {RequestMethod.POST})
    public RestResponse<Void> updateConfig(@RequestBody ConfigObject configObject) {

        for(ConfigObject.ConfigItem configItem : configObject.getDataList()) {

            MsgMessageTemplate msgMessageTemplate = new MsgMessageTemplate();
            msgMessageTemplate.setValue(configItem.getValue());
            msgMessageTemplate.setId(configItem.getId());
            msgMessageTemplateService.update(msgMessageTemplate);
        }

        return RestResponse.getSuccesseResponse();
    }


    @ResponseBody
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public RestResponse<Void> update(@RequestBody MsgMessageTemplate msgMessageTemplate) {

        if (!msgMessageTemplateService.update(msgMessageTemplate)) {
            return RestResponse.getFailedResponse(500, "更新数据失败,MsgMessageTemplate为:" + msgMessageTemplate.toString());
        }

        return RestResponse.getSuccesseResponse();
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public RestResponse<Void> add(@RequestBody MsgMessageTemplate msgMessageTemplate) {

        if (msgMessageTemplateService.insert(msgMessageTemplate)) {
            return RestResponse.getSuccesseResponse();
        } else {
            return RestResponse.getFailedResponse(500, "增加失败");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    public RestResponse<MsgMessageTemplate> get(long msgMessageTemplateId) {

        MsgMessageTemplate msgMessageTemplate = msgMessageTemplateService.getById(msgMessageTemplateId);

        if (null == msgMessageTemplate) {
            return RestResponse.getFailedResponse(500, "无法查找数据,请检查msgMessageTemplateId是否正确");

        }
        return RestResponse.getSuccesseResponse(msgMessageTemplate);
    }

    @ResponseBody
    @RequestMapping(value = "/queryList", method = {RequestMethod.GET})
    public RestResponse<List<MsgMessageTemplate>> queryList() {

        Example example = new Example(MsgMessageTemplate.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);

        List<MsgMessageTemplate> msgMessageTemplateList = msgMessageTemplateService.queryList(example);

        if (msgMessageTemplateList == null) {
            logger.error("读取列表失败");
            return RestResponse.getSystemInnerErrorResponse();
        }
        return RestResponse.getSuccesseResponse(msgMessageTemplateList);
    }

    @ResponseBody
    @RequestMapping(value = "/queryPageList", method = {RequestMethod.GET})
    public RestResponse<PageInfo<MsgMessageTemplate>> queryPageList(@RequestParam(defaultValue = "1") int pageNum,
                                                                    @RequestParam(defaultValue = "4") int pageSize) {

        Example example = new Example(MsgMessageTemplate.class);
        example.setOrderByClause("gmt_create desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);

        PageInfo<MsgMessageTemplate> msgMessageTemplatePageInfo = msgMessageTemplateService.query(pageNum, pageSize, example);

        if (msgMessageTemplatePageInfo == null) {
            logger.error("读取列表失败");
            return RestResponse.getSystemInnerErrorResponse();
        }
        return RestResponse.getSuccesseResponse(msgMessageTemplatePageInfo);
    }


    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE})
    public RestResponse<Map<String, Object>> delete(long msgMessageTemplateId) {

        MsgMessageTemplate msgMessageTemplate = msgMessageTemplateService.getById(msgMessageTemplateId);

        if (null == msgMessageTemplate) {
            return RestResponse.getFailedResponse(500, "无法查找数据,请检查msgMessageTemplateId 是否正确");

        }

        msgMessageTemplate.setIsDeleted(true);

        if (!msgMessageTemplateService.update(msgMessageTemplate)) {
            return RestResponse.getFailedResponse(500, "删除数据失败,msgMessageTemplateId为:" + msgMessageTemplateId);
        }

        return RestResponse.getSuccesseResponse();
    }
}