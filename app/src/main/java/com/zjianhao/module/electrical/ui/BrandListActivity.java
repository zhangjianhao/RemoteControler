package com.zjianhao.module.electrical.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zjianhao.adapter.recyclerview.MultiItemTypeAdapter;
import com.zjianhao.base.NavigatorActivity;
import com.zjianhao.http.DefaultCallback;
import com.zjianhao.http.DeviceApi;
import com.zjianhao.http.ResponseHeader;
import com.zjianhao.http.RetrofitManager;
import com.zjianhao.module.electrical.adapter.BrandAdapter;
import com.zjianhao.module.electrical.model.Brand;
import com.zjianhao.universalcontroller.R;
import com.zjianhao.view.WaveSideBar;
import com.zjianhao.view.WaveSideBar.OnSelectIndexItemListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;

/**
 * Created by 张建浩（Clarence) on 2017-4-21 16:02.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class BrandListActivity extends NavigatorActivity implements OnSelectIndexItemListener {
    @InjectView(R.id.brand_list)
    RecyclerView brandList;
    @InjectView(R.id.right_side_bar)
    WaveSideBar rightSideBar;
    @InjectView(R.id.load_brand_progress)
    ProgressBar loadBrandProgress;

    private BrandAdapter adapter;
    private int typeId;


    private List<Brand> brands = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("品牌列表");
        setContentView(R.layout.ele_brand_list_main);
        ButterKnife.inject(this);
        typeId = getIntent().getIntExtra("type_id", 1);
        brandList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BrandAdapter(this, R.layout.ele_brand_item, brands);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Brand brand = brands.get(position);
                Intent intent = new Intent(BrandListActivity.this, AddControllerActivity.class);
                intent.putExtra("type_id", typeId);
                intent.putExtra("brand_id", brand.getBrandId());
                startActivity(intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        brandList.setAdapter(adapter);
        rightSideBar.setOnSelectIndexItemListener(this);
        loadBrandData();
    }


    public void loadBrandData() {
        DeviceApi deviceApi = RetrofitManager.getDeviceApi();
        final Call<ResponseHeader<List<Brand>>> brandLists = deviceApi.getBrands(typeId);
        brandLists.enqueue(new DefaultCallback<List<Brand>>(brandList) {
            @Override
            public void onResponse(List<Brand> data) {
                loadBrandProgress.setVisibility(View.GONE);
                brands = data;
                Collections.sort(data);
                adapter.setDatas(data);
            }

            @Override
            public void onFailure(Throwable t) {
                super.onFailure(t);
                loadBrandProgress.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onSelectIndexItem(String index) {
        Toast.makeText(BrandListActivity.this, index, Toast.LENGTH_SHORT).show();
        for (int i = 0; i < brands.size(); i++) {
            if (brands.get(i).getLetterIndex().equals(index)) {
                ((LinearLayoutManager) brandList.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                return;
            }
        }
    }
}
