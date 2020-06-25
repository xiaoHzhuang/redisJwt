package com.inspur.system.exception;

public class BaseException extends RuntimeException {
    private IResponseEnum responseEnum;
    private String message;
    private Object[] args;
    private Throwable cause;
    private Assert assertA;

    public BaseException(IResponseEnum response, Object[] args, String message) {
        this.args = args;
        this.message = message;
        this.responseEnum = response;
    }

    public BaseException(IResponseEnum response, Object[] args, String message, Throwable cause) {
        this.responseEnum = response;
        this.args = args;
        this.message = message;
        this.cause = cause;
    }

    public IResponseEnum getResponseEnum() {
        return responseEnum;
    }

    public void setResponseEnum(IResponseEnum responseEnum) {
        this.responseEnum = responseEnum;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public Assert getAssertA() {
        return assertA;
    }

    public void setAssertA(Assert assertA) {
        this.assertA = assertA;
    }
}
