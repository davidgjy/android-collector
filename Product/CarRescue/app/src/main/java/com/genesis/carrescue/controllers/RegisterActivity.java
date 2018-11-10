package com.genesis.carrescue.controllers;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.genesis.carrescue.R;
import com.genesis.carrescue.domain.pojo.Constants;
import com.genesis.carrescue.domain.pojo.RegisterData;
import com.genesis.carrescue.domain.pojo.PostData;
import com.genesis.util.network.RestTask;
import com.genesis.util.network.RestUtil;
import com.google.gson.Gson;

public class RegisterActivity extends AppCompatActivity
        implements RestTask.ProgressCallback, RestTask.ResponseCallback {

    private static final String TAG = "RegisterActivity";
    private TextView mCellPhone;
    private TextView mPassword;
    private Button mRegisterButton;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("新用户注册");

        mCellPhone = (TextView) findViewById(R.id.register_cell_phone);
        mPassword = (TextView) findViewById(R.id.register_password);

        mRegisterButton = (Button) findViewById(R.id.btn_register);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        Log.d(TAG, "Register...");
        //Create the requests
        try{
            RegisterData data = new RegisterData();
            data.setUserPhone(mCellPhone.getText().toString());
            data.setPassword(mPassword.getText().toString());
            data.setCAPTCHA("");

            PostData<RegisterData> postData = new PostData<>();
            postData.setMethod("user.create");
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
            mProgress = ProgressDialog.show(this, "Searching", "Waiting For Results...", true);
        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e.getMessage());
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
