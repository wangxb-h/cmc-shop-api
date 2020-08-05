package com.fh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fh.utils.PageBean;

import java.math.BigDecimal;
import java.util.Date;
//订单表
@TableName("shop_order")
public class OrderInfo extends PageBean {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //地址id
    @TableField("addressId")
    private Integer addressId;
    @TableField(exist = false)
    private String addressName;
    //支付方式
    @TableField("payType")
    private Integer payType;
    //购买商品个数
    @TableField("proTypeCount")
    private Integer proTypeCount;
    //总价格
    @TableField("totalMoney")
    private BigDecimal totalMoney;
    //支付状态
    @TableField("payStatus")
    private Integer payStatus;
    //支付时间
    @TableField("createDate")
    private Date createDate;

    @TableField("vipId")
    private String vipId;


    public String getVipId() {
        return vipId;
    }

    public void setVipId(String vipId) {
        this.vipId = vipId;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getProTypeCount() {
        return proTypeCount;
    }

    public void setProTypeCount(Integer proTypeCount) {
        this.proTypeCount = proTypeCount;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
