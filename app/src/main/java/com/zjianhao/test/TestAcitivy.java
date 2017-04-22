package com.zjianhao.test;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;

import com.zjianhao.base.BaseActivity;
import com.zjianhao.module.electrical.model.Brand;
import com.zjianhao.universalcontroller.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 张建浩（Clarence) on 2017-4-16 22:43.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class TestAcitivy extends BaseActivity {
    @InjectView(R.id.edittext)
    EditText edittext;
    @InjectView(R.id.send)
    Button send;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.test_main);
        ButterKnife.inject(this);
        System.out.println("test oncreate");
    }

    @OnClick(R.id.send)
    public void onClick() {
        Brand brand = new Brand();
        brand.setName("小 米");
        edittext.setText(brand.getLetterIndex());
    }
}
