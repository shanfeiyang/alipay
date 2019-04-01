package com.example.common.exception;

import com.example.common.result.ResultEnum;

/**
 * @Author: shanfeiyang
 * @Date: 2019/3/25 13:14
 * @Version 1.0
 */

public class PayException extends RuntimeException {
    private Integer code;

    public PayException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public PayException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
