package com.example.common.exception;


import com.example.common.result.ResultCode;

/**
 * 服务（业务）异常如“ 账号或密码错误 ”，该异常只做INFO级别的日志记录 @see WebMvcConfigurer
 */
public class ServiceException extends RuntimeException {
    private int code;
    private String message; //异常信息
    private String extraMessage;    //异常扩展信息

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public void setExtraMessage(String extraMessage) {
        this.extraMessage = extraMessage;
    }

    /* public void setExtraMessage(String extraMessage) {
            this.extraMessage = extraMessage;
        }
    */
    public ServiceException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code=resultCode.getCode();
    }

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
        this.extraMessage = message;
    }

    public ServiceException(ResultCode resultCode, String message, Throwable cause){
        super(resultCode.getMessage(),cause);
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.extraMessage = message;


    }

    public ServiceException(ResultCode resultCode, Throwable cause){
        super(resultCode.getMessage(),cause);
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public ServiceException(String message, Throwable cause) {

    }

    public ServiceException(int code, String message, Throwable cause) {
        super(message,cause);
        this.code = code;
        this.extraMessage = message;
    }

    public ServiceException(int code,String message,String extraMessage,Throwable cause){
        super(message,cause);
        this.code=code;
        this.extraMessage=extraMessage;
    }



    public ServiceException(ResultCode resultCode, String extraMessage){
        this(resultCode.getCode(),resultCode.getMessage(),extraMessage,null);
    }

    public ServiceException(String extraMessage){
        this(ResultCode.INVALID_PARAM,extraMessage);
    }


    public int getCode() {
        return code;
    }

    public String getExtraMessage() {
        return extraMessage;
    }

}
