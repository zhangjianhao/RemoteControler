package com.zjianhao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zjianhao.base.BaseActivity;
import com.zjianhao.http.DefaultCallback;
import com.zjianhao.http.ResponseHeader;
import com.zjianhao.http.RetrofitManager;
import com.zjianhao.model.User;
import com.zjianhao.universalcontroller.AppApplication;
import com.zjianhao.universalcontroller.MainActivity;
import com.zjianhao.universalcontroller.R;
import com.zjianhao.utils.MD5Util;
import com.zjianhao.utils.SharePreferenceUtils;

import retrofit2.Call;

import static com.zjianhao.universalcontroller.R.id.password_et;
import static com.zjianhao.universalcontroller.R.id.username_et;
import static com.zjianhao.utils.SharePreferenceUtils.getStringValue;

/**
 * Created by 张建浩（Clarence) on 2017-5-26 00:02.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class UserLoginAty extends BaseActivity implements View.OnClickListener {
    private EditText usernameEt;
    private EditText passwordEt;
    private Button loginBtn;
    private TextView registNowTv;
    private TextView aa;
    private ImageView fingerLoginEntrance;
    private RelativeLayout fingerLoginArea;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login_main);
        initView();

    }

    private void initView() {
        usernameEt = (EditText) findViewById(username_et);
        passwordEt = (EditText) findViewById(password_et);

        String username = getStringValue(this, "user", "username");
        String password = SharePreferenceUtils.getStringValue(this, "user", "password");
        if (username != null)
            usernameEt.setText(username);
        if (password != null)
            passwordEt.setText(MD5Util.convertMD5(password));

        loginBtn = (Button) findViewById(R.id.login_btn);
        registNowTv = (TextView) findViewById(R.id.regist_now_tv);
        registNowTv.setOnClickListener(this);
        aa = (TextView) findViewById(R.id.aa);
        fingerLoginEntrance = (ImageView) findViewById(R.id.finger_login_entrance);
        fingerLoginEntrance.setOnClickListener(this);
        fingerLoginArea = (RelativeLayout) findViewById(R.id.finger_login_area);
        if (SharePreferenceUtils.getBoolValue(this, "user", "fingerprint_auth", false))
            fingerLoginArea.setVisibility(View.VISIBLE);

        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                if (validate()) {
                    login();
                }

                break;
            case R.id.finger_login_entrance:
                Intent intent = new Intent(this, FingerprintLogin.class);
                startActivity(intent);
                break;
            case R.id.regist_now_tv:
                Intent intent1 = new Intent(this, RegistAty.class);
                startActivity(intent1);
                break;
        }
    }


    public void login() {
        String username = usernameEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();
        password = MD5Util.convertMD5(password);
        SharePreferenceUtils.save(this, "user", "username", username);
        SharePreferenceUtils.save(this, "user", "password", password);
        Call<ResponseHeader<User>> call = RetrofitManager.getUserApi().login(username, password);
        call.enqueue(new DefaultCallback<User>(usernameEt) {
            @Override
            public void onResponse(User data) {
                ((AppApplication) getApplication()).setUser(data);
                SharePreferenceUtils.save(UserLoginAty.this, "user", "user_id", data.getUserId());
                SharePreferenceUtils.save(UserLoginAty.this, "user", "token", data.getToken());
                Intent intent = new Intent(UserLoginAty.this, MainActivity.class);
                startActivity(intent);
            }

        });
    }

    private boolean validate() {
        String et = usernameEt.getText().toString().trim();
        if (TextUtils.isEmpty(et)) {
            Toast.makeText(this, "用户名", Toast.LENGTH_SHORT).show();
            return false;
        }

        String password = passwordEt.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
