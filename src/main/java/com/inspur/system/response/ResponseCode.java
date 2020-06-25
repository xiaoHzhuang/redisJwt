package com.inspur.system.response;

public enum ResponseCode {
    ERROR(0, "ERROR"),
    SUCCESS(1, "SUCCESS"),
    NEED_LOGIN(10, "NEED_LOGIN"),
    BAD_LICENCE_TYPE(7001, "Bad licence type."),
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

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
