package com.genesis.carrescue.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.genesis.carrescue.R;
import com.genesis.carrescue.domain.pojo.CompanyInfo;
import com.genesis.carrescue.domain.pojo.LoginResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyInfoFragment extends Fragment {
    public static final String ARG_USER_ID = "CarRescue.USER_ID";
    public static final String ARG_TOKEN = "CarRescue.TOKEN";
    public static final String COMPANY_INFO = "CarRescue.COMPANY_INFO";

    private static final String TAG = "CompanyInfoFragment";

    private LoginResult mResult;
    private CompanyInfo mCompanyInfo;

    private EditText txtCompanyName;
    private  EditText txtCompanyAddress;
    private EditText txtBusinessLicense;
    private EditText txtContactPhone;
    private EditText txtSubContactPhone;
    private EditText txtCarNumber;
    private EditText txtAvgAnnualOutput;

    private GridView grid;
    private int[] imageIds = new int[]{
            R.drawable.business_sample1, R.drawable.business_sample1,R.drawable.grid_add
    };

    public static CompanyInfoFragment newInstance(int userId, String token, CompanyInfo info) {
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        args.putString(ARG_TOKEN, token);
        args.putSerializable(COMPANY_INFO, info);

        CompanyInfoFragment fragment = new CompanyInfoFragment();
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
            Log.d(TAG, "Get Passed Company Address: " + mCompanyInfo.getCompanyAddress());

            mResult = new LoginResult();
            mResult.setUserId(userId);
            mResult.setToken(token);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_company_info, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("公司信息");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtCompanyName = (EditText) v.findViewById(R.id.edt_company_name);
        txtCompanyName.setText(mCompanyInfo.getCompanyName());
        txtCompanyAddress = (EditText) v.findViewById(R.id.edt_company_address);
        txtCompanyAddress.setText(mCompanyInfo.getCompanyAddress());
        txtBusinessLicense = (EditText) v.findViewById(R.id.edt_business_license);
        txtBusinessLicense.setText(mCompanyInfo.getBusinessLisence());
        txtContactPhone = (EditText) v.findViewById(R.id.edt_contact_phone);
        txtContactPhone.setText(mCompanyInfo.getContactPhone());
        txtSubContactPhone = (EditText) v.findViewById(R.id.edt_sub_contact_phone);
        txtSubContactPhone.setText(mCompanyInfo.getSubContactPhone());
        txtCarNumber = (EditText) v.findViewById(R.id.edt_car_number);
        txtCarNumber.setText(Integer.toString(mCompanyInfo.getCarNumber()));
        txtAvgAnnualOutput = (EditText) v.findViewById(R.id.edt_avg_annual_output);
        txtAvgAnnualOutput.setText(Double.toString(mCompanyInfo.getAverageOutputValue()));

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
        grid = (GridView) v.findViewById(R.id.grid_business_license);
        grid.setAdapter(simpleAdapter);

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




