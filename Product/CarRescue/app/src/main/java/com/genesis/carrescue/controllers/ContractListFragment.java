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
import com.genesis.carrescue.domain.pojo.Constants;
import com.genesis.carrescue.domain.pojo.ContractInfo;
import com.genesis.carrescue.domain.pojo.ContractResult;
import com.genesis.carrescue.domain.pojo.PageData;
import com.genesis.carrescue.domain.pojo.PostData;
import com.genesis.carrescue.domain.pojo.ResponseData;
import com.genesis.util.network.RestTask;
import com.genesis.util.network.RestUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContractListFragment extends BaseFragment
        implements RestTask.ProgressCallback, RestTask.ResponseCallback {
    private static final String TAG = "ContractListFragment";

    private List<ContractInfo> mContractList;

    private RecyclerView mContractRecyclerView;
    private ContractAdapter mAdapter;

    private ProgressDialog mProgress;

    public static ContractListFragment newInstance(int userId, String token) {
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        args.putString(ARG_TOKEN, token);

        ContractListFragment fragment = new ContractListFragment();
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
        View view = inflater.inflate(R.layout.fragment_contract_list, container, false);

        super.setBarTitle("合同管理");

        mContractRecyclerView = (RecyclerView) view
                .findViewById(R.id.contract_recycler_view);
        mContractRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //updateUI();
        getContracts();

        return view;
    }

    private class ContractHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mContractCompanyNameTextView;
        private TextView mContractCompanyAddressTextView;

        private ContractInfo mContract;

        public ContractHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mContractCompanyNameTextView = (TextView) itemView.findViewById(R.id.item_contract_company_name);
            mContractCompanyAddressTextView = (TextView) itemView.findViewById(R.id.item_contract_company_address);
        }

        public void bindContract(ContractInfo contract) {
            mContract = contract;
            mContractCompanyNameTextView.setText(mContract.getRescueCompanyName());
            mContractCompanyAddressTextView.setText(mContract.getRescueCompanyAddress());
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "Id: " + mContract.getId() + ", Company Name: " + mContract.getRescueCompanyName() + " selected!", Toast.LENGTH_SHORT).show();
            transferToContractInfo(mContract);
        }
    }

    private class ContractAdapter extends RecyclerView.Adapter<ContractHolder> {

        private List<ContractInfo> mContracts;

        public ContractAdapter(List<ContractInfo> contracts) {
            mContracts = contracts;
        }

        @Override
        public ContractHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_contract, parent, false);
            return new ContractHolder(view);
        }

        @Override
        public void onBindViewHolder(ContractHolder holder, int position) {
            ContractInfo contract = mContracts.get(position);
            holder.bindContract(contract);
        }

        @Override
        public int getItemCount() {
            return mContracts.size();
        }

        public void setContracts(List<ContractInfo> contracts) {
            mContracts = contracts;
        }
    }

    private void transferToContractInfo(ContractInfo contract) {
        Log.d(TAG, "Transfer to contract info...");
        Intent i = ContractActivity.newIntent(getActivity(), contract, pResult);
        startActivity(i);
    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new ContractAdapter(mContractList);
            mContractRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setContracts(mContractList);
            mAdapter.notifyDataSetChanged();
        }

        //updateSubtitle();
    }

    private void getContracts() {
        Log.d(TAG, "Accquiring contract list...");
        try {
            PageData data = new PageData();
            data.setSkip(0);
            data.setLimit(10);

            PostData<PageData> postData = new PostData<>();
            postData.setMethod("contract.index");
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
        List<ResponseData<ContractResult>> responseData = gson.fromJson(response, new TypeToken<List<ResponseData<ContractResult>>>() {
        }.getType());
        ContractResult result = responseData.get(0).getResult();
        mContractList = result.getResults();

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
