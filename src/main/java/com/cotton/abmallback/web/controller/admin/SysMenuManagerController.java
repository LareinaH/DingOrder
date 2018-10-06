package com.cotton.abmallback.web.controller.admin;

import com.cotton.abmallback.model.SysMenu;
import com.cotton.abmallback.service.SysMenuService;
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
 * SysMenuManager
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/6
 */
@Controller
@RequestMapping("/admin/sysMenu")
public class SysMenuManagerController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(SysMenuManagerController.class);

    private SysMenuService sysMenuService;

    @Autowired
    public SysMenuManagerController(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @ResponseBody
    @RequestMapping(value = "/example")
    public RestResponse<Map<String, Object>> example() {

        Map<String, Object> map = new HashMap<>(2);

        return RestResponse.getSuccesseResponse(map);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public RestResponse<Void> add(@RequestBody SysMenu sysMenu) {

        if (sysMenuService.insert(sysMenu)) {
            return RestResponse.getSuccesseResponse();
        } else {
            return RestResponse.getFailedResponse(500, "增加失败");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public RestResponse<Void> update(@RequestBody SysMenu sysMenu) {

        if (!sysMenuService.update(sysMenu)) {
            return RestResponse.getFailedResponse(500, "更新数据失败,SysMenu为:" + sysMenu.toString());
        }

        return RestResponse.getSuccesseResponse();
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    public RestResponse<SysMenu> get(long sysMenuId) {

        SysMenu sysMenu = sysMenuService.getById(sysMenuId);

        if (null == sysMenu) {
            return RestResponse.getFailedResponse(500, "无法查找数据,请检查sysMenuId是否正确");

        }
        return RestResponse.getSuccesseResponse(sysMenu);
    }

    @ResponseBody
    @RequestMapping(value = "/queryList", method = {RequestMethod.GET})
    public RestResponse<List<SysMenu>> queryList() {

        Example example = new Example(SysMenu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);

        List<SysMenu> sysMenuList = sysMenuService.queryList(example);

        if (sysMenuList == null) {
            logger.error("读取列表失败");
            return RestResponse.getSystemInnerErrorResponse();
        }
        return RestResponse.getSuccesseResponse(sysMenuList);
    }

    @ResponseBody
    @RequestMapping(value = "/queryPageList", method = {RequestMethod.GET})
    public RestResponse<PageInfo<SysMenu>> queryPageList(@RequestParam(defaultValue = "1") int pageNum,
                                                         @RequestParam(defaultValue = "4") int pageSize) {

        Example example = new Example(SysMenu.class);
        example.setOrderByClause("gmt_create desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);

        PageInfo<SysMenu> sysMenuPageInfo = sysMenuService.query(pageNum, pageSize, example);

        if (sysMenuPageInfo == null) {
            logger.error("读取列表失败");
            return RestResponse.getSystemInnerErrorResponse();
        }
        return RestResponse.getSuccesseResponse(sysMenuPageInfo);
    }


    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE})
    public RestResponse<Map<String, Object>> delete(long sysMenuId) {

        SysMenu sysMenu = sysMenuService.getById(sysMenuId);

        if (null == sysMenu) {
            return RestResponse.getFailedResponse(500, "无法查找数据,请检查sysMenuId 是否正确");

        }

        sysMenu.setIsDeleted(true);

        if (!sysMenuService.update(sysMenu)) {
            return RestResponse.getFailedResponse(500, "删除数据失败,sysMenuId为:" + sysMenuId);
        }

        return RestResponse.getSuccesseResponse();
    }
}