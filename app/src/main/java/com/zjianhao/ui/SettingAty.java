package com.zjianhao.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

import com.zjianhao.base.NavigatorActivity;
import com.zjianhao.universalcontroller.R;
import com.zjianhao.utils.CircularAnimUtil;
import com.zjianhao.utils.FingerprintUtil;
import com.zjianhao.utils.SharePreferenceUtils;

/**
 * Created by 张建浩（Clarence) on 2017-5-25 23:32.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class SettingAty extends NavigatorActivity {
    private SwitchCompat autoBackup;
    private SwitchCompat fingerprintLoginSwitcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_setting_main);
        initView();

    }

    private void initView() {
        autoBackup = (SwitchCompat) findViewById(R.id.auto_backup_switcher);
        autoBackup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharePreferenceUtils.save(SettingAty.this, "user", "auto_backup", isChecked);
            }
        });
        autoBackup.setChecked(SharePreferenceUtils.getBoolValue(this, "user", "auto_backup", true));
        fingerprintLoginSwitcher = (SwitchCompat) findViewById(R.id.fingerprint_login_switcher);
        fingerprintLoginSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!buttonView.isPressed())
                    return;
                if (FingerprintUtil.isSupportFingerprint(SettingAty.this)) {
                    if (isChecked) {
                        showBindFingerprintDialog();
                    } else {
                        showShutdownFingerprintDialog();
                    }
                } else {
                    //不支持指纹登陆，弹出提示框
                    fingerprintLoginSwitcher.setChecked(false);
                    showNotSupportDialog();
                }
            }
        });
        fingerprintLoginSwitcher.setChecked(SharePreferenceUtils.getBoolValue(this, "user", "fingerprint_auth", false));
    }


    private void showNotSupportDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("温馨提示")
                .setMessage("您的手机暂不支持指纹登陆")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    private void showBindFingerprintDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("温馨提示")
                .setMessage("启用指纹登陆需要绑定指纹信息")
                .setPositiveButton("去绑定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SettingAty.this, FingerprintBindAty.class);
                        CircularAnimUtil.startActivity(SettingAty.this, intent, fingerprintLoginSwitcher, R.color.colorAccent, 100);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        boolean canUseFingerprint = SharePreferenceUtils.getBoolValue(SettingAty.this, "user", "fingerprint_auth", false);
                        fingerprintLoginSwitcher.setChecked(canUseFingerprint);
                    }
                })
                .show();
    }

    public void showShutdownFingerprintDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("温馨提示")
                .setMessage("确定要关闭指纹登陆吗?关闭后下次使用指纹登陆需要重新绑定用户信息")
                .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharePreferenceUtils.save(SettingAty.this, "user", "fingerprint_auth", false);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        fingerprintLoginSwitcher.setChecked(true);
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        boolean canUseFingerprint = SharePreferenceUtils.getBoolValue(SettingAty.this, "user", "fingerprint_auth", false);
                        fingerprintLoginSwitcher.setChecked(canUseFingerprint);
                    }
                })
                .show();

    }
}
