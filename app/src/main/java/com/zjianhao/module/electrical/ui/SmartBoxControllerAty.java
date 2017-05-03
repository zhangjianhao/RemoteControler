package com.zjianhao.module.electrical.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zjianhao.universalcontroller.R;

import at.markushi.ui.CircleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 张建浩（Clarence) on 2017-5-1 15:15.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class SmartBoxControllerAty extends ControllerAty {
    @InjectView(R.id.smart_box_power)
    LinearLayout smartBoxPower;
    @InjectView(R.id.smart_box_menu)
    LinearLayout smartBoxMenu;
    @InjectView(R.id.smart_box_back)
    LinearLayout smartBoxBack;
    @InjectView(R.id.sound_sub)
    CircleButton soundSub;
    @InjectView(R.id.sound_add)
    CircleButton soundAdd;
    @InjectView(R.id.smart_box_home)
    CircleButton smartBoxHome;
    @InjectView(R.id.tvbox_up)
    Button tvboxUp;
    @InjectView(R.id.tvbox_left)
    Button tvboxLeft;
    @InjectView(R.id.tvbox_right)
    Button tvboxRight;
    @InjectView(R.id.tvbox_down)
    Button tvboxDown;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ele_controller_smart_box_main);
        ButterKnife.inject(this);
        System.out.println("deviceId:" + deviceId);
    }

    @OnClick({R.id.smart_box_power, R.id.smart_box_menu, R.id.smart_box_back, R.id.sound_sub, R.id.sound_add, R.id.smart_box_home, R.id.tvbox_up, R.id.tvbox_left, R.id.tvbox_right, R.id.tvbox_down})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.smart_box_power:
                send(view, "power");
                break;
            case R.id.smart_box_menu:
                send(view, "menu");
                break;
            case R.id.smart_box_back:
                send(view, "back");
                break;
            case R.id.sound_sub:
                send(view, "volsub");
                break;
            case R.id.sound_add:
                send(view, "voladd");
                break;
            case R.id.smart_box_home:
                send(view, "boot");
                break;
            case R.id.tvbox_up:
                send(view, "up");
                break;
            case R.id.tvbox_left:
                send(view, "left");
                break;
            case R.id.tvbox_right:
                send(view, "right");
                break;
            case R.id.tvbox_down:
                send(view, "down");
                break;
        }
    }
}
