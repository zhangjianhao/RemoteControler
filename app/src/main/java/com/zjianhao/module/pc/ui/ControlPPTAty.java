package com.zjianhao.module.pc.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.zjianhao.base.NavigatorActivity;
import com.zjianhao.module.pc.util.PCCommand;
import com.zjianhao.universalcontroller.AppApplication;
import com.zjianhao.universalcontroller.R;

import static com.zjianhao.utils.UdpSender.sendOrderSyn;

/**
 * Created by 张建浩 on 2017/3/8
 */
public class ControlPPTAty extends NavigatorActivity implements View.OnClickListener{
    private static String TAG = ControlPPTAty.class.getName();
    private Button clickUp;
    private Button clickDown;
    private Button startFullScreen;
    private Button exitFullScreen;
    private String ip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_ppt_control);
        clickUp = (Button)findViewById(R.id.click_up);
        clickUp.setOnClickListener(this);
        clickDown = (Button)findViewById(R.id.click_down);
        clickDown.setOnClickListener(this);
        startFullScreen = (Button)findViewById(R.id.start_ppt_fullscreen);
        startFullScreen.setOnClickListener(this);
        exitFullScreen = (Button)findViewById(R.id.exit_ppt_fullscreen);
        exitFullScreen.setOnClickListener(this);
        setTitle("遥控ppt");

        AppApplication application  = (AppApplication)getApplication();
        ip  = application.getIp();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.click_up:
                Log.v(TAG,"向上");
                sendOrderSyn(ip, PCCommand.KEY_EVENT_UP);
                break;
            case R.id.click_down:
                Log.v(TAG,"向下");
                sendOrderSyn(ip, PCCommand.KEY_EVENT_DOWN);
                break;
            case R.id.start_ppt_fullscreen:
               sendOrderSyn(ip,PCCommand.KEY_EVENT_F5);
                break;
            case R.id.exit_ppt_fullscreen:
                sendOrderSyn(ip,PCCommand.KEY_EVENT_ESC);
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_VOLUME_UP:
                sendOrderSyn(ip,PCCommand.KEY_EVENT_UP);
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                sendOrderSyn(ip,PCCommand.KEY_EVENT_DOWN);
                break;
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
        }
        return true;
    }
}
