package com.hqy.yunapiclientsdk.exception;

public enum ErrorCode {
    SUCCESS(0,"OK"),
    PARAMS_ERROR(40000,"请求参数错误"),
    NOT_LOGIN_ERROR(40100,"未登录"),
    NO_AUTH_ERROR(40100,"权限校验未通过"),
    FORBIDDEN_ERROR(40300,"禁止访问"),
    NOT_FOUND_ERROR(40400,"请求数据不存在"),
    MEHTOD_NOT_ALLOWED(40500,"请求方法不被允许"),
    SYSTEM_ERROR(50000,"系统内部异常"),
    OPERATE_ERROR(50001,"操作失败");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode(){

        return this.code;
    }
    public String getMessage(){
        return this.message;
    }
}
