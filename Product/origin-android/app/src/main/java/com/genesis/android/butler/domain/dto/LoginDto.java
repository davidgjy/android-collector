package com.genesis.android.butler.domain.dto;

import java.io.Serializable;

/**
 * Created by kg on 2017/12/11.
 */

public class LoginDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userName;
    private String password;
    private String verifyCode;
    private String pictureUuid;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getPictureUuid() {
        return pictureUuid;
    }

    public void setPictureUuid(String pictureUuid) {
        this.pictureUuid = pictureUuid;
    }

}
