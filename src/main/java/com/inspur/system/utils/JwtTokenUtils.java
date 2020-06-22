package com.inspur.system.utils;

import com.inspur.constant.Constant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * Jwt工具类
 */
public class JwtTokenUtils {


    // 创建token
    public static String createToken(String username, boolean isRememberMe, Map<String, Object> claims) {
//        long expiration = isRememberMe ? Constant.EXPIRATION_REMEMBER :Constant.EXPIRATION;
        long expiration = Constant.BROWSER_TOKEN_EXPIRATION;
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, Constant.SIGNING_KEY)
                .setIssuer(Constant.ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    /**
     * 从token中获取用户名
     *
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        return getTokenBody(token).getSubject();
    }

    /**
     * 判断是否已过期
     *
     * @param token
     * @return
     */
    public static boolean isExpiration(String token) {
        return getTokenBody(token).getExpiration().before(new Date());
    }

    /**
     * 获取body信息
     *
     * @param token
     * @return
     */
    public static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(Constant.SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

}
