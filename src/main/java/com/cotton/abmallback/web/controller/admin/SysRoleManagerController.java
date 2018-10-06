package com.cotton.abmallback.web.controller.admin;

import com.cotton.abmallback.model.SysRole;
import com.cotton.abmallback.service.SysRoleService;
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
 * SysRoleManager
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/6
 */
@Controller
@RequestMapping("/admin/sysRole")
public class SysRoleManagerController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(SysRoleManagerController.class);

    private SysRoleService sysRoleService;

    @Autowired
    public SysRoleManagerController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @ResponseBody
    @RequestMapping(value = "/example")
    public RestResponse<Map<String, Object>> example() {

        Map<String, Object> map = new HashMap<>(2);

        return RestResponse.getSuccesseResponse(map);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public RestResponse<Void> add(@RequestBody SysRole sysRole) {

        if (sysRoleService.insert(sysRole)) {
            return RestResponse.getSuccesseResponse();
        } else {
            return RestResponse.getFailedResponse(500, "增加失败");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public RestResponse<Void> update(@RequestBody SysRole sysRole) {

        if (!sysRoleService.update(sysRole)) {
            return RestResponse.getFailedResponse(500, "更新数据失败,SysRole为:" + sysRole.toString());
        }

        return RestResponse.getSuccesseResponse();
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    public RestResponse<SysRole> get(long sysRoleId) {

        SysRole sysRole = sysRoleService.getById(sysRoleId);

        if (null == sysRole) {
            return RestResponse.getFailedResponse(500, "无法查找数据,请检查sysRoleId是否正确");

        }
        return RestResponse.getSuccesseResponse(sysRole);
    }

    @ResponseBody
    @RequestMapping(value = "/queryList", method = {RequestMethod.GET})
    public RestResponse<List<SysRole>> queryList() {

        Example example = new Example(SysRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);

        List<SysRole> sysRoleList = sysRoleService.queryList(example);

        if (sysRoleList == null) {
            logger.error("读取列表失败");
            return RestResponse.getSystemInnerErrorResponse();
        }
        return RestResponse.getSuccesseResponse(sysRoleList);
    }

    @ResponseBody
    @RequestMapping(value = "/queryPageList", method = {RequestMethod.GET})
    public RestResponse<PageInfo<SysRole>> queryPageList(@RequestParam(defaultValue = "1") int pageNum,
                                                         @RequestParam(defaultValue = "4") int pageSize) {

        Example example = new Example(SysRole.class);
        example.setOrderByClause("gmt_create desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);

        PageInfo<SysRole> sysRolePageInfo = sysRoleService.query(pageNum, pageSize, example);

        if (sysRolePageInfo == null) {
            logger.error("读取列表失败");
            return RestResponse.getSystemInnerErrorResponse();
        }
        return RestResponse.getSuccesseResponse(sysRolePageInfo);
    }


    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE})
    public RestResponse<Map<String, Object>> delete(long sysRoleId) {

        SysRole sysRole = sysRoleService.getById(sysRoleId);

        if (null == sysRole) {
            return RestResponse.getFailedResponse(500, "无法查找数据,请检查sysRoleId 是否正确");

        }

        sysRole.setIsDeleted(true);

        if (!sysRoleService.update(sysRole)) {
            return RestResponse.getFailedResponse(500, "删除数据失败,sysRoleId为:" + sysRoleId);
        }

        return RestResponse.getSuccesseResponse();
    }
}