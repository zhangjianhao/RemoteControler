package com.zjianhao.module.electrical.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zjianhao.base.NavigatorActivity;
import com.zjianhao.dao.DaoUtil;
import com.zjianhao.entity.AirCmd;
import com.zjianhao.http.DefaultCallback;
import com.zjianhao.http.ResponseHeader;
import com.zjianhao.http.RetrofitManager;
import com.zjianhao.module.electrical.model.AirCondition;
import com.zjianhao.module.electrical.util.InfraredUtil;
import com.zjianhao.universalcontroller.R;
import com.zjianhao.view.IconFont;

import at.markushi.ui.CircleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by 张建浩（Clarence) on 2017-4-22 21:21.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class AirConditionControllerAty extends NavigatorActivity {
    @InjectView(R.id.air_state)
    TextView airState;
    @InjectView(R.id.air_temperature)
    TextView airTemperature;
    @InjectView(R.id.air_mode_text)
    TextView airModeText;
    @InjectView(R.id.air_mode_img)
    IconFont airModeImg;
    @InjectView(R.id.air_cmd_on)
    LinearLayout airCmdOn;
    @InjectView(R.id.air_cmd_off)
    LinearLayout airCmdOff;
    @InjectView(R.id.air_cmd_increase)
    LinearLayout airCmdIncrease;
    @InjectView(R.id.air_cmd_decrease)
    LinearLayout airCmdDecrease;
    @InjectView(R.id.air_cmd_mode)

    CircleButton airCmdMode;
    private AirCondition airCondition;
    private DaoUtil daoUtil;
    private int deviceId;
    private InfraredUtil infraredUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ele_controller_air_condition_main);
        ButterKnife.inject(this);
        infraredUtil = new InfraredUtil(this);
        deviceId = getIntent().getIntExtra("deviceId", 0);
        setTitle(getIntent().getStringExtra("device_name"));
        daoUtil = new DaoUtil(this);
        airCondition = new AirCondition();
        airState.setText(airCondition.getCurrentState());
        airTemperature.setText(0 + "");
        airModeImg.setVisibility(View.GONE);
        airModeText.setVisibility(View.GONE);
    }

    @OnClick({R.id.air_cmd_on, R.id.air_cmd_off, R.id.air_cmd_increase, R.id.air_cmd_decrease, R.id.air_cmd_mode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.air_cmd_on:
                powerOn();
                break;
            case R.id.air_cmd_off:
                poweroff();
                break;
            case R.id.air_cmd_increase:
                increaseTemp();
                break;
            case R.id.air_cmd_decrease:

                break;
            case R.id.air_cmd_mode:
                changeMode();
                break;
        }
    }


    private void changeMode() {
        airCondition.changeMode();
        airModeText.setText(airCondition.getModeDesc());
        airModeImg.setText(airCondition.getModeIconText());
        sendCmd(airCondition.getCurrentMode(), airCondition.getTemperature());
    }

    private void poweroff() {
        airCondition.powerOff();
        airState.setText(airCondition.getCurrentState());
        airModeImg.setVisibility(View.GONE);
        airModeText.setVisibility(View.GONE);
        sendCmd(airCondition.getCurrentMode(), airCondition.getTemperature());
    }

    private void sendCmd(String cmd, int temp) {
        AirCmd airCmd = daoUtil.getAirCmd(deviceId, cmd, temp + "");
        //没有数据从网络加载
        if (airCmd == null || airCmd.getData() == null)
            loadKeyData(cmd, temp);
        else//数据库中存在数据直接发送
            infraredUtil.send(airCmd.getData());
    }

    private void powerOn() {
        airCondition.powerOn();
        airState.setText(airCondition.getCurrentState());
        airModeImg.setVisibility(View.VISIBLE);
        airModeText.setVisibility(View.VISIBLE);
        airModeImg.setText(airCondition.getModeIconText());
        airModeText.setText(airCondition.getModeDesc());
        airTemperature.setText(airCondition.getTemperature() + "");
        sendCmd(AirCondition.ON, airCondition.getTemperature());
    }

    private void increaseTemp() {
        airTemperature.setText(airCondition.increaseTemperature() + "");
        sendCmd(airCondition.getCurrentMode(), airCondition.getTemperature());
    }

    private void decreaseTemp() {
        airTemperature.setText(airCondition.decreaseTemperature() + "");
        sendCmd(airCondition.getCurrentMode(), airCondition.getTemperature());
    }

    private void loadKeyData(final String cmd, final int temp) {
        Call<ResponseHeader<String>> dataCall = RetrofitManager.getDeviceApi().getAirKeyData(deviceId, cmd, temp);
        dataCall.enqueue(new DefaultCallback<String>(airTemperature) {
            @Override
            public void onResponse(String data) {
                infraredUtil.send(data);
                AirCmd airCmd = new AirCmd();
                airCmd.setDevice_id(deviceId);
                airCmd.setCmd(cmd);
                airCmd.setTemp(temp);
                airCmd.setData(data);
                daoUtil.insertAirCmd(airCmd);
            }
        });
    }
}
