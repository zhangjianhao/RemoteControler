package com.zjianhao.module.electrical.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zjianhao.universalcontroller.R;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 张建浩（Clarence) on 2017-4-23 22:50.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class TVBoxControllerAty extends ControllerAty {

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



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ele_controller_tvbox_main);

    }





    @OnClick({R.id.tvbox_power, R.id.tvbox_menu, R.id.txbox_back, R.id.channel_add, R.id.channel_sub, R.id.sound_add, R.id.sound_sub, R.id.tvbox_up, R.id.tvbox_ok, R.id.tvbox_left, R.id.tvbox_right, R.id.tvbox_down, R.id.num_1, R.id.num_2, R.id.num_3, R.id.num_4, R.id.num_5, R.id.num_6, R.id.num_7, R.id.num_8, R.id.num_9, R.id.num_0})
    public void onClick(View view) {
        System.out.println("click");
        switch (view.getId()) {
            case R.id.tvbox_power:
                send(view, "power");
                break;
            case R.id.tvbox_menu:
                send(view, "menu");
                break;
            case R.id.txbox_back:
                send(view, "back");
                break;
            case R.id.channel_add:
                send(view, "chadd");
                break;
            case R.id.channel_sub:
                send(view, "chsub");
                break;
            case R.id.sound_add:
                send(view, "voladd");
                break;
            case R.id.sound_sub:
                send(view, "volsub");
                break;
            case R.id.tvbox_up:
                send(view, "up");
                break;
            case R.id.tvbox_ok:
                send(view, "ok");
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
            case R.id.num_1:
                send(view, "1");
                break;
            case R.id.num_2:
                send(view, "2");
                break;
            case R.id.num_3:
                send(view, "3");
                break;
            case R.id.num_4:
                send(view, "4");
                break;
            case R.id.num_5:
                send(view, "5");
                break;
            case R.id.num_6:
                send(view, "6");
                break;
            case R.id.num_7:
                send(view, "7");
                break;
            case R.id.num_8:
                send(view, "8");
                break;
            case R.id.num_9:
                send(view, "9");
                break;
            case R.id.num_0:
                send(view, "0");
                break;
        }
    }



}
