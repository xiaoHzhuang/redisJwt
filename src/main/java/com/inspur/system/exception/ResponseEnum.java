package com.inspur.system.exception;

import com.inspur.system.response.ResponseCode;

public enum ResponseEnum implements BusinessExceptionAssert {

    BAD_LICENCE_TYPE(ResponseCode.BAD_LICENCE_TYPE.getCode(), ResponseCode.BAD_LICENCE_TYPE.getDesc()),

    LICENCE_NOT_FOUND(ResponseCode.LICENCE_NOT_FOUND.getCode(), ResponseCode.LICENCE_NOT_FOUND.getDesc()),
    /**
     * 用户已被注册
     */
    USER_EXIST(ResponseCode.USER_EXIST.getCode(), ResponseCode.USER_EXIST.getDesc()),
    /**
     * 用户邮箱已被注册
     */
    USER_MAIL_EXIST(ResponseCode.USER_MAIL_EXIST.getCode(), ResponseCode.USER_MAIL_EXIST.getDesc()),

    /**
     * 用户邮箱不存在
     */
    USER_MAIL_NOT_EXIST(ResponseCode.USER_MAIL_NOT_EXIST.getCode(),ResponseCode.USER_MAIL_NOT_EXIST.getDesc());

    ResponseEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 返回码
     */
    private int code;
    /**
     * 返回消息
     */
    private String message;

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
