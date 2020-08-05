package com.fh.service;

import com.fh.entity.AddressInfo;

import java.util.List;

public interface AddressService {
    List<AddressInfo> findAddress(String iphone);

    void addRess(AddressInfo addressInfo);

    void updateIscheck(Integer id);

}
