package com.inspur.system.security.service.impl;

import com.inspur.system.exception.ResponseEnum;
import com.inspur.system.redis.util.RedisLockUtil;
import com.inspur.system.security.dao.SystemUserMapper;
import com.inspur.system.security.po.SystemUser;
import com.inspur.system.security.service.IUserService;
import com.inspur.system.utils.DesPassword;
import com.inspur.system.utils.DesTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private SystemUserMapper systemUserMapper;
    @Autowired
    private RedisLockUtil redisLock;

    @Override
    public void regiser(SystemUser user) {
        user.setUserid(UUID.randomUUID().toString());
        SystemUser systemUser = null;
        redisLock.lock(2000);
        try {
            systemUser = systemUserMapper.getUserByUserName(user.getUsername());
            if (systemUser == null) {
                String password = user.getPwd();
                DesPassword desPassword = new DesPassword();
                password = desPassword.strDec(password);
                user.setPwd(DesTools.encryptPassword(password));
                systemUserMapper.insert(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            redisLock.unlock();
        }
        if (systemUser != null) {
            ResponseEnum.USER_EXIST.assertNull(user);
        }
    }
}
