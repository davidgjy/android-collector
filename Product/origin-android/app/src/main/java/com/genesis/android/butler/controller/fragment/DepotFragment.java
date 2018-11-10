package com.genesis.android.butler.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.genesis.android.butler.R;
import com.genesis.android.butler.controller.activity.MenuListActivity;
import com.genesis.android.butler.controller.base.BarButtonType;
import com.genesis.android.butler.controller.base.TabBarFragment;
import com.genesis.android.butler.domain.pojo.CompanyInfo;

/**
 * Created by kg on 2017/12/21.
 */

public class DepotFragment extends TabBarFragment {

    private static final String TAG = "DepotFragment";

    private TextView mCompanyName;
    private TextView mUserPhone;
    private CompanyInfo mCompanyInfo;
    private View mCompanyThumbnail;

    private View listItemMenuInfo;

    public static DepotFragment newInstance(int userId, String token) {
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        args.putString(ARG_TOKEN, token);

        DepotFragment fragment = new DepotFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int userId = (int) getArguments().getSerializable(ARG_USER_ID);
        String token = (String) getArguments().getSerializable(ARG_TOKEN);
        Log.d(TAG, "DepotFragment User Id: " + userId);
        Log.d(TAG, "DepotFragment Token: " + token);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_depot, parent, false);

        super.setBarTitle("库存", false);

        initTabBar(v, BarButtonType.DEPOT);

        mCompanyName = (TextView) v.findViewById(R.id.txt_company_name);
        mUserPhone = (TextView) v.findViewById(R.id.txt_user_phone);
        mCompanyThumbnail = v.findViewById(R.id.company_thumbnail);
        mCompanyThumbnail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //transferToUserInfo();
            }
        });

        listItemMenuInfo = v.findViewById(R.id.list_item_menu_info);
        listItemMenuInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                transferToMenuManagement();
            }
        });

        return v;
    }

    private void transferToMenuManagement() {
        Log.d(TAG, "Transfer to menu management...");
        Intent i = MenuListActivity.newIntent(getActivity(), pResult);
        startActivity(i);
    }
}
