package com.fh.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.fh.common.AliyunSmsUtils;
import com.fh.common.JsonData;
import com.fh.common.RedisUtil;
import com.fh.entity.UserVipInfo;
import com.fh.service.LoginService;
import com.fh.utils.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@CrossOrigin
@RequestMapping("login")
public class LoginController {
    @Autowired
    private LoginService loginService;
    //验证短袖验证码
    @RequestMapping("verificationCode")
    public JsonData verificationCode(String telephone, HttpServletRequest req) throws Exception {
        UserVipInfo userVipInfo=loginService.findUser(telephone);
        if (userVipInfo!=null){
            //AliyunSmsUtils.setNewcode();
            //int code = AliyunSmsUtils.getNewcode();
            String code = "111";
            String userPhone = userVipInfo.getUserPhone();
            //发短信
            /*SendSmsResponse response = AliyunSmsUtils.sendSms(userPhone, code);*/
            Jedis jedis = RedisUtil.getJedis();
            //将code验证码存入redis
            jedis.set(telephone+"_wxb",code);
            RedisUtil.returnJedis(jedis);
            return JsonData.getJsonSuccess("成功");
        }
        return JsonData.getJsonError("手机号或用户名不存在");
    }

    //登录
    @RequestMapping("login")
    public JsonData login(String codeMage,String iphone, HttpServletRequest req) {
        Map logMap=new HashMap();
        Jedis jedis = RedisUtil.getJedis();
        //从redis取出验证码
        String redisCode = jedis.get(iphone + "_wxb");
        //判断验证码是否一致
        if (codeMage!=null && redisCode.equals(codeMage)){
            UserVipInfo userVipInfo=new UserVipInfo();
            userVipInfo.setUserPhone(iphone);
            //将手机号进行jwt加密生成密钥
            String sign = JWT.sign(userVipInfo, 1000 * 60 * 60 * 24);
            //加签  手机号+sign值  目的是为了防止篡改数据
            String token = Base64.getEncoder().encodeToString((iphone + "," + sign).getBytes());
            //将最新的密钥放入redis
            jedis.set("token_"+iphone,sign);
            logMap.put("code",200);
            logMap.put("message","登陆成功");
            logMap.put("token",token);
            RedisUtil.returnJedis(jedis);
            return JsonData.getJsonSuccess(logMap);
        }else {
            logMap.put("code",300);
            logMap.put("message","用户不存在 或者 验证码错误");
            RedisUtil.returnJedis(jedis);
        }
        return JsonData.getJsonSuccess(logMap);
    }
    //注册用户
    @RequestMapping("adduser")
    public JsonData adduser(UserVipInfo userVipInfo){
        Jedis jedis = RedisUtil.getJedis();
        String string = JSONObject.toJSONString(userVipInfo);
        jedis.hset("zhuche",userVipInfo.getUserName(),string);
        String zhuche = jedis.hget("zhuche", userVipInfo.getUserName());
        RedisUtil.returnJedis(jedis);
        return JsonData.getJsonSuccess(string);
    }
    //文件上传
    @RequestMapping("uploadFile")
    public Map uploadFile(@RequestParam("usrImg") MultipartFile usrImg,HttpServletRequest req) throws Exception {
        Map map=new HashMap();
        String realPath = req.getSession().getServletContext().getRealPath("/images");
        File file=new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        String imgFile= UUID.randomUUID().toString()+usrImg.getOriginalFilename().substring(usrImg.getOriginalFilename().lastIndexOf("."));
        usrImg.transferTo(new File(realPath + "/" + imgFile));
        String filePath=("images"+"/"+imgFile);
        map.put("filePath",filePath);
        return map;
    }

}
