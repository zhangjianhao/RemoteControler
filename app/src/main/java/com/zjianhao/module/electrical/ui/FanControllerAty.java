package com.zjianhao.module.electrical.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.zjianhao.universalcontroller.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 张建浩（Clarence) on 2017-5-1 22:07.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class FanControllerAty extends ControllerAty {
    @InjectView(R.id.fan_power)
    LinearLayout fanPower;
    @InjectView(R.id.fan_mode)
    LinearLayout fanMode;
    @InjectView(R.id.fan_mute)
    LinearLayout fanMute;
    @InjectView(R.id.wind_speed)
    LinearLayout windSpeed;
    @InjectView(R.id.fan_timer)
    LinearLayout fanTimer;
    @InjectView(R.id.fan_shake)
    LinearLayout fanShake;
    private boolean isOpen = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ele_controller_fan_main);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.fan_power, R.id.fan_mode, R.id.fan_mute, R.id.wind_speed, R.id.fan_timer, R.id.fan_shake})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fan_power:
                isOpen = !isOpen;//改变状态
                System.out.println("state:" + isOpen);
                if (cmdKeys.containsKey("poweroff")) {
                    if (isOpen)
                        send(view, "power");
                    else
                        send(view, "poweroff");
                } else
                    send(view, "power");
                break;
            case R.id.fan_mode:
                send(view, "mode");
                break;
            case R.id.fan_mute:
                send(view, "mute");
                break;
            case R.id.wind_speed:
                send(view, "speed");
                break;
            case R.id.fan_timer:
                send(view, "timer");
                break;
            case R.id.fan_shake:
                send(view, "oscillation");
                break;
        }
    }
}
