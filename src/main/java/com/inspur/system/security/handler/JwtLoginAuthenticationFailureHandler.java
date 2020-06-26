package com.inspur.system.security.handler;

import com.inspur.constant.Constant;
import com.inspur.system.response.ResponseCode;
import com.inspur.utils.HttpResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component("jwtLoginAuthenticationFailureHandler")
public class JwtLoginAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Map<String, String> infoMap = new HashMap<String, String>(2);
        infoMap.put("status", ResponseCode.NEED_LOGIN.getCode() + "");
        if (exception.getMessage().equalsIgnoreCase(Constant.LOGIN_ERROR)) {
            infoMap.put("msg", "用户名或者密码不对");
        } else {
            infoMap.put("msg", "未知原因");
        }
        HttpResponseUtil.outputJsonMsg(response, infoMap);
    }
}
