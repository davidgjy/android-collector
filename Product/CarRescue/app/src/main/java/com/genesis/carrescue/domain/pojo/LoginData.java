package com.genesis.carrescue.domain.pojo;

import java.io.Serializable;

/**
 * Created by KG on 16/10/9.
 */
public class LoginData implements Serializable {
    private String userPhone;
    private String password;

    public String getUserPhone() {
        return userPhone;
    }
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
