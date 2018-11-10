package com.genesis.android.butler.controller.base;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.genesis.android.butler.R;
import com.genesis.android.butler.controller.activity.DepotActivity;
import com.genesis.android.butler.controller.activity.HomeActivity;
import com.genesis.android.butler.controller.activity.MeActivity;
import com.genesis.android.butler.ui.TabButton;

/**
 * Created by KG on 16/10/17.
 */
public class TabBarFragment extends BaseFragment {
    private static final String TAG = "TabBarFragment";

    private TabButton mHomeButton;
    private TabButton mDepotButton;
    private TabButton mFinanceButton;
    private TabButton mUserButton;

    protected void initTabBar(View v, BarButtonType activeBar) {
        mHomeButton = (TabButton)v.findViewById(R.id.tab_btn_home);
        mDepotButton = (TabButton)v.findViewById(R.id.tab_btn_depot);
        mFinanceButton = (TabButton)v.findViewById(R.id.tab_btn_business);
        mUserButton = (TabButton)v.findViewById(R.id.tab_btn_user);

        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Home selected...");
                setBarButtonStatus(v, BarButtonType.HOME, mHomeButton, mDepotButton, mFinanceButton, mUserButton);
                Intent i = HomeActivity.newIntent(getActivity(), pResult);
                startActivity(i);
            }
        });

        mDepotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Depot selected...");
                setBarButtonStatus(v, BarButtonType.DEPOT, mHomeButton, mDepotButton, mFinanceButton, mUserButton);
                Intent i = DepotActivity.newIntent(getActivity(), pResult);
                startActivity(i);
            }
        });

        mFinanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Finance selected...");
                setBarButtonStatus(v, BarButtonType.FINANCE, mHomeButton, mDepotButton, mFinanceButton, mUserButton);
//                Intent i = BusinessActivity.newIntent(getActivity(), pResult);
//                startActivity(i);
            }
        });

        mUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Me selected...");
                setBarButtonStatus(v, BarButtonType.ME, mHomeButton, mDepotButton, mFinanceButton, mUserButton);
                Intent i = MeActivity.newIntent(getActivity(), pResult);
                startActivity(i);
            }
        });

        setBarButtonStatus(v, activeBar, mHomeButton, mDepotButton, mFinanceButton, mUserButton);
    }

    protected void setBarButtonStatus(View v, BarButtonType type,
                                    TabButton btnHome, TabButton btnDepot,
                                    TabButton btnBusiness, TabButton btnUser) {
        switch (type) {
            case HOME:
                btnHome.setPressed(true);
                btnDepot.setPressed(false);
                btnBusiness.setPressed(false);
                btnUser.setPressed(false);
                break;
            case DEPOT:
                btnHome.setPressed(false);
                btnDepot.setPressed(true);
                btnBusiness.setPressed(false);
                btnUser.setPressed(false);
                break;
            case FINANCE:
                btnHome.setPressed(false);
                btnDepot.setPressed(false);
                btnBusiness.setPressed(true);
                btnUser.setPressed(false);
                break;
            case ME:
                btnHome.setPressed(false);
                btnDepot.setPressed(false);
                btnUser.setPressed(true);
                btnBusiness.setPressed(false);
                break;
            default:
                btnHome.setPressed(true);
                btnDepot.setPressed(false);
                btnBusiness.setPressed(false);
                btnUser.setPressed(false);
                break;
        }
    }

    public void onBackPressed() { }
}



