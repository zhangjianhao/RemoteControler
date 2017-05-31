package com.zjianhao.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.zjianhao.universalcontroller.BuildConfig;
import com.zjianhao.universalcontroller.R;

import java.util.ArrayList;

import static com.zjianhao.service.Action.ACTION_START_BAIDU_AUDIO;

/**
 * Created by 张建浩（Clarence) on 2017-4-10 16:31.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class BaseActivity extends AppCompatActivity {
    private AppManager manager = AppManager.getAppManager();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        manager.addActivity(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.finishActivity(this);
    }


    public void showSnackbar(View view,String msg){
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
                .setActionTextColor(getResources().getColor(R.color.colorAccent))
                .setDuration(3000).show();
    }
}
