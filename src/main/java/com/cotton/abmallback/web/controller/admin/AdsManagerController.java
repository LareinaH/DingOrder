package com.cotton.abmallback.web.controller.admin;

import com.cotton.abmallback.enumeration.AdTypeEnum;
import com.cotton.abmallback.model.Ads;
import com.cotton.abmallback.service.AdsService;
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
 * AdsManager
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/6
 */
@Controller
@RequestMapping("/admin/ads")
public class AdsManagerController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(AdsManagerController.class);

    private AdsService adsService;

    @Autowired
    public AdsManagerController(AdsService adsService) {
        this.adsService = adsService;
    }

    @ResponseBody
    @RequestMapping(value = "/example")
    public RestResponse<Map<String, Object>> example() {

        Map<String, Object> map = new HashMap<>(2);

        return RestResponse.getSuccesseResponse(map);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public RestResponse<Void> add(@RequestBody Ads ads) {

        if (adsService.insert(ads)) {
            return RestResponse.getSuccesseResponse();
        } else {
            return RestResponse.getFailedResponse(500, "增加失败");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public RestResponse<Void> update(@RequestBody Ads ads) {

        if (!adsService.update(ads)) {
            return RestResponse.getFailedResponse(500, "更新数据失败,Ads为:" + ads.toString());
        }

        return RestResponse.getSuccesseResponse();
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    public RestResponse<Ads> get(long adsId) {

        Ads ads = adsService.getById(adsId);

        if (null == ads) {
            return RestResponse.getFailedResponse(500, "无法查找数据,请检查adsId是否正确");

        }
        return RestResponse.getSuccesseResponse(ads);
    }

    @ResponseBody
    @RequestMapping(value = "/queryList", method = {RequestMethod.GET})
    public RestResponse<List<Ads>> queryList(AdTypeEnum adType) {

        Example example = new Example(Ads.class);
        example.setOrderByClause("sort asc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);
        criteria.andEqualTo("adType",adType.name());


        List<Ads> adsList = adsService.queryList(example);

        if (adsList == null) {
            logger.error("读取列表失败");
            return RestResponse.getSystemInnerErrorResponse();
        }
        return RestResponse.getSuccesseResponse(adsList);
    }

    @ResponseBody
    @RequestMapping(value = "/queryPageList", method = {RequestMethod.GET})
    public RestResponse<PageInfo<Ads>> queryPageList(@RequestParam(defaultValue = "1") int pageNum,
                                                     @RequestParam(defaultValue = "4") int pageSize,
                                                     AdTypeEnum adType) {

        Example example = new Example(Ads.class);
        example.setOrderByClause("gmt_create desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);
        criteria.andEqualTo("adType",adType.name());

        PageInfo<Ads> adsPageInfo = adsService.query(pageNum, pageSize, example);

        if (adsPageInfo == null) {
            logger.error("读取列表失败");
            return RestResponse.getSystemInnerErrorResponse();
        }
        return RestResponse.getSuccesseResponse(adsPageInfo);
    }


    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE})
    public RestResponse<Map<String, Object>> delete(long adsId) {

        Ads ads = adsService.getById(adsId);

        if (null == ads) {
            return RestResponse.getFailedResponse(500, "无法查找数据,请检查adsId 是否正确");

        }

        ads.setIsDeleted(true);

        if (!adsService.update(ads)) {
            return RestResponse.getFailedResponse(500, "删除数据失败,adsId为:" + adsId);
        }

        return RestResponse.getSuccesseResponse();
    }
}