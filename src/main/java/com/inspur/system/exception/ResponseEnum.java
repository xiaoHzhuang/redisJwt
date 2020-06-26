package com.inspur.system.exception;

import com.inspur.system.response.ResponseCode;

public enum ResponseEnum implements BusinessExceptionAssert {
    /**
     * Bad licence type
     */
    BAD_LICENCE_TYPE(ResponseCode.BAD_LICENCE_TYPE.getCode(), ResponseCode.BAD_LICENCE_TYPE.getDesc()),
    /**
     * Licence not found
     */
    LICENCE_NOT_FOUND(ResponseCode.LICENCE_NOT_FOUND.getCode(), ResponseCode.LICENCE_NOT_FOUND.getDesc()),

    USER_EXIST(ResponseCode.USER_EXIST.getCode(), ResponseCode.USER_EXIST.getDesc());

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
