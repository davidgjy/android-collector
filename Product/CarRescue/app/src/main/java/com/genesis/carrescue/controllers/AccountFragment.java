package com.genesis.carrescue.controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.genesis.carrescue.R;
import com.genesis.carrescue.domain.pojo.BasePost;
import com.genesis.carrescue.domain.pojo.CompanyInfo;
import com.genesis.carrescue.domain.pojo.Constants;
import com.genesis.carrescue.domain.pojo.LoginResult;
import com.genesis.carrescue.domain.pojo.ResponseData;
import com.genesis.ui.TabButton;
import com.genesis.util.network.RestTask;
import com.genesis.util.network.RestUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class AccountFragment extends Fragment
        implements RestTask.ProgressCallback, RestTask.ResponseCallback {
    public static final String ARG_USER_ID = "CarRescue.USER_ID";
    public static final String ARG_TOKEN = "CarRescue.TOKEN";

    private static final String TAG = "AccountFragment";

    private TextView mCompanyName;
    private TextView mUserPhone;
    private CompanyInfo mCompanyInfo;
    private View mCompanyThumbnail;

    private ProgressDialog mProgress;

    private TabButton mHomeButton;
    private TabButton mOrderButton;
    private TabButton mBusinessButton;
    private TabButton mUserButton;

    private View listItemCompanyInfo;
    private View listItemDriverManagement;
    private View listItemContractManagement;
    private View listItemCarManagement;

    private LoginResult mResult;

    public static AccountFragment newInstance(int userId, String token) {
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        args.putString(ARG_TOKEN, token);

        AccountFragment fragment = new AccountFragment();
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
        View v = inflater.inflate(R.layout.fragment_account, parent, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("账户");

        initTabBar(v);

        mCompanyName = (TextView) v.findViewById(R.id.txt_company_name);
        mUserPhone = (TextView) v.findViewById(R.id.txt_user_phone);
        mCompanyThumbnail = v.findViewById(R.id.company_thumbnail);
        mCompanyThumbnail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                transferToUserInfo();
            }
        });

        listItemCompanyInfo = v.findViewById(R.id.list_item_company_info);
        listItemCompanyInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                transferToCompanyInfo();
            }
        });

        listItemDriverManagement = v.findViewById(R.id.list_item_driver_management);
        listItemDriverManagement.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                transferToDriverManagement();
            }
        });

        listItemContractManagement = v.findViewById(R.id.list_item_contract_management);
        listItemContractManagement.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                transferToContractManagement();
            }
        });

        listItemCarManagement = v.findViewById(R.id.list_item_car_management);
        listItemCarManagement.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                transferToCarManagement();
            }
        });

        getCompanyInfo();

        return v;
    }

    private void transferToUserInfo() {
        Log.d(TAG, "Transfer to account info...");
        Intent i = UserInfoActivity.newIntent(getActivity(), mCompanyInfo, mResult);
        startActivity(i);
    }

    private void transferToCompanyInfo() {
        Log.d(TAG, "Transfer to company info...");
        Intent i = CompanyInfoActivity.newIntent(getActivity(), mCompanyInfo, mResult);
        startActivity(i);
    }

    private void transferToDriverManagement() {
        Log.d(TAG, "Transfer to driver management...");
        Intent i = DriverListActivity.newIntent(getActivity(), mResult);
        startActivity(i);
    }

    private void transferToContractManagement() {
        Log.d(TAG, "Transfer to contract management...");
        Intent i = ContractListActivity.newIntent(getActivity(), mResult);
        startActivity(i);
    }

    private void transferToCarManagement() {
        Log.d(TAG, "Transfer to car management...");
        Intent i = CarListActivity.newIntent(getActivity(), mResult);
        startActivity(i);
    }

    private void getCompanyInfo() {
        Log.d(TAG, "Accquiring company info...");
        try {
            BasePost postData = new BasePost();
            postData.setMethod("rescue.getDetail");
            postData.setId(1);

            Gson gson = new Gson();
            String jsonData = "[" + gson.toJson(postData) + "]";

            Log.d(TAG, "请求数据: " + jsonData);

            RestTask postTask = RestUtil.obtainAuthPostTask(Constants.POST_URI, jsonData, Integer.toString(mResult.getUserId()), mResult.getToken());
            postTask.setResponseCallback(this);
            //postTask.setProgressCallback(this);

            postTask.execute();

            //Display progress to the user
            mProgress = ProgressDialog.show(getActivity(), "搜索", "信息请求中, 请稍等...", true);
        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e.getMessage());
        }
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

        setBarButtonStatus(v, BarButtonType.ACCOUNT, mHomeButton, mOrderButton, mBusinessButton, mUserButton);
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

    @Override
    public void onProgressUpdate(int progress) {
        if (progress >= 0) {
            if (mProgress != null) {
                mProgress.dismiss();
                mProgress = null;
            }
            //Update user of progress
            Log.d(TAG, String.format("Download Progress: %d%%", progress));
        }
    }

    @Override
    public void onRequestSuccess(String response) {
        //Clear progress indicator
        if(mProgress != null) {
            mProgress.dismiss();
        }

        //Process the response data (here we just display it)
        Log.d(TAG, "返回结果:" + response);

        Gson gson = new Gson();
        List<ResponseData<CompanyInfo>> responseData = gson.fromJson(response, new TypeToken<List<ResponseData<CompanyInfo>>>() {
        }.getType());
        CompanyInfo info = responseData.get(0).getResult();
        mCompanyInfo = info;
        Log.d(TAG, "User Phone:" + info.getUserPhone());
        Log.d(TAG, "Company Name:" + info.getCompanyName());
        mCompanyName.setText(info.getCompanyName());
        mUserPhone.setText(info.getUserPhone());
    }

    @Override
    public void onRequestError(Exception error) {
        //Clear progress indicator
        if(mProgress != null) {
            mProgress.dismiss();
        }
        //Process the response data (here we just display it)
        Log.d(TAG, "An Error Occurred: " + error.getMessage());
    }
}




