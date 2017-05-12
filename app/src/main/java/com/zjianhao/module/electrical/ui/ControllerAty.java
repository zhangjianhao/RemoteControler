package com.zjianhao.module.electrical.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.zjianhao.base.NavigatorActivity;
import com.zjianhao.dao.DaoUtil;
import com.zjianhao.entity.Keyas;
import com.zjianhao.http.DefaultCallback;
import com.zjianhao.http.ResponseHeader;
import com.zjianhao.http.RetrofitManager;
import com.zjianhao.module.electrical.util.InfraredUtil;
import com.zjianhao.utils.VibratorUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by 张建浩（Clarence) on 2017-5-1 15:52.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class ControllerAty extends NavigatorActivity {

    private InfraredUtil infraredUtil;
    private DaoUtil daoUtil;
    protected int deviceId;
    protected Map<String, Keyas> cmdKeys;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();

    }

    protected void initData() {
        deviceId = getIntent().getIntExtra("device_id", 0);
        String deviceName = getIntent().getStringExtra("device_name");
        setTitle(deviceName);
        infraredUtil = new InfraredUtil(this);
        daoUtil = new DaoUtil(this);
        cmdKeys = listToMap(daoUtil.queryKeys(deviceId));
    }

    private Map<String, Keyas> listToMap(List<Keyas> keys) {
        Map<String, Keyas> map = new HashMap<>();
        for (Keyas key : keys) {
            map.put(key.getCmd(), key);
        }
        return map;
    }

    protected void send(View view, String cmd) {
        VibratorUtil.Vibrate(this, 60);
        if (!cmdKeys.containsKey(cmd)) {
            Toast.makeText(this, "没有该按键数据", Toast.LENGTH_SHORT).show();
            return;
        }
        Keyas key = daoUtil.queryKey(deviceId, cmd);
        if (key == null) {
            showSnackbar(view, "没有该按键信息");
            return;
        }
        if (key.getData() != null)
            infraredUtil.send(key.getData());
        else
            loadKeyData(view, key, cmd);

    }


    protected void loadKeyData(final View view, final Keyas key, String cmd) {

        Call<ResponseHeader<String>> dataCall = RetrofitManager.getDeviceApi().getKeyData(deviceId, cmd);
        dataCall.enqueue(new DefaultCallback<String>(view) {
            @Override
            public void onResponse(String data) {
                if (data != null) {
                    infraredUtil.send(data);
                    key.setData(data);
                    daoUtil.updateKey(key);
                } else {
                    showSnackbar(view, "获取该按键失败");
                }

            }
        });
    }
}
