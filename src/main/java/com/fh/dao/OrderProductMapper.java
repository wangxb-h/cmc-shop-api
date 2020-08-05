package com.fh.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.entity.OrderProductInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderProductMapper extends BaseMapper<OrderProductInfo> {
    void batchAdd(@Param("list") List<OrderProductInfo> list,@Param("id") Integer id);
}
