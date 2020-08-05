package com.fh.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.entity.ProductInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper extends BaseMapper<ProductInfo> {

    Long getCount(ProductInfo product);

    List findProductByTypeId(ProductInfo product);

    List<ProductInfo> findHotAll();

    List<ProductInfo> findProductAllByProductId(Integer id);

    String findAreaNameByAreaIds(Integer areaId);

    int updateProductCount(@Param("id") Integer id,@Param("count") Integer count);
}
