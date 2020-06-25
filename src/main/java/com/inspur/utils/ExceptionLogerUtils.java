/**
 * @Package: com.inspur.util
 * @author: zhuanghuan
 * @date: 2020年2月18日 下午5:10:02
 */


package com.inspur.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @ClassName: ExceptionExtractUtils
 * @Description: TODO
 * @author: zhuanghuan
 * @date: 2020年2月18日 下午5:10:02
 */
public class ExceptionLogerUtils {

    public static String getErrmessage(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        t.printStackTrace(new PrintWriter(stringWriter, true));
        return stringWriter.getBuffer().toString();
    }

    /**
     * 截取异常中封装的错误信息
     *
     * @param e 异常对象
     * @return String
     * @author zhuanghuan
     * @date 2019年1月9日上午9:29:37
     */
    public static String cutException(Exception e) {
        String errorMessage = "";
        try {
            int index = e.toString().lastIndexOf(":");
            index = e.toString().lastIndexOf(":", index - 1);
            errorMessage = e.toString().substring(index + 1);
            index = errorMessage.lastIndexOf(":");
            if (index != -1) {
                errorMessage = errorMessage.substring(index + 1);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return errorMessage;
    }
}
