package com.genesis.carrescue.controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.genesis.carrescue.domain.pojo.LoginResult;

public class AccountActivity extends SingleFragmentActivity {
    private static final String EXTRA_LOGIN_UESR_ID = "com.genesis.login_result_user_id";
    private static final String EXTRA_LOGIN_TOKEN = "com.genesis.login_result_token";

    public static Intent newIntent(Context packageContext, LoginResult result) {
        Intent intent = new Intent(packageContext, AccountActivity.class);
        intent.putExtra(EXTRA_LOGIN_UESR_ID, result.getUserId());
        intent.putExtra(EXTRA_LOGIN_TOKEN, result.getToken());
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int userId = getIntent().getIntExtra(EXTRA_LOGIN_UESR_ID, 0);
        String token = getIntent().getStringExtra(EXTRA_LOGIN_TOKEN);

        return AccountFragment.newInstance(userId, token);
    }
}
