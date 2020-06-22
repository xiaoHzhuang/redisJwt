package com.inspur.system.security.token;

import com.inspur.constant.Constant;
import com.inspur.system.redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TokenRedisUtil {
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 保存token至redis中
     *
     * @param token  token
     * @param userId 用户id
     */
    public void saveTokenwithExpireTime(String token, String userId) {
        //token保存至redis中
        redisUtil.set(userId, token, Constant.REDIS_TOKEN_EXPIRATION);
    }

    /**
     * 获取保存至redis中的entry
     *
     * @param userId 用户id
     * @return
     */
    public String getToken(String userId) {
        return (String) redisUtil.get(userId);
    }

    /**
     * 刷寻过期时间
     *
     * @param tokenKey
     */
    public void refreshExpireTime(String tokenKey) {
        redisUtil.expire(tokenKey, Constant.REDIS_TOKEN_EXPIRATION);
    }

    /**
     * 删除指定的token
     *
     * @param tokenKey
     * @return
     */
    public boolean deleteToken(String tokenKey) {
        return redisUtil.delete(tokenKey);
    }

    /**
     * redis中是否存在对应的token
     *
     * @param tokenKey
     * @return
     */
    public boolean hasTokenKey(String tokenKey) {
        return redisUtil.hasKey(tokenKey);
    }

    /**
     * 获取剩余过期时间
     *
     * @param tokenKey
     * @return
     */
    public long getTokenExpireTime(String tokenKey) {
        return redisUtil.getExpire(tokenKey);
    }
}
