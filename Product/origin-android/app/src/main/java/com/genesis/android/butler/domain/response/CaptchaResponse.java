package com.genesis.android.butler.domain.response;

/**
 * Created by kg on 2017/12/11.
 */

public class CaptchaResponse {
    private String code;
    private String msg;
    private String verifyBase64;
    private String mobileBase64;
    private String pictureUuid;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getVerifyBase64() {
        return verifyBase64;
    }

    public void setVerifyBase64(String verifyBase64) {
        this.verifyBase64 = verifyBase64;
    }

    public String getMobileBase64() {
        return mobileBase64;
    }

    public void setMobileBase64(String mobileBase64) {
        this.mobileBase64 = mobileBase64;
    }

    public String getPictureUuid() {
        return pictureUuid;
    }

    public void setPictureUuid(String pictureUuid) {
        this.pictureUuid = pictureUuid;
    }
}

