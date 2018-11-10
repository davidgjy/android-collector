package com.genesis.android.butler.domain.dto;

import java.io.Serializable;

/**
 * Created by kg on 2017/12/11.
 */

public class CaptchaDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String pictureToken = "";

    public String getPictureToken() {
        return pictureToken;
    }

    public void setPictureToken(String pictureToken) {
        this.pictureToken = pictureToken;
    }
}
