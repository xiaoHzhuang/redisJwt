package com.inspur.system.security.service.impl;

import com.inspur.mail.service.IMailService;
import com.inspur.system.exception.ResponseEnum;
import com.inspur.system.redis.util.RedisLockUtil;
import com.inspur.system.security.dao.SystemUserMapper;
import com.inspur.system.security.po.SystemUser;
import com.inspur.system.security.service.IUserService;
import com.inspur.system.utils.DesPassword;
import com.inspur.system.utils.DesTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Autowired
    private RedisLockUtil redisLock;

    @Autowired
    private IMailService mailServiceImpl;

    @Value("${spring.mail.username}")
    private String mailSender;

    @Override
    public void regiser(SystemUser user) {
        user.setUserid(UUID.randomUUID().toString());
        SystemUser systemUserByName = null;
        SystemUser systemUserByMail = null;
        redisLock.lock(2000);
        try {
            systemUserByName = systemUserMapper.getUserByUserName(user.getUsername());
            systemUserByMail = systemUserMapper.getUserByUserMail(user.getEmail());
            if (systemUserByName == null && systemUserByMail == null) {
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
        if (systemUserByName != null) {
            ResponseEnum.USER_EXIST.assertNull(user);
        }
        if (systemUserByMail != null) {
            ResponseEnum.USER_MAIL_EXIST.assertNull(user);
        }
    }

    @Override
    public void retrievePwd(String mailReceiver) throws Exception {
        String mailSubject = "后台管理系统登录密码找回";
        SystemUser systemUser = systemUserMapper.getUserByUserMail(mailReceiver);
        if(systemUser == null){
            ResponseEnum.USER_MAIL_EXIST.assertNotNull(systemUser);
        }
        String mailContent = "密码为：" + DesTools.decryptPassword(systemUser.getPwd());
        mailServiceImpl.sendMail(mailSubject, mailContent, mailSender, new String[]{mailReceiver});
    }
}
