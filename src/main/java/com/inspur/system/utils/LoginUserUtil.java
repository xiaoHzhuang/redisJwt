package com.inspur.system.utils;

import com.inspur.system.security.po.SystemUserDetail;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoginUserUtil {


    public static SystemUserDetail getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SystemUserDetail SystemUserDetail = null;
        if ((principal instanceof SystemUserDetail)) {
            SystemUserDetail = (SystemUserDetail) principal;
        }
        return SystemUserDetail;
    }
}
