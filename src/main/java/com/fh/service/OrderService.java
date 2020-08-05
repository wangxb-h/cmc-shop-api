package com.fh.service;

import com.fh.common.exception.CountException;
import com.fh.entity.AddressInfo;
import com.fh.entity.OrderInfo;
import com.fh.utils.PageBean;

import java.util.List;
import java.util.Map;

public interface OrderService {

    Map createOrder(Integer addressId, Integer payId) throws CountException;

    Map createMeonyPhoto(Integer orderId) throws Exception;

    Integer selectPayStatus(Integer orderId) throws Exception;

    List<OrderInfo> selectOrderXq(Integer orderId);

    OrderInfo selectOrderMessage(OrderInfo orderInfo);
}
