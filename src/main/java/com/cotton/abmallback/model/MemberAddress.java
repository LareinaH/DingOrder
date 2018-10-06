package com.cotton.abmallback.model;

import com.cotton.base.model.BaseModel;
import javax.persistence.*;

@Table(name = "member_address")
public class MemberAddress extends BaseModel {
    /**
     * 会员id
     */
    @Column(name = "member_id")
    private Long memberId;

    /**
     * 地址类型：收货地址 赠品地址
     */
    @Column(name = "address_type")
    private String addressType;

    /**
     * 收货人姓名
     */
    @Column(name = "receiver_name")
    private String receiverName;

    /**
     * 收货人联系电话
     */
    @Column(name = "receiver_phone")
    private String receiverPhone;

    /**
     * 收货地址-省名称
     */
    @Column(name = "receiver_province_name")
    private String receiverProvinceName;

    /**
     * 收货地址-省编码
     */
    @Column(name = "receiver_province_code")
    private Integer receiverProvinceCode;

    /**
     * 收货地址-城市名称
     */
    @Column(name = "receiver_city_name")
    private String receiverCityName;

    /**
     * 收货地址-城市编码
     */
    @Column(name = "receiver_city_code")
    private Integer receiverCityCode;

    /**
     * 收货地址：县名称
     */
    @Column(name = "receiver_county_name")
    private String receiverCountyName;

    /**
     * 收货地址-县编码
     */
    @Column(name = "receiver_county_code")
    private Integer receiverCountyCode;

    /**
     * 详细地址
     */
    @Column(name = "receiver_address")
    private String receiverAddress;

    /**
     * 是否默认地址	0:否 1:是
     */
    @Column(name = "is_default")
    private Boolean isDefault;

    /**
     * 获取会员id
     *
     * @return member_id - 会员id
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * 设置会员id
     *
     * @param memberId 会员id
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取地址类型：收货地址 赠品地址
     *
     * @return address_type - 地址类型：收货地址 赠品地址
     */
    public String getAddressType() {
        return addressType;
    }

    /**
     * 设置地址类型：收货地址 赠品地址
     *
     * @param addressType 地址类型：收货地址 赠品地址
     */
    public void setAddressType(String addressType) {
        this.addressType = addressType == null ? null : addressType.trim();
    }

    /**
     * 获取收货人姓名
     *
     * @return receiver_name - 收货人姓名
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * 设置收货人姓名
     *
     * @param receiverName 收货人姓名
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    /**
     * 获取收货人联系电话
     *
     * @return receiver_phone - 收货人联系电话
     */
    public String getReceiverPhone() {
        return receiverPhone;
    }

    /**
     * 设置收货人联系电话
     *
     * @param receiverPhone 收货人联系电话
     */
    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone == null ? null : receiverPhone.trim();
    }

    /**
     * 获取收货地址-省名称
     *
     * @return receiver_province_name - 收货地址-省名称
     */
    public String getReceiverProvinceName() {
        return receiverProvinceName;
    }

    /**
     * 设置收货地址-省名称
     *
     * @param receiverProvinceName 收货地址-省名称
     */
    public void setReceiverProvinceName(String receiverProvinceName) {
        this.receiverProvinceName = receiverProvinceName == null ? null : receiverProvinceName.trim();
    }

    /**
     * 获取收货地址-省编码
     *
     * @return receiver_province_code - 收货地址-省编码
     */
    public Integer getReceiverProvinceCode() {
        return receiverProvinceCode;
    }

    /**
     * 设置收货地址-省编码
     *
     * @param receiverProvinceCode 收货地址-省编码
     */
    public void setReceiverProvinceCode(Integer receiverProvinceCode) {
        this.receiverProvinceCode = receiverProvinceCode;
    }

    /**
     * 获取收货地址-城市名称
     *
     * @return receiver_city_name - 收货地址-城市名称
     */
    public String getReceiverCityName() {
        return receiverCityName;
    }

    /**
     * 设置收货地址-城市名称
     *
     * @param receiverCityName 收货地址-城市名称
     */
    public void setReceiverCityName(String receiverCityName) {
        this.receiverCityName = receiverCityName == null ? null : receiverCityName.trim();
    }

    /**
     * 获取收货地址-城市编码
     *
     * @return receiver_city_code - 收货地址-城市编码
     */
    public Integer getReceiverCityCode() {
        return receiverCityCode;
    }

    /**
     * 设置收货地址-城市编码
     *
     * @param receiverCityCode 收货地址-城市编码
     */
    public void setReceiverCityCode(Integer receiverCityCode) {
        this.receiverCityCode = receiverCityCode;
    }

    /**
     * 获取收货地址：县名称
     *
     * @return receiver_county_name - 收货地址：县名称
     */
    public String getReceiverCountyName() {
        return receiverCountyName;
    }

    /**
     * 设置收货地址：县名称
     *
     * @param receiverCountyName 收货地址：县名称
     */
    public void setReceiverCountyName(String receiverCountyName) {
        this.receiverCountyName = receiverCountyName == null ? null : receiverCountyName.trim();
    }

    /**
     * 获取收货地址-县编码
     *
     * @return receiver_county_code - 收货地址-县编码
     */
    public Integer getReceiverCountyCode() {
        return receiverCountyCode;
    }

    /**
     * 设置收货地址-县编码
     *
     * @param receiverCountyCode 收货地址-县编码
     */
    public void setReceiverCountyCode(Integer receiverCountyCode) {
        this.receiverCountyCode = receiverCountyCode;
    }

    /**
     * 获取详细地址
     *
     * @return receiver_address - 详细地址
     */
    public String getReceiverAddress() {
        return receiverAddress;
    }

    /**
     * 设置详细地址
     *
     * @param receiverAddress 详细地址
     */
    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress == null ? null : receiverAddress.trim();
    }

    /**
     * 获取是否默认地址	0:否 1:是
     *
     * @return is_default - 是否默认地址	0:否 1:是
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * 设置是否默认地址	0:否 1:是
     *
     * @param isDefault 是否默认地址	0:否 1:是
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
}