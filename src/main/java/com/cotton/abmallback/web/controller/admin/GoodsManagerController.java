package com.cotton.abmallback.web.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cotton.abmallback.mapper.GoodsMapper;
import com.cotton.abmallback.mapper.GoodsSpecificationMapper;
import com.cotton.abmallback.model.Goods;
import com.cotton.abmallback.model.GoodsSpecification;
import com.cotton.abmallback.model.vo.GoodsVO;
import com.cotton.abmallback.service.GoodsService;
import com.cotton.abmallback.service.GoodsSpecificationService;
import com.cotton.base.common.RestResponse;
import com.cotton.base.controller.BaseController;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.*;

/**
 * GoodsManager
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/6
 */
@Controller
@RequestMapping("/admin/goods")
public class GoodsManagerController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(GoodsManagerController.class);

    private GoodsService goodsService;

    private GoodsSpecificationService goodsSpecificationService;

    @Autowired
    GoodsSpecificationMapper goodsSpecificationMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    public GoodsManagerController(GoodsService goodsService, GoodsSpecificationService goodsSpecificationService) {
        this.goodsService = goodsService;
        this.goodsSpecificationService = goodsSpecificationService;
    }

    @ResponseBody
    @RequestMapping(value = "/example")
    public RestResponse<Map<String, Object>> example() {

        Map<String, Object> map = new HashMap<>(2);

        return RestResponse.getSuccesseResponse(map);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public RestResponse<Void> add(@RequestBody GoodsVO goodsVO) {

        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsVO,goods);

        //补全信息
        if(null != goodsVO.getGoodsSpecificationList() && goodsVO.getGoodsSpecificationList().size() > 0) {

            //判断是否上架 如果存在规格上架，默认商品上架，如果所有规格都不上架 默认商品不上架
            boolean isOnSale = false;
            //库存
            Integer stock = 0;
            for (GoodsSpecification goodsSpecification : goodsVO.getGoodsSpecificationList()) {
                stock += goodsSpecification.getStock();
                isOnSale = isOnSale || goodsSpecification.getIsOnSell();

            }
            goods.setIsOnSell(isOnSale);
            goods.setStock(stock);

            goods.setUnit(goodsVO.getGoodsSpecificationList().get(0).getGoodsSpecificationName());
            goods.setPrice(goodsVO.getGoodsSpecificationList().get(0).getPrice().toString());
            goods.setPreferentialPrice(goodsVO.getGoodsSpecificationList().get(0).getPreferentialPrice());
        }

        //缩略图
        JSONArray images =  JSON.parseArray(goodsVO.getImages());
        if(images != null && images.size() >0) {
            goods.setThums(images.get(0).toString());
        }


        if (goodsService.insert(goods)) {

            if(null != goodsVO.getGoodsSpecificationList() &&
                    goodsVO.getGoodsSpecificationList().size()>0){

                for (GoodsSpecification goodsSpecification: goodsVO.getGoodsSpecificationList()) {

                    goodsSpecification.setGoodsId(goods.getId());
                    goodsSpecificationService.insert(goodsSpecification);
                }

            }
            return RestResponse.getSuccesseResponse();
        } else {
            return RestResponse.getFailedResponse(500, "增加失败");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public RestResponse<Void> update(@RequestBody GoodsVO goodsVO) {


        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsVO,goods);

        //补全信息
        if(null != goodsVO.getGoodsSpecificationList() && goodsVO.getGoodsSpecificationList().size() > 0) {

            //判断是否上架 如果存在规格上架，默认商品上架，如果所有规格都不上架 默认商品不上架
            boolean isOnSale = false;
            //库存
            Integer stock = 0;
            for (GoodsSpecification goodsSpecification : goodsVO.getGoodsSpecificationList()) {
                stock += goodsSpecification.getStock();
                isOnSale = isOnSale || goodsSpecification.getIsOnSell();

            }
            goods.setIsOnSell(isOnSale);
            goods.setStock(stock);
            goods.setUnit(goodsVO.getGoodsSpecificationList().get(0).getGoodsSpecificationName());
            goods.setPrice(goodsVO.getGoodsSpecificationList().get(0).getPrice().toString());
            goods.setPreferentialPrice(goodsVO.getGoodsSpecificationList().get(0).getPreferentialPrice());
        }

        //缩略图
        JSONArray images =  JSON.parseArray(goodsVO.getImages());
        if(images != null && images.size() >0) {
            goods.setThums(images.get(0).toString());
        }

        if (!goodsService.update(goods)) {
            return RestResponse.getFailedResponse(500, "更新数据失败,Goods为:" + goods.toString());
        }

        //更新规格
        if(null != goodsVO.getGoodsSpecificationList()){
            for(GoodsSpecification goodsSpecification : goodsVO.getGoodsSpecificationList()){

                if(goodsSpecification.getId() != null){
                    goodsSpecificationService.update(goodsSpecification);
                }else {
                    goodsSpecificationService.insert(goodsSpecification);
                }
            }
        }

        return RestResponse.getSuccesseResponse();
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    public RestResponse<GoodsVO> get(long goodsId) {

        Goods goods = goodsService.getById(goodsId);

        if (null == goods) {
            return RestResponse.getFailedResponse(500, "无法查找数据,请检查goodsId是否正确");

        }
        GoodsVO  goodsVO = new GoodsVO();

        BeanUtils.copyProperties(goods,goodsVO);

        GoodsSpecification model = new GoodsSpecification();
        model.setGoodsId(goodsId);
        model.setIsDeleted(false);

        List<GoodsSpecification> goodsSpecificationList = goodsSpecificationService.queryList(model);

        goodsVO.setGoodsSpecificationList(goodsSpecificationList);

        return RestResponse.getSuccesseResponse(goodsVO);
    }

    @ResponseBody
    @RequestMapping(value = "/queryList", method = {RequestMethod.GET})
    public RestResponse<List<Goods>> queryList() {

        Example example = new Example(Goods.class);
        example.setOrderByClause("id desc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);

        List<Goods> goodsList = goodsService.queryList(example);

        if (goodsList == null) {
            logger.error("读取列表失败");
            return RestResponse.getSystemInnerErrorResponse();
        }
        return RestResponse.getSuccesseResponse(goodsList);
    }

    @ResponseBody
    @RequestMapping(value = "/queryPageList", method = {RequestMethod.POST})
    public RestResponse<PageInfo<GoodsVO>> queryPageList(@RequestParam(defaultValue = "1") int pageNum,
                                                         @RequestParam(defaultValue = "4") int pageSize,
                                                         @RequestBody(required = false)Map<String,Object> conditions) {

        Example example = new Example(Goods.class);
        example.setOrderByClause("gmt_create desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);

        if(null != conditions){
            if(null != conditions.get("groupId")){
                criteria.andEqualTo("groupId",conditions.get("groupId"));
            }

            if(null != conditions.get("isOnSell")){
                criteria.andEqualTo("isOnSell",conditions.get("isOnSell"));
            }

            if(null != conditions.get("goodsName")){
                criteria.andLike("goodsName","%" + conditions.get("goodsName").toString() + "%");
            }
        }


        PageInfo<Goods> goodsPageInfo = goodsService.query(pageNum, pageSize, example);

        if (goodsPageInfo == null) {
            logger.error("读取列表失败");
            return RestResponse.getSystemInnerErrorResponse();
        }

        PageInfo<GoodsVO> goodsVOPageInfo = new PageInfo<>();

        BeanUtils.copyProperties(goodsPageInfo,goodsVOPageInfo);

        if(goodsPageInfo.getList() != null && goodsPageInfo.getList().size() >0){

            List<GoodsVO> goodsVOS = new ArrayList<>();
            for(Goods goods : goodsPageInfo.getList()){
                GoodsVO goodsVO = new GoodsVO();
                BeanUtils.copyProperties(goods,goodsVO);

                GoodsSpecification model = new GoodsSpecification();
                model.setIsDeleted(false);
                model.setGoodsId(goods.getId());
                List<GoodsSpecification> goodsSpecificationList = goodsSpecificationService.queryList(model);
                goodsVO.setGoodsSpecificationList(goodsSpecificationList);

                goodsVOS.add(goodsVO);
            }
            goodsVOPageInfo.setList(goodsVOS);
        }


        return RestResponse.getSuccesseResponse(goodsVOPageInfo);
    }


    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE})
    public RestResponse<Map<String, Object>> delete(long goodsId) {

        Goods goods = goodsService.getById(goodsId);

        if (null == goods) {
            return RestResponse.getFailedResponse(500, "无法查找数据,请检查goodsId 是否正确");

        }

        goods.setIsDeleted(true);

        if (!goodsService.update(goods)) {
            return RestResponse.getFailedResponse(500, "删除数据失败,goodsId为:" + goodsId);
        }

        //删除所有规格
        GoodsSpecification model = new GoodsSpecification();
        model.setGoodsId(goodsId);
        model.setIsDeleted(false);

        List<GoodsSpecification> goodsSpecificationList = goodsSpecificationService.queryList(model);

        if(null!= goodsSpecificationList && goodsSpecificationList.size() > 0){
            for (GoodsSpecification goodsSpecification : goodsSpecificationList){
                goodsSpecification.setIsDeleted(true);

                goodsSpecificationService.update(goodsSpecification);
            }
        }
        return RestResponse.getSuccesseResponse();
    }

    @ResponseBody
    @RequestMapping(value = "/getSpecUnitList", method = {RequestMethod.GET})
    public RestResponse<List> getSpecUnitList() {
        return RestResponse.getSuccesseResponse(goodsSpecificationService.getSpecUnitList());
    }

    @ResponseBody
    @RequestMapping(value = "/setGoodsOnSaleStatus", method = {RequestMethod.POST})
    public RestResponse<Map<String, Object>> delete(
            @RequestParam(defaultValue = "goodsId") long goodsId,
            @RequestParam(defaultValue = "isOnSale") int isOnSale
    ) {
        goodsSpecificationMapper.setGoodsOnSaleStatus(goodsId, isOnSale);
        goodsMapper.setGoodsOnSaleStatus(goodsId, isOnSale);
        return RestResponse.getSuccesseResponse();
    }
}