package com.genesis.carrescue.domain.pojo;

import java.io.Serializable;

/**
 * Created by KG on 16/10/9.
 */
public class PostData<T> extends BasePost implements Serializable {
    private T params;

    public T getParams() {
        return params;
    }
    public void setParams(T params) {
        this.params = params;
    }
}



