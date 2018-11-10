package com.genesis.carrescue.controllers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.genesis.carrescue.R;
import com.genesis.carrescue.domain.pojo.Constants;
import com.genesis.carrescue.domain.pojo.LoginData;
import com.genesis.carrescue.domain.pojo.LoginResult;
import com.genesis.carrescue.domain.pojo.PostData;
import com.genesis.carrescue.domain.pojo.ResponseData;
import com.genesis.util.data.GsonUtil;
import com.genesis.util.network.RestTask;
import com.genesis.util.network.RestUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class LoginActivity extends AppCompatActivity
        implements RestTask.ProgressCallback, RestTask.ResponseCallback {

    private static final String TAG = "LoginActivity";
    private Button mLoginButton;
    private TextView mCellPhone;
    private TextView mPassword;
    private TextView mRegisterLink;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginButton = (Button) findViewById(R.id.btn_login);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        mCellPhone = (TextView) findViewById(R.id.login_cell_phone);
        mPassword = (TextView) findViewById(R.id.login_password);

        mRegisterLink = (TextView) findViewById(R.id.link_register);
        mRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferToRegister();
            }
        });
    }

    private void login() {
        Log.d(TAG, "Login...");
        //Create the requests
        try{
            LoginData data = new LoginData();
            data.setUserPhone(mCellPhone.getText().toString());
            data.setPassword(mPassword.getText().toString());

            PostData<LoginData> postData = new PostData<>();
            postData.setMethod("user.login");
            postData.setId(1);
            postData.setParams(data);

            Gson gson = new Gson();
            String jsonData = "[" + gson.toJson(postData) + "]";

            Log.d(TAG, "请求数据: " + jsonData);

            RestTask postTask = RestUtil.obtainFormPostTask(Constants.POST_URI, jsonData);
            postTask.setResponseCallback(this);
            //postTask.setProgressCallback(this);

            postTask.execute();

            //Display progress to the user
            mProgress = ProgressDialog.show(this, "搜索", "信息请求中, 请稍等...", true);
        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e.getMessage());
        }
    }

    private void transferToRegister() {
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    private void transferToHomeUI(LoginResult result) {
        Intent i = AccountActivity.newIntent(LoginActivity.this, result);
        startActivity(i);
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
        List<ResponseData<LoginResult>> responseData = gson.fromJson(response, new TypeToken<List<ResponseData<LoginResult>>>() {
        }.getType());
        LoginResult result = responseData.get(0).getResult();
        int userId = result.getUserId();
        String token = result.getToken();
        Log.d(TAG, "User Id:" + userId);
        Log.d(TAG, "Token:" + token);
        if (userId > 0) {
            transferToHomeUI(result);
        }
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

