package com.genesis.carrescue.controllers;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.genesis.carrescue.R;
import com.genesis.carrescue.domain.pojo.LoginResult;
import com.genesis.ui.TabButton;

/**
 * Created by KG on 16/10/17.
 */
public class TabBarFragment extends Fragment {
    private static final String TAG = "TabBarFragment";

    private TabButton pHomeButton;
    private TabButton pOrderButton;

    public void initTabBar(final Activity parentActivity, View parentView, final LoginResult result) {
        pHomeButton = (TabButton)parentView.findViewById(R.id.tab_btn_home);
        pOrderButton = (TabButton)parentView.findViewById(R.id.tab_btn_order);

        pHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Home selected...");
                setBarButtonStatus(v, BarButtonType.HOME, pHomeButton, pOrderButton);
                Intent i = HomeActivity.newIntent(parentActivity, result);
                startActivity(i);
            }
        });

        pOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Order selected...");
                setBarButtonStatus(v, BarButtonType.ORDER, pHomeButton, pOrderButton);
                Intent i = AccountActivity.newIntent(parentActivity, result);
                startActivity(i);
            }
        });

        setBarButtonStatus(parentView, BarButtonType.HOME, pHomeButton, pOrderButton);
    }

    private void setBarButtonStatus(View v, BarButtonType type,
                                    TabButton btnHome, TabButton btnOrder) {
        switch (type) {
            case HOME:
                btnHome.setPressed(true);
                btnOrder.setPressed(false);
                break;
            case ORDER:
                btnHome.setPressed(false);
                btnOrder.setPressed(true);
                break;
            default:
                btnHome.setPressed(true);
                btnOrder.setPressed(false);
                break;
        }
    }
}

