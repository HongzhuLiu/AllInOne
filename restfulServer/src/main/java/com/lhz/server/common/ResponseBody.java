package com.lhz.server.common;

/**
 * Created by LHZ on 2017/1/17.
 */
public class ResponseBody {
    private boolean result;
    private String message;
    private Object data;

    public ResponseBody() {
    }

    public ResponseBody(boolean result, String message, Object data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
