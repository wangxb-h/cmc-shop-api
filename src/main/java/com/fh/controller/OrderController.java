package com.fh.controller;

import com.fh.common.JsonData;
import com.fh.common.RedisUtil;
import com.fh.common.exception.CountException;
import com.fh.entity.OrderInfo;
import com.fh.entity.UserVipInfo;
import com.fh.service.OrderService;
import com.fh.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
//订单
@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    //创建订单
    @RequestMapping("createOrder")
    public JsonData createOrder(Integer addressId,Integer payId,String flag) throws CountException {
        //处理接口的幂等性  同一个请求 发送多次   结果只处理一次
        Jedis jedis = RedisUtil.getJedis();
        Boolean exists = jedis.exists(flag);
        if (exists==true){//二次请求
            RedisUtil.returnJedis(jedis);
            return JsonData.getJsonError(300,"请求处理中");
        }else {
            jedis.setex(flag,15,"");
            RedisUtil.returnJedis(jedis);
        }
        Map map=orderService.createOrder(addressId,payId);
        return JsonData.getJsonSuccess(map);
    }

    //查询订单信息
    @RequestMapping("selectOrderMessage")
    public OrderInfo selectOrderMessage(OrderInfo orderInfo){
        orderInfo=orderService.selectOrderMessage(orderInfo);
        return orderInfo;
    }
    //查询订单详情
    @RequestMapping("selectOrderXq")
    public JsonData selectOrderXq(Integer orderId){
        List<OrderInfo> orderList=orderService.selectOrderXq(orderId);
        return JsonData.getJsonSuccess(orderList);
    }

}
