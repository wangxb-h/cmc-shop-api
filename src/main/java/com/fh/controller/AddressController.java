package com.fh.controller;

import com.fh.common.JsonData;
import com.fh.entity.AddressInfo;
import com.fh.entity.UserVipInfo;
import com.fh.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//收货地址
@RestController
@RequestMapping("address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    //查询收货人信息
    @RequestMapping("findAddress")
    public JsonData findAddress(HttpServletRequest request){
        UserVipInfo login_user = (UserVipInfo) request.getAttribute("login_user");
        String iphone = login_user.getUserPhone();
        List<AddressInfo> list=addressService.findAddress(iphone);
        return JsonData.getJsonSuccess(list);
    }

    //新增收货人信息
    @RequestMapping("addRess")
    public JsonData addRess(AddressInfo addressInfo,HttpServletRequest request){
        UserVipInfo login_user = (UserVipInfo) request.getAttribute("login_user");
        String iphone = login_user.getUserPhone();
        addressInfo.setVipId(iphone);
        addressService.addRess(addressInfo);
        return JsonData.getJsonSuccess("成功");
    }

    @RequestMapping("updateIscheck")
    public JsonData updateIscheck(Integer id){
        addressService.updateIscheck(id);
        return JsonData.getJsonSuccess("111");
    }
}
