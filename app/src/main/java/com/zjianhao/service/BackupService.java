package com.zjianhao.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjianhao.dao.DaoUtil;
import com.zjianhao.entity.Device;
import com.zjianhao.http.DefaultCallback;
import com.zjianhao.http.ResponseHeader;
import com.zjianhao.http.RetrofitManager;

import java.util.List;

import retrofit2.Call;

/**
 * Created by 张建浩（Clarence) on 2017-5-28 13:54.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class BackupService extends Service {

    private static final String TAG = BackupService.class.getName();

    /**
     * 备份设备action
     */
    public static final String ACTION_BACKUP_DEVICE = "backup_device";
    /**
     * 恢复设备action
     */
    public static final String ACTION_RESTORE_DEVICE = "restore_device";
    private DaoUtil daoUtil = new DaoUtil(this);

    public void backup(final String userId, final String token) {
        //第一步获取最后一次备份时间
        Toast.makeText(this, "开始备份设备", Toast.LENGTH_SHORT).show();
        Call<ResponseHeader<Long>> call = RetrofitManager.getUserApi().readBackupTime(userId, token);
        call.enqueue(new DefaultCallback<Long>(null) {
            @Override
            public void onResponse(Long data) {
                System.out.println("resonse :" + data);
                //第二步,读取数据库中设备数据
                DaoUtil daoUtil = new DaoUtil(BackupService.this);
                List<Device> deviceList = daoUtil.getLastestDevice(data);
                for (Device device : deviceList) {
                    Log.d(TAG, "需要备份的设备:" + device.getDevice_name());
                }
                if (deviceList.size() > 0)
                    uploadDevice(userId, token, deviceList);
            }
        });
    }


    private void uploadDevice(String userId, String token, List<Device> devices) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(devices);
            Call<ResponseHeader<Long>> call = RetrofitManager.getUserApi().uploadData(userId, token, json);
            call.enqueue(new DefaultCallback<Long>(null) {
                @Override
                public void onResponse(Long data) {
                    if (data == 200)
                        Toast.makeText(BackupService.this, "数据备份完成", Toast.LENGTH_SHORT).show();
                    else
                        Log.d(TAG, "数据备份失败");

                }
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public void restore(String userId, String token) {
        Call<ResponseHeader<List<Device>>> call = RetrofitManager.getUserApi().restoreData(userId, token);
        call.enqueue(new DefaultCallback<List<Device>>(null) {
            @Override
            public void onResponse(List<Device> data) {
                for (Device device : data) {
                    if (!daoUtil.isExist(device))
                        daoUtil.insertDevice(device);
                }
                Toast.makeText(BackupService.this, "数据还原完成", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String userId = intent.getStringExtra("user_id");
        String token = intent.getStringExtra("token");
        switch (intent.getAction()) {
            case ACTION_BACKUP_DEVICE:
                backup(userId, token);
                break;
            case ACTION_RESTORE_DEVICE:
                restore(userId, token);
                break;

        }

        return super.onStartCommand(intent, flags, startId);
    }
}
