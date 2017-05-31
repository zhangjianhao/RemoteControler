package com.zjianhao.module.pc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjianhao.base.BaseActivity;
import com.zjianhao.module.pc.adapter.PCHostAdapter;
import com.zjianhao.module.pc.model.Host;
import com.zjianhao.module.pc.util.BroadcastDiscovery;
import com.zjianhao.universalcontroller.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 张建浩（Clarence) on 2017-4-11 16:23.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class SearchHostAty extends BaseActivity implements BroadcastDiscovery.DiscoverHostListener {
    @InjectView(R.id.radar_scanning)
    ImageView scanning;
    @InjectView(R.id.recycleView)
    RecyclerView recycleView;
    @InjectView(R.id.no_host_remind_info)
    TextView noHostRemindInfo;
    private RotateAnimation animation;
    private PCHostAdapter adapter;
    private ArrayList<Host> hosts = new ArrayList<>();
    private BroadcastDiscovery discovery;
    public static final int STOP_DISCOVERY_HOST_COMMAND = 1;
    public static final int REQUEST_SEARCH_HOST = 0;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case STOP_DISCOVERY_HOST_COMMAND:
                    stopRadarAnimation();
                    if (hosts.size() == 0)
                        noHostRemindInfo.setVisibility(View.VISIBLE);
                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra("hosts", hosts);
                    setResult(REQUEST_SEARCH_HOST,intent);
                    finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_scan_main);
        ButterKnife.inject(this);
        startRadarAnimation();
        adapter = new PCHostAdapter(this, hosts);
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        discovery = new BroadcastDiscovery(this);
        discovery.setOnDiscoverHostListener(this);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(STOP_DISCOVERY_HOST_COMMAND);
            }
        },4000);
    }

    @Override
    protected void onResume() {
        super.onResume();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                discovery.start();
            }
        },1000);

    }

    public void startRadarAnimation() {
        animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2000);
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(Animation.INFINITE);
        LinearInterpolator lir = new LinearInterpolator();
        animation.setInterpolator(lir);
        animation.setFillAfter(true);
        scanning.startAnimation(animation);
    }

    public void stopRadarAnimation() {
        if (animation != null)
            animation.cancel();
    }

    @Override
    public void onDiscovery(String ip, String hostname, String sysType) {
        final Host host = new Host(hostname, ip, sysType);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.addData(host);
            }
        });
    }

    @Override
    public void onFailure(Exception e) {
        e.printStackTrace();
        showSnackbar(recycleView, "获取主机失败");
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        discovery.stopDiscovery();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        discovery.stopDiscovery();
    }
}
