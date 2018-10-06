package com.cotton.abmallback.web.controller.front;

import com.cotton.abmallback.enumeration.MemberLevelEnum;
import com.cotton.abmallback.manager.SmsManager;
import com.cotton.abmallback.model.Member;
import com.cotton.abmallback.model.MemberAddress;
import com.cotton.abmallback.model.vo.front.MyTeamVO;
import com.cotton.abmallback.service.MemberAddressService;
import com.cotton.abmallback.service.MemberService;
import com.cotton.abmallback.web.controller.ABMallFrontBaseController;
import com.cotton.base.common.RestResponse;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
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
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/10
 */

@Controller
@RequestMapping("/member")
public class MemberController extends ABMallFrontBaseController {

    private Logger logger = LoggerFactory.getLogger(MemberController.class);

    private MemberService memberService;

    private MemberAddressService memberAddressService;

    private SmsManager smsManager;

    @Autowired
    public MemberController(MemberAddressService memberAddressService, MemberService memberService, SmsManager smsManager) {
        this.memberAddressService = memberAddressService;
        this.memberService = memberService;
        this.smsManager = smsManager;
    }

    /**
     * 获取用户的账户信息
     * @return RestResponse
     */
    @ResponseBody
    @RequestMapping(value = "/myInfo",method = {RequestMethod.GET})
    public RestResponse<Map<String, Object>> myInfo() {

        Map<String, Object> map = new HashMap<>(2);
        Member member = memberService.getById(getCurrentMemberId());
        map.put("totalSales",member.getMoneyTotalEarn());
        map.put("availablePickUpCashAmount",member.getMoneyTotalEarn().subtract(member.getMoneyTotalTake()).subtract(member.getMoneyLock()));

        return RestResponse.getSuccesseResponse(map);
    }

    /**
     * 绑定手机号
     * @return 统一返回对象
     */
    @ResponseBody
    @RequestMapping(value = "/bindPhoneNum",method = {RequestMethod.POST})
    public RestResponse<Void> bindPhoneNum(String phoneNum,String code) {

        //校验验证码
        if(!smsManager.checkCaptcha(phoneNum,code)){
            return RestResponse.getFailedResponse(500,"验证码错误");
        }

        //查看手机号是否存在
        Member model = new Member();
        model.setPhoneNum(phoneNum);
        model.setIsDeleted(false);

        if(memberService.count(model) > 0){
            return RestResponse.getFailedResponse(500,"该手机号已经存在");
        }

        //更新手机号
        Member member = getCurrentMember();
        member.setPhoneNum(phoneNum);

        if(memberService.update(member)){
            return RestResponse.getSuccesseResponse();
        }else {
            return RestResponse.getFailedResponse(1,"绑定手机号失败！");
        }
    }

    /**
     * 绑定手机号
     * @return 统一返回对象
     */
    @ResponseBody
    @RequestMapping(value = "/changePhoneNum",method = {RequestMethod.POST})
    public RestResponse<Void> changePhoneNum(String oldPhoneNum,String phoneNum,String code) {

        if(oldPhoneNum.equals(phoneNum)){
            return RestResponse.getFailedResponse(1,"新手机号与原有手机号相同！");
        }

        Member member = memberService.getById(getCurrentMemberId());

        if(!oldPhoneNum.equals(member.getPhoneNum())){
            return RestResponse.getFailedResponse(1,"原手机号错误！");

        }

        //校验验证码
        if(!smsManager.checkCaptcha(phoneNum,code)){
            return RestResponse.getFailedResponse(500,"验证码错误");
        }

        //查看手机号是否存在
        Member model = new Member();
        model.setPhoneNum(phoneNum);
        model.setIsDeleted(false);

        if(memberService.count(model) > 0){
            return RestResponse.getFailedResponse(500,"该手机号已经存在");
        }

        member.setPhoneNum(phoneNum);

        if(memberService.update(member)){
            return RestResponse.getSuccesseResponse();
        }else {
            return RestResponse.getFailedResponse(1,"绑定手机号失败！");
        }
    }

    /**
     * 收货地址列表
     * @return 统一返回对象
     */
    @ResponseBody
    @RequestMapping(value = "/addressList",method = {RequestMethod.GET})
    public RestResponse<List<MemberAddress>> addressList(@RequestParam(defaultValue = "1") int pageNum,
                                                         @RequestParam(defaultValue = "4") int pageSize,
                                                         @RequestParam(defaultValue = "ADDRESS")String addressType) {

        Example example = new Example(MemberAddress.class);
        example.setOrderByClause("is_default desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("memberId", getCurrentMemberId());
        criteria.andEqualTo("isDeleted","0");
        criteria.andEqualTo("addressType",addressType);

        PageInfo<MemberAddress> memberAddressPageInfo = memberAddressService.query(pageNum,pageSize,example);

        if(null != memberAddressPageInfo) {
            return RestResponse.getSuccesseResponse(memberAddressPageInfo.getList());
        }else {
            return RestResponse.getFailedResponse(500,"读取列表失败");
        }

    }

    /**
     * 默认地址
     * @return 统一返回对象
     */
    @ResponseBody
    @RequestMapping(value = "/defaultAddress",method = {RequestMethod.GET})
    public RestResponse<MemberAddress> defaultAddress(@RequestParam(defaultValue = "ADDRESS")String addressType) {

        Example example = new Example(MemberAddress.class);
        example.setOrderByClause("is_default desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("memberId", getCurrentMemberId());
        criteria.andEqualTo("isDeleted","0");
        criteria.andEqualTo("addressType",addressType);

        PageInfo<MemberAddress> memberAddressPageInfo = memberAddressService.query(1,1,example);

        if(null != memberAddressPageInfo) {
            if(memberAddressPageInfo.getList().size()>0) {
                return RestResponse.getSuccesseResponse(memberAddressPageInfo.getList().get(0));
            }else {
                return RestResponse.getSuccesseResponse();
            }
        }else {
            return RestResponse.getFailedResponse(500,"读取列表失败");
        }

    }

    /**
     * 增加收货地址
     * @return 统一返回对象
     */
    @ResponseBody
    @RequestMapping(value = "/addAddress",method = {RequestMethod.POST})
    public RestResponse<Void> addAddress(@RequestBody MemberAddress memberAddress) {

        //MemberAddress memberAddress = new MemberAddress();
        memberAddress.setMemberId(getCurrentMemberId());
        if(memberAddressService.insert(memberAddress)){
            return RestResponse.getSuccesseResponse();
        }else {
            return RestResponse.getFailedResponse(500,"增加收货地址失败");
        }
    }


    /**
     * 增加收货地址
     * @return 统一返回对象
     */
    @ResponseBody
    @RequestMapping(value = "/editAddress",method = {RequestMethod.POST})
    public RestResponse<Void> editAddress(@RequestBody MemberAddress memberAddress) {

        //MemberAddress memberAddress = new MemberAddress();
        if(memberAddressService.update(memberAddress)){
            return RestResponse.getSuccesseResponse();
        }else {
            return RestResponse.getFailedResponse(500,"增加收货地址失败");
        }
    }

    /**
     * 删除收货地址
     * @return 统一返回对象
     */
    @ResponseBody
    @RequestMapping(value = "/deleteAddress",method = {RequestMethod.DELETE})
    public RestResponse<Void> deleteAddress(long addressId) {

        MemberAddress model = new MemberAddress();
        model.setId(addressId);
        model.setIsDeleted(true);

        if(memberAddressService.update(model)){
            return RestResponse.getSuccesseResponse();
        }else {
            return RestResponse.getFailedResponse(500,"删除收货地址失败");
        }
    }

    /**
     * 设为默认收货地址
     * @return 统一返回对象
     */
    @ResponseBody
    @RequestMapping(value = "/setDefaultAddress",method = {RequestMethod.POST})
    public RestResponse<Void> setDefaultAddress(long addressId, String addressType) {

        //取消默认地址
        MemberAddress model = new MemberAddress();
        model.setMemberId(getCurrentMemberId());
        model.setAddressType(addressType);
        model.setIsDefault(true);
        model.setIsDeleted(false);

        List<MemberAddress> memberAddressList = memberAddressService.queryList(model);

        for(MemberAddress memberAddress : memberAddressList){
            memberAddress.setIsDefault(false);
            memberAddressService.update(memberAddress);
        }

        //设为默认地址
        model.setId(addressId);

        if(memberAddressService.update(model)){
            return RestResponse.getSuccesseResponse();
        }else {
            return RestResponse.getFailedResponse(500,"设置默认收货地址失败");
        }
    }


    /**
     * 我的团队
     * @return 统一返回对象
     */
    @ResponseBody
    @RequestMapping(value = "/myTeam",method = {RequestMethod.GET})
    public RestResponse<List<MyTeamVO>> myTeam(@RequestParam(defaultValue = "1") int pageNum,
                                                         @RequestParam(defaultValue = "4") int pageSize,
                                                         @RequestParam(defaultValue = "")String level) {

        Example example = new Example(Member.class);
        example.setOrderByClause("refer_total_agent_count desc,gmt_create desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("referrerId", getCurrentMemberId());
        criteria.andEqualTo("isDeleted","0");

        if(StringUtils.isNotBlank(level)) {
            criteria.andEqualTo("level", level);
        }

        PageInfo<Member> memberPageInfo = memberService.query(pageNum,pageSize,example);

        if(null != memberPageInfo) {

            List<MyTeamVO> myTeamVOList = new ArrayList<>();

            for(Member member : memberPageInfo.getList()){

                MyTeamVO myTeamVO = new MyTeamVO();
                BeanUtils.copyProperties(member,myTeamVO);
                myTeamVOList.add(myTeamVO);
            }

            return RestResponse.getSuccesseResponse(myTeamVOList);
        }else {
            return RestResponse.getFailedResponse(500,"读取列表失败");
        }

    }

    /**
     * 我的团队数目统计
     * @return 统一返回对象
     */
    @ResponseBody
    @RequestMapping(value = "/myTeamCount",method = {RequestMethod.GET})
    public RestResponse<Map<String, Object>> myTeamCount() {

        Map<String, Object> map = new HashMap<>(2);

        Example example = new Example(Member.class);
        example.setOrderByClause("gmt_create desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("referrerId", getCurrentMemberId());
        criteria.andEqualTo("isDeleted","0");

        long total = memberService.count(example);
        map.put("total",total);

        criteria.andEqualTo("level",MemberLevelEnum.WHITE.name());
        long whiteTotal = memberService.count(example);
        map.put("white",whiteTotal);

        criteria.andEqualTo("level",MemberLevelEnum.V1.name());
        long v1Total = memberService.count(example);
        map.put("v1",v1Total);

        criteria.andEqualTo("level",MemberLevelEnum.V2.name());
        long v2Total = memberService.count(example);
        map.put("v2",v2Total);

        criteria.andEqualTo("level",MemberLevelEnum.V3.name());
        long v3Total = memberService.count(example);
        map.put("v3",v3Total);

        return RestResponse.getSuccesseResponse(map);
    }

    /**
     * 排行榜
     * @return 统一返回对象
     */
    @ResponseBody
    @RequestMapping(value = "/rank",method = {RequestMethod.GET})
    public RestResponse<List<MyTeamVO>> rank(@RequestParam(defaultValue = "1") int pageNum,
                                               @RequestParam(defaultValue = "4") int pageSize) {

        Example example = new Example(Member.class);
        example.setOrderByClause("refer_total_count desc");

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("referrerId", getCurrentMemberId());
        criteria.andEqualTo("isDeleted","0");

        PageInfo<Member> memberPageInfo = memberService.query(pageNum,pageSize,example);

        if(null != memberPageInfo) {

            List<MyTeamVO> rankList = new ArrayList<>();

            for(Member member : memberPageInfo.getList()){

                MyTeamVO myTeamVO = new MyTeamVO();
                BeanUtils.copyProperties(member,myTeamVO);
                rankList.add(myTeamVO);
            }

            return RestResponse.getSuccesseResponse(rankList);
        }else {
            return RestResponse.getFailedResponse(500,"获取排行榜失败");
        }

    }
}