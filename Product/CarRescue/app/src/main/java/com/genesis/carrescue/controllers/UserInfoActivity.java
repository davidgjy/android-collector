package com.genesis.carrescue.controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.genesis.carrescue.R;
import com.genesis.carrescue.domain.pojo.CompanyInfo;
import com.genesis.carrescue.domain.pojo.LoginResult;

public class UserInfoActivity extends SingleFragmentActivity {
    private static final String EXTRA_LOGIN_UESR_ID = "com.genesis.login_result_user_id";
    private static final String EXTRA_LOGIN_TOKEN = "com.genesis.login_result_token";
    private static final String EXTRA_COMPANY_INFO = "com.genesis.company_info";

    public static Intent newIntent(Context packageContext, CompanyInfo info, LoginResult result) {
        Intent intent = new Intent(packageContext, UserInfoActivity.class);
        intent.putExtra(EXTRA_LOGIN_UESR_ID, result.getUserId());
        intent.putExtra(EXTRA_LOGIN_TOKEN, result.getToken());

        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_COMPANY_INFO, info);
        intent.putExtras(bundle);

        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int userId = getIntent().getIntExtra(EXTRA_LOGIN_UESR_ID, 0);
        String token = getIntent().getStringExtra(EXTRA_LOGIN_TOKEN);
        CompanyInfo info = (CompanyInfo) getIntent().getSerializableExtra(EXTRA_COMPANY_INFO);

        return UserInfoFragment.newInstance(userId, token, info);
    }
}


