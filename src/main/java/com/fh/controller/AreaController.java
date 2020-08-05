package com.fh.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.JsonData;
import com.fh.common.RedisUtil;
import com.fh.entity.AreaInfo;
import com.fh.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("area")
@CrossOrigin
public class AreaController {
    @Autowired
    private AreaService areaService;


    @RequestMapping("findArea")
    public JsonData findArea() {
        Jedis jedis = RedisUtil.getJedis();
        if (jedis.get("areaShop_json_wxb")!=null){
            String areaShop_json_wxb = jedis.get("areaShop_json_wxb");
            RedisUtil.returnJedis(jedis);
            return JsonData.getJsonSuccess(areaShop_json_wxb);
        }
        else {
            List<AreaInfo> list = areaService.findArea();
            String string = JSONObject.toJSONString(list);
            jedis.set("areaShop_json_wxb",string);
            RedisUtil.returnJedis(jedis);
            return JsonData.getJsonSuccess(list);
        }
    }


    @RequestMapping(value = "toupdate", method = RequestMethod.POST)
    public Map toupdate(Integer id) {
        Map map = new HashMap();
        try {
            AreaInfo areaInfo = areaService.toupdate(id);
            map.put("data", areaInfo);
            map.put("code", 200);
            map.put("message", "回显成功");
        } catch (Exception e) {
            map.put("code", 500);
            map.put("message", "回显失败");
        }
        return map;
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Map update(AreaInfo areaInfo) {
        Map map = new HashMap();
        try {
            areaService.update(areaInfo);
            map.put("message", "修改成功");
            map.put("code", 200);
        } catch (Exception e) {
            map.put("message", "修改失败");
            map.put("code", 500);
        }
        return map;
    }
}
