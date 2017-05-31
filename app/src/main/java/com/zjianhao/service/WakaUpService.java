package com.zjianhao.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.widget.Toast;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.VoiceRecognitionService;
import com.zjianhao.model.User;
import com.zjianhao.module.pc.util.PCCommand;
import com.zjianhao.ui.UserLoginAty;
import com.zjianhao.universalcontroller.AppApplication;
import com.zjianhao.universalcontroller.BuildConfig;
import com.zjianhao.universalcontroller.Constant;
import com.zjianhao.universalcontroller.R;
import com.zjianhao.utils.UdpSender;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by 张建浩（Clarence) on 2017-5-30 13:58.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class WakaUpService extends Service implements RecognitionListener {

    private static final String TAG = WakaUpService.class.getName();
    private EventManager mWpEventManager;
    private SpeechRecognizer speechRecognizer;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        // 唤醒功能打开步骤
        // 1) 创建唤醒事件管理器
        mWpEventManager = EventManagerFactory.create(this, "wp");

        // 2) 注册唤醒事件监听器
        mWpEventManager.registerListener(new EventListener() {
            @Override
            public void onEvent(String name, String params, byte[] data, int offset, int length) {
                Log.d(TAG, String.format("event: name=%s, params=%s", name, params));
                try {
                    JSONObject json = new JSONObject(params);
                    if ("wp.data".equals(name)) { // 每次唤醒成功, 将会回调name=wp.data的时间, 被激活的唤醒词在params的word字段
                        String word = json.getString("word");
                        handleWakaupWord(word);
                        if (BuildConfig.DEBUG) Log.d(TAG, "唤醒成功, 唤醒词: " + word + "\r\n");
                    } else if ("wp.exit".equals(name)) {
                        if (BuildConfig.DEBUG) Log.d(TAG, "唤醒已经停止: " + params + "\r\n");
                    }
                } catch (JSONException e) {
                    throw new AndroidRuntimeException(e);
                }
            }
        });

        // 3) 通知唤醒管理器, 启动唤醒功能
        HashMap params = new HashMap();
        params.put("kws-file", "assets:///WakeUp.bin"); // 设置唤醒资源, 唤醒资源请到 http://yuyin.baidu.com/wake#m4 来评估和导出
        mWpEventManager.send("wp.start", new JSONObject(params).toString(), null, 0, 0);
        if (BuildConfig.DEBUG) Log.d(TAG, "语音唤醒服务开启");

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this, new ComponentName(this, VoiceRecognitionService.class));

        speechRecognizer.setRecognitionListener(this);

    }

    public void bindParams(Intent intent) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (sp.getBoolean("tips_sound", true)) {
            intent.putExtra(Constant.EXTRA_SOUND_START, R.raw.bdspeech_recognition_start);
            intent.putExtra(Constant.EXTRA_SOUND_END, R.raw.bdspeech_speech_end);
            intent.putExtra(Constant.EXTRA_SOUND_SUCCESS, R.raw.bdspeech_recognition_success);
            intent.putExtra(Constant.EXTRA_SOUND_ERROR, R.raw.bdspeech_recognition_error);
            intent.putExtra(Constant.EXTRA_SOUND_CANCEL, R.raw.bdspeech_recognition_cancel);
        }
        if (sp.contains(Constant.EXTRA_INFILE)) {
            String tmp = sp.getString(Constant.EXTRA_INFILE, "").replaceAll(",.*", "").trim();
            intent.putExtra(Constant.EXTRA_INFILE, tmp);
        }
        if (sp.getBoolean(Constant.EXTRA_OUTFILE, false)) {
            intent.putExtra(Constant.EXTRA_OUTFILE, "sdcard/outfile.pcm");
        }
        if (sp.getBoolean(Constant.EXTRA_GRAMMAR, false)) {
            intent.putExtra(Constant.EXTRA_GRAMMAR, "assets:///baidu_speech_grammar.bsg");
        }
        if (sp.contains(Constant.EXTRA_SAMPLE)) {
            String tmp = sp.getString(Constant.EXTRA_SAMPLE, "").replaceAll(",.*", "").trim();
            if (null != tmp && !"".equals(tmp)) {
                intent.putExtra(Constant.EXTRA_SAMPLE, Integer.parseInt(tmp));
            }
        }
        if (sp.contains(Constant.EXTRA_LANGUAGE)) {
            String tmp = sp.getString(Constant.EXTRA_LANGUAGE, "").replaceAll(",.*", "").trim();
            if (null != tmp && !"".equals(tmp)) {
                intent.putExtra(Constant.EXTRA_LANGUAGE, tmp);
            }
        }
        if (sp.contains(Constant.EXTRA_NLU)) {
            String tmp = sp.getString(Constant.EXTRA_NLU, "").replaceAll(",.*", "").trim();
            if (null != tmp && !"".equals(tmp)) {
                intent.putExtra(Constant.EXTRA_NLU, tmp);
            }
        }

        if (sp.contains(Constant.EXTRA_VAD)) {
            String tmp = sp.getString(Constant.EXTRA_VAD, "").replaceAll(",.*", "").trim();
            if (null != tmp && !"".equals(tmp)) {
                intent.putExtra(Constant.EXTRA_VAD, tmp);
            }
        }
        String prop = null;
        if (sp.contains(Constant.EXTRA_PROP)) {
            String tmp = sp.getString(Constant.EXTRA_PROP, "").replaceAll(",.*", "").trim();
            if (null != tmp && !"".equals(tmp)) {
                intent.putExtra(Constant.EXTRA_PROP, Integer.parseInt(tmp));
                prop = tmp;
            }
        }

        // offline asr
        {
            intent.putExtra(Constant.EXTRA_OFFLINE_ASR_BASE_FILE_PATH, "/sdcard/easr/s_1");
            if (null != prop) {
                int propInt = Integer.parseInt(prop);
                if (propInt == 10060) {
                    intent.putExtra(Constant.EXTRA_OFFLINE_LM_RES_FILE_PATH, "/sdcard/easr/s_2_Navi");
                } else if (propInt == 20000) {
                    intent.putExtra(Constant.EXTRA_OFFLINE_LM_RES_FILE_PATH, "/sdcard/easr/s_2_InputMethod");
                }
            }
//            intent.putExtra(Constant.EXTRA_OFFLINE_SLOT_DATA, buildTestSlotData());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWpEventManager.send("wp.stop", null, null, 0, 0);
        speechRecognizer.destroy();
        if (BuildConfig.DEBUG) Log.d(TAG, "语音唤醒服务关闭");
    }


    private void handleWakaupWord(String word) {
        System.out.println("word:" + word);
        switch (word) {
            case "开始播放":
                sendCmd(PCCommand.KEY_EVENT_F5);
                break;
            case "ok小浩":
                Intent intent = new Intent();
                bindParams(intent);
                speechRecognizer.startListening(intent);
                break;
            case "用户登陆":
                Intent intent2 = new Intent(Action.ACTION_START_BAIDU_AUDIO);
                sendBroadcast(intent2);
                break;
        }
    }

    private void sendCmd(int cmd) {
        String ip = ((AppApplication) getApplication()).getIp();
        if (ip != null) {
            UdpSender.sendOrderSyn(ip, cmd);
            if (BuildConfig.DEBUG) Log.d(TAG, "发送指令");
        }
    }

    private void handleCmdWord(String word) {
        if (word.contains("上一页"))
            sendCmd(PCCommand.KEY_EVENT_UP);
        else if (word.contains("下一页"))
            sendCmd(PCCommand.KEY_EVENT_DOWN);
        else if (word.contains("播放"))
            sendCmd(PCCommand.KEY_EVENT_F5);
        else if (word.contains("退出"))
            sendCmd(PCCommand.KEY_EVENT_ESC);
        else if (word.contains("设备备份"))
            startBackup();
        else if (word.contains("备份还原") || word.contains("设备还原"))
            restoreDevice();
    }

    private void startBackup() {
        User user = ((AppApplication) getApplication()).getUser();
        if (user == null) {
            Toast.makeText(this, "请先登陆", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, UserLoginAty.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, BackupService.class);
            intent.putExtra("user_id", user.getUserId());
            intent.putExtra("token", user.getToken());
            intent.setAction(BackupService.ACTION_BACKUP_DEVICE);
            startService(intent);
        }


    }

    private void restoreDevice() {
        User user = ((AppApplication) getApplication()).getUser();
        if (user == null) {
            Toast.makeText(this, "请先登陆", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, UserLoginAty.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, BackupService.class);
            intent.putExtra("user_id", user.getUserId());
            intent.putExtra("token", user.getToken());
            intent.setAction(BackupService.ACTION_BACKUP_DEVICE);
            startService(intent);
        }
    }

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {
        if (BuildConfig.DEBUG) Log.d(TAG, "on error");
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> nbest = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String result = Arrays.toString(nbest.toArray(new String[nbest.size()]));
        result = result.substring(1, result.length() - 1);
        if (BuildConfig.DEBUG)
            Log.d(TAG, "识别成功：" + result);
        handleCmdWord(result);
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
}
