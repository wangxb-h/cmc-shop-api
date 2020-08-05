package com.fh.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.JsonData;
import com.fh.common.RedisUtil;
import com.fh.entity.AreaInfo;
import com.fh.entity.TypeInfo;
import com.fh.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("type")
@CrossOrigin
public class TypeController {
    @Autowired
    private TypeService typeService;

    @RequestMapping("findType")
    public JsonData findType() {
        Jedis jedis = RedisUtil.getJedis();
        if (jedis.get("type_json_wxb") != null) {
            String type_json_wxb = jedis.get("type_json_wxb");
            RedisUtil.returnJedis(jedis);
            return JsonData.getJsonSuccess(type_json_wxb);
        } else {
            List<TypeInfo> list = typeService.findType();
            String string = JSONObject.toJSONString(list);
            jedis.set("type_json_wxb", string);
            RedisUtil.returnJedis(jedis);
            return JsonData.getJsonSuccess(list);
        }
    }

    @RequestMapping("toUpdate")
    public Map toUpdate(Integer id) {
        Map map = new HashMap();
        try {
            TypeInfo typeInfo = typeService.toUpdate(id);
            map.put("data", typeInfo);
            map.put("message", "回显成功");
            map.put("code", 200);
        } catch (Exception e) {
            map.put("message", "回显失败");
            map.put("code", 500);
        }
        return map;
    }

    @RequestMapping("update")
    public Map update(TypeInfo typeInfo) {
        Map map = new HashMap();
        try {
            typeService.update(typeInfo);
            map.put("message", "修改成功");
            map.put("code", 200);
        } catch (Exception e) {
            map.put("message", "修改失败");
            map.put("code", 500);
        }
        return map;
    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Map delete(Integer id) {
        Map map = new HashMap();
        try {
            typeService.delete(id);
            map.put("message", "删除成功");
            map.put("code", 200);
        } catch (Exception e) {
            map.put("message", "删除失败");
            map.put("code", 500);
        }
        return map;
    }
}
