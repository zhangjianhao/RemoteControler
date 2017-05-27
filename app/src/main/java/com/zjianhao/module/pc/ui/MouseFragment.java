package com.zjianhao.module.pc.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zjianhao.module.pc.util.PCCommand;
import com.zjianhao.universalcontroller.AppApplication;
import com.zjianhao.universalcontroller.R;
import com.zjianhao.utils.UdpSender;

/**
 * Created by 张建浩 on 2017/3/8.
 */
public class MouseFragment extends Fragment implements View.OnClickListener {
    private RelativeLayout trackpad;
    private Button mouseLeftClick;
    private Button mouseRightClick;
    private ImageView keyboardImg;
    private float oldX;
    private float oldY;
    private String ip;
    private boolean isclick = true;
    private int clickCount = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.pc_mouse_control_fragment, container, false);
        AppApplication application  = (AppApplication) getActivity().getApplication();
        ip  = application.getIp();
        trackpad   = (RelativeLayout)view.findViewById(R.id.trackpad);
        trackpad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        onMouseMove(event);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        onMouseDown(event);
                        break;
                    case MotionEvent.ACTION_UP:
                        onMouseUp(event);
                }
                return false;
            }
        });
        trackpad.setOnClickListener(this);
        mouseLeftClick = (Button)view.findViewById(R.id.mouse_click_left);
        mouseLeftClick.setOnClickListener(this);
        mouseRightClick = (Button)view.findViewById(R.id.mouse_click_right);
        mouseRightClick.setOnClickListener(this);
        keyboardImg = (ImageView)view.findViewById(R.id.keyboard_img);
        keyboardImg.setOnClickListener(this);
        return view;
    }


    public void onMouseDown(MotionEvent event){
        oldX =  event.getX();
        oldY = event.getY();
        isclick = true;
        clickCount = 0;
        System.out.println("mouse down");
    }

    public void onMouseMove(MotionEvent event){
        System.out.println("mouse move");
        clickCount++;
        float newx = event.getX();
        float newy = event.getY();
        final float movex = oldX - newx;
        final float movey  = oldY - newy;
        oldX = newx;
        oldY = newy;
        if (movex== 0&& movex==0)
            return;
        UdpSender.sendOrderSyn(ip, PCCommand.MOUSE_EVENT_MOVE,movex+":"+movey);
    }

    public void onMouseUp(MotionEvent event){
        System.out.println("mouse up");
        if (isclick&&clickCount<5){
            UdpSender.sendOrderSyn(ip, PCCommand.MOUSE_EVENT_LEFT_CLICK);
        }
        isclick = false;
        clickCount = 0;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.mouse_click_left:
                UdpSender.sendOrderSyn(ip, PCCommand.MOUSE_EVENT_LEFT_CLICK);
                break;
            case R.id.mouse_click_right:
                UdpSender.sendOrderSyn(ip,PCCommand.MOUSE_EVENT_RIGHT_CLICK);
                break;
            case R.id.keyboard_img:
               Intent intent  = new Intent(getActivity(),KeyBoardInputAty.class);
                startActivity(intent);
                break;



        }
    }
}
