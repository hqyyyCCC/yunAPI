package com.hqy.yunapiclientsdk.exception;

public class ErrorApiException extends  Exception{
    private int code ;

    public ErrorApiException(int code) {
        this.code = code;
    }

    public ErrorApiException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ErrorApiException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public ErrorApiException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public ErrorApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
    public ErrorApiException(ErrorCode errorCode,String message){
        super(message);
        this.code = errorCode.getCode();
    }
    public ErrorApiException(int errorCode,String message){
        super(message);
        this.code = errorCode;
    }
}
