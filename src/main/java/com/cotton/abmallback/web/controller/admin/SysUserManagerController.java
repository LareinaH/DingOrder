package com.cotton.abmallback.web.controller.admin;

import com.cotton.abmallback.model.SysUser;
import com.cotton.abmallback.service.SysUserService;
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
 * SysUser
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/25
 */
@Controller
@RequestMapping("/admin/sysUser")
public class SysUserManagerController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(SysUserManagerController.class);

    private SysUserService sysUserService;


    @Autowired
    public SysUserManagerController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @ResponseBody
    @RequestMapping(value = "/example")
    public RestResponse<Map<String, Object>> example() {

        Map<String, Object> map = new HashMap<>(2);

        return RestResponse.getSuccesseResponse(map);
    }

    @ResponseBody
    @RequestMapping(value = "/add",method = {RequestMethod.POST})
    public RestResponse<Void> add(@RequestBody SysUser sysUser) {

        if(sysUserService.insert(sysUser)){
            return RestResponse.getSuccesseResponse();
        }else {
            return RestResponse.getFailedResponse(500,"增加失败");
        }

    }

    @ResponseBody
    @RequestMapping(value = "/update" ,method = {RequestMethod.POST})
    public RestResponse<Void> update(@RequestBody SysUser sysUser) {


        if(!sysUserService.update(sysUser)){
            return RestResponse.getFailedResponse(500,"更新数据失败"+sysUser.toString());
        }

        return RestResponse.getSuccesseResponse();
    }

    @ResponseBody
    @RequestMapping(value = "/editPassword" ,method = {RequestMethod.POST})
    public RestResponse<Void> editPassword(@RequestParam long sysUserId,
                                           @RequestParam String oldPwd,
                                           @RequestParam String newPwd) {


        SysUser sysUser = sysUserService.getById(sysUserId);

        if(null == sysUser){
            return RestResponse.getFailedResponse(500,"无法查找数据,请检查sysUserId是否正确");

        }

        if(!sysUser.getPassword().equals(oldPwd)){
            return RestResponse.getFailedResponse(500,"原密码不正确！");
        }

        sysUser.setPassword(newPwd);

        if(!sysUserService.update(sysUser)){
            return RestResponse.getFailedResponse(500,"修改密码失败"+sysUser.toString());
        }

        return RestResponse.getSuccesseResponse();
    }

    @ResponseBody
    @RequestMapping(value = "/get",method = {RequestMethod.GET})
    public RestResponse<SysUser> get(@RequestParam long sysUserId) {

        SysUser sysUser = sysUserService.getById(sysUserId);

        if(null == sysUser){
            return RestResponse.getFailedResponse(500,"无法查找数据,请检查sysUserId是否正确");

        }
        return RestResponse.getSuccesseResponse(sysUser);
    }

    @ResponseBody
    @RequestMapping(value = "/queryList",method = {RequestMethod.GET})
    public RestResponse<List<SysUser>> queryList() {

        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted",false);
        //TODO:查询条件

        List<SysUser> sysUserPageInfo = sysUserService.queryList(example);

        if(sysUserPageInfo == null ){
            logger.error("读取列表失败");
            return RestResponse.getSystemInnerErrorResponse();
        }
        return RestResponse.getSuccesseResponse(sysUserPageInfo);
    }

    @ResponseBody
    @RequestMapping(value = "/queryPageList",method = {RequestMethod.GET})
    public RestResponse<PageInfo<SysUser>> queryPageList(@RequestParam(defaultValue = "1") int pageNum,
                                                         @RequestParam(defaultValue = "4") int pageSize) {

        Example example = new Example(SysUser.class);
        example.setOrderByClause("gmt_create desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted",false);

        //TODO:查询条件

        PageInfo<SysUser> sysUserPageInfo = sysUserService.query(pageNum,pageSize,example);

        if(sysUserPageInfo == null ){
            logger.error("读取列表失败");
            return RestResponse.getSystemInnerErrorResponse();
        }
        return RestResponse.getSuccesseResponse(sysUserPageInfo);
    }

    @ResponseBody
    @RequestMapping(value = "/delete",method = {RequestMethod.DELETE})
    public RestResponse<Map<String, Object>> delete(@RequestParam long sysUserId) {

        SysUser sysUser = sysUserService.getById(sysUserId);

        if(null == sysUser){
            return RestResponse.getFailedResponse(500,"无法查找数据,请检查sysUserId 是否正确");

        }
        sysUser.setIsDeleted(true);

        if(!sysUserService.update(sysUser)){
            return RestResponse.getFailedResponse(500,"更新数据失败,sysUserId为:"+sysUserId);
        }

        return RestResponse.getSuccesseResponse();
    }
}