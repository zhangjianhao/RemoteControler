package com.zjianhao.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zjianhao.base.BaseActivity;
import com.zjianhao.http.DefaultCallback;
import com.zjianhao.http.ResponseHeader;
import com.zjianhao.http.RetrofitManager;
import com.zjianhao.model.User;
import com.zjianhao.universalcontroller.R;
import com.zjianhao.utils.MD5Util;

import static com.zjianhao.utils.SharePreferenceUtils.getStringValue;

/**
 * Created by 张建浩（Clarence) on 2016-11-18 16:14.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class FingerprintBindAty extends BaseActivity implements View.OnClickListener {
    private EditText bindUsername;
    private EditText bindPassword;
    private Button nextStep;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x00:
                    Toast.makeText(FingerprintBindAty.this, "请求错误，请检查网络连接", Toast.LENGTH_SHORT).show();
                    break;
                case 0x01:
                    Intent nextStepIntent = new Intent(FingerprintBindAty.this, FingerprintBind2Aty.class);
                    nextStepIntent.putExtra("loginId", bindUsername.getText().toString().trim());
                    nextStepIntent.putExtra("password", bindPassword.getText().toString().trim());
                    startActivity(nextStepIntent);
                    finish();
                    break;
                case 0x02:
                    Toast.makeText(FingerprintBindAty.this, "验证失败,用户名或密码错误", Toast.LENGTH_SHORT).show();
                    break;
                case 0x03:
                    Toast.makeText(FingerprintBindAty.this, "数据解析失败", Toast.LENGTH_SHORT).show();
                    break;

            }
            super.handleMessage(msg);
        }
    };
    private Toolbar mainToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.finger_bind_one_main);
        initView();

    }

    private void initView() {
        bindUsername = (EditText) findViewById(R.id.bind_username);
        bindPassword = (EditText) findViewById(R.id.bind_password);

        String username = getStringValue(this, "user", "username");
        String password = getStringValue(this, "user", "password");
        if (username != null)
            bindUsername.setText(username);
        if (password != null)
            bindPassword.setText(MD5Util.convertMD5(password));

        nextStep = (Button) findViewById(R.id.next_step);


        nextStep.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_step:
                if (validate())
                    commitUserInfo();

                break;
        }
    }

    private void commitUserInfo() {
        String username = bindUsername.getText().toString().trim();
        String password = bindPassword.getText().toString().trim();
        password = MD5Util.convertMD5(password);
        retrofit2.Call<ResponseHeader<User>> call = RetrofitManager.getUserApi().login(username, password);
        call.enqueue(new DefaultCallback<User>(nextStep) {
            @Override
            public void onResponse(User data) {
                if (data != null) {
                    Intent nextStepIntent = new Intent(FingerprintBindAty.this, FingerprintBind2Aty.class);
                    nextStepIntent.putExtra("userId", data.getUserId());
                    nextStepIntent.putExtra("token", data.getToken());
                    startActivity(nextStepIntent);
                    finish();
                }
            }
        });


    }

    private boolean validate() {
        // validate
        String username = bindUsername.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "请确认用户名", Toast.LENGTH_SHORT).show();
            return false;
        }

        String password = bindPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请确认密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
