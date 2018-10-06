package com.cotton.abmallback.web.controller.admin;

import com.cotton.abmallback.enumeration.ContentStudyStatusEnum;
import com.cotton.abmallback.mapper.ContentStudyMapper;
import com.cotton.abmallback.model.ContentStudy;
import com.cotton.abmallback.service.ContentStudyService;
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
 * ContentStudyManager
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/7/16
 */
@Controller
@RequestMapping("/admin/contentStudy")
public class ContentStudyManagerController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(ContentStudyManagerController.class);

    private ContentStudyService contentStudyService;

    @Autowired
    ContentStudyMapper contentStudyMapper;

    @Autowired
    public ContentStudyManagerController(ContentStudyService contentStudyService) {
        this.contentStudyService = contentStudyService;
    }

    @ResponseBody
    @RequestMapping(value = "/example")
    public RestResponse<Map<String, Object>> example() {

        Map<String, Object> map = new HashMap<>(2);

        return RestResponse.getSuccesseResponse(map);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public RestResponse<Void> add(@RequestBody ContentStudy contentStudy) {

        if (contentStudyService.insert(contentStudy)) {
            return RestResponse.getSuccesseResponse();
        } else {
            return RestResponse.getFailedResponse(500, "增加失败");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public RestResponse<Void> update(@RequestBody ContentStudy contentStudy) {

        if (!contentStudyService.update(contentStudy)) {
            return RestResponse.getFailedResponse(500, "更新数据失败,ContentStudy为:" + contentStudy.toString());
        }

        return RestResponse.getSuccesseResponse();
    }

    @ResponseBody
    @RequestMapping(value = "/updateStatus", method = {RequestMethod.POST})
    public RestResponse<Void> updateStatus(
            @RequestParam(value = "id") Long id,
            @RequestParam(value = "status") String status
    ) {
        contentStudyMapper.updateStatus(id, status);
        return RestResponse.getSuccesseResponse();
    }


    @ResponseBody
    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    public RestResponse<ContentStudy> get(long contentStudyId) {

        ContentStudy contentStudy = contentStudyService.getById(contentStudyId);

        if (null == contentStudy) {
            return RestResponse.getFailedResponse(500, "无法查找数据,请检查contentStudyId是否正确");

        }
        return RestResponse.getSuccesseResponse(contentStudy);
    }

    @ResponseBody
    @RequestMapping(value = "/queryList", method = {RequestMethod.GET})
    public RestResponse<List<ContentStudy>> queryList() {

        Example example = new Example(ContentStudy.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);

        List<ContentStudy> contentStudyList = contentStudyService.queryList(example);

        if (contentStudyList == null) {
            logger.error("读取列表失败");
            return RestResponse.getSystemInnerErrorResponse();
        }
        return RestResponse.getSuccesseResponse(contentStudyList);
    }

    @ResponseBody
    @RequestMapping(value = "/queryPageList", method = {RequestMethod.POST})
    public RestResponse<PageInfo<ContentStudy>> queryPageList(@RequestParam(defaultValue = "1") int pageNum,
                                                              @RequestParam(defaultValue = "4") int pageSize,
                                                              @RequestBody(required = false) Map<String, Object> conditions) {

        Example example = new Example(ContentStudy.class);
        example.setOrderByClause("gmt_create desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);

        if (null != conditions) {

            if(null != conditions.get("title")){
                criteria.andLike("title","%" + conditions.get("title").toString()+ "%");
            }
        }

        PageInfo<ContentStudy> contentStudyPageInfo = contentStudyService.query(pageNum, pageSize, example);

        if (contentStudyPageInfo == null) {
            logger.error("读取列表失败");
            return RestResponse.getSystemInnerErrorResponse();
        }
        return RestResponse.getSuccesseResponse(contentStudyPageInfo);
    }


    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE})
    public RestResponse<Map<String, Object>> delete(long contentStudyId) {

        ContentStudy contentStudy = contentStudyService.getById(contentStudyId);

        if (null == contentStudy) {
            return RestResponse.getFailedResponse(500, "无法查找数据,请检查contentStudyId 是否正确");

        }

        contentStudy.setIsDeleted(true);

        if (!contentStudyService.update(contentStudy)) {
            return RestResponse.getFailedResponse(500, "删除数据失败,contentStudyId为:" + contentStudyId);
        }

        return RestResponse.getSuccesseResponse();
    }
}