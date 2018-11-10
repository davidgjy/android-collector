package com.genesis.carrescue.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.genesis.carrescue.domain.pojo.CompanyInfo;
import com.genesis.carrescue.domain.pojo.LoginResult;

public class CompanyInfoActivity extends SingleFragmentActivity {
    private static final String EXTRA_LOGIN_UESR_ID = "com.genesis.login_result_user_id";
    private static final String EXTRA_LOGIN_TOKEN = "com.genesis.login_result_token";
    private static final String EXTRA_COMPANY_INFO = "com.genesis.company_info";

    public static Intent newIntent(Context packageContext, CompanyInfo info, LoginResult result) {
        Intent intent = new Intent(packageContext, CompanyInfoActivity.class);
        intent.putExtra(EXTRA_LOGIN_UESR_ID, result.getUserId());
        intent.putExtra(EXTRA_LOGIN_TOKEN, result.getToken());

        //Intent intent = newSubIntent(packageContext, CompanyInfoActivity.class, result);
        
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

        return CompanyInfoFragment.newInstance(userId, token, info);
    }
}


