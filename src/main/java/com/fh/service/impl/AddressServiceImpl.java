package com.fh.service.impl;

import com.fh.dao.AddressMapper;
import com.fh.entity.AddressInfo;
import com.fh.entity.UserVipInfo;
import com.fh.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private HttpServletRequest request;

    //查询收货人信息
    @Override
    public List<AddressInfo> findAddress(String iphone) {
        List<AddressInfo> list=addressMapper.findAddress(iphone);
        for (int i = 0; i < list.size(); i++) {
            String areaIds = list.get(i).getAreaIds();
            String substring = areaIds.substring(1,areaIds.length()-1);
            String[] split = substring.split(",");
            String areaNames="";
            for (int j = 0; j < split.length; j++) {
                Integer areaId = Integer.valueOf(split[j]);
                String areaName=addressMapper.findAreaName(areaId);
                areaNames+=areaName+",";
            }
            list.get(i).setAreaName(areaNames.substring(0,areaNames.length()-1));
        }
        return list;
    }
    //添加收货人信息
    @Override
    public void addRess(AddressInfo addressInfo) {
        Date date=new Date();
        addressInfo.setCreateDate(date);
        addressMapper.addRess(addressInfo);
    }
    //修改默认地址
    @Override
    public void updateIscheck(Integer id) {
        UserVipInfo login_user = (UserVipInfo) request.getAttribute("login_user");
        String userPhone = login_user.getUserPhone();
        //根据用户手机号查询该用户的收货地址
        List<AddressInfo> list=addressMapper.findAddressAll(userPhone);
        for (int i = 0; i < list.size(); i++) {
            AddressInfo addressInfo = (AddressInfo) list.get(i);
            if (addressInfo.getIsCheck().equals("true")){
                addressInfo.setIsCheck("false");
                addressMapper.updateIsCheckFalse(addressInfo);
            }
        }
        AddressInfo addressInfos=new AddressInfo();
        addressInfos.setIsCheck("true");
        addressInfos.setId(id);
        addressMapper.updateIsCheckTrue(addressInfos);
    }
}
