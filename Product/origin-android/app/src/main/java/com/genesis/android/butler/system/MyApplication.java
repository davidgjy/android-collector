package com.genesis.android.butler.system;

import android.app.Application;

/**
 * Created by kg on 2017/12/11.
 */

public class MyApplication extends Application {
    private String loginStatus;
    private String token;
    private String captchUUID;

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCaptchUUID() {
        return captchUUID;
    }

    public void setCaptchUUID(String captchUUID) {
        this.captchUUID = captchUUID;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }
}
