package com.genesis.carrescue.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.genesis.carrescue.domain.pojo.ContractInfo;
import com.genesis.carrescue.domain.pojo.LoginResult;

/**
 * Created by KG on 16/10/26.
 */
public class ContractActivity extends SingleFragmentActivity {
    private static final String EXTRA_LOGIN_UESR_ID = "com.genesis.login_result_user_id";
    private static final String EXTRA_LOGIN_TOKEN = "com.genesis.login_result_token";
    private static final String EXTRA_CONTRACT_INFO = "com.genesis.contract_info";

    public static Intent newIntent(Context packageContext, ContractInfo contractInfo, LoginResult result) {
        Intent intent = new Intent(packageContext, ContractActivity.class);
        intent.putExtra(EXTRA_LOGIN_UESR_ID, result.getUserId());
        intent.putExtra(EXTRA_LOGIN_TOKEN, result.getToken());

        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_CONTRACT_INFO, contractInfo);
        intent.putExtras(bundle);

        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int userId = getIntent().getIntExtra(EXTRA_LOGIN_UESR_ID, 0);
        String token = getIntent().getStringExtra(EXTRA_LOGIN_TOKEN);
        ContractInfo contract = (ContractInfo) getIntent().getSerializableExtra(EXTRA_CONTRACT_INFO);

        return ContractFragment.newInstance(userId, token, contract);
    }
}

