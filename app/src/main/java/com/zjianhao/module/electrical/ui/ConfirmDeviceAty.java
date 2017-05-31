package com.zjianhao.module.electrical.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.zjianhao.base.NavigatorActivity;
import com.zjianhao.dao.DaoUtil;
import com.zjianhao.entity.Device;
import com.zjianhao.entity.Keyas;
import com.zjianhao.http.DefaultCallback;
import com.zjianhao.http.ResponseHeader;
import com.zjianhao.http.RetrofitManager;
import com.zjianhao.module.pc.util.CmdUtil;
import com.zjianhao.universalcontroller.R;
import com.zjianhao.utils.CircularAnim;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by 张建浩（Clarence) on 2017-4-23 10:37.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class ConfirmDeviceAty extends NavigatorActivity {
    @InjectView(R.id.device_name)
    EditText deviceName;
    @InjectView(R.id.device_add_confirm)
    Button deviceAddConfirm;
    @InjectView(R.id.key_load_progress)
    FrameLayout keyLoadProgress;
    private int deviceId;
    private int typeId;
    private int brandId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ele_device_confirm_main);
        ButterKnife.inject(this);
        String brandName = getIntent().getStringExtra("brand_name");
        brandId = getIntent().getIntExtra("brand_id", 0);
        typeId = getIntent().getIntExtra("type_id", 1);
        deviceId = getIntent().getIntExtra("device_id", 0);
        deviceName.setText(brandName + CmdUtil.getTypeName(typeId));
    }

    @OnClick(R.id.device_add_confirm)
    public void onClick() {
        CircularAnim.hide(deviceAddConfirm).go();
        keyLoadProgress.setVisibility(View.VISIBLE);
        save();
    }

    public void save() {
        switch (typeId) {
            case 3://空调
                if (!isEmpty())
                    saveDevice();
                finish();
                break;
            default:
                if (!isEmpty())
                    saveDevice();
                loadKeyListData();

        }
    }

    /**
     * 检查数据是否有空数据
     *
     * @return
     */
    public boolean isEmpty() {
        if (TextUtils.isEmpty(deviceName.getText())) {
            showSnackbar(deviceName, "遥控器名称不能为空");
            return true;
        }
        return false;
    }

    private void saveDevice() {
        DaoUtil util = new DaoUtil(this);
        Device device = new Device();
        device.setType_id(typeId);
        device.setBrand_id(brandId);
        device.setDevice_id(deviceId);
        device.setDevice_name(deviceName.getText().toString());
        device.setBackup_time(System.currentTimeMillis());
        util.insertDevice(device);
    }

    public void loadKeyListData() {
        Call<ResponseHeader<List<Keyas>>> keyList = RetrofitManager.getDeviceApi().getKeyList(deviceId);
        keyList.enqueue(new DefaultCallback<List<Keyas>>(deviceName) {
            @Override
            public void onResponse(List<Keyas> data) {
                for (Keyas keyas : data) {
                    keyas.setDevice_id(deviceId);
                    System.out.println(keyas.getDevice_id() + ":" + keyas.getCmd() + ":" + keyas.getName());
                }
                DaoUtil util = new DaoUtil(ConfirmDeviceAty.this);
                util.insertKeyList(data);
                finish();
            }
        });
    }


}
