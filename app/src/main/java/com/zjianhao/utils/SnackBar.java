package com.zjianhao.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by 张建浩（Clarence) on 2017-4-13 09:50.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class SnackBar {
    public static void show(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
                .setDuration(3000).show();
    }
}
