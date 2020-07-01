package com.inspur.system.security.dao;

import com.inspur.system.security.po.SystemUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SystemUserMapper {
    int insert(SystemUser record);

    int insertSelective(SystemUser record);

    SystemUser getUserByUserName(String userName);

    SystemUser getUserByUserMail(String userMail);
}