package com.genesis.carrescue.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.genesis.carrescue.domain.pojo.CarInfo;
import com.genesis.carrescue.domain.pojo.LoginResult;

/**
 * Created by KG on 16/10/26.
 */
public class CarActivity extends SingleFragmentActivity {
    private static final String EXTRA_LOGIN_UESR_ID = "com.genesis.login_result_user_id";
    private static final String EXTRA_LOGIN_TOKEN = "com.genesis.login_result_token";
    private static final String EXTRA_CAR_INFO = "com.genesis.car_info";

    public static Intent newIntent(Context packageContext, CarInfo carInfo, LoginResult result) {
        Intent intent = new Intent(packageContext, CarActivity.class);
        intent.putExtra(EXTRA_LOGIN_UESR_ID, result.getUserId());
        intent.putExtra(EXTRA_LOGIN_TOKEN, result.getToken());

        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_CAR_INFO, carInfo);
        intent.putExtras(bundle);

        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int userId = getIntent().getIntExtra(EXTRA_LOGIN_UESR_ID, 0);
        String token = getIntent().getStringExtra(EXTRA_LOGIN_TOKEN);
        CarInfo car = (CarInfo) getIntent().getSerializableExtra(EXTRA_CAR_INFO);

        return CarFragment.newInstance(userId, token, car);
    }
}

