package com.inspur.system.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * 前台加密后台解密算法
 * @ClassName: DesPassword 
 * @Description: 前台加密后台解密算法
 * @author: jingcc
 * @date: 2018年7月25日 上午10:52:02
 */
public class DesPassword {


    /**
     * 字符串加密
     * @Description: 字符串加密
     * @author: jingcc
     * @date: 2018年7月25日 上午10:52:55 
     * @param data
     * @return
     */
    public String strEnc(String data) {
        String encData = null;
		try {
			encData = new String(Base64.encodeBase64(data.getBytes()), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return encData;
    }

   /**
    * 字符串解密算法
    * @Description: 字符串解密算法
    * @author: jingcc
    * @date: 2018年7月25日 上午10:53:14 
    * @param data
    * @return
    */
    public String strDec(String data){
    	data = data.replace(" ", "+");
    	String decData = null;
		try {
			decData = new String(Base64.decodeBase64(data), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return decData;
    }

    
}