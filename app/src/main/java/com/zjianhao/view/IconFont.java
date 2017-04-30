package com.zjianhao.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Cocbin on 2016/07/30.
 * E-mail 460469837@qq.com
 */
public class IconFont extends TextView {
    public IconFont(Context context) {
        super(context);
        init();
    }

    public IconFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        if (!isInEditMode()) {
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "iconfont.ttf");
            if (typeface != null)
                setTypeface(typeface);
        }
    }
}
