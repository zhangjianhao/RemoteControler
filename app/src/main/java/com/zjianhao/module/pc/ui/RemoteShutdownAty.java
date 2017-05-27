package com.zjianhao.module.pc.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zjianhao.base.NavigatorActivity;
import com.zjianhao.module.pc.util.PCCommand;
import com.zjianhao.universalcontroller.AppApplication;
import com.zjianhao.universalcontroller.R;
import com.zjianhao.utils.UdpSender;


public class RemoteShutdownAty extends NavigatorActivity {
    private Button shutdown;
    private String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_shutdown_main);
        AppApplication application  = (AppApplication)getApplication();
        ip  = application.getIp();
        shutdown = (Button)findViewById(R.id.shutdown_btn);
        shutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shutdown.setBackgroundResource(R.drawable.shutdown_btn_style_press);
                UdpSender.sendOrderSyn(ip, PCCommand.SHUTDOWN_COMMAND);
            }
        });
        setTitle("遥控关机");
    }

}
