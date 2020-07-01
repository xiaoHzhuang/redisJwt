package com.inspur.system.security.controller;

import com.inspur.system.response.ResponseCode;
import com.inspur.system.response.ServerResponse;
import com.inspur.constant.Constant;
import com.inspur.system.security.po.SystemUser;
import com.inspur.system.security.po.SystemUserDetail;
import com.inspur.system.security.service.IUserService;
import com.inspur.system.security.token.TokenRedisUtil;
import com.inspur.system.utils.LoginUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("user")
public class UserController {
    @Autowired
    private TokenRedisUtil tokenRedisUtil;

    @Autowired
    private IUserService userService;


    @RequestMapping("login")
    public ServerResponse<Map<String, Object>> login(HttpServletRequest request) {
        Map<String, Object> rsMap = new HashMap<String, Object>(2);
        rsMap.put("token", request.getAttribute(Constant.TOKEN_HEADER));
        rsMap.put("userName", request.getAttribute("userCaption"));
        return ServerResponse.createBySuccess(rsMap);
    }

    @RequestMapping("logOut")
    public ServerResponse<Map<String, Object>> logOut(HttpServletResponse response) {
        Map<String, Object> rsMap = new HashMap<String, Object>(2);
        SystemUserDetail systemUserDetail = LoginUserUtil.getCurrentUser();
        if (tokenRedisUtil.hasTokenKey(systemUserDetail.getUserId())) {
            tokenRedisUtil.deleteToken(systemUserDetail.getUserId());
        }
        rsMap.put("status", ResponseCode.RE_LOGIN.getCode());
        return ServerResponse.createBySuccess(rsMap);
    }


    @RequestMapping("register")
    public ServerResponse<String> regiser(@RequestBody SystemUser user) {
        userService.regiser(user);
        return ServerResponse.createBySuccess("注册成功");
    }


    @RequestMapping("pwd/retrieve")
    public ServerResponse<String> retrievePwd(@RequestParam("mail") String mailReceiver) throws Exception {
        userService.retrievePwd(mailReceiver);
        return ServerResponse.createBySuccess("发送邮件成功");
    }
}
