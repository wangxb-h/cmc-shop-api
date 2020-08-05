package com.fh.service.impl;

import com.fh.dao.LoginMapper;
import com.fh.entity.UserVipInfo;
import com.fh.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;

    @Override
    public UserVipInfo findUser(String telephone) {
        return loginMapper.findUser(telephone);
    }
}
