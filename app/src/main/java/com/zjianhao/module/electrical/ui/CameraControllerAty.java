package com.zjianhao.module.electrical.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.zjianhao.universalcontroller.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 张建浩（Clarence) on 2017-4-24 10:41.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class CameraControllerAty extends ControllerAty {
    @InjectView(R.id.camera_power)
    Button cameraPower;
    @InjectView(R.id.camera_take_photos)
    Button cameraTakePhotos;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ele_controller_camera_main);
        ButterKnife.inject(this);

    }

    @OnClick({R.id.camera_power, R.id.camera_take_photos})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.camera_power:
                send(view, "power");
                break;
            case R.id.camera_take_photos:
                send(view, "2sec");
                break;
        }
    }


}
