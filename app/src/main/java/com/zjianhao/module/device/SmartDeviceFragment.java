package com.zjianhao.module.device;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.zjianhao.base.BaseFragment;
import com.zjianhao.universalcontroller.R;

/**
 * Created by 张建浩（Clarence) on 2017-4-10 17:00.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class SmartDeviceFragment extends BaseFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pc_fragment_main,container,false);
        getActivity().setTitle("智能设备");
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.smart_device_tool_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("on option item selected"+item.getTitle());
        return super.onOptionsItemSelected(item);
    }
}
