package com.genesis.carrescue.domain.pojo;

/**
 * Created by KG on 16/10/12.
 */
public class LoginResult {
    private String token;
    private int userId;

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
