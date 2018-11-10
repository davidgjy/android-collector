package com.genesis.android.butler.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.genesis.android.butler.controller.base.SingleFragmentActivity;
import com.genesis.android.butler.controller.fragment.HomeFragment;
import com.genesis.android.butler.domain.constants.ConstParam;
import com.genesis.android.butler.domain.response.LoginResponse;

public class HomeActivity extends SingleFragmentActivity {
    public static Intent newIntent(Context packageContext, LoginResponse response) {
        Intent intent = new Intent(packageContext, HomeActivity.class);
        intent.putExtra(ConstParam.EXTRA_LOGIN_UESR_ID, response.getUserId());
        intent.putExtra(ConstParam.EXTRA_LOGIN_TOKEN, response.getToken());
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int userId = getIntent().getIntExtra(ConstParam.EXTRA_LOGIN_UESR_ID, 0);
        String token = getIntent().getStringExtra(ConstParam.EXTRA_LOGIN_TOKEN);

        return HomeFragment.newInstance(userId, token);
    }
}
