package com.genesis.android.butler.controller.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.genesis.android.butler.domain.response.LoginResponse;

/**
 * Created by KG on 16/10/27.
 */
public abstract class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    public static final String ARG_USER_ID = "Butler.USER_ID";
    public static final String ARG_TOKEN = "Butler.TOKEN";

    protected LoginResponse pResult;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setBarTitle(String title, boolean isBackBtnDisplay) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle(title);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(isBackBtnDisplay);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        int userId = (int) getArguments().getSerializable(ARG_USER_ID);
        String token = (String) getArguments().getSerializable(ARG_TOKEN);
        Log.d(TAG, "Get Passed User Id: " + userId);
        Log.d(TAG, "Get Passed Token: " + token);

        pResult = new LoginResponse();
        pResult.setUserId(userId);
        pResult.setToken(token);
    }

    public abstract void onBackPressed();
}

