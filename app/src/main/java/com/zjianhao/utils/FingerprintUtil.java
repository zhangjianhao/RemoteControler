package com.zjianhao.utils;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;

import static android.content.Context.FINGERPRINT_SERVICE;

/**
 * Created by 张建浩（Clarence) on 2017-4-18 23:29.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class FingerprintUtil {

    public static boolean isSupportFingerprint(Context context) {
        try {
            Class.forName("android.hardware.fingerprint.FingerprintManager");
            FingerprintManager manager = (FingerprintManager) context.getSystemService(FINGERPRINT_SERVICE);
            return manager.isHardwareDetected();
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

}
