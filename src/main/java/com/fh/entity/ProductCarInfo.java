package com.fh.entity;

import java.math.BigDecimal;

public class ProductCarInfo {
    private Integer id;
    private String productName;
    private BigDecimal productPrice;
    private boolean isCheck;
    private String productImg;
    private Integer count;
    private BigDecimal countMoney;

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

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getCountMoney() {
        return countMoney;
    }

    public void setCountMoney(BigDecimal countMoney) {
        this.countMoney = countMoney;
    }
}
