package com.zjianhao.universalcontroller;

import android.app.Application;

/**
 * Created by 张建浩（Clarence) on 2017-4-13 16:38.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class AppApplication extends Application {
    private String ip;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
