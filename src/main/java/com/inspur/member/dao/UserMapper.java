package com.inspur.member.dao;

import com.inspur.member.DO.User;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);
}