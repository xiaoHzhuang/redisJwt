package com.inspur.system.security.filter;

import com.inspur.constant.Constant;
import com.inspur.system.response.ResponseCode;
import com.inspur.system.security.po.SystemUser;
import com.inspur.system.security.po.SystemUserDetail;
import com.inspur.system.security.token.JwtAuthenticationToken;
import com.inspur.system.security.token.TokenRedisUtil;
import com.inspur.system.utils.JwtTokenUtils;
import com.inspur.utils.HttpResponseUtil;
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
import java.util.List;
import java.util.Map;

/**
 * 请求鉴权过滤器
 */
public class JwtRequestAuthorizationFilter extends BasicAuthenticationFilter {
    private TokenRedisUtil tokenRedisUtil;

    private static List notFilterUrl = new ArrayList();

    /**
     * 白名单
     */
    static {
        notFilterUrl.add("/user/register");
    }

    public JwtRequestAuthorizationFilter(AuthenticationManager authenticationManager, TokenRedisUtil tokenRedisUtil) {
        super(authenticationManager);
        this.tokenRedisUtil = tokenRedisUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String requestUrl = request.getRequestURI();
        String appName = request.getServletContext().getContextPath();
        requestUrl = requestUrl.replace(appName, "");
        if (notFilterUrl.contains(requestUrl)) {
            request.getRequestDispatcher(requestUrl).forward(request, response);
        } else {
            authenticateToken(request, response, chain);
        }
    }

    private void authenticateToken(HttpServletRequest request,
                                   HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String tokenHeader = request.getHeader(Constant.TOKEN_HEADER);
        // 如果请求头中没有token信息则直接状态码-1，前台自动跳转至浏览器界面
        if (tokenHeader == null || !tokenHeader.startsWith(Constant.TOKEN_PREFIX)) {
            logger.info("token不存在");
            reLoginResponse(response, "token不存在", ResponseCode.NEED_LOGIN.getCode());
            return;
        }
        //1:从前台请求中获取token
        String tokenFromBrowser = tokenHeader.replace(Constant.TOKEN_PREFIX, "");
        String userId = JwtTokenUtils.getTokenBody(tokenFromBrowser).get("userId").toString();
        //2:检验前台请求token是否过期，若来自前台的token过期，需要前台跳转到登录界面
        boolean tokenValidate = JwtTokenUtils.isExpiration(tokenFromBrowser);
        if (tokenValidate) {
            logger.info("token已过期");
            reLoginResponse(response, "token已过期", ResponseCode.NEED_LOGIN.getCode());
            return;
        } else {
            //3:校验在redis中是否存在对应用户的token，若不存在，则跳转登录界面
            boolean redisValidate = tokenRedisUtil.hasTokenKey(userId);
            if (!redisValidate) {
                logger.info("token已失效");
                reLoginResponse(response, "token已失效", ResponseCode.NEED_LOGIN.getCode());
                return;
            } else {
                String tokenInRedis = tokenRedisUtil.getToken(userId);
                //若来自前台请求的token和redis中存储的token信息不一致，则返回状态码-2，前台重新发送请求
                if (!tokenFromBrowser.equals(tokenInRedis)) {
                    logger.info("token已无效");
                    reLoginResponse(response, "token已无效", ResponseCode.RESEND_REQUEST.getCode());
                    return;
                } else {
                    //4:获取token在redis的剩余有效期，若剩余有效期小于5分钟,则生成新的token
                    refershToken(userId, tokenFromBrowser, response);
                }
            }
        }
        // 如果请求头中有token，则进行解析，并且在当前线程上下文设置认证信息
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
        super.doFilterInternal(request, response, chain);
    }

    private void refershToken(String userId, String tokenFromBrowser, HttpServletResponse response) {
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

    private void reLoginResponse(HttpServletResponse response, String msg, int code) throws IOException {
        Map<String, String> infoMap = new HashMap<String, String>(2);
        infoMap.put("status", code + "");
        infoMap.put("msg", msg);
        response.setStatus(code);
        HttpResponseUtil.outputJsonMsg(response, infoMap);
    }

    /**
     * 这里从token中获取用户信息并新建一个JwtAuthenticationToken
     */

    private JwtAuthenticationToken getAuthentication(String tokenHeader) {
        String token = tokenHeader.replace(Constant.TOKEN_PREFIX, "");
        String username = JwtTokenUtils.getUsername(token);
        String userId = (String) JwtTokenUtils.getTokenBody(token).get("userId");
        SystemUser systemUser = new SystemUser();
        systemUser.setUserid(userId);
        systemUser.setUsername(username);
        UserDetails userDetails = new SystemUserDetail(systemUser);
        if (username != null) {
            return new JwtAuthenticationToken(userDetails, null, new ArrayList<>());
        }
        return null;
    }

}
