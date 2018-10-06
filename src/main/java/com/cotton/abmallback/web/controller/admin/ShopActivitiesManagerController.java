package com.cotton.abmallback.web.controller.admin;

import com.cotton.abmallback.model.ShopActivities;
import com.cotton.abmallback.model.ShopActivityConfig;
import com.cotton.abmallback.model.ShopActivityGoods;
import com.cotton.abmallback.model.vo.admin.ShopActivitiesVO;
import com.cotton.abmallback.service.ShopActivitiesService;
import com.cotton.abmallback.service.ShopActivityConfigService;
import com.cotton.abmallback.service.ShopActivityGoodsService;
import com.cotton.base.common.RestResponse;
import com.cotton.base.controller.BaseController;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ShopActivitiesManager
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/6
 */
@Controller
@RequestMapping("/admin/shopActivities")
public class ShopActivitiesManagerController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(ShopActivitiesManagerController.class);

    private ShopActivitiesService shopActivitiesService;

    private ShopActivityGoodsService shopActivityGoodsService;

    private ShopActivityConfigService shopActivityConfigService;

    @Autowired
    public ShopActivitiesManagerController(ShopActivitiesService shopActivitiesService, ShopActivityGoodsService shopActivityGoodsService, ShopActivityConfigService shopActivityConfigService) {
        this.shopActivitiesService = shopActivitiesService;
        this.shopActivityGoodsService = shopActivityGoodsService;
        this.shopActivityConfigService = shopActivityConfigService;
    }

    @ResponseBody
    @RequestMapping(value = "/example")
    public RestResponse<Map<String, Object>> example() {

        Map<String, Object> map = new HashMap<>(2);

        return RestResponse.getSuccesseResponse(map);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public RestResponse<Void> add(@RequestBody ShopActivitiesVO shopActivitiesVO) {

        ShopActivities shopActivities = new ShopActivities();
        BeanUtils.copyProperties(shopActivitiesVO,shopActivities);

        if (shopActivitiesService.insert(shopActivities)) {

            //插入商品
            ShopActivityGoods shopActivityGoods = shopActivitiesVO.getShopActivityGoods();
            shopActivityGoods.setActivityId(shopActivities.getId());
            shopActivityGoodsService.insert(shopActivityGoods);

            //插入配置
            for(ShopActivityConfig shopActivityConfig:shopActivitiesVO.getShopActivityConfigList()){
                shopActivityConfig.setShopActivitesId(shopActivities.getId());
                shopActivityConfigService.insert(shopActivityConfig);
            }

            return RestResponse.getSuccesseResponse();
        } else {
            return RestResponse.getFailedResponse(500, "增加失败");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public RestResponse<Void> update(@RequestBody ShopActivitiesVO shopActivitiesVO) {

        ShopActivities shopActivities = new ShopActivities();
        BeanUtils.copyProperties(shopActivitiesVO,shopActivities);

        if (!shopActivitiesService.update(shopActivities)) {
            return RestResponse.getFailedResponse(500, "更新数据失败,ShopActivities为:" + shopActivities.toString());
        }

        //更新商品
        ShopActivityGoods shopActivityGoods = shopActivitiesVO.getShopActivityGoods();
        shopActivityGoodsService.update(shopActivityGoods);

        //更新配置
        for(ShopActivityConfig shopActivityConfig:shopActivitiesVO.getShopActivityConfigList()){
            shopActivityConfigService.update(shopActivityConfig);
        }
        return RestResponse.getSuccesseResponse();
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    public RestResponse<ShopActivitiesVO> get(long shopActivitiesId) {

        ShopActivities shopActivities = shopActivitiesService.getById(shopActivitiesId);

        if (null == shopActivities) {
            return RestResponse.getFailedResponse(500, "无法查找数据,请检查shopActivitiesId是否正确");

        }

        ShopActivitiesVO shopActivitiesVO = new ShopActivitiesVO();
        BeanUtils.copyProperties(shopActivities,shopActivitiesVO);

        ShopActivityGoods model = new ShopActivityGoods();
        model.setIsDeleted(false);
        model.setActivityId(shopActivitiesId);
        ShopActivityGoods shopActivityGoods = shopActivityGoodsService.selectOne(model);
        shopActivitiesVO.setShopActivityGoods(shopActivityGoods);


        ShopActivityConfig model1 = new ShopActivityConfig();
        model1.setShopActivitesId(shopActivitiesId);
        model1.setIsDeleted(false);
        List<ShopActivityConfig> shopActivityConfigList = shopActivityConfigService.queryList(model1);

        shopActivitiesVO.setShopActivityConfigList(shopActivityConfigList);

        return RestResponse.getSuccesseResponse(shopActivitiesVO);
    }

    @ResponseBody
    @RequestMapping(value = "/queryList", method = {RequestMethod.GET})
    public RestResponse<List<ShopActivities>> queryList() {

        Example example = new Example(ShopActivities.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);

        List<ShopActivities> shopActivitiesList = shopActivitiesService.queryList(example);

        if (shopActivitiesList == null) {
            logger.error("读取列表失败");
            return RestResponse.getSystemInnerErrorResponse();
        }
        return RestResponse.getSuccesseResponse(shopActivitiesList);
    }

    @ResponseBody
    @RequestMapping(value = "/queryPageList", method = {RequestMethod.POST})
    public RestResponse<PageInfo<ShopActivities>> queryPageList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "4") int pageSize, @RequestBody() Map<String, Object> conditions) {

        Example example = new Example(ShopActivities.class);
        example.setOrderByClause("gmt_create desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);

        if (null != conditions) {
            if (null != conditions.get("activityName")) {
                criteria.andLike("activityName", "%" + conditions.get("activityName").toString() + "%");
            }
            if (null != conditions.get("activityStatus")) {
                criteria.andEqualTo("activityStatus", conditions.get("activityStatus").toString());

            }
        }

        PageInfo<ShopActivities> shopActivitiesPageInfo = shopActivitiesService.query(pageNum, pageSize, example);

        if (shopActivitiesPageInfo == null) {
            logger.error("读取列表失败");
            return RestResponse.getSystemInnerErrorResponse();
        }
        return RestResponse.getSuccesseResponse(shopActivitiesPageInfo);
    }


    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE})
    public RestResponse<Map<String, Object>> delete(long shopActivitiesId) {

        ShopActivities shopActivities = shopActivitiesService.getById(shopActivitiesId);

        if (null == shopActivities) {
            return RestResponse.getFailedResponse(500, "无法查找数据,请检查shopActivitiesId 是否正确");

        }

        shopActivities.setIsDeleted(true);

        if (!shopActivitiesService.update(shopActivities)) {
            return RestResponse.getFailedResponse(500, "删除数据失败,shopActivitiesId为:" + shopActivitiesId);
        }

        return RestResponse.getSuccesseResponse();
    }
}