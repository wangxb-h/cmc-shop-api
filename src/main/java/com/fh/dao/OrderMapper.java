package com.fh.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.entity.AddressInfo;
import com.fh.entity.OrderInfo;
import com.fh.utils.PageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper extends BaseMapper<OrderInfo> {

    Long getCount(String iphone);

    List<OrderInfo> selectOrderMessage(@Param("orderInfo") OrderInfo orderInfo);

    List<OrderInfo> selectOrderXq(Integer orderId);


}
