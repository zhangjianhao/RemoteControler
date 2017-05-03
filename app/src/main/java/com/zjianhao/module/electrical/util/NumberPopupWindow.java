package com.zjianhao.module.electrical.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.zjianhao.universalcontroller.R;

/**
 * Created by 张建浩（Clarence) on 2017-5-3 10:32.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class NumberPopupWindow extends PopupWindow implements View.OnClickListener {

    private Button num0;
    private Button num1;
    private Button num2;
    private Button num3;
    private Button num4;
    private Button num5;
    private Button num6;
    private Button num7;
    private Button num8;
    private Button num9;
    private NumberClickListener listener;

    public interface NumberClickListener {
        public void onNumberClick(int num);
    }


    public NumberPopupWindow(Context context, NumberClickListener listener) {
        super(context);
        this.listener = listener;
        setTouchable(true);
        setOutsideTouchable(true);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View popupView = inflater.inflate(R.layout.ele_controller_nums_popup, null);
        num0 = (Button) popupView.findViewById(R.id.num_0);
        num0.setOnClickListener(this);
        num1 = (Button) popupView.findViewById(R.id.num_1);
        num1.setOnClickListener(this);
        num2 = (Button) popupView.findViewById(R.id.num_2);
        num2.setOnClickListener(this);
        num3 = (Button) popupView.findViewById(R.id.num_3);
        num3.setOnClickListener(this);
        num4 = (Button) popupView.findViewById(R.id.num_4);
        num4.setOnClickListener(this);
        num5 = (Button) popupView.findViewById(R.id.num_5);
        num5.setOnClickListener(this);
        num6 = (Button) popupView.findViewById(R.id.num_6);
        num6.setOnClickListener(this);
        num7 = (Button) popupView.findViewById(R.id.num_7);
        num7.setOnClickListener(this);
        num8 = (Button) popupView.findViewById(R.id.num_8);
        num8.setOnClickListener(this);
        num9 = (Button) popupView.findViewById(R.id.num_9);
        num9.setOnClickListener(this);
        setContentView(popupView);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setAnimationStyle(R.style.pop_submenu_anim_style);
        popupView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = popupView.getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    public void onClick(View view) {
        System.out.println("click");
        switch (view.getId()) {
            case R.id.num_0:
                listener.onNumberClick(0);
                break;
            case R.id.num_1:
                listener.onNumberClick(1);
                break;
            case R.id.num_2:
                listener.onNumberClick(2);
                break;
            case R.id.num_3:
                listener.onNumberClick(3);
                break;
            case R.id.num_4:
                listener.onNumberClick(4);
                break;
            case R.id.num_5:
                listener.onNumberClick(5);
                break;
            case R.id.num_6:
                listener.onNumberClick(6);
                break;
            case R.id.num_7:
                listener.onNumberClick(7);
                break;
            case R.id.num_8:
                listener.onNumberClick(8);
                break;
            case R.id.num_9:
                listener.onNumberClick(9);
                break;
        }
    }
}
