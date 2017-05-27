package com.zjianhao.universalcontroller;

import android.app.Activity;
import android.content.Intent;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.zjianhao.module.electrical.util.InfraredUtil;

/**
 * Created by 张建浩（Clarence) on 2017-5-21 03:26.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class InfraredSendTest extends InstrumentationTestCase {
    private Activity activity;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent();
        intent.setClassName("com.zjianhao.universalcontroller", MainActivity.class.getName());
        activity = getInstrumentation().startActivitySync(intent);
    }

    public void testSendInfrared() throws Exception {
        InfraredUtil infraredUtil = new InfraredUtil(activity);
        System.out.println(infraredUtil.isSupportInfrared());
        if (infraredUtil.isSupportInfrared())
            infraredUtil.send("38000,1000,579,579,654 520");
        else
            Log.v("InfraredSendTest", "not support infrared");
    }
}
