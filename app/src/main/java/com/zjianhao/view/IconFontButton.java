package com.zjianhao.view;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by 张建浩（Clarence) on 2017-5-1 16:54.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class IconFontButton extends Button {
    public static final int SHAPE_RECTANGLE = 1;
    public static final int SHAPE_CIRCLE = 2;

    public IconFontButton(Context context) {
        super(context);
        init();
    }

    public IconFontButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IconFontButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        if (!isInEditMode()) {
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "iconfont.ttf");
            if (typeface != null)
                setTypeface(typeface);
        }
    }

    public void setShape(int shape) {
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        Shape sha = null;
        switch (shape) {
            case SHAPE_CIRCLE:
                sha = new OvalShape();

                break;
            case SHAPE_RECTANGLE:
                sha = new RectShape();
                break;
        }
        shapeDrawable.setShape(sha);
    }


}
