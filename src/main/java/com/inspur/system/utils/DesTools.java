package com.inspur.system.utils;

import java.math.BigInteger;

/**
 * 登录验证中密码加密的方法
 *
 * @ClassName: DesTools
 * @Description: 登录验证中密码加密的方法
 * @author: jingcc
 * @date: 2018年7月24日 下午2:02:43
 */
public class DesTools {

    private static final int RADIX = 16;
    private static final String SEED = "0933910847463829827159347601486730416058";
    public static final String PASSWORD_ENCRYPTED_PREFIX = "Encrypted ";

    /**
     * 字符串加密方法
     *
     * @param password 要加密的方法
     * @return String 加密后的字符串
     */
    public static final String encryptPassword(String password) {
        if (password == null) {
            return "";
        }
        if (password.length() == 0) {
            return "";
        }
        BigInteger biPasswd = new BigInteger(password.getBytes());
        BigInteger biR0 = new BigInteger(SEED);
        BigInteger biR1 = biR0.xor(biPasswd);
        return biR1.toString(RADIX);
    }

    /**
     * 字符串解密方法
     *
     * @param encrypted 要解密的字符串
     * @return 解密后的字符串
     */
    public static final String decryptPassword(String encrypted) {
        encrypted = encrypted.replace(PASSWORD_ENCRYPTED_PREFIX, "");
        if (encrypted == null) {
            return "";
        }
        if (encrypted.length() == 0) {
            return "";
        }
        BigInteger biConfuse = new BigInteger(SEED);
        try {
            BigInteger biR1 = new BigInteger(encrypted, RADIX);
            BigInteger biR0 = biR1.xor(biConfuse);

            return new String(biR0.toByteArray());
        } catch (Exception e) {
            return "";
        }
    }
}
