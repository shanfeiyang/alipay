package com.example.common.exception;


import com.example.common.result.ResultCode;

/**
 * 作用：设备异常类
 * @author: xingwz@egivesoft.com
 *
 * @date: 2018/5/7
 */
public class MachineException extends RuntimeException {
    private int code;
    private String message; //异常信息
    private String extraMessage;    //异常扩展信息

    public int getCode() {
        return code;
    }

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

    public String getExtraMessage() {
        return extraMessage;
    }

    public void setExtraMessage(String extraMessage) {
        this.extraMessage = extraMessage;
    }

    public MachineException(int errorCode) {
        this.code = errorCode;
    }

    public MachineException(String errorMsg, int errorCode, Throwable cause) {
        super(errorMsg, cause);
        this.code = errorCode;
        this.message = errorMsg;
    }

    public MachineException(int errorCode, String errorMsg){
        super(errorMsg);
        this.code = errorCode;
        this.message = errorMsg;
    }

    public MachineException(ResultCode resultCode, String errorMsgExt){
        super(errorMsgExt);
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.extraMessage = errorMsgExt;
    }

    public MachineException(ResultCode resultCode, String errorMsgExt, Throwable cause){
        super(errorMsgExt, cause);
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.extraMessage = errorMsgExt;
    }


}
