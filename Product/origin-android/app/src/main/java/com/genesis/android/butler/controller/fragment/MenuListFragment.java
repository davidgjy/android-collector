package com.genesis.android.butler.controller.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.genesis.android.butler.R;
import com.genesis.android.butler.controller.activity.DepotActivity;
import com.genesis.android.butler.controller.base.BaseFragment;
import com.genesis.android.butler.domain.constants.ConstUrl;
import com.genesis.android.butler.domain.dto.MenuDto;
import com.genesis.android.butler.domain.pojo.MenuInfo;
import com.genesis.android.butler.domain.response.BaseResponse;
import com.genesis.android.butler.util.network.RestTask;
import com.genesis.android.butler.util.network.RestUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by kg on 2017/12/21.
 */

public class MenuListFragment extends BaseFragment
        implements RestTask.ProgressCallback, RestTask.ResponseCallback {
    private static final String TAG = "MenuListFragment";

    private List<MenuInfo> mMenuList;
    private RecyclerView mMenuRecyclerView;
    private MenuAdapter mAdapter;

    private ProgressDialog mProgress;

    public static MenuListFragment newInstance(int userId, String token) {
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        args.putString(ARG_TOKEN, token);

        MenuListFragment fragment = new MenuListFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_list, container, false);

        super.setBarTitle("菜单管理", true);

        mMenuRecyclerView = (RecyclerView) view
                .findViewById(R.id.menu_recycler_view);
        mMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //updateUI();
        getMenus();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_menu_list, menu);
    }

    private class MenuHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mMenuNameTextView;
        private TextView mMenuPriceTextView;

        private MenuInfo mMenu;

        public MenuHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mMenuNameTextView = (TextView) itemView.findViewById(R.id.item_menu_name);
            mMenuPriceTextView = (TextView) itemView.findViewById(R.id.item_menu_price);
        }

        public void bindMenu(MenuInfo menu) {
            mMenu = menu;
            mMenuNameTextView.setText(mMenu.getName());
            mMenuPriceTextView.setText(String.format("%.2f", mMenu.getSellPrice()));
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "Name: " + mMenu.getName() + " selected!", Toast.LENGTH_SHORT).show();
            //transferToDriverInfo(mDriver);
        }
    }

    private class MenuAdapter extends RecyclerView.Adapter<MenuHolder> {

        private List<MenuInfo> mMenus;

        public MenuAdapter(List<MenuInfo> menus) {
            mMenus = menus;
        }

        @Override
        public MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_menu, parent, false);
            return new MenuHolder(view);
        }

        @Override
        public void onBindViewHolder(MenuHolder holder, int position) {
            MenuInfo menuInfo = mMenus.get(position);
            holder.bindMenu(menuInfo);
        }

        @Override
        public int getItemCount() {
            return mMenus.size();
        }

        public void setMenus(List<MenuInfo> menus) {
            mMenus = menus;
        }
    }

    private void getMenus() {
        Log.d(TAG, "Login...");
        String url = ConstUrl.BASE_URL + "Menu/queryMenuInfo";

        try{
            MenuDto dto = new MenuDto();
            dto.setUserId(pResult.getUserId());
            dto.setToken(pResult.getToken());
            dto.setPageNum(0);
            dto.setPageSize(0);
            Gson gson = new Gson();
            String jsonData = gson.toJson(dto);

            RestTask postTask = RestUtil.obtainFormPostTask(url, jsonData);
            postTask.setResponseCallback(this);
            //postTask.setProgressCallback(this);

            postTask.execute();

            //Display progress to the user
            mProgress = ProgressDialog.show(getActivity(), "搜索", "信息请求中, 请稍等...", true);
        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e.getMessage());
        }
    }

    private void updateUI() {
        //List<DriverInfo> drivers = mockDrivers();

        if (mAdapter == null) {
            mAdapter = new MenuAdapter(mMenuList);
            mMenuRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setMenus(mMenuList);
            mAdapter.notifyDataSetChanged();
        }

        //updateSubtitle();
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
        BaseResponse<List<MenuInfo>> responseData = gson.fromJson(response, new TypeToken<BaseResponse<List<MenuInfo>>>() {
        }.getType());
        mMenuList = responseData.getData();

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

    public void onBackPressed() {
        Log.d(TAG, "User Id:" + pResult.getUserId() + " back to account UI...");
        Intent i = DepotActivity.newIntent(getActivity(), pResult);
        startActivity(i);
    }
}
