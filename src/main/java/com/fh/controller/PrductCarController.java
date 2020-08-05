package com.fh.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.common.JsonData;
import com.fh.common.RedisUtil;
import com.fh.entity.ProductCarInfo;
import com.fh.entity.ProductInfo;
import com.fh.entity.UserVipInfo;
import com.fh.service.ProductCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
//购物车
@RestController
@RequestMapping("car")
public class PrductCarController {
    @Autowired
    private ProductCarService productCarService;

    //加入购物车
    @RequestMapping("addCart")
    public JsonData addCart(Integer id, Integer count, HttpServletRequest request){
        Integer count_y=productCarService.addCart(id,count,request);
        return JsonData.getJsonSuccess(count_y);
    }

    //查询购物车
    @RequestMapping("findShopCart")
    public JsonData findShopCart(HttpServletRequest request){
        UserVipInfo login_user = (UserVipInfo) request.getAttribute("login_user");
        String iphone = login_user.getUserPhone();
        Jedis jedis = RedisUtil.getJedis();
        Map<String, String> map = jedis.hgetAll("car_" + iphone + "_wxb");
        Set<String> keys = map.keySet();
        List list=new ArrayList();
        for (String key: keys){
            ProductCarInfo carInfo = JSONObject.parseObject(map.get(key),ProductCarInfo.class);
            list.add(carInfo);
        }
        RedisUtil.returnJedis(jedis);
        return JsonData.getJsonSuccess(list);
    }

    //删除购物车商品
    @RequestMapping("deleteShopCar")
    public JsonData deleteShopCar(Integer id,HttpServletRequest request){
        String Sid = String.valueOf(id);
        UserVipInfo login_user = (UserVipInfo) request.getAttribute("login_user");
        String iphone = login_user.getUserPhone();
        Jedis jedis = RedisUtil.getJedis();
        jedis.hdel("car_"+iphone+"_wxb",Sid);
        RedisUtil.returnJedis(jedis);
        return JsonData.getJsonSuccess("success");
    }

    //修改购物车商品选中状态 true/false
    @RequestMapping("updateIsCheck")
    public JsonData updateIsCheck(Boolean isCheck,Integer id,HttpServletRequest request){
        if (id==null){
            return JsonData.getJsonError("id为空");
        }
        UserVipInfo login_user = (UserVipInfo)  request.getAttribute("login_user");
        String iphone = login_user.getUserPhone();
        Jedis jedis = RedisUtil.getJedis();
        String hget = jedis.hget("car_" + iphone + "_wxb", String.valueOf(id));
        ProductCarInfo carInfo = JSONArray.parseObject(hget, ProductCarInfo.class);
        carInfo.setCheck(isCheck);
        jedis.hset("car_" + iphone + "_wxb",String.valueOf(id),JSONObject.toJSONString(carInfo));
        RedisUtil.returnJedis(jedis);
        return JsonData.getJsonSuccess("修改状态成功");
    }

    //查询选中的订单
    @RequestMapping("findCheckProduct")
    public JsonData findCheckProduct(HttpServletRequest request){
        UserVipInfo userVipInfo = (UserVipInfo) request.getAttribute("login_user");
        String iphone = userVipInfo.getUserPhone();
        Jedis jedis = RedisUtil.getJedis();
        Map<String, String> map = jedis.hgetAll("car_" + iphone + "_wxb");
        Set<String> keys = map.keySet();
        List<ProductCarInfo> list=new ArrayList<>();
        for (String key : keys) {
            ProductCarInfo carInfo = JSONArray.parseObject(map.get(key), ProductCarInfo.class);
            if (carInfo.isCheck()==true){
                list.add(carInfo);
            }
        }
        RedisUtil.returnJedis(jedis);
        return JsonData.getJsonSuccess(list);
    }
}
