package com.genesis.carrescue.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.genesis.carrescue.R;
import com.genesis.carrescue.domain.pojo.CarInfo;
import com.genesis.util.time.DateTimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KG on 16/10/27.
 */
public class CarFragment extends BaseFragment {
    private static final String TAG = "CarFragment";
    public static final String CAR_INFO = "CarRescue.CAR_INFO";

    private CarInfo mCarInfo;

    private EditText txtLicenseNumber;
    private EditText txtPurchaseTime;
    private EditText txtInsuranceStartTime;
    private EditText txtInsuranceTerminalTime;
    private EditText txtMotTestTime;

    private GridView gridCarInsurance;
    private int[] imageIds = new int[]{
            R.drawable.business_sample1, R.drawable.business_sample1,R.drawable.grid_add
    };

    public static CarFragment newInstance(int userId, String token, CarInfo car) {
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        args.putString(ARG_TOKEN, token);
        args.putSerializable(CAR_INFO, car);

        CarFragment fragment = new CarFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mCarInfo = (CarInfo) getArguments().getSerializable(CAR_INFO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car, container, false);

        super.setBarTitle("合同信息");

        txtLicenseNumber = (EditText) view.findViewById(R.id.edt_car_license_number);
        txtLicenseNumber.setText(mCarInfo.getLicensePlateNumber());
        txtPurchaseTime = (EditText) view.findViewById(R.id.edt_car_purchase_time);
        txtPurchaseTime.setText(DateTimeUtil.getDateTimeByFormat(mCarInfo.getPurchaseTime(), "yyyy-MM-dd hh:mm:ss"));
        txtInsuranceStartTime = (EditText) view.findViewById(R.id.edt_insurance_start_time);
        txtInsuranceStartTime.setText(DateTimeUtil.getDateTimeByFormat(mCarInfo.getCreateTime(), "yyyy-MM-dd hh:mm:ss"));
        txtInsuranceTerminalTime = (EditText) view.findViewById(R.id.edt_insurance_terminal_time);
        txtInsuranceTerminalTime.setText(DateTimeUtil.getDateTimeByFormat(mCarInfo.getInsuranceTerminalTime(), "yyyy-MM-dd hh:mm:ss"));
        txtMotTestTime = (EditText) view.findViewById(R.id.edt_mot_test_time);
        txtMotTestTime.setText(DateTimeUtil.getDateTimeByFormat(mCarInfo.getMOTTestTime(), "yyyy-MM-dd hh:mm:ss"));

        gridCarInsurance = (GridView) view.findViewById(R.id.grid_car_insurance);
        bindGrid(gridCarInsurance);

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
        Log.d(TAG, "User Id: " + pResult.getUserId() + " back to car list...");
        Intent i = CarListActivity.newIntent(getActivity(), pResult);
        startActivity(i);
    }
}
