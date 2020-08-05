package com.fh.dao;

import com.fh.entity.UserVipInfo;

public interface LoginMapper {
    UserVipInfo findUser(String telephone);
}
