package com.genesis.carrescue.controllers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.genesis.carrescue.R;
import com.genesis.carrescue.domain.pojo.BasePost;
import com.genesis.carrescue.domain.pojo.Constants;
import com.genesis.carrescue.domain.pojo.DriverInfo;
import com.genesis.carrescue.domain.pojo.DriverResult;
import com.genesis.carrescue.domain.pojo.LoginData;
import com.genesis.carrescue.domain.pojo.LoginResult;
import com.genesis.carrescue.domain.pojo.PageData;
import com.genesis.carrescue.domain.pojo.PostData;
import com.genesis.carrescue.domain.pojo.ResponseData;
import com.genesis.util.network.RestTask;
import com.genesis.util.network.RestUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DriverListFragment extends BaseFragment
        implements RestTask.ProgressCallback, RestTask.ResponseCallback {
    private static final String TAG = "DriverListFragment";

    private List<DriverInfo> mDriverList;

    private RecyclerView mDriverRecyclerView;
    private DriverAdapter mAdapter;

    private ProgressDialog mProgress;

    public static DriverListFragment newInstance(int userId, String token) {
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        args.putString(ARG_TOKEN, token);

        DriverListFragment fragment = new DriverListFragment();
        fragment.setArguments(args);

        return fragment;
    }

    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setHasOptionsMenu(true);
//
//        int userId = (int) getArguments().getSerializable(ARG_USER_ID);
//        String token = (String) getArguments().getSerializable(ARG_TOKEN);
//        Log.d(TAG, "Get Passed User Id: " + userId);
//        Log.d(TAG, "Get Passed Token: " + token);
//
//        mResult = new LoginResult();
//        mResult.setUserId(userId);
//        mResult.setToken(token);
    }
    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_driver_list, container, false);

        super.setBarTitle("司机管理");

        mDriverRecyclerView = (RecyclerView) view
                .findViewById(R.id.driver_recycler_view);
        mDriverRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //updateUI();
        getDrivers();

        return view;
    }

    private class DriverHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mDriverNameTextView;
        private TextView mDriverPhoneTextView;

        private DriverInfo mDriver;

        public DriverHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mDriverNameTextView = (TextView) itemView.findViewById(R.id.item_diriver_name);
            mDriverPhoneTextView = (TextView) itemView.findViewById(R.id.item_diriver_phone);
        }

        public void bindDriver(DriverInfo driver) {
            mDriver = driver;
            mDriverNameTextView.setText(mDriver.getName());
            mDriverPhoneTextView.setText(mDriver.getUserPhone());
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "Id: " + mDriver.getId() + ", Name: " + mDriver.getName() + " selected!", Toast.LENGTH_SHORT).show();
            transferToDriverInfo(mDriver);
        }
    }

    private class DriverAdapter extends RecyclerView.Adapter<DriverHolder> {

        private List<DriverInfo> mDrivers;

        public DriverAdapter(List<DriverInfo> drviers) {
            mDrivers = drviers;
        }

        @Override
        public DriverHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_driver, parent, false);
            return new DriverHolder(view);
        }

        @Override
        public void onBindViewHolder(DriverHolder holder, int position) {
            DriverInfo driver = mDrivers.get(position);
            holder.bindDriver(driver);
        }

        @Override
        public int getItemCount() {
            return mDrivers.size();
        }

        public void setDrivers(List<DriverInfo> drivers) {
            mDrivers = drivers;
        }
    }

    private void transferToDriverInfo(DriverInfo driver) {
        Log.d(TAG, "Transfer to driver info...");
        Intent i = DriverActivity.newIntent(getActivity(), driver, pResult);
        startActivity(i);
    }

    private void updateUI() {
        //List<DriverInfo> drivers = mockDrivers();

        if (mAdapter == null) {
            mAdapter = new DriverAdapter(mDriverList);
            mDriverRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setDrivers(mDriverList);
            mAdapter.notifyDataSetChanged();
        }

        //updateSubtitle();
    }

    private void getDrivers() {
        Log.d(TAG, "Accquiring driver list...");
        try {
            PageData data = new PageData();
            data.setSkip(0);
            data.setLimit(10);

            PostData<PageData> postData = new PostData<>();
            postData.setMethod("driver.index");
            postData.setId(1);
            postData.setParams(data);

            Gson gson = new Gson();
            String jsonData = "[" + gson.toJson(postData) + "]";

            Log.d(TAG, "请求数据: " + jsonData);

            RestTask postTask = RestUtil.obtainAuthPostTask(Constants.POST_URI, jsonData, Integer.toString(pResult.getUserId()), pResult.getToken());
            postTask.setResponseCallback(this);
            //postTask.setProgressCallback(this);

            postTask.execute();

            //Display progress to the user
            mProgress = ProgressDialog.show(getActivity(), "搜索", "信息请求中, 请稍等...", true);
        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e.getMessage());
        }
    }

    /*
    private List<DriverInfo> mockDrivers() {
        List<DriverInfo> drivers = new ArrayList<>();

        DriverInfo driver1 = new DriverInfo();
        driver1.setName("张三");
        driver1.setPhone("15678901281");
        drivers.add(driver1);

        DriverInfo driver2 = new DriverInfo();
        driver2.setName("李四");
        driver2.setPhone("12345678910");
        drivers.add(driver2);

        DriverInfo driver3 = new DriverInfo();
        driver3.setName("王二");
        driver3.setPhone("15017897129");
        drivers.add(driver3);

        return drivers;
    }
    */

    public void onBackPressed() {
        Log.d(TAG, "User Id:" + pResult.getUserId() + " back to account UI...");
        Intent i = AccountActivity.newIntent(getActivity(), pResult);
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
        if (mProgress != null) {
            mProgress.dismiss();
        }

        //Process the response data (here we just display it)
        Log.d(TAG, "返回结果:" + response);

        Gson gson = new Gson();
        List<ResponseData<DriverResult>> responseData = gson.fromJson(response, new TypeToken<List<ResponseData<DriverResult>>>() {
        }.getType());
        DriverResult result = responseData.get(0).getResult();
        mDriverList = result.getResults();

        updateUI();
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
