package com.zjianhao.ui;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.zjianhao.base.BaseActivity;
import com.zjianhao.http.DefaultCallback;
import com.zjianhao.http.ResponseHeader;
import com.zjianhao.http.RetrofitManager;
import com.zjianhao.model.User;
import com.zjianhao.module.electrical.model.Brand;
import com.zjianhao.universalcontroller.AppApplication;
import com.zjianhao.universalcontroller.Constant;
import com.zjianhao.universalcontroller.MainActivity;
import com.zjianhao.universalcontroller.R;
import com.zjianhao.utils.SharePreferenceUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;


/**
 * Created by 张建浩（Clarence) on 2016-11-18 23:54.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class FingerprintLogin extends BaseActivity {

    private FingerprintManager manager;
    private CancellationSignal mCancellationSignal = new CancellationSignal();
    private TextView fingerLoginInfo;
    private boolean loginFailure = false;
    private String level;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x00:
                    Toast.makeText(FingerprintLogin.this, "请求错误，请检查网络连接", Toast.LENGTH_SHORT).show();
                    break;

                case 0x02:
                    loginFailure = true;
                    showReBindDialog();
//                    Toast.makeText(FingerprintLogin.this, "用户信息验证失败", Toast.LENGTH_SHORT).show();

                    break;
                case 0x03:
                    Toast.makeText(FingerprintLogin.this, "数据解析失败", Toast.LENGTH_SHORT).show();
                    break;

            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.finger_login_main);
        initView();
        Intent intent = getIntent();
        if (intent != null)
            level = intent.getStringExtra("event_notification_level");

        manager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!manager.hasEnrolledFingerprints()) {
            fingerLoginInfo.setTextColor(Color.RED);
            fingerLoginInfo.setText("检测到指纹库中不存在指纹，请先在系统设置中录入指纹之后再进行信息绑定");

        } else {
            manager.authenticate(null, mCancellationSignal, 0, new MyCallBack(), null);

        }
    }

    private void initView() {
        fingerLoginInfo = (TextView) findViewById(R.id.finger_login_info);
    }


    private class MyCallBack extends FingerprintManager.AuthenticationCallback {
        private static final String TAG = "MyCallBack";

        // 当出现错误的时候回调此函数，比如多次尝试都失败了的时候，errString是错误信息
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            Toast.makeText(FingerprintLogin.this, "指纹验证错误，请稍后再试", Toast.LENGTH_SHORT).show();
        }

        // 当指纹验证失败的时候会回调此函数，失败之后允许多次尝试，失败次数过多会停止响应一段时间然后再停止sensor的工作
        @Override
        public void onAuthenticationFailed() {
            Toast.makeText(FingerprintLogin.this, "指纹验证失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {

        }

        // 当验证的指纹成功时会回调此函数，然后不再监听指纹sensor


        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            Toast.makeText(FingerprintLogin.this, "指纹验证成功", Toast.LENGTH_SHORT).show();
            commitUserInfo();

        }

    }

    private void commitUserInfo() {
        String userId = SharePreferenceUtils.getStringValue(this, "user", "user_id");
        String token = SharePreferenceUtils.getStringValue(this, "user", "token");
        System.out.println(userId + ":" + token);
        Call<ResponseHeader<User>> user = RetrofitManager.getUserApi().tokenVerify(userId, token);
        user.enqueue(new DefaultCallback<User>(fingerLoginInfo) {
            @Override
            public void onResponse(User data) {
                SharePreferenceUtils.save(FingerprintLogin.this, "user", "user_id", data.getUserId());
                SharePreferenceUtils.save(FingerprintLogin.this, "user", "token", data.getToken());
                ((AppApplication) FingerprintLogin.this.getApplication()).setUser(data);
                Intent intent = new Intent(FingerprintLogin.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    public void showReBindDialog() {
        //
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("温馨提示")
                .setMessage("服务端用户信息已变更,请重新绑定账户信息")
                .setPositiveButton("重新绑定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(FingerprintLogin.this, FingerprintBindAty.class);
                        startActivity(intent);
                        finish();
                    }
                })

                .show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (loginFailure) {//指纹 登陆失败的情况下，屏蔽back,home键，强制跳转至重新绑定用户信息界面
            if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME)
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCancellationSignal != null && !mCancellationSignal.isCanceled())
            mCancellationSignal.cancel();
    }
}
