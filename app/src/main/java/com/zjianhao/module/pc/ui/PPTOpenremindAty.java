package com.zjianhao.module.pc.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import com.zjianhao.base.NavigatorActivity;
import com.zjianhao.universalcontroller.R;

/**
 * Created by 张建浩 on 2017/3/10.
 */
public class PPTOpenremindAty extends NavigatorActivity {
    private Button startControlPPT;
    private Button upBtn;
    private Button downBtn;
    private FragmentManager mFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_open_ppt_remind);
        mFragmentManager = getSupportFragmentManager();
        startControlPPT = (Button)findViewById(R.id.start_control_ppt);
        startControlPPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PPTOpenremindAty.this,ControlPPTAty.class);
                startActivity(intent);
                finish();
            }
        });
        setTitle("遥控ppt");

    }
}
