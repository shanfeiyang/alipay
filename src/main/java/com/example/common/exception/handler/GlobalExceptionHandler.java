package com.example.common.exception.handler;


import com.example.common.exception.ServiceException;
import com.example.common.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 作用：异常类拦截器
 * @author: xingwz@egivesoft.com
 *
 * @date: 2018/5/7
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    class ErrorInfo{
        private String code;
        private String message;
        private String messageExt;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMessageExt() {
            return messageExt;
        }

        public void setMessageExt(String messageExt) {
            this.messageExt = messageExt;
        }
    }

    /**
     * 作用：拦截服务接口异常
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    public Object handlerServiceException(ServiceException e){
        System.out.println("============= 接口出异常了！===========");
        e.printStackTrace();
        Result result = new Result();
        String message = e.getMessage();
        String extraMessage = e.getExtraMessage();
        if (null == message || "".equals(message)) {
            message = extraMessage;
        }
        if (null == extraMessage || "".equals(extraMessage)) {
            extraMessage = message;
        }
        result.setCode(e.getCode());
        result.setMessage(message);
        result.setMessageExt(extraMessage);
        return result;
    }
}
