package com.fh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.utils.StringUtils;
import com.fh.common.RedisUtil;
import com.fh.common.enums.PayStatusEnum;
import com.fh.common.exception.CountException;
import com.fh.dao.OrderMapper;
import com.fh.dao.OrderProductMapper;
import com.fh.dao.ProductMapper;
import com.fh.entity.*;
import com.fh.service.OrderService;
import com.fh.utils.PageBean;
import com.fh.utils.RedisUse;
import com.github.wxpay.sdk.FeiConfig;
import com.github.wxpay.sdk.WXPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderProductMapper orderProductMapper;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ProductMapper productMapper;

    //生成订单
    @Override
    public Map createOrder(Integer addressId, Integer payId) throws CountException{
        Map map = new HashMap();
        //完善订单数据
        OrderInfo orderInfo = new OrderInfo();
        //订单详情数据集合
        List<OrderProductInfo> oplist = new ArrayList<>();
        //订单支付状态
        orderInfo.setPayStatus(PayStatusEnum.PAY_STATUS_INIT.getStatus());
        //订单地址id
        orderInfo.setAddressId(addressId);
        //订单支付类型
        orderInfo.setPayType(payId);
        //订单创建时间
        orderInfo.setCreateDate(new Date());

        //设置有多少个订单
        Integer typeCount = 0;
        //设置订单金额
        BigDecimal totalMony = new BigDecimal(0);

        //获取用户信息
        UserVipInfo login_user = (UserVipInfo) request.getAttribute("login_user");
        String iphone = login_user.getUserPhone();

        //获取redis 购物车中所有商品
        Jedis jedis = RedisUtil.getJedis();
        //key-value对字符串
        Map<String, String> map1 = jedis.hgetAll("car_" + iphone + "_wxb");
        //获取map中所有的keys
        Set<String> keys = map1.keySet();
        List<ProductCarInfo> productCarlist = new ArrayList<>();
        for (String key : keys) {
            ProductCarInfo productCarInfo = JSONObject.parseObject(map1.get(key), ProductCarInfo.class);
            productCarlist.add(productCarInfo);
        }

        for (int i = 0; i < productCarlist.size(); i++) {
            //判断是否为购物车中选中数据
            if (productCarlist.get(i).isCheck() == true) {
                ProductInfo productInfo = productMapper.selectById(productCarlist.get(i).getId());
                //判断库存是否足够
                if (productInfo.getStoryCount() > productCarlist.get(i).getCount()) {
                    //订单个数
                    typeCount++;
                    //订单总金额
                    totalMony = totalMony.add(productCarlist.get(i).getCountMoney());
                    //完善订单详情数据
                    OrderProductInfo orderProduct = new OrderProductInfo();
                    orderProduct.setProductId(productCarlist.get(i).getId());
                    orderProduct.setCount(productCarlist.get(i).getCount());
                    oplist.add(orderProduct);//这时候还没有订单id

                    //减库存  数据库的锁 保证不会超卖  update  返回一个值 int
                    int i1 = productMapper.updateProductCount(productCarlist.get(i).getId(), productCarlist.get(i).getCount());
                    //0是超卖
                    if (i1 == 0) {
                        throw new CountException("商品编号为:" + productCarlist.get(i).getId() + "的库存不足   库存只有：" + productInfo.getStoryCount());
                    }
                } else {//库存不够 进行事物的回滚
                    throw new CountException("商品编号为:" + productCarlist.get(i).getId() + "的库存不足   库存只有：" + productInfo.getStoryCount());
                }
            }
        }
        orderInfo.setTotalMoney(totalMony);
        orderInfo.setProTypeCount(typeCount);
        //生成订单表数据
        orderMapper.insert(orderInfo);
        //生成订单详情数据
        orderProductMapper.batchAdd(oplist, orderInfo.getId());
        //结算完以后删除原本在购物车中选中的商品
        for (int i = 0; i < productCarlist.size(); i++) {
            if (productCarlist.get(i).isCheck() == true) {
                jedis.hdel("car_" + iphone + "_wxb", String.valueOf(productCarlist.get(i).getId()));
            }
        }
        RedisUtil.returnJedis(jedis);
        map.put("orderId", orderInfo.getId());
        map.put("totalMoney", totalMony);
        return map;
    }

    //统一下单   返回二维码url   生成二维码（有效时间30分钟  保证订单30分钟有效）
    @Override
    public Map createMeonyPhoto(Integer orderId) throws Exception {
        Map rs=new HashMap();
        //从redsi中判断是否已经生成过
        String meonyPhotoUrl = RedisUse.get("order_"+orderId+"_wxb");
        if(StringUtils.isEmpty(meonyPhotoUrl)!=true){//不为空  已经生成过二维码
            rs.put("code",200);
            rs.put("url",meonyPhotoUrl);
            return rs;
        }
        OrderInfo order = orderMapper.selectById(orderId);
        // 微信支付  natvie   商户生成二维码
        //配置配置信息
        FeiConfig config = new FeiConfig();
        //得到微信支付对象
        WXPay wxpay = new WXPay(config);
        //设置请求参数
        Map<String, String> data = new HashMap<String, String>();
        //对订单信息描述
        data.put("body", "飞狐电商666-订单支付");
        //String payId = System.currentTimeMillis()+"";
        //设置订单号 （保证唯一 ）
        data.put("out_trade_no","weixin1_order_wxb_"+orderId);
        //设置币种
        data.put("fee_type", "CNY");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        Date d=new Date();
        String dateStr = sdf.format(new Date(d.getTime() + 120000000));
        //设置二维码的失效时间
        data.put("time_expire", dateStr);
        //设置订单金额   单位分
        BigDecimal totalMoney = order.getTotalMoney();
        BigDecimal num2 = new BigDecimal(100);
        BigDecimal BtotalMoney = totalMoney.multiply(num2);
        String tatalMonyStr = String.valueOf(BtotalMoney);
        data.put("total_fee","1");
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        //设置支付方式
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
        // 统一下单
        Map<String, String> resp = wxpay.unifiedOrder(data);
        //这一块必须用log4j 做记录的
        System.out.println(orderId+"下订单结果为:"+ JSONObject.toJSONString(resp));
        if("SUCCESS".equalsIgnoreCase(resp.get("return_code"))&&"SUCCESS".equalsIgnoreCase(resp.get("result_code"))){
            rs.put("code",200);
            rs.put("url",resp.get("code_url"));
            //更新订单状态
            order.setPayStatus(PayStatusEnum.PAY_STATUS_ING.getStatus());
            orderMapper.updateById(order);
            //将二维码存入redis  设置失效时间
            RedisUse.set("order_"+orderId+"_wxb",resp.get("code_url"),30*60);
        }else {
            rs.put("code",600);
            rs.put("info",resp.get("return_msg"));
        }
        return rs;
    }
    // 1 支付成功  2 支付中  3 未支付
    @Override
    public Integer selectPayStatus(Integer orderId) throws Exception {
        FeiConfig config = new FeiConfig();
        WXPay wxpay = new WXPay(config);
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no","weixin1_order_wxb_"+orderId);
        // 查询支付状态
        Map<String, String> resp = wxpay.orderQuery(data);
        System.out.println("查询结果："+JSONObject.toJSONString(resp));
        if("SUCCESS".equalsIgnoreCase(resp.get("return_code"))&&"SUCCESS".equalsIgnoreCase(resp.get("result_code"))){
            if("SUCCESS".equalsIgnoreCase(resp.get("trade_state"))){//支付成功
                //更新订单状态
                OrderInfo order=new OrderInfo();
                order.setId(orderId);
                order.setPayStatus(PayStatusEnum.PAY_STATUS_SUCCESS.getStatus());
                orderMapper.updateById(order);
                return 1;
            }else if("NOTPAY".equalsIgnoreCase(resp.get("trade_state"))){
                return 3;
            }else if("USERPAYING".equalsIgnoreCase(resp.get("trade_state"))){
                return 2;
            }
        }
        return 0;
    }
    //查询订单信息
    @Override
    public OrderInfo selectOrderMessage(OrderInfo orderInfo) {
        UserVipInfo login_user = (UserVipInfo) request.getAttribute("login_user");
        String iphone = login_user.getUserPhone();

        Long count=orderMapper.getCount(iphone);
        orderInfo.setRecordsTotal(count);
        orderInfo.setRecordsFiltered(count);
        orderInfo.setVipId(iphone);
        List<OrderInfo> list=orderMapper.selectOrderMessage(orderInfo);
        orderInfo.setData(list);
        return orderInfo;
    }
    //查询订单详情
    @Override
    public List<OrderInfo> selectOrderXq(Integer orderId) {
        List<OrderInfo> orderList=orderMapper.selectOrderXq(orderId);

        UserVipInfo login_user = (UserVipInfo) request.getAttribute("login_user");
        String iphone = login_user.getUserPhone();
        ProductInfo productInfo = productMapper.selectById(iphone);
        Integer productId = productInfo.getId();
        List<OrderInfo> productList=orderMapper.selectOrderXq(orderId);
        return orderList;
    }
}
