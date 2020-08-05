package com.fh.service;

import com.fh.entity.UserVipInfo;

public interface LoginService {
    UserVipInfo findUser(String telephone);
}
