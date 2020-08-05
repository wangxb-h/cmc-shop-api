package com.fh.dao;

import com.fh.entity.AddressInfo;

import java.util.List;

public interface AddressMapper {
    List<AddressInfo> findAddress(String iphone);

    String findAreaName(Integer areaId);

    void addRess(AddressInfo addressInfo);

    List<AddressInfo> findAddressAll(String userPhone);

    void updateIsCheckFalse(AddressInfo addressInfo);

    void updateIsCheckTrue(AddressInfo addressInfos);

}
