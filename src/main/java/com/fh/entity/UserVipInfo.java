package com.fh.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class UserVipInfo {
    private Integer id;
    private String userName;
    private String userPhone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date brithday;
    private String areaIds;
    private String usrImg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Date getBrithday() {
        return brithday;
    }

    public void setBrithday(Date brithday) {
        this.brithday = brithday;
    }

    public String getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String areaIds) {
        this.areaIds = areaIds;
    }

    public String getUsrImg() {
        return usrImg;
    }

    public void setUsrImg(String usrImg) {
        this.usrImg = usrImg;
    }
}
