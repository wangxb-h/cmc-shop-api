package com.fh.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.fh.common.JsonData;
import com.fh.common.RedisUtil;
import com.fh.common.exception.NologinException;
import com.fh.entity.ProductInfo;
import com.fh.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.List;

@RestController
@RequestMapping("product")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;
    //通过类型id查询对应商品
    @RequestMapping("findProductByTypeId")
    public JsonData findProductByTypeId(ProductInfo product) {
        List<ProductInfo> list = productService.findProductByTypeId(product);
        return JsonData.getJsonSuccess(list);
    }
    //查询热销数据
    @RequestMapping("findHotAll")
    public JsonData findHotAll() {
        Jedis jedis = RedisUtil.getJedis();
        String hot_json = jedis.get("hot_json");
        if (StringUtils.isEmpty(hot_json) == true) {
            List<ProductInfo> list = productService.findHotAll();
            String string = JSONObject.toJSONString(list);
            jedis.set("hot_json", string);
            RedisUtil.returnJedis(jedis);
            return JsonData.getJsonSuccess(string);
        } else {
            RedisUtil.returnJedis(jedis);
            return JsonData.getJsonSuccess(hot_json);
        }
    }
    //通过商品id查询对应商品
    @RequestMapping("findProductAllByProductId")
    public JsonData findProductAllByProductId(Integer id) {
        List<ProductInfo> proList = productService.findProductAllByProductId(id);
        return JsonData.getJsonSuccess(proList);
    }

}
