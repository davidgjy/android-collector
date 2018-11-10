package com.genesis.android.butler.controller.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.genesis.android.butler.R;
import com.genesis.android.butler.domain.dto.CaptchaDto;
import com.genesis.android.butler.domain.dto.LoginDto;
import com.genesis.android.butler.domain.constants.ConstUrl;
import com.genesis.android.butler.domain.response.CaptchaResponse;
import com.genesis.android.butler.domain.response.LoginResponse;
import com.genesis.android.butler.system.MyApplication;
import com.genesis.android.butler.util.network.RestTask;
import com.genesis.android.butler.util.network.RestUtil;
import com.genesis.android.butler.util.pic.Base64Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LoginActivity extends AppCompatActivity
        implements RestTask.ProgressCallback, RestTask.ResponseCallback {

    private static final String TAG = "LoginActivity";
    private Button mLoginButton;
    private TextView mCellPhone;
    private TextView mPassword;
    private TextView mCaptchaText;
    private TextView mRegisterLink;
    private ProgressDialog mProgress;

    private Bitmap bmImg;
    private ImageView mImgCaptcha;

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
        mCaptchaText = (TextView) findViewById(R.id.edt_captcha);

        mRegisterLink = (TextView) findViewById(R.id.link_register);
        mRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferToRegister();
            }
        });

        loadCaptcha();
    }

    private void loadCaptcha() {
        Log.d(TAG, "Load captcha...");
        String url = ConstUrl.BASE_URL + "getCaptcha";

        try{
            CaptchaDto dto = new CaptchaDto();
            dto.setPictureToken("");
            Gson gson = new Gson();
            String jsonData = gson.toJson(dto);

            Log.d(TAG, "请求数据: " + jsonData);
            RestTask postTask = RestUtil.obtainFormPostTask(url, jsonData);
            postTask.setResponseCallback(this);
            //postTask.setProgressCallback(this);

            postTask.execute();

            //Display progress to the user
            mProgress = ProgressDialog.show(this, "搜索", "信息请求中, 请稍等...", true);
        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e.getMessage());
        }
    }

    private void login() {
        Log.d(TAG, "Login...");
        String url = ConstUrl.BASE_URL + "login/signin";

        try{
            MyApplication application = (MyApplication)this.getApplicationContext();
            String picUUID = application.getCaptchUUID();

            Log.d(TAG, "Picture UUID: " + picUUID);

            LoginDto dto = new LoginDto();
            dto.setUserName(mCellPhone.getText().toString());
            dto.setPassword(mPassword.getText().toString());
            dto.setPictureUuid(picUUID);
            dto.setVerifyCode(mCaptchaText.getText().toString());
            Gson gson = new Gson();
            String jsonData = gson.toJson(dto);

            RestTask postTask = RestUtil.obtainFormPostTask(url, jsonData);
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
        Log.d(TAG, "Go to register...");
    }

    private void transferToHomeUI(LoginResponse response) {
        Intent i = HomeActivity.newIntent(LoginActivity.this, response);
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

        if (response.contains("mobileBase64")) {
            // captcha response
            Log.d(TAG, "Captcha response");
            Gson gson = new Gson();
            CaptchaResponse responseData = gson.fromJson(response, new TypeToken<CaptchaResponse>() {
            }.getType());
            String mobileBase64 = responseData.getMobileBase64();
            mImgCaptcha = (ImageView) findViewById(R.id.imgCaptcha);
            mImgCaptcha.setImageBitmap(returnBitMap(mobileBase64));

            MyApplication application = (MyApplication)this.getApplicationContext();
            application.setCaptchUUID(responseData.getPictureUuid());
        } else {
            // login response
            Log.d(TAG, "Login response");
            Gson gson = new Gson();
            LoginResponse responseData = gson.fromJson(response, new TypeToken<LoginResponse>() {
            }.getType());
            String code = responseData.getCode();
            if (code.equals("0")) {
                // login success
                Log.d(TAG, "登陆成功!");
                transferToHomeUI(responseData);
            }
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

    public Bitmap returnBitMap(String data){
        Bitmap pic = Base64Util.stringtoBitmap(data);

        return pic;
    }
}

