package com.genesis.android.butler.domain.dto;

import java.io.Serializable;

/**
 * Created by kg on 2017/12/21.
 */

public class MenuDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String token;

    private Integer pageNum;

    private Integer pageSize;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
