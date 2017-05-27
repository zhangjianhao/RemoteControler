package com.zjianhao.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zjianhao.http.DefaultCallback;
import com.zjianhao.http.ResponseHeader;
import com.zjianhao.http.RetrofitManager;
import com.zjianhao.universalcontroller.R;
import com.zjianhao.utils.MD5Util;


/**
 * Created by zjianhao on 16-6-12.
 */
public class RegistAty extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = RegistAty.class.getName();
    private Button summitUserInfo;
    private EditText passwordEt;
    private EditText confirmPasswordEt;
    private EditText emailEt;
    private EditText usernameEt;
    private Toolbar mainToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_regist_main);
        summitUserInfo = (Button) findViewById(R.id.summit_userinfo_btn);
        summitUserInfo.setOnClickListener(this);
        passwordEt = (EditText) findViewById(R.id.password_et);
        confirmPasswordEt = (EditText) findViewById(R.id.confirm_password_et);
        emailEt = (EditText) findViewById(R.id.email_et);
        usernameEt = (EditText) findViewById(R.id.username_et);
        mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);

        setSupportActionBar(mainToolbar);
        if (android.os.Build.VERSION.SDK_INT >= 21)
            mainToolbar.setElevation(20);
        mainToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mainToolbar.setTitleTextColor(Color.WHITE);
        mainToolbar.setTitle("注册");


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.summit_userinfo_btn:
                if (checkUpdateUI())
                    registUser();
                break;
        }


    }

    public void registUser() {
        String email = emailEt.getText().toString().trim();
        String username = usernameEt.getText().toString().trim();
        String password = MD5Util.convertMD5(passwordEt.getText().toString().trim());
        retrofit2.Call<ResponseHeader<Integer>> ca = RetrofitManager.getUserApi().regist(username, password, email);
        ca.enqueue(new DefaultCallback<Integer>(usernameEt) {
            @Override
            public void onResponse(Integer data) {
                if (data == -100) {
                    Toast.makeText(RegistAty.this, "该用户已经存在", Toast.LENGTH_SHORT).show();
                } else if (data == 200) {
                    Toast.makeText(RegistAty.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistAty.this, UserLoginAty.class);
                    startActivity(intent);
                }
            }
        });

    }

    public boolean checkUpdateUI() {
        if (usernameEt.getText().toString().length() < 1) {
            Toast.makeText(RegistAty.this, R.string.please_input_username, Toast.LENGTH_SHORT).show();

            return false;
        } else if (emailEt.getText().toString().length() < 1) {
            Toast.makeText(RegistAty.this, R.string.please_input_email, Toast.LENGTH_SHORT).show();
            return false;

        } else if (passwordEt.getText().toString().length() < 1) {
            Toast.makeText(RegistAty.this, R.string.please_input_password, Toast.LENGTH_SHORT).show();
            return false;

        } else if (!passwordEt.getText().toString().equals(confirmPasswordEt.getText().toString())) {
            Toast.makeText(RegistAty.this, R.string.password_not_equal, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}
