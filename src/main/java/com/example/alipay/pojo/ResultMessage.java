package com.example.alipay.pojo;

import java.io.Serializable;
import java.util.Map;

public class ResultMessage implements Serializable {
    private Boolean success;
    private String message;
    private Map map;

    @Override
    public String toString() {
        return "ResultMessage{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", map=" + map +
                '}';
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
