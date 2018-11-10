package com.genesis.carrescue.domain.pojo;

import java.io.Serializable;

/**
 * Created by KG on 16/10/10.
 */
public class RegisterData implements Serializable {
    private String userPhone;
    private String password;
    private String CAPTCHA;

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

    public String getCAPTCHA() { return CAPTCHA; }
    public void setCAPTCHA(String CAPTCHA) { this.CAPTCHA = CAPTCHA; }
}
