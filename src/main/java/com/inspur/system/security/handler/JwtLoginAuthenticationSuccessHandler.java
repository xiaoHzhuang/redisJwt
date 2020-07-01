package com.inspur.system.security.handler;

import com.inspur.constant.Constant;
import com.inspur.system.security.po.SystemUserDetail;
import com.inspur.system.security.token.TokenRedisUtil;
import com.inspur.system.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component("jwtLoginAuthenticationSuccessHandler")
public class JwtLoginAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private TokenRedisUtil tokenRedisUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        SystemUserDetail systemUserDetail = (SystemUserDetail) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<String, Object>(2);
        claims.put("userId", systemUserDetail.getUserId());
        claims.put("userName", systemUserDetail.getUsername());
        //这里创建的token只是单纯的token,按照jwt的规定，最后请求的格式应该是 `Bearer token`
        String tempToken = JwtTokenUtils.createToken(systemUserDetail.getUsername(), false, claims);
        String token = Constant.TOKEN_PREFIX + tempToken;
        tokenRedisUtil.saveTokenwithExpireTime(tempToken, systemUserDetail.getUserId());
        response.setHeader(Constant.TOKEN_HEADER, token);
        request.setAttribute(Constant.TOKEN_HEADER, token);
        request.setAttribute("userName", systemUserDetail.getUsername());
        request.setAttribute("userCaption", systemUserDetail.getUserCaption());
        //请求内部重定向
        request.getRequestDispatcher("/user/login").forward(request, response);
    }
}
