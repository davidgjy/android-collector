package com.genesis.carrescue.controllers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.genesis.carrescue.R;
import com.genesis.carrescue.domain.pojo.CompanyInfo;
import com.genesis.carrescue.domain.pojo.LoginResult;

public class UserInfoFragment extends Fragment {
    public static final String ARG_USER_ID = "CarRescue.USER_ID";
    public static final String ARG_TOKEN = "CarRescue.TOKEN";
    public static final String COMPANY_INFO = "CarRescue.COMPANY_INFO";

    private static final String TAG = "UserInfoFragment";

    private LoginResult mResult;
    private CompanyInfo mCompanyInfo;
    private TextView mCompanyName;
    private  TextView mAccountName;

    public static UserInfoFragment newInstance(int userId, String token, CompanyInfo info) {
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        args.putString(ARG_TOKEN, token);
        args.putSerializable(COMPANY_INFO, info);

        UserInfoFragment fragment = new UserInfoFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getArguments() != null) {
            int userId = (int) getArguments().getSerializable(ARG_USER_ID);
            String token = (String) getArguments().getSerializable(ARG_TOKEN);
            mCompanyInfo = (CompanyInfo) getArguments().getSerializable(COMPANY_INFO);

            Log.d(TAG, "Get Passed User Id: " + userId);
            Log.d(TAG, "Get Passed Token: " + token);
            Log.d(TAG, "Get Passed Company Name: " + mCompanyInfo.getCompanyName());
            Log.d(TAG, "Get Passed Cell Phone: " + mCompanyInfo.getUserPhone());

            mResult = new LoginResult();
            mResult.setUserId(userId);
            mResult.setToken(token);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_info, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("用户信息");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCompanyName = (TextView) v.findViewById(R.id.user_info_company_name);
        mCompanyName.setText(mCompanyInfo.getCompanyName());
        mAccountName = (TextView) v.findViewById(R.id.user_info_account_name);
        mAccountName.setText(mCompanyInfo.getUserPhone());

        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onBackPressed() {
        Log.d(TAG, "Back to account UI...");
        Intent i = AccountActivity.newIntent(getActivity(), mResult);
        startActivity(i);
    }
}




