package com.cotton.abmallback.web.controller.admin;

import com.cotton.abmallback.model.Goods;
import com.cotton.abmallback.model.GoodsGroup;
import com.cotton.abmallback.model.vo.admin.GoodsGroupVO;
import com.cotton.abmallback.service.GoodsGroupService;
import com.cotton.abmallback.service.GoodsService;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GoodsGroupManager
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/17
 */
@Controller
@RequestMapping("/admin/goodsGroup")
public class GoodsGroupManagerController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(GoodsGroupManagerController.class);

    private GoodsGroupService goodsGroupService;

    private GoodsService goodsService;

    @Autowired
    public GoodsGroupManagerController(GoodsGroupService goodsGroupService, GoodsService goodsService) {
        this.goodsGroupService = goodsGroupService;
        this.goodsService = goodsService;
    }

    @ResponseBody
    @RequestMapping(value = "/example")
    public RestResponse<Map<String, Object>> example() {

        Map<String, Object> map = new HashMap<>(2);

        return RestResponse.getSuccesseResponse(map);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public RestResponse<Void> add(@RequestBody GoodsGroup goodsGroup) {

        if (goodsGroupService.insert(goodsGroup)) {
            return RestResponse.getSuccesseResponse();
        } else {
            return RestResponse.getFailedResponse(500, "增加失败");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public RestResponse<Void> update(@RequestBody GoodsGroup goodsGroup) {

        if (!goodsGroupService.update(goodsGroup)) {
            return RestResponse.getFailedResponse(500, "更新数据失败,GoodsGroup为:" + goodsGroup.toString());
        }

        return RestResponse.getSuccesseResponse();
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    public RestResponse<GoodsGroup> get(long goodsGroupId) {

        GoodsGroup goodsGroup = goodsGroupService.getById(goodsGroupId);

        if (null == goodsGroup) {
            return RestResponse.getFailedResponse(500, "无法查找数据,请检查goodsGroupId是否正确");

        }
        return RestResponse.getSuccesseResponse(goodsGroup);
    }

    @ResponseBody
    @RequestMapping(value = "/queryList", method = {RequestMethod.GET})
    public RestResponse<List<GoodsGroup>> queryList() {

        Example example = new Example(GoodsGroup.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);

        List<GoodsGroup> goodsGroupList = goodsGroupService.queryList(example);

        if (goodsGroupList == null) {
            logger.error("读取列表失败");
            return RestResponse.getSystemInnerErrorResponse();
        }
        return RestResponse.getSuccesseResponse(goodsGroupList);
    }

    @ResponseBody
    @RequestMapping(value = "/queryPageList", method = {RequestMethod.POST})
    public RestResponse<PageInfo<GoodsGroupVO>> queryPageList(@RequestParam(defaultValue = "1") int pageNum,
                                                              @RequestParam(defaultValue = "4") int pageSize,
                                                              @RequestBody()Map<String,Object> conditions) {

        Example example = new Example(GoodsGroup.class);
        example.setOrderByClause("gmt_create desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);


        if(null != conditions){

            if(null != conditions.get("goodsGroupName")){
                criteria.andLike("goodsGroupName","%" + conditions.get("goodsGroupName").toString()+ "%");
            }
        }

        PageInfo<GoodsGroup> goodsGroupPageInfo = goodsGroupService.query(pageNum, pageSize, example);

        if (goodsGroupPageInfo == null) {
            logger.error("读取列表失败");
            return RestResponse.getSystemInnerErrorResponse();
        }

        PageInfo<GoodsGroupVO> goodsGroupVOPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(goodsGroupPageInfo,goodsGroupVOPageInfo);

        if(goodsGroupPageInfo.getList() != null  && goodsGroupPageInfo.getList().size() > 0) {

            List<GoodsGroupVO> goodsGroupVOList = new ArrayList<>();

            for (GoodsGroup goodsGroup : goodsGroupPageInfo.getList()) {

                GoodsGroupVO goodsGroupVO = new GoodsGroupVO();
                BeanUtils.copyProperties(goodsGroup,goodsGroupVO);

                Goods model = new Goods();
                model.setGroupId(goodsGroup.getId());
                model.setIsDeleted(false);
                long count = goodsService.count(model);

                goodsGroupVO.setGoodsCount(count);

                goodsGroupVOList.add(goodsGroupVO);

            }
        }


        return RestResponse.getSuccesseResponse(goodsGroupVOPageInfo);
    }


    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE})
    public RestResponse<Map<String, Object>> delete(long goodsGroupId) {

        if(goodsGroupId == 0L){
            return RestResponse.getFailedResponse(500, "不能删除默认分组");

        }

        GoodsGroup goodsGroup = goodsGroupService.getById(goodsGroupId);

        if (null == goodsGroup) {
            return RestResponse.getFailedResponse(500, "无法查找数据,请检查goodsGroupId 是否正确");

        }

        goodsGroup.setIsDeleted(true);

        if (!goodsGroupService.update(goodsGroup)) {

            Goods model = new Goods();
            model.setGroupId(goodsGroup.getId());
            model.setIsDeleted(false);

            List<Goods> goodsList = goodsService.queryList(model);

            if(goodsList != null && goodsList.size() > 0){
                for(Goods goods : goodsList){
                    goods.setGroupId(0L);
                    goodsService.update(goods);
                }
            }
            return RestResponse.getFailedResponse(500, "删除数据失败,goodsGroupId为:" + goodsGroupId);
        }

        return RestResponse.getSuccesseResponse();
    }
}