package com.fh.service;

import com.fh.entity.ProductCarInfo;

import javax.servlet.http.HttpServletRequest;

public interface ProductCarService {

    Integer addCart(Integer id, Integer count, HttpServletRequest request);
}
