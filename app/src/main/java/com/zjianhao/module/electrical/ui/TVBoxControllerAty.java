package com.zjianhao.module.electrical.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zjianhao.base.NavigatorActivity;
import com.zjianhao.dao.DaoUtil;
import com.zjianhao.entity.Keyas;
import com.zjianhao.http.DefaultCallback;
import com.zjianhao.http.ResponseHeader;
import com.zjianhao.http.RetrofitManager;
import com.zjianhao.module.electrical.util.InfraredUtil;
import com.zjianhao.universalcontroller.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by 张建浩（Clarence) on 2017-4-23 22:50.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class TVBoxControllerAty extends NavigatorActivity {

    @InjectView(R.id.tvbox_power)
    LinearLayout tvboxPower;
    @InjectView(R.id.tvbox_menu)
    LinearLayout tvboxMenu;
    @InjectView(R.id.txbox_back)
    LinearLayout txboxBack;
    @InjectView(R.id.channel_add)
    Button channelAdd;
    @InjectView(R.id.channel_sub)
    Button channelSub;
    @InjectView(R.id.sound_add)
    Button soundAdd;
    @InjectView(R.id.sound_sub)
    Button soundSub;
    @InjectView(R.id.tvbox_up)
    Button tvboxUp;
    @InjectView(R.id.tvbox_ok)
    Button tvboxOk;
    @InjectView(R.id.tvbox_left)
    Button tvboxLeft;
    @InjectView(R.id.tvbox_right)
    Button tvboxRight;
    @InjectView(R.id.tvbox_down)
    Button tvboxDown;
    @InjectView(R.id.num_1)
    Button num1;
    @InjectView(R.id.num_2)
    Button num2;
    @InjectView(R.id.num_3)
    Button num3;
    @InjectView(R.id.num_4)
    Button num4;
    @InjectView(R.id.num_5)
    Button num5;
    @InjectView(R.id.num_6)
    Button num6;
    @InjectView(R.id.num_7)
    Button num7;
    @InjectView(R.id.num_8)
    Button num8;
    @InjectView(R.id.num_9)
    Button num9;
    @InjectView(R.id.num_0)
    Button num0;

    private InfraredUtil infraredUtil;
    private DaoUtil daoUtil;
    private int deviceId;
    private Map<String, Keyas> cmdKeys;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ele_controller_tvbox_main);
        deviceId = getIntent().getIntExtra("device_id", 0);
        String deviceName = getIntent().getStringExtra("device_name");
        setTitle(deviceName);
        ButterKnife.inject(this);
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


    @OnClick({R.id.tvbox_power, R.id.tvbox_menu, R.id.txbox_back, R.id.channel_add, R.id.channel_sub, R.id.sound_add, R.id.sound_sub, R.id.tvbox_up, R.id.tvbox_ok, R.id.tvbox_left, R.id.tvbox_right, R.id.tvbox_down, R.id.num_1, R.id.num_2, R.id.num_3, R.id.num_4, R.id.num_5, R.id.num_6, R.id.num_7, R.id.num_8, R.id.num_9, R.id.num_0})
    public void onClick(View view) {
        System.out.println("click");
        switch (view.getId()) {
            case R.id.tvbox_power:
                send("power");
                break;
            case R.id.tvbox_menu:
                send("menu");
                break;
            case R.id.txbox_back:
                send("back");
                break;
            case R.id.channel_add:
                System.out.println("click add");
                send("chadd");
                break;
            case R.id.channel_sub:
                send("chsub");
                break;
            case R.id.sound_add:
                send("voladd");
                break;
            case R.id.sound_sub:
                send("volsub");
                break;
            case R.id.tvbox_up:
                send("up");
                break;
            case R.id.tvbox_ok:
                send("ok");
                break;
            case R.id.tvbox_left:
                send("left");
                break;
            case R.id.tvbox_right:
                send("right");
                break;
            case R.id.tvbox_down:
                send("down");
                break;
            case R.id.num_1:
                send("1");
                break;
            case R.id.num_2:
                send("2");
                break;
            case R.id.num_3:
                send("3");
                break;
            case R.id.num_4:
                send("4");
                break;
            case R.id.num_5:
                send("5");
                break;
            case R.id.num_6:
                send("6");
                break;
            case R.id.num_7:
                send("7");
                break;
            case R.id.num_8:
                send("8");
                break;
            case R.id.num_9:
                send("9");
                break;
            case R.id.num_0:
                send("0");
                break;
        }
    }


    private void send(String cmd) {
        if (!cmdKeys.containsKey(cmd)) {
            Toast.makeText(this, "没有该按键数据", Toast.LENGTH_SHORT).show();
            return;
        }
        Keyas key = daoUtil.queryKey(deviceId, cmd);
        if (key == null) {
            showSnackbar(tvboxPower, "没有该按键信息");
            return;
        }
        if (key.getData() != null)
            infraredUtil.send(key.getData());
        else
            loadKeyData(key, cmd);

    }


    private void loadKeyData(final Keyas key, String cmd) {

        Call<ResponseHeader<String>> dataCall = RetrofitManager.getDeviceApi().getKeyData(deviceId, cmd);
        dataCall.enqueue(new DefaultCallback<String>(tvboxPower) {
            @Override
            public void onResponse(String data) {
                if (data != null) {
                    infraredUtil.send(data);
                    key.setData(data);
                    daoUtil.updateKey(key);
                } else {
                    showSnackbar(tvboxPower, "获取该按键失败");
                }

            }
        });
    }
}
