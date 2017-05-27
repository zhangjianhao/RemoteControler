package com.zjianhao.module.pc.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.zjianhao.universalcontroller.R;

/**
 * Created by 张建浩 on 2017/3/5.
 */
public class MouseControlAty extends Activity {
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_mouse_control_main);
        mFragmentManager = getFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        MouseFragment mouseFragment = new MouseFragment();
        transaction.replace(R.id.mouse_control_container,mouseFragment);
        transaction.commit();

    }
}
