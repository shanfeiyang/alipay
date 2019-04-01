package com.example.common.exception;

import com.example.common.exception.PayException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class PayExceptionHandler {
    @ExceptionHandler(value = PayException.class)
    public ModelAndView handerlPayException(PayException ex) {
        System.out.println("出异常了:");
        ex.printStackTrace();
        ModelAndView mv = new ModelAndView();
        mv.addObject("exception", ex.getMessage());
        mv.setViewName("payException");
        return mv;
    }
}

