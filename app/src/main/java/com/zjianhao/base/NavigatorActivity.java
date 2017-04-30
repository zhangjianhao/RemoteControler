package com.zjianhao.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zjianhao.universalcontroller.R;

/**
 * Created by 张建浩（Clarence) on 2017-4-13 16:21.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class NavigatorActivity extends BaseActivity {

    Toolbar mainToolbar;
    LinearLayout navigatorContent;
    private TextView centerTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.navigator_main);
        mainToolbar = (Toolbar)findViewById(R.id.main_toolbar);
        mainToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        navigatorContent = (LinearLayout)findViewById(R.id.navigator_content);
        centerTitle = (TextView)findViewById(R.id.center_title);
//        setSupportActionBar(mainToolbar);


    }

    protected void setTitle(String title){
        centerTitle.setVisibility(View.VISIBLE);
        centerTitle.setText(title);
    }

    protected void setNavigatorText(String text) {
        mainToolbar.setTitle(text);
        mainToolbar.setTitleTextColor(Color.WHITE);
    }

    public void setContentView(int layoutResID) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getBaseContext()).inflate(layoutResID,null);
        navigatorContent.addView(viewGroup);
        viewGroup.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT));
    }
}
