package com.zjianhao.module.electrical.ui;

import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.zjianhao.base.NavigatorActivity;
import com.zjianhao.dao.DaoUtil;
import com.zjianhao.entity.Keyas;
import com.zjianhao.http.DefaultCallback;
import com.zjianhao.http.ResponseHeader;
import com.zjianhao.http.RetrofitManager;
import com.zjianhao.universalcontroller.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by 张建浩（Clarence) on 2017-4-24 10:41.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class CameraControllerAty extends NavigatorActivity {
    @InjectView(R.id.camera_power)
    Button cameraPower;
    @InjectView(R.id.camera_take_photos)
    Button cameraTakePhotos;
    private DaoUtil daoUtil;
    private List<Keyas> keys;
    private int deviceId;
    private ConsumerIrManager manager;
    private String deviceName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ele_controller_camera_main);
        ButterKnife.inject(this);
        deviceId = getIntent().getIntExtra("device_id", 0);
        deviceName = getIntent().getStringExtra("device_name");
        daoUtil = new DaoUtil(this);
        keys = daoUtil.queryKeys(deviceId);
        System.out.println("query keys size:" + keys.size());
        for (Keyas key : keys) {
            System.out.println(key.getName() + ":" + key.getCmd());
        }
        manager = (ConsumerIrManager) getSystemService(CONSUMER_IR_SERVICE);
        loadKeysData();
    }

    @OnClick({R.id.camera_power, R.id.camera_take_photos})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.camera_power:

                break;
            case R.id.camera_take_photos:
                break;
        }
    }

    private void loadKeysData() {
        System.out.println("----request key id:" + deviceId);
        for (final Keyas key : keys) {
            if (key.getData() == null) {
                Call<ResponseHeader<String>> keyData = RetrofitManager.getDeviceApi().getKeyData(deviceId, key.getCmd());
                keyData.enqueue(new DefaultCallback<String>(cameraPower) {
                    @Override
                    public void onResponse(String data) {
                        key.setData(data);
                        System.out.println("----cmd:" + key.getCmd() + ":" + key.getData());
                    }
                });
            }

        }
    }
}
