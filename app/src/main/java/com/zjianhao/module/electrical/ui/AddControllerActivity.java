package com.zjianhao.module.electrical.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zjianhao.adapter.recyclerview.CommonAdapter;
import com.zjianhao.adapter.recyclerview.base.ViewHolder;
import com.zjianhao.adapter.recyclerview.wrapper.SpaceItemDecoration;
import com.zjianhao.base.NavigatorActivity;
import com.zjianhao.entity.Keyas;
import com.zjianhao.http.DefaultCallback;
import com.zjianhao.http.DeviceApi;
import com.zjianhao.http.ResponseHeader;
import com.zjianhao.http.RetrofitManager;
import com.zjianhao.module.electrical.ScrollSpeedLinearLayoutManger;
import com.zjianhao.module.electrical.model.KeyTest;
import com.zjianhao.module.electrical.util.InfraredUtil;
import com.zjianhao.module.pc.util.CmdUtil;
import com.zjianhao.universalcontroller.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by 张建浩（Clarence) on 2017-4-22 17:06.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class AddControllerActivity extends NavigatorActivity {
    @InjectView(R.id.key_test_list)
    RecyclerView keyTestList;
    @InjectView(R.id.key_effect_container)
    ConstraintLayout keyEffectContainer;
    @InjectView(R.id.key_test_num)
    TextView keyTestNum;
    @InjectView(R.id.key_test_name)
    TextView keyTestName;
    @InjectView(R.id.key_effect_no)
    Button keyEffectNo;
    @InjectView(R.id.key_effect_yes)
    Button keyEffectYes;
    @InjectView(R.id.key_load_progress)
    ProgressBar keyLoadProgress;
    private List<KeyTest> keyTests = new ArrayList<>();
    private int typeId;
    private int brandId;
    private CommonAdapter<KeyTest> adapter;
    private String keyPosition;
    private int currentPosition;
    private InfraredUtil infraredUtil;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("按键测试");
        setContentView(R.layout.ele_key_test_main);
        ButterKnife.inject(this);
        typeId = getIntent().getIntExtra("type_id", 1);
        brandId = getIntent().getIntExtra("brand_id", 1);
        keyTestList.setLayoutManager(new ScrollSpeedLinearLayoutManger(this, LinearLayoutManager.HORIZONTAL, false));
        new LinearSnapHelper().attachToRecyclerView(keyTestList);
        adapter = new CommonAdapter<KeyTest>(this, R.layout.ele_key_tet_list_item, keyTests) {
            @Override
            protected void convert(ViewHolder holder, KeyTest keyTest, final int position) {
                View view = holder.getView(R.id.key_test_button);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KeyTest keyTest = keyTests.get(position);
                        keyEffectContainer.setVisibility(View.VISIBLE);
                        Keyas key = keyTest.getKeys().get(0);
                        System.out.println("order no:" + keyTest.getOrderno());
                        infraredUtil.send(key.getData());
                        System.out.println(keyTest.getName() + ":" + keyTest.getKeys().get(0).getData());
                    }
                });
            }
        };
        keyTestList.setAdapter(adapter);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.recycle_view_item_spacing);
        keyTestList.addItemDecoration(new SpaceItemDecoration(0, 0, spacingInPixels, spacingInPixels));
        keyTestList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int first = layoutManager.findFirstVisibleItemPosition();
                int last = layoutManager.findLastVisibleItemPosition();
                if (first == last) {
                    currentPosition = first;
                    String name = keyTests.get(currentPosition).getKeys().get(0).getName();
                    keyTestName.setText(CmdUtil.cmdDesc(name));
                    keyTestNum.setText(String.format(keyPosition, first + 1, adapter.getDatas().size()));
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        keyPosition = getResources().getString(R.string.test_key_position);
        keyTestNum.setText(String.format(keyPosition, 0, 0));
        infraredUtil = new InfraredUtil(this);
        loadKeyTestData();
    }

    public void loadKeyTestData() {
        DeviceApi deviceApi = RetrofitManager.getDeviceApi();
        final Call<ResponseHeader<List<KeyTest>>> keyTests = deviceApi.getKeyTests(typeId, brandId);
        keyTests.enqueue(new DefaultCallback<List<KeyTest>>(keyTestList) {
            @Override
            public void onResponse(List<KeyTest> data) {
                Collections.sort(data);
                keyLoadProgress.setVisibility(View.GONE);
                adapter.setDatas(data);
                AddControllerActivity.this.keyTests = data;
                keyTestNum.setText(String.format(keyPosition, 1, data.size()));
                String name = data.get(0).getKeys().get(0).getName();
                keyTestName.setText(CmdUtil.cmdDesc(name));
            }
        });
    }



    @OnClick({R.id.key_effect_no, R.id.key_effect_yes})
    public void onClick(View view) {
        keyEffectContainer.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.key_effect_no:
                if (currentPosition + 1 < keyTests.size())
                    keyTestList.getLayoutManager().smoothScrollToPosition(keyTestList, null, currentPosition + 1);
                else
                    Toast.makeText(this, "对不起,可能不存在该设备的红外遥控数据", Toast.LENGTH_LONG).show();
//                keyTestList.getLayoutManager().scrollToPosition(currentPosition + 1);
                break;
            case R.id.key_effect_yes:
                KeyTest keyTest = keyTests.get(currentPosition);
                Intent intent = new Intent(this, ConfirmDeviceAty.class);
                intent.putExtra("brand_name", keyTest.getName());
                intent.putExtra("type_id", typeId);
                intent.putExtra("device_id", keyTest.getDeviceId());
                intent.putExtra("brand_id", keyTest.getBrandId());
                startActivity(intent);
                finish();
                break;
        }
    }

}
