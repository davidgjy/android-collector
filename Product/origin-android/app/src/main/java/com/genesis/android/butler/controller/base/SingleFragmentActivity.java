package com.genesis.android.butler.controller.base;

/**
 * Created by KG on 16/3/15.
 */
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.genesis.android.butler.R;
import com.genesis.android.butler.domain.response.LoginResponse;

public abstract class SingleFragmentActivity extends AppCompatActivity {

//    public static final String EXTRA_LOGIN_UESR_ID = "com.genesis.login_result_user_id";
//    public static final String EXTRA_LOGIN_TOKEN = "com.genesis.login_result_token";

    protected abstract Fragment createFragment();

    /*
    public static Intent newSubIntent(Context packageContext, Class<?> cls, LoginResult result) {
        Intent intent = new Intent(packageContext, cls);
        intent.putExtra(EXTRA_LOGIN_UESR_ID, result.getUserId());
        intent.putExtra(EXTRA_LOGIN_TOKEN, result.getToken());
        return intent;
    }
    */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = createFragment();
            manager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }
}
