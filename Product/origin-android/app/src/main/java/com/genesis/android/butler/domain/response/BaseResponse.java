package com.genesis.android.butler.domain.response;

import java.io.Serializable;

/**
 * Created by kg on 2017/12/21.
 */

public class BaseResponse<T> implements Serializable {
    protected String code;
    protected String message;
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
