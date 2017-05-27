package com.zjianhao.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zjianhao.base.BaseActivity;
import com.zjianhao.universalcontroller.R;
import com.zjianhao.utils.SharePreferenceUtils;

/**
 * Created by 张建浩（Clarence) on 2016-11-18 17:41.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class FingerprintBind2Aty extends BaseActivity {
    private String userId;
    private String token;
    private FingerprintManager manager;
    private TextView fingerBindInfo;
    private CancellationSignal mCancellationSignal = new CancellationSignal();
    private Toolbar mainToolbar;
    private int errorCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.finger_bind_two_main);
        initView();
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        token = intent.getStringExtra("token");
        manager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
    }

    @Override
    protected void onResume() {
        if (!manager.hasEnrolledFingerprints()) {
            fingerBindInfo.setTextColor(Color.RED);
            fingerBindInfo.setText("检测到指纹库中不存在指纹，请先在系统设置中录入指纹之后再进行信息绑定");

        } else {
            manager.authenticate(null, mCancellationSignal, 0, new MyCallBack(), null);
        }

        super.onResume();
    }

    private void initView() {
        fingerBindInfo = (TextView) findViewById(R.id.finger_bind_info);
        mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);

        mainToolbar.setTitle("返回");
        mainToolbar.setTitleTextColor(Color.WHITE);
        mainToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class MyCallBack extends FingerprintManager.AuthenticationCallback {
        private static final String TAG = "MyCallBack";

        // 当出现错误的时候回调此函数，比如多次尝试都失败了的时候，errString是错误信息
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            showRegistFingerprintInfo();
        }

        // 当指纹验证失败的时候会回调此函数，失败之后允许多次尝试，失败次数过多会停止响应一段时间然后再停止sensor的工作
        @Override
        public void onAuthenticationFailed() {
            Toast.makeText(FingerprintBind2Aty.this, "指纹验证失败", Toast.LENGTH_SHORT).show();
            errorCount++;
            if (errorCount >= 5) {
                errorCount = 0;
                showRegistFingerprintInfo();
            }
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {

        }

        // 当验证的指纹成功时会回调此函数，然后不再监听指纹sensor


        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            errorCount = 0;
            SharePreferenceUtils.save(FingerprintBind2Aty.this, "user", "fingerprint_auth", true);
            SharePreferenceUtils.save(FingerprintBind2Aty.this, "user", "user_id", userId);
            SharePreferenceUtils.save(FingerprintBind2Aty.this, "user", "token", token);
            showLoginDialog();

        }

    }

    public void showLoginDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("温馨提示")
                .setMessage("验证成功,可以使用指纹登陆了")
                .setPositiveButton("去体验指纹登陆", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(FingerprintBind2Aty.this, FingerprintLogin.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void showRegistFingerprintInfo() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("温馨提示")
                .setMessage("失败次数过多,可能指纹库中不存在您的指纹信息。您可以去系统设置中录入指纹")
                .setPositiveButton("去录入指纹", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("稍后再试", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mCancellationSignal != null && !mCancellationSignal.isCanceled())
            mCancellationSignal.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
