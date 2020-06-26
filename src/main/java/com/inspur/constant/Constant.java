package com.inspur.constant;

public class Constant {
    /**
     * 过期时间是3个小时
     */
    public static final long REDIS_TOKEN_EXPIRATION = 3 * 60 * 60L;
    /**
     * 过期时间是24个小时
     */
    public static final long BROWSER_TOKEN_EXPIRATION = 24 * 60 * 60L;

    /**
     * 选择了记住我之后的过期时间为7天
     */
    public static final long EXPIRATION_REMEMBER = 604800L;
    /**
     * token header
     */
    public static final String TOKEN_HEADER = "Authorization";
    /**
     * token prefix
     */
    public static final String TOKEN_PREFIX = "Bearer ";
    /**
     * siging_key
     */
    public static final String SIGNING_KEY = "jwtsecretdemo";
    public static final String ISS = "zhuanghuan";

    public static final String LOGIN_ERROR = "The username or password is incorrect";
}
