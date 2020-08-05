package com.fh.service;

import com.fh.entity.ProductInfo;

import java.util.List;

public interface ProductService {
    List<ProductInfo> findProductByTypeId(ProductInfo product);

    List<ProductInfo> findHotAll();

    List<ProductInfo> findProductAllByProductId(Integer id);

}
