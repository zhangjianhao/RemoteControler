package com.zjianhao.module.electrical;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.zjianhao.http.DefaultCallback;
import com.zjianhao.http.DeviceApi;
import com.zjianhao.http.ResponseHeader;
import com.zjianhao.http.RetrofitManager;
import com.zjianhao.module.electrical.adapter.DeviceTypeAdapter;
import com.zjianhao.module.electrical.model.DeviceType;
import com.zjianhao.module.electrical.ui.BrandListActivity;
import com.zjianhao.universalcontroller.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by 张建浩（Clarence) on 2017-4-20 13:33.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class DevicePopupWindow extends PopupWindow implements DeviceTypeAdapter.DeviceTypeListener {


    private GridView deviceTypeGrid;
    private ProgressBar deviceLoadProgress;
    private List<DeviceType> deviceTypes = new ArrayList<>();
    private DeviceTypeAdapter adapter;
    private Context context;

    public DevicePopupWindow(Context context) {
        super();
        this.context = context;
        setTouchable(true);
        setOutsideTouchable(true);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View popupView = inflater.inflate(R.layout.ele_device_type_popup, null);
        deviceTypeGrid = (GridView) popupView.findViewById(R.id.device_type_grid);
        deviceLoadProgress = (ProgressBar) popupView.findViewById(R.id.device_load_progress);
        deviceLoadProgress.setVisibility(View.VISIBLE);
        adapter = new DeviceTypeAdapter(context, R.layout.ele_deivce_type_item, deviceTypes);
        adapter.setDeviceTypeListener(this);
        deviceTypeGrid.setAdapter(adapter);
        setContentView(popupView);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setAnimationStyle(R.style.pop_submenu_anim_style);
        ColorDrawable dw = new ColorDrawable(0x000000);
        this.setBackgroundDrawable(dw);
        popupView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = popupView.getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


        loadDeviceType();
    }

    public void loadDeviceType() {
        final DeviceApi deviceApi = RetrofitManager.getDeviceApi();
        Call<ResponseHeader<List<DeviceType>>> deviceTypes = deviceApi.getDeviceTypes();
        deviceTypes.enqueue(new DefaultCallback<List<DeviceType>>(deviceTypeGrid) {
            @Override
            public void onResponse(List<DeviceType> data) {
                deviceLoadProgress.setVisibility(View.GONE);
                adapter.setDatas(data);
            }

            @Override
            public void onFailure(Throwable t) {
                super.onFailure(t);
                dismiss();
                deviceLoadProgress.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onClickDeviceType(DeviceType item) {
        dismiss();
        int typeId = item.getTypeId();
        Intent intent = new Intent(context, BrandListActivity.class);
        intent.putExtra("type_id", typeId);
        context.startActivity(intent);
    }


}

