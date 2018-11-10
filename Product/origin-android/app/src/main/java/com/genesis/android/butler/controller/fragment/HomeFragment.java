package com.genesis.android.butler.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.android.butler.controller.base.BarButtonType;
import com.genesis.android.butler.controller.base.TabBarFragment;
import com.genesis.android.butler.R;
import com.genesis.android.butler.domain.response.LoginResponse;
import com.genesis.android.butler.ui.TabButton;

public class HomeFragment extends TabBarFragment {
    private static final String TAG = "HomeFragment";

    public static HomeFragment newInstance(int userId, String token) {
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        args.putString(ARG_TOKEN, token);

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int userId = (int) getArguments().getSerializable(ARG_USER_ID);
        String token = (String) getArguments().getSerializable(ARG_TOKEN);
        Log.d(TAG, "HomeFragment User Id: " + userId);
        Log.d(TAG, "HomeFragment Token: " + token);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, parent, false);

//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        activity.getSupportActionBar().setTitle("首页");

        super.setBarTitle("首页", false);

        super.initTabBar(v, BarButtonType.HOME);

        return v;
    }




}




