package com.inspur.system.response;

public enum ResponseCode {

    /**
     * 登陆失败
     */
    NEED_LOGIN(-1, "登陆失败"),

    /**
     * 失败
     */
    ERROR(0, "ERROR"),
    /**
     * 成功
     */
    SUCCESS(1, "SUCCESS"),
    /**
     * 用户已被注册
     */

    USER_EXIST(2, "用户已被注册"),
    /**
     * 邮箱已被注册
     */
    USER_MAIL_EXIST(3, "邮箱已被注册"),
    /**
     * 邮箱不存在
     */
    USER_MAIL_NOT_EXIST(4, "邮箱不存在"),
    /**
     * 登陆失败
     */
    RE_LOGIN(401, "Token过期,重新登录"),
    /**
     * 重新发送请求
     */
    RESEND_REQUEST(402, "重新发送请求"),
    /**
     * 证书错误
     */
    BAD_LICENCE_TYPE(7001, "Bad licence type."),

    /**
     * 证书不存在
     */
    LICENCE_NOT_FOUND(7002, "Licence not found.");


    /**
     * 响应代号
     */
    private final int code;
    /**
     * 响应描述
     */
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 返回响应代号
     *
     * @return
     */
    public int getCode() {
        return code;
    }

    /**
     * 返回响应描述
     *
     * @return
     */
    public String getDesc() {
        return desc;
    }
}
