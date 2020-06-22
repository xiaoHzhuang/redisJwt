package com.inspur.system.security.service.impl;

import com.inspur.system.security.dao.SystemUserMapper;
import com.inspur.system.security.po.SystemUser;
import com.inspur.system.security.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    public void regiser(SystemUser user) {
        user.setUserid(UUID.randomUUID().toString());
        systemUserMapper.insert(user);
    }
}
