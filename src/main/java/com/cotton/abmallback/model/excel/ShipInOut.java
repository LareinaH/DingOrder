package com.cotton.abmallback.model.excel;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTimeUtils;

import java.util.Date;

public class ShipInOut {
    // 编号
    String id;
    // 中文船名
    String shipNameCn;
    // 船舶识别号
    String shipIdentity;
    // 总吨
    Double totalTon;
    // 载重吨
    Double weightTon;
    // 主机功率
    Double power;
    // 船舶种类
    String shipType;
    // 船籍港
    String shipBorn;
    // 船舶长度
    Double shipLength;
    // 船舶宽度
    Double shipWidth;
    // 进出港
    String inOutType;
    // 海事机构
    String shipOrg;
    // 申请时间
    Date applyDateTime;
    // 预抵离时间
    Date preInOutDateTime;
    // 港口
    String portName;
    // 泊位
    String parkName;
    // 上下港
    String upDownPort;
    // 定期签证
    String regularCert;
    // 航次数量
    Double shipCount;
    // 实载货量
    Double realWeightTon;
    // 装卸货量
    Double loadingTon;
    // 实载客量
    Double realPassengerCount;
    // 上下客量
    Double upDownPassengerCount;
    // 货物种类
    String goodsType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShipNameCn() {
        return shipNameCn;
    }

    public void setShipNameCn(String shipNameCn) {
        this.shipNameCn = shipNameCn;
    }

    public String getShipIdentity() {
        return shipIdentity;
    }

    public void setShipIdentity(String shipIdentity) {
        this.shipIdentity = shipIdentity;
    }

    public Double getTotalTon() {
        return totalTon;
    }

    public void setTotalTon(Double totalTon) {
        this.totalTon = totalTon;
    }

    public Double getWeightTon() {
        return weightTon;
    }

    public void setWeightTon(Double weightTon) {
        this.weightTon = weightTon;
    }

    public Double getPower() {
        return power;
    }

    public void setPower(Double power) {
        this.power = power;
    }

    public String getShipType() {
        return shipType;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
    }

    public String getShipBorn() {
        return shipBorn;
    }

    public void setShipBorn(String shipBorn) {
        this.shipBorn = shipBorn;
    }

    public Double getShipLength() {
        return shipLength;
    }

    public void setShipLength(Double shipLength) {
        this.shipLength = shipLength;
    }

    public Double getShipWidth() {
        return shipWidth;
    }

    public void setShipWidth(Double shipWidth) {
        this.shipWidth = shipWidth;
    }

    public String getInOutType() {
        return inOutType;
    }

    public void setInOutType(String inOutType) {
        this.inOutType = inOutType;
    }

    public String getShipOrg() {
        return shipOrg;
    }

    public void setShipOrg(String shipOrg) {
        this.shipOrg = shipOrg;
    }

    public Date getApplyDateTime() {
        return applyDateTime;
    }

    public void setApplyDateTime(Date applyDateTime) {
        this.applyDateTime = applyDateTime;
    }

    public Date getPreInOutDateTime() {
        return preInOutDateTime;
    }

    public void setPreInOutDateTime(Date preInOutDateTime) {
        this.preInOutDateTime = preInOutDateTime;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getUpDownPort() {
        return upDownPort;
    }

    public void setUpDownPort(String upDownPort) {
        this.upDownPort = upDownPort;
    }

    public String getRegularCert() {
        return regularCert;
    }

    public void setRegularCert(String regularCert) {
        this.regularCert = regularCert;
    }

    public Double getShipCount() {
        return shipCount;
    }

    public void setShipCount(Double shipCount) {
        this.shipCount = shipCount;
    }

    public Double getRealWeightTon() {
        return realWeightTon;
    }

    public void setRealWeightTon(Double realWeightTon) {
        this.realWeightTon = realWeightTon;
    }

    public Double getLoadingTon() {
        return loadingTon;
    }

    public void setLoadingTon(Double loadingTon) {
        this.loadingTon = loadingTon;
    }

    public Double getRealPassengerCount() {
        return realPassengerCount;
    }

    public void setRealPassengerCount(Double realPassengerCount) {
        this.realPassengerCount = realPassengerCount;
    }

    public Double getUpDownPassengerCount() {
        return upDownPassengerCount;
    }

    public void setUpDownPassengerCount(Double upDownPassengerCount) {
        this.upDownPassengerCount = upDownPassengerCount;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(id).append(',');
        sb.append(shipNameCn).append(',');
        sb.append(shipIdentity).append(',');
        sb.append(totalTon).append(',');
        sb.append(weightTon).append(',');
        sb.append(power).append(',');
        sb.append(shipType).append(',');
        sb.append(shipBorn).append(',');
        sb.append(shipLength).append(',');
        sb.append(shipWidth).append(',');
        sb.append(inOutType).append(',');
        sb.append(shipOrg).append(',');
        sb.append(DateFormatUtils.format(applyDateTime, "yyyy-MM-dd HH:mm:ss")).append(',');
        sb.append(preInOutDateTime).append(',');
        sb.append(portName).append(',');
        sb.append(parkName).append(',');
        sb.append(upDownPort).append(',');
        sb.append(regularCert).append(',');
        sb.append(shipCount).append(',');
        sb.append(realWeightTon).append(',');
        sb.append(loadingTon).append(',');
        sb.append(realPassengerCount).append(',');
        sb.append(upDownPassengerCount).append(',');
        sb.append(goodsType);
        return sb.toString();
    }
}
