package com.genesis.carrescue.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.genesis.carrescue.R;
import com.genesis.carrescue.domain.pojo.CompanyInfo;
import com.genesis.carrescue.domain.pojo.DriverInfo;
import com.genesis.carrescue.domain.pojo.LoginResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KG on 16/10/27.
 */
public class DriverFragment extends BaseFragment {
    private static final String TAG = "DriverFragment";
    public static final String DRIVER_INFO = "CarRescue.DRIVER_INFO";

    private DriverInfo mDriverInfo;

    private EditText txtDriverName;
    private EditText txtIdCardNo;
    private EditText txtUserPhone;

    private GridView gridIdCard;
    private GridView gridDrivingLicense;
    private int[] imageIds = new int[]{
            R.drawable.business_sample1, R.drawable.business_sample1,R.drawable.grid_add
    };

    public static DriverFragment newInstance(int userId, String token, DriverInfo driver) {
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        args.putString(ARG_TOKEN, token);
        args.putSerializable(DRIVER_INFO, driver);

        DriverFragment fragment = new DriverFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mDriverInfo = (DriverInfo) getArguments().getSerializable(DRIVER_INFO);

            Log.d(TAG, "Get Passed Driver Id: " + mDriverInfo.getId());
            Log.d(TAG, "Get Passed Driver Name: " + mDriverInfo.getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_driver, container, false);

        super.setBarTitle("司机信息");

        txtDriverName = (EditText) view.findViewById(R.id.edt_driver_name);
        txtDriverName.setText(mDriverInfo.getName());
        txtIdCardNo = (EditText) view.findViewById(R.id.edt_idcard_number);
        txtIdCardNo.setText(mDriverInfo.getIdentityCardNo());
        txtUserPhone = (EditText) view.findViewById(R.id.edt_user_phone);
        txtUserPhone.setText(mDriverInfo.getUserPhone());

        gridIdCard = (GridView) view.findViewById(R.id.grid_idcard);
        bindGrid(gridIdCard);
        gridDrivingLicense = (GridView) view.findViewById(R.id.grid_driving_license);
        bindGrid(gridDrivingLicense);

        return view;
    }

    private void bindGrid(GridView grid) {
        // setup grid ui
        List<Map<String, Object>> listItems =
                new ArrayList<Map<String, Object>>();
        for (int i = 0; i < imageIds.length; i++)
        {
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("image", imageIds[i]);
            listItems.add(listItem);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),
                listItems, R.layout.cell, new String[] { "image" },
                new int[] { R.id.cell_image });
        grid.setAdapter(simpleAdapter);
    }

    public void onBackPressed() {
        Log.d(TAG, "User Id: " + pResult.getUserId() + " back to account driver list...");
        Intent i = DriverListActivity.newIntent(getActivity(), pResult);
        startActivity(i);
    }
}
