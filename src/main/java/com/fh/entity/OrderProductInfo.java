package com.fh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

//订单详情表
@TableName("shop_order_product")
public class OrderProductInfo {
    @TableId(value = "id",type = IdType.AUTO)
    private  Integer id;
    //订单id
    @TableField("orderId")
    private Integer orderId;
    //商品id
    @TableField("productId")
    private Integer productId;
    //购买商品的个数
    @TableField("count")
    private Integer count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
