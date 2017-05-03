package com.zjianhao.module.electrical.util;

import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.os.Build;

import static android.content.Context.CONSUMER_IR_SERVICE;

/**
 * Created by 张建浩（Clarence) on 2017-4-24 20:02.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class InfraredUtil {

    private ConsumerIrManager manager;
    private Context context;

    public InfraredUtil(Context context) {
        this.context = context;
    }

    public boolean isSupportInfrared() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return false;
        if (manager == null)
            manager = (ConsumerIrManager) context.getSystemService(CONSUMER_IR_SERVICE);
        return manager.hasIrEmitter();
    }

    public void send(int carrierFrequency, int[] pattern) {
        if (manager == null)
            manager = (ConsumerIrManager) context.getSystemService(CONSUMER_IR_SERVICE);
        manager.transmit(carrierFrequency, pattern);
    }

    public void send(String data) {
        System.out.println("send:" + data);
        if (data == null)
            return;
        if (manager == null)
            manager = (ConsumerIrManager) context.getSystemService(CONSUMER_IR_SERVICE);
        String frequency = data.substring(0, data.indexOf(','));
        String patterns = data.substring(data.indexOf(',') + 1);

        String[] str = patterns.split(",");
        int[] pattern = new int[str.length];
        for (int i = 0; i < str.length; i++)
            pattern[i] = Integer.parseInt(str[i]);
        System.out.println(Integer.parseInt(frequency) + "," + pattern);

        if (isSupportInfrared()) {
            manager.transmit(Integer.parseInt(frequency), pattern);
            System.out.println("send success");
        } else
            System.out.println("not support");
    }


}
