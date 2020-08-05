package com.fh.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

//收货地址表
public class AddressInfo {
    private Integer id;
    //用户id 唯一标识
    private String VipId;
    //收货人名称
    private String name;
    //收货地址详情
    private String detailAdd;
    //收货地区
    private String areaIds;
    //收货地区名
    private String areaName;
    //手机号
    private String iphone;
    //默认选中
    private String isCheck;
    //
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;


    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVipId() {
        return VipId;
    }

    public void setVipId(String vipId) {
        VipId = vipId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetailAdd() {
        return detailAdd;
    }

    public void setDetailAdd(String detailAdd) {
        this.detailAdd = detailAdd;
    }

    public String getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String areaIds) {
        this.areaIds = areaIds;
    }

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
