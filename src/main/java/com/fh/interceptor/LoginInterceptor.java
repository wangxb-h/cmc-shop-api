package com.fh.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.utils.StringUtils;
import com.fh.common.RedisUtil;
import com.fh.common.exception.NologinException;
import com.fh.entity.UserVipInfo;
import com.fh.utils.JWT;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

@CrossOrigin
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //获取加密的密钥token
        String token = request.getHeader("token");
        //验证头信息是否完整
        if(StringUtils.isEmpty(token)){
            throw new NologinException("没有登录");
        }
        //解密  字节数组
        byte[] decode = Base64.getDecoder().decode(token);
        // 得到字符串  字节数组转为字符串  字符串是什么格式  iphone+","+sign
        String signToken=new String(decode);
        //判断是否被篡改
        String[] split = signToken.split(",");
        if (split.length!=2){
            throw new NologinException("没有登录");
        }
        String iphone = split[0];
        // jwt的秘钥
        String sign = split[1];
        //对加密的密钥进行jwt解密
        UserVipInfo user = JWT.unsign(sign, UserVipInfo.class);
        Jedis jedis = RedisUtil.getJedis();
        if (user!=null){
            //从redis取出最新密钥token
            String sign_redis = jedis.get("token_" + iphone);
            if (!sign_redis.equals(sign)){
                RedisUtil.returnJedis(jedis);
                throw new NologinException("验证码已过期  请重新登录");
            }
        }
        //续命
        jedis.set("token_"+iphone,sign);
        if (user==null){
            RedisUtil.returnJedis(jedis);
            //返回json字符串
            throw new NologinException("没有登录");
        }
        RedisUtil.returnJedis(jedis);
        //将用户信息放入request中  方便后面需求处理
        request.setAttribute("login_user",user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
