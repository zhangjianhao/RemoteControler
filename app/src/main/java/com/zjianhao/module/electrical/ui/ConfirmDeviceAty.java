package com.zjianhao.module.electrical.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.zjianhao.base.NavigatorActivity;
import com.zjianhao.module.pc.util.CmdUtil;
import com.zjianhao.universalcontroller.R;
import com.zjianhao.utils.CircularAnim;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 张建浩（Clarence) on 2017-4-23 10:37.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class ConfirmDeviceAty extends NavigatorActivity {
    @InjectView(R.id.device_name)
    EditText deviceName;
    @InjectView(R.id.device_add_confirm)
    Button deviceAddConfirm;
    @InjectView(R.id.key_load_progress)
    FrameLayout keyLoadProgress;
    private int deviceId;
    private int typeId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ele_device_confirm_main);
        ButterKnife.inject(this);
        String brandName = getIntent().getStringExtra("brand_name");
        typeId = getIntent().getIntExtra("type_id", 1);
        deviceId = getIntent().getIntExtra("device_id", 0);
        deviceName.setText(brandName + CmdUtil.getTypeName(typeId));
    }

    @OnClick(R.id.device_add_confirm)
    public void onClick() {
        CircularAnim.hide(deviceAddConfirm).go();
        keyLoadProgress.setVisibility(View.VISIBLE);
    }

    public void loadKeyList() {
        switch (typeId) {
            case 3:
                loadAirKeys();
                break;
            default:

        }
    }

    private void loadAirKeys() {

    }

    private void loadOtherKeys() {

    }
}
