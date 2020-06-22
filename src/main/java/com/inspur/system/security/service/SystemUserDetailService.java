package com.inspur.system.security.service;

import com.inspur.system.security.po.SystemUser;
import com.inspur.system.security.po.SystemUserDetail;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SystemUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser systemUser = new SystemUser();
        systemUser.setUserId("0001");
        systemUser.setUserName("scott");
        systemUser.setPassword("aaaaaa");
        return new SystemUserDetail(systemUser);
    }
}
