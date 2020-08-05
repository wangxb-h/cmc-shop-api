package com.fh.controller;

import com.fh.common.JsonData;
import com.fh.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.FeiConfig;
import com.github.wxpay.sdk.WXPay;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("weixinPay")
public class WeiPay {
    @Autowired
    private OrderService orderService;
    //统一下单   返回二维码url   生成二维码（有效时间30分钟  保证订单30分钟有效）
    @RequestMapping("PlaceOrder")
    public JsonData PlaceOrder(Integer orderId,String totalMoney) throws Exception {
        Map monyPhoto=orderService.createMeonyPhoto(orderId);
        return JsonData.getJsonSuccess(monyPhoto);
    }

    // 1 支付成功  2 支付中  3 未支付
    @RequestMapping("selectOrderType")
    public JsonData selectOrderType(Integer orderId) throws Exception {
        Integer status = orderService.selectPayStatus(orderId);
        return JsonData.getJsonSuccess(status);
    }

}
