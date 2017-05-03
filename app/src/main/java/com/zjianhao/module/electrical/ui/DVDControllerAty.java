package com.zjianhao.module.electrical.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zjianhao.module.electrical.util.NumberPopupWindow;
import com.zjianhao.universalcontroller.R;

import at.markushi.ui.CircleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 张建浩（Clarence) on 2017-4-23 22:51.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class DVDControllerAty extends ControllerAty implements NumberPopupWindow.NumberClickListener {

    @InjectView(R.id.dvd_ok)
    Button dvdOk;
    @InjectView(R.id.dvd_up)
    CircleButton dvdUp;
    @InjectView(R.id.dvd_left)
    CircleButton dvdLeft;
    @InjectView(R.id.dvd_down)
    CircleButton dvdDown;
    @InjectView(R.id.dvd_right)
    CircleButton dvdRight;
    @InjectView(R.id.dvd_power)
    LinearLayout dvdPower;
    @InjectView(R.id.dvd_menu)
    LinearLayout dvdMenu;
    @InjectView(R.id.dvd_mute)
    LinearLayout dvdMute;
    @InjectView(R.id.dvd_exit)
    LinearLayout dvdExit;
    @InjectView(R.id.sound_sub)
    LinearLayout soundSub;
    @InjectView(R.id.sound_add)
    LinearLayout soundAdd;
    @InjectView(R.id.num_keys)
    LinearLayout numKeys;
    @InjectView(R.id.dvd_previous)
    LinearLayout dvdPrevious;
    @InjectView(R.id.dvd_back_fast)
    LinearLayout dvdBackFast;
    @InjectView(R.id.dvd_play)
    LinearLayout dvdPlay;
    @InjectView(R.id.dvd_go_fast)
    LinearLayout dvdGoFast;
    @InjectView(R.id.dvd_next)
    LinearLayout dvdNext;
    @InjectView(R.id.dvd_pause)
    LinearLayout dvdPause;
    private boolean isPlay = false;

    private NumberPopupWindow numberPopupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ele_controller_dvd_main);
        ButterKnife.inject(this);

    }

    @OnClick({R.id.dvd_ok, R.id.dvd_up, R.id.dvd_left, R.id.dvd_down, R.id.dvd_right, R.id.dvd_power,
            R.id.dvd_menu, R.id.dvd_mute, R.id.dvd_exit, R.id.sound_sub, R.id.sound_add,
            R.id.num_keys, R.id.dvd_previous, R.id.dvd_back_fast, R.id.dvd_play,
            R.id.dvd_go_fast, R.id.dvd_next, R.id.dvd_pause})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dvd_ok:
                send(view, "ok");
                break;
            case R.id.dvd_up:
                send(view, "up");
                break;
            case R.id.dvd_left:
                send(view, "left");
                break;
            case R.id.dvd_down:
                send(view, "down");
                break;
            case R.id.dvd_right:
                send(view, "right");
                break;
            case R.id.dvd_power:
                send(view, "power");
                break;
            case R.id.dvd_menu:
                send(view, "menu");
                break;
            case R.id.dvd_mute:
                send(view, "mute");
                break;
            case R.id.dvd_exit:
                send(view, "exit");
                break;
            case R.id.sound_sub:
                send(view, "volsub");
                break;
            case R.id.sound_add:
                send(view, "voladd");
                break;
            case R.id.num_keys:
                togglePopueWindow();
                break;
            case R.id.dvd_previous:
                send(view, "previous");
                break;
            case R.id.dvd_back_fast:
                send(view, "rew");
                break;
            case R.id.dvd_play:
                send(view, "play");
                break;
            case R.id.dvd_go_fast:
                send(view, "ff");
                break;
            case R.id.dvd_next:
                send(view, "next");
                break;
            case R.id.dvd_pause:
                send(view, "pause");
                break;
        }
    }

    private void togglePopueWindow() {
        if (numberPopupWindow == null) {
            numberPopupWindow = new NumberPopupWindow(this, this);
            numberPopupWindow.showAtLocation(numKeys,
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        } else if (numberPopupWindow.isShowing()) {
            numberPopupWindow.dismiss();
        } else {
            numberPopupWindow.showAtLocation(numKeys,
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (numberPopupWindow != null && numberPopupWindow.isShowing())
            numberPopupWindow.dismiss();
    }

    @Override
    public void onBackPressed() {
        //销毁数字框
        if (numberPopupWindow != null && numberPopupWindow.isShowing())
            numberPopupWindow.dismiss();
        else
            super.onBackPressed();
    }

    @Override
    public void onNumberClick(int num) {
        System.out.println("click:" + num);
        send(numKeys, num + "");
    }


}
