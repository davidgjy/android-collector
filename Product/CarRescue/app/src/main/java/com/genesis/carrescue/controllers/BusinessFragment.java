package com.genesis.carrescue.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.carrescue.R;
import com.genesis.carrescue.domain.pojo.LoginResult;
import com.genesis.ui.TabButton;

public class BusinessFragment extends Fragment {
    public static final String ARG_USER_ID = "CarRescue.USER_ID";
    public static final String ARG_TOKEN = "CarRescue.TOKEN";

    private static final String TAG = "BusinessFragment";

    private TabButton mHomeButton;
    private TabButton mOrderButton;
    private TabButton mBusinessButton;
    private TabButton mUserButton;

    private LoginResult mResult;

    public static BusinessFragment newInstance(int userId, String token) {
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        args.putString(ARG_TOKEN, token);

        BusinessFragment fragment = new BusinessFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int userId = (int) getArguments().getSerializable(ARG_USER_ID);
        String token = (String) getArguments().getSerializable(ARG_TOKEN);
        Log.d(TAG, "Get Passed User Id: " + userId);
        Log.d(TAG, "Get Passed Token: " + token);

        mResult = new LoginResult();
        mResult.setUserId(userId);
        mResult.setToken(token);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_business, parent, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("业务");

        initTabBar(v);

        return v;
    }

    private void initTabBar(View v) {
        mHomeButton = (TabButton)v.findViewById(R.id.tab_btn_home);
        mOrderButton = (TabButton)v.findViewById(R.id.tab_btn_order);
        mBusinessButton = (TabButton)v.findViewById(R.id.tab_btn_business);
        mUserButton = (TabButton)v.findViewById(R.id.tab_btn_user);

        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Home selected...");
                setBarButtonStatus(v, BarButtonType.HOME, mHomeButton, mOrderButton, mBusinessButton, mUserButton);
                Intent i = HomeActivity.newIntent(getActivity(), mResult);
                startActivity(i);
            }
        });

        mOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Order selected...");
                setBarButtonStatus(v, BarButtonType.ORDER, mHomeButton, mOrderButton, mBusinessButton, mUserButton);
                Intent i = OrderActivity.newIntent(getActivity(), mResult);
                startActivity(i);
            }
        });

        mBusinessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Business selected...");
                setBarButtonStatus(v, BarButtonType.BUSINESS, mHomeButton, mOrderButton, mBusinessButton, mUserButton);
                Intent i = BusinessActivity.newIntent(getActivity(), mResult);
                startActivity(i);
            }
        });

        mUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Account selected...");
                setBarButtonStatus(v, BarButtonType.ACCOUNT, mHomeButton, mOrderButton, mBusinessButton, mUserButton);
                Intent i = AccountActivity.newIntent(getActivity(), mResult);
                startActivity(i);
            }
        });

        setBarButtonStatus(v, BarButtonType.BUSINESS, mHomeButton, mOrderButton, mBusinessButton, mUserButton);
    }

    private void setBarButtonStatus(View v, BarButtonType type,
                                    TabButton btnHome, TabButton btnOrder,
                                    TabButton btnBusiness, TabButton btnUser) {
        switch (type) {
            case HOME:
                btnHome.setPressed(true);
                btnOrder.setPressed(false);
                btnBusiness.setPressed(false);
                btnUser.setPressed(false);
                break;
            case ORDER:
                btnHome.setPressed(false);
                btnOrder.setPressed(true);
                btnBusiness.setPressed(false);
                btnUser.setPressed(false);
                break;
            case BUSINESS:
                btnHome.setPressed(false);
                btnOrder.setPressed(false);
                btnBusiness.setPressed(true);
                btnUser.setPressed(false);
                break;
            case ACCOUNT:
                btnHome.setPressed(false);
                btnOrder.setPressed(false);
                btnUser.setPressed(true);
                btnBusiness.setPressed(false);
                break;
            default:
                btnHome.setPressed(true);
                btnOrder.setPressed(false);
                btnBusiness.setPressed(false);
                btnUser.setPressed(false);
                break;
        }
    }
}




