package com.zjianhao.module.electrical.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.zjianhao.adapter.recyclerview.CommonAdapter;
import com.zjianhao.adapter.recyclerview.base.ViewHolder;
import com.zjianhao.adapter.recyclerview.wrapper.SpaceItemDecoration;
import com.zjianhao.base.BaseFragment;
import com.zjianhao.dao.DaoUtil;
import com.zjianhao.entity.Device;
import com.zjianhao.http.DefaultCallback;
import com.zjianhao.http.ResponseHeader;
import com.zjianhao.http.RetrofitManager;
import com.zjianhao.model.User;
import com.zjianhao.module.electrical.DevicePopupWindow;
import com.zjianhao.service.BackupService;
import com.zjianhao.ui.UserLoginAty;
import com.zjianhao.universalcontroller.AppApplication;
import com.zjianhao.universalcontroller.R;
import com.zjianhao.view.IconFont;

import java.util.ArrayList;
import java.util.List;

import at.markushi.ui.CircleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;

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
                CircleButton circleButton = (CircleButton) holder.getView(R.id.control_device_img);
                circleButton.setColor(getColor(device.getType_id()));
                IconFont deviceIcon = (IconFont) holder.getView(R.id.device_icon);
                deviceIcon.setText(getIconText(device.getType_id()));
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Device device = devices.get(position);
                        Intent intent = getTargetIntent(device.getType_id());
                        intent.putExtra("device_id", device.getDevice_id());
                        intent.putExtra("device_name", device.getDevice_name());
                        startActivity(intent);
                    }
                });
                convertView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        showDeleteDialog(position);
                        return false;
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


    private int getColor(int deviceType) {
        switch (deviceType) {
            case 1:
                return getResources().getColor(R.color.green);
            case 2:
                return getResources().getColor(R.color.orange);
            case 3:
                return getResources().getColor(R.color.red_color);
            case 4:
                return getResources().getColor(R.color.purple);
            case 5:
                return getResources().getColor(R.color.light_green);
            case 6:
                return getResources().getColor(R.color.cyan);
            case 7:
                return getResources().getColor(R.color.colorAccent);
            case 8:
                return getResources().getColor(R.color.amber);
            case 9:
                return getResources().getColor(R.color.colorAccent);
        }
        return getResources().getColor(R.color.colorAccent);
    }

    private String getIconText(int deviceType) {
        switch (deviceType) {
            case 1:
                return "\ue7f2";
            case 2:
                return "\ue66b";
            case 3:
                return "\ue638";
            case 4:
                return "\ue624";
            case 5:
                return "\ue659";
            case 6:
                return "\ue614";
            case 7:
                return "\ue782";
            case 8:
                return "\ue704";
            case 9:
                return "\ue66e";
        }
        return "\ue66b";
    }

    private void showDeleteDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(new String[]{"删除", "重命名"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println(which);
                if (which == 0) {
                    daoUtil.deleteDevice(devices.get(position));
                    adapter.delete(position);
                } else if (which == 1)
                    showRenameDialog(position);
            }
        }).show();
    }

    private void showRenameDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_rename_edittext, null);
        final EditText edittext = (EditText) view.findViewById(R.id.device_rename_et);
        edittext.setText(devices.get(position).getDevice_name());
        builder.setTitle("重命名");
        builder.setView(view).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(edittext.getText())) {
                    Toast.makeText(getActivity(), "名称不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    String newName = edittext.getText().toString();
                    Device device = devices.get(position);
                    device.setDevice_name(newName);
                    adapter.notifyItemChanged(position);
                    daoUtil.updateDevice(device);

                }
            }
        }).show();

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
        User user = ((AppApplication) getActivity().getApplication()).getUser();
        if (user == null) {
            Toast.makeText(getActivity(), "请先登陆", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), UserLoginAty.class);
            startActivity(intent);
            getActivity().finish();
        }
        int id = item.getItemId();
        switch (id) {
            case R.id.device_backup:
                System.out.println("click backup menu");
                Intent intent = new Intent(getActivity(), BackupService.class);
                intent.putExtra("user_id", user.getUserId());
                intent.putExtra("token", user.getToken());
                intent.setAction(BackupService.ACTION_BACKUP_DEVICE);
                getActivity().startService(intent);

                break;
            case R.id.device_restore:
                restore(user.getUserId(), user.getToken());
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    public void restore(String userId, String token) {
        Call<ResponseHeader<List<Device>>> call = RetrofitManager.getUserApi().restoreData(userId, token);
        call.enqueue(new DefaultCallback<List<Device>>(null) {
            @Override
            public void onResponse(List<Device> data) {
                for (Device device : data) {
                    if (!daoUtil.isExist(device))
                        daoUtil.insertDevice(device);
                }
                devices = daoUtil.getDeviceList();
                adapter.setDatas(devices);
                Toast.makeText(getActivity(), "数据还原完成", Toast.LENGTH_SHORT).show();
            }
        });
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


    public boolean hidePopupWindow() {
        if (window == null || !window.isShowing())
            return false;
        window.dismiss();
        return true;
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
                intent = new Intent(getActivity(), DVDControllerAty.class);
                break;
            case 5:
                intent = new Intent(getActivity(), ProjectorControllerAty.class);
                break;
            case 6:
                intent = new Intent(getActivity(), SmartBoxControllerAty.class);
                break;
            case 7:
                intent = new Intent(getActivity(), FanControllerAty.class);
                break;
            case 8:
                intent = new Intent(getActivity(), ProjectorControllerAty.class);
                break;
            case 9:
                intent = new Intent(getActivity(), CameraControllerAty.class);
                break;
        }
        return intent;
    }

}
