package com.fh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fh.utils.PageBean;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("shop_product")
public class ProductInfo extends PageBean {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField(value = "productName")
    private String productName;
    @TableField(value = "proImage")
    private String proImage;
    @TableField(value = "productPrice")
    private Double productPrice;
    @TableField(value = "isput")
    private Integer isput;
    @TableField(value = "ishot")
    private Integer ishot;
    @TableField(value = "createTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    @TableField(value = "brandId")
    private Integer brandId;
    @TableField(value = "areaIds")
    private String areaIds;
    @TableField(exist = false)
    private String areaName;
    @TableField(value = "typeIds")
    private String typeIds;
    @TableField(value = "storyCount")
    private Integer storyCount;


    public Integer getStoryCount() {
        return storyCount;
    }

    public void setStoryCount(Integer storyCount) {
        this.storyCount = storyCount;
    }

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProImage() {
        return proImage;
    }

    public void setProImage(String proImage) {
        this.proImage = proImage;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getIsput() {
        return isput;
    }

    public void setIsput(Integer isput) {
        this.isput = isput;
    }

    public Integer getIshot() {
        return ishot;
    }

    public void setIshot(Integer ishot) {
        this.ishot = ishot;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String areaIds) {
        this.areaIds = areaIds;
    }

    public String getTypeIds() {
        return typeIds;
    }

    public void setTypeIds(String typeIds) {
        this.typeIds = typeIds;
    }


}
