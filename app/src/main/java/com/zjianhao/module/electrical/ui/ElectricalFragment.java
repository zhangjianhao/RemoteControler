package com.zjianhao.module.electrical.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.zjianhao.adapter.recyclerview.CommonAdapter;
import com.zjianhao.adapter.recyclerview.base.ViewHolder;
import com.zjianhao.adapter.recyclerview.wrapper.SpaceItemDecoration;
import com.zjianhao.base.BaseFragment;
import com.zjianhao.dao.DaoUtil;
import com.zjianhao.entity.Device;
import com.zjianhao.module.electrical.DevicePopupWindow;
import com.zjianhao.universalcontroller.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 张建浩（Clarence) on 2017-4-10 17:00.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class ElectricalFragment extends BaseFragment {

    @InjectView(R.id.controller_device)
    RecyclerView controllerDevice;
    @InjectView(R.id.device_add_menu)
    FloatingActionButton deviceAddMenu;
    @InjectView(R.id.swipe_refresh_device)
    SwipeRefreshLayout swipeRefreshDevice;
    private DevicePopupWindow window;
    private CommonAdapter<Device> adapter;
    private List<Device> devices = new ArrayList<>();
    private DaoUtil daoUtil;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.electric_fragment_main, container, false);
        ButterKnife.inject(this, view);
        controllerDevice.setLayoutManager(new LinearLayoutManager(getActivity()));
        daoUtil = new DaoUtil(getActivity());
        devices = daoUtil.getDeviceList();
        adapter = new CommonAdapter<Device>(getActivity(), R.layout.ele_device_list_item, devices) {
            @Override
            protected void convert(ViewHolder holder, Device device, final int position) {
                View convertView = holder.getConvertView();
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("onclick");
                        Device device = devices.get(position);
                        Intent intent = getTargetIntent(device.getType_id());
                        intent.putExtra("device_id", device.getDevice_id());
                        intent.putExtra("device_name", device.getDevice_name());
                        startActivity(intent);
                    }
                });
                holder.setText(R.id.controll_device_name, device.getDevice_name());
            }
        };

        controllerDevice.addItemDecoration(new SpaceItemDecoration(2, 0, 0, 0));
        controllerDevice.setAdapter(adapter);
        swipeRefreshDevice.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                devices = daoUtil.getDeviceList();
                adapter.setDatas(devices);
                swipeRefreshDevice.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        devices = daoUtil.getDeviceList();
        adapter.setDatas(devices);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.electrical_tool_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("on option item selected" + item.getTitle());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.device_add_menu)
    public void onClick() {
        if (window == null) {
            window = new DevicePopupWindow(getActivity());
        }
        if (!window.isShowing()) {
            window.showAtLocation(deviceAddMenu,
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else {
            window.dismiss();
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (window != null)
            window.dismiss();
    }


    public Intent getTargetIntent(int typeId) {
        Intent intent = null;
        switch (typeId) {
            case 1:
                intent = new Intent(getActivity(), TVBoxControllerAty.class);
                break;
            case 2:
                intent = new Intent(getActivity(), TVBoxControllerAty.class);
                break;
            case 3:
                intent = new Intent(getActivity(), AirConditionControllerAty.class);
                break;
            case 4:
                intent = new Intent(getActivity(), TVBoxControllerAty.class);
                break;
            case 5:
                intent = new Intent(getActivity(), TVBoxControllerAty.class);
                break;
            case 6:
                intent = new Intent(getActivity(), TVBoxControllerAty.class);
                break;
            case 7:
                intent = new Intent(getActivity(), TVBoxControllerAty.class);
                break;
            case 8:
                intent = new Intent(getActivity(), TVBoxControllerAty.class);
                break;
            case 9:
                intent = new Intent(getActivity(), CameraControllerAty.class);
                break;

        }
        return intent;
    }

}
