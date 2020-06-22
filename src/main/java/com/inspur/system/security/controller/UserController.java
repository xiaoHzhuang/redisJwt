package com.inspur.system.security.controller;

import com.inspur.common.PO.ServerResponse;
import com.inspur.constant.Constant;
import com.inspur.member.DO.Member;
import com.inspur.system.security.po.SystemUser;
import com.inspur.system.security.po.SystemUserDetail;
import com.inspur.system.security.service.IUserService;
import com.inspur.system.security.token.TokenRedisUtil;
import com.inspur.system.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
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
        rsMap.put("userName", request.getAttribute("userName"));
        rsMap.put("flag", true);
        return ServerResponse.createBySuccess(rsMap);
    }

    @RequestMapping("logOut")
    public ServerResponse<Map<String, Object>> logOut(HttpServletResponse response) {
        Map<String, Object> rsMap = new HashMap<String, Object>(2);
        SystemUserDetail systemUserDetail = LoginUserUtil.getCurrentUser();
        System.out.println(systemUserDetail.getUsername());
        response.setStatus(401);
        if (tokenRedisUtil.hasTokenKey(systemUserDetail.getUserId())) {
            tokenRedisUtil.deleteToken(systemUserDetail.getUserId());
        }
        rsMap.put("flag", true);
        return ServerResponse.createBySuccess(rsMap);
    }


    @RequestMapping("regiser")
    public ServerResponse<String> regiser(@RequestBody SystemUser user) {
        Map<String, String> rs = new HashMap<String, String>();
        try {
            userService.regiser(user);
            rs.put("code", "1");
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("code", "0");
        }
        return ServerResponse.createBySuccess("1");
    }
}