package com.zjianhao.module.pc.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zjianhao.base.NavigatorActivity;
import com.zjianhao.universalcontroller.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 张建浩（Clarence) on 2017-4-13 16:08.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class PCMenuAty extends NavigatorActivity {
    @InjectView(R.id.remote_shutdown_btn)
    Button remoteShutdownBtn;
    @InjectView(R.id.remote_ppt_btn)
    Button remotePptBtn;
    @InjectView(R.id.remote_mouse_btn)
    Button remoteMouseBtn;
    @InjectView(R.id.adb_wireless_debug_btn)
    Button adbWirelessDebugBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_menu_function_menu);
        ButterKnife.inject(this);
    }





    @OnClick({R.id.remote_shutdown_btn, R.id.remote_ppt_btn, R.id.remote_mouse_btn, R.id.adb_wireless_debug_btn})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.remote_shutdown_btn:
                intent = new Intent(this, RemoteShutdownAty.class);
                startActivity(intent);
                break;
            case R.id.remote_ppt_btn:
                intent = new Intent(this, PPTOpenremindAty.class);
                startActivity(intent);
                break;
            case R.id.remote_mouse_btn:
                intent = new Intent(this, MouseControlAty.class);
                startActivity(intent);
                break;
            case R.id.adb_wireless_debug_btn:
                intent = new Intent(this, AdbDebugAty.class);
                startActivity(intent);
                break;
        }
    }
}
