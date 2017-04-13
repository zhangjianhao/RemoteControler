package com.zjianhao.module.pc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.zjianhao.base.BaseActivity;
import com.zjianhao.module.pc.adapter.PCHostAdapter;
import com.zjianhao.module.pc.model.Host;
import com.zjianhao.module.pc.util.BroadcastDiscovery;
import com.zjianhao.universalcontroller.R;

import java.util.ArrayList;
import java.util.List;

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
    private RotateAnimation animation;
    private PCHostAdapter adapter;
    private List<Host> hosts = new ArrayList<>();
    private BroadcastDiscovery discovery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_scan_main);
        ButterKnife.inject(this);
        startRadarAnimation();
        adapter = new PCHostAdapter(this,hosts);
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        discovery = new BroadcastDiscovery(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        discovery.start();
    }

    public void startRadarAnimation() {
        animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2000);
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(Animation.INFINITE);
        LinearInterpolator lir = new LinearInterpolator();
        animation.setInterpolator(lir);
        scanning.startAnimation(animation);
    }

    public void stopRadarAnimation() {
        if (animation != null)
            animation.cancel();
    }

    @Override
    public void onDiscovery(String ip, String hostname, String sysType) {
        Host host = new Host(hostname,ip,sysType);
        adapter.addData(host);
    }

    @Override
    public void onFailure(Exception e) {
        showSnackbar(recycleView,"获取主机失败");
    }
}
