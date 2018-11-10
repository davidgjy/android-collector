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
import com.genesis.carrescue.domain.pojo.ContractInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KG on 16/10/27.
 */
public class ContractFragment extends BaseFragment {
    private static final String TAG = "ContractFragment";
    public static final String CONTRACT_INFO = "CarRescue.CONTRACT_INFO";

    private ContractInfo mContractInfo;

    private EditText txtContractNumber;
    private EditText txtUnitPrice;
    private EditText txtStartFee;
    private EditText txtStartRange;

    private GridView gridContracts;
    private int[] imageIds = new int[]{
            R.drawable.business_sample1, R.drawable.business_sample1,R.drawable.grid_add
    };

    public static ContractFragment newInstance(int userId, String token, ContractInfo contract) {
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        args.putString(ARG_TOKEN, token);
        args.putSerializable(CONTRACT_INFO, contract);

        ContractFragment fragment = new ContractFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mContractInfo = (ContractInfo) getArguments().getSerializable(CONTRACT_INFO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contract, container, false);

        super.setBarTitle("合同信息");

        txtContractNumber = (EditText) view.findViewById(R.id.edt_contract_number);
        txtContractNumber.setText(mContractInfo.getContractNo());
        txtUnitPrice = (EditText) view.findViewById(R.id.edt_unit_price);
        txtUnitPrice.setText(Double.toString(mContractInfo.getUnitPrice()));
        txtStartFee = (EditText) view.findViewById(R.id.edt_start_fee);
        txtStartFee.setText(Double.toString(mContractInfo.getStartFee()));
        txtStartRange = (EditText) view.findViewById(R.id.edt_start_range);
        txtStartRange.setText(Integer.toString(mContractInfo.getStartRange()));

        gridContracts = (GridView) view.findViewById(R.id.grid_contracts);
        bindGrid(gridContracts);

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
        Log.d(TAG, "User Id: " + pResult.getUserId() + " back to contract list...");
        Intent i = ContractListActivity.newIntent(getActivity(), pResult);
        startActivity(i);
    }
}
