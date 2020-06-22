package com.inspur.system.security.filter;

import com.inspur.constant.Constant;
import com.inspur.system.security.po.SystemUser;
import com.inspur.system.security.po.SystemUserDetail;
import com.inspur.system.security.token.JwtAuthenticationToken;
import com.inspur.system.security.token.TokenRedisUtil;
import com.inspur.system.utils.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private TokenRedisUtil tokenRedisUtil;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, TokenRedisUtil tokenRedisUtil) {
        super(authenticationManager);
        this.tokenRedisUtil = tokenRedisUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String tokenHeader = request.getHeader(Constant.TOKEN_HEADER);
        // 如果请求头中没有token信息则直接401状态码，前台自动跳转至浏览器界面
        if (tokenHeader == null || !tokenHeader.startsWith(Constant.TOKEN_PREFIX)) {
            response.getWriter().write("authentication failed, reason:无token信息");
            //前台收到该状态码，会自动跳转到首页
            response.setStatus(401);
            return;
        }
        //1:从前台请求中获取token
        String tokenFromBrowser = tokenHeader.replace(Constant.TOKEN_PREFIX, "");
        String userId = JwtTokenUtils.getTokenBody(tokenFromBrowser).get("userId").toString();
        //2:检验前台请求token是否过期，若来自前台的token过期，需要前台跳转到登录界面
        boolean tokenValidate = JwtTokenUtils.isExpiration(tokenFromBrowser);
        if (tokenValidate) {
            logger.info("已过期");
            response.getWriter().write("authentication failed, reason:token已过期");
            //前台收到该状态码，会自动重新发送请求
            response.setStatus(401);
            return;
        } else {
            //3:校验token在redis中是否存在，若不存在，则跳转登录界面
            boolean redisValidate = tokenRedisUtil.hasTokenKey(userId);
            if (!redisValidate) {
                response.getWriter().write("authentication failed, reason:token已过期");
                response.setStatus(401);
                return;
            } else {
                String tokenInRedis = tokenRedisUtil.getToken(userId);
                //若来自前台请求的token和redis中存储的token信息不一致，则返回状态码429，前台重新发送请求
                if (!tokenFromBrowser.equals(tokenInRedis)) {
                    response.getWriter().write("authentication failed, reason:token已过期");
                    response.setStatus(401);
                    return;
                } else {
                    //4:获取token在redis的剩余有效期，若剩余有效期小于5分钟,则生成新的token
                    long expireTime = tokenRedisUtil.getTokenExpireTime(userId);
                    if (expireTime < 300) {
                        Claims claims = JwtTokenUtils.getTokenBody(tokenFromBrowser);
                        Map<String, Object> claimsMap = new HashMap<String, Object>(2);
                        claimsMap.put("userId", claims.get("userId"));
                        claimsMap.put("userName", claims.get("userName"));
                        String newToken = JwtTokenUtils.createToken(claims.get("userName").toString(), false, claimsMap);
                        tokenRedisUtil.saveTokenwithExpireTime(newToken, claims.get("userId").toString());
                        response.setHeader(Constant.TOKEN_HEADER, newToken);
                    }
                }
            }
        }
        // 如果请求头中有token，则进行解析，并且在当前线程上下文设置认证信息
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
        super.doFilterInternal(request, response, chain);
    }

    // 这里从token中获取用户信息并新建一个JwtAuthenticationToken
    private JwtAuthenticationToken getAuthentication(String tokenHeader) {
        String token = tokenHeader.replace(Constant.TOKEN_PREFIX, "");
        String username = JwtTokenUtils.getUsername(token);
        String userId = (String) JwtTokenUtils.getTokenBody(token).get("userId");
        SystemUser systemUser = new SystemUser();
        systemUser.setUserId(userId);
        systemUser.setUserName(username);
        UserDetails userDetails = new SystemUserDetail(systemUser);
        if (username != null) {
            return new JwtAuthenticationToken(userDetails, null, new ArrayList<>());
        }
        return null;
    }

}
