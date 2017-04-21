package com.zjianhao.module.electrical.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.zjianhao.base.BaseFragment;
import com.zjianhao.module.electrical.DevicePopupWindow;
import com.zjianhao.universalcontroller.R;

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
    private DevicePopupWindow window;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.electric_fragment_main, container, false);
        getActivity().setTitle("我的家电");
        ButterKnife.inject(this, view);
        return view;
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
        window.dismiss();
    }
}
