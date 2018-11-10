package com.genesis.carrescue.controllers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.genesis.carrescue.R;
import com.genesis.carrescue.domain.pojo.CarInfo;
import com.genesis.carrescue.domain.pojo.CarResult;
import com.genesis.carrescue.domain.pojo.Constants;
import com.genesis.carrescue.domain.pojo.PageData;
import com.genesis.carrescue.domain.pojo.PostData;
import com.genesis.carrescue.domain.pojo.ResponseData;
import com.genesis.util.network.RestTask;
import com.genesis.util.network.RestUtil;
import com.genesis.util.time.DateTimeUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarListFragment extends BaseFragment
        implements RestTask.ProgressCallback, RestTask.ResponseCallback {
    private static final String TAG = "CarListFragment";

    private List<CarInfo> mCarList;

    private RecyclerView mCarRecyclerView;
    private CarAdapter mAdapter;

    private ProgressDialog mProgress;

    public static CarListFragment newInstance(int userId, String token) {
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        args.putString(ARG_TOKEN, token);

        CarListFragment fragment = new CarListFragment();
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
        View view = inflater.inflate(R.layout.fragment_car_list, container, false);

        super.setBarTitle("车辆管理");

        mCarRecyclerView = (RecyclerView) view
                .findViewById(R.id.car_recycler_view);
        mCarRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //updateUI();
        getCars();

        return view;
    }

    private class CarHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mCarLicenseNumberTextView;
        private TextView mCarPurchaseTimeTextView;

        private CarInfo mCar;

        public CarHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mCarLicenseNumberTextView = (TextView) itemView.findViewById(R.id.item_car_license_number);
            mCarPurchaseTimeTextView = (TextView) itemView.findViewById(R.id.item_car_purchase_time);
        }

        public void bindCar(CarInfo car) {
            mCar = car;
            mCarLicenseNumberTextView.setText(mCar.getLicensePlateNumber());
            mCarPurchaseTimeTextView.setText(DateTimeUtil.getDateTimeByFormat(mCar.getPurchaseTime(), "yyyy-MM-dd hh:mm:ss"));
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "Id: " + mCar.getId() + ", License Number: " + mCar.getLicensePlateNumber() + " selected!", Toast.LENGTH_SHORT).show();
            transferToCarInfo(mCar);
        }
    }

    private class CarAdapter extends RecyclerView.Adapter<CarHolder> {

        private List<CarInfo> mCars;

        public CarAdapter(List<CarInfo> cars) {
            mCars = cars;
        }

        @Override
        public CarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_car, parent, false);
            return new CarHolder(view);
        }

        @Override
        public void onBindViewHolder(CarHolder holder, int position) {
            CarInfo car = mCars.get(position);
            holder.bindCar(car);
        }

        @Override
        public int getItemCount() {
            return mCars.size();
        }

        public void setCars(List<CarInfo> cars) {
            mCars = cars;
        }
    }

    private void transferToCarInfo(CarInfo car) {
        Log.d(TAG, "Transfer to car info...");
        Intent i = CarActivity.newIntent(getActivity(), car, pResult);
        startActivity(i);
    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new CarAdapter(mCarList);
            mCarRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setCars(mCarList);
            mAdapter.notifyDataSetChanged();
        }

        //updateSubtitle();
    }

    private void getCars() {
        Log.d(TAG, "Accquiring car list...");
        try {
            PageData data = new PageData();
            data.setSkip(0);
            data.setLimit(10);

            PostData<PageData> postData = new PostData<>();
            postData.setMethod("car.index");
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
        List<ResponseData<CarResult>> responseData = gson.fromJson(response, new TypeToken<List<ResponseData<CarResult>>>() {
        }.getType());
        CarResult result = responseData.get(0).getResult();
        mCarList = result.getResults();

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
