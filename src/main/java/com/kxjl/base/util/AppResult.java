package com.kxjl.base.util;

import lombok.Data;

@Data
public class AppResult {

    private String errCode;
    private String errMsg;
    private String rtime;
    private Object data;

    public AppResult(){ }

    public AppResult(String code, String message, Object data) {
        this.errCode = code;
        this.errMsg = message;
        this.data = data;
    }


    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

}
