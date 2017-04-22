package com.zjianhao.module.electrical.adapter;

import android.content.Context;

import com.zjianhao.adapter.recyclerview.CommonAdapter;
import com.zjianhao.adapter.recyclerview.base.ViewHolder;
import com.zjianhao.module.electrical.model.Brand;
import com.zjianhao.universalcontroller.R;

import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2017-4-21 16:51.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class BrandAdapter extends CommonAdapter<Brand> {

    public BrandAdapter(Context context, int layoutId, List<Brand> datas) {
        super(context, layoutId, datas);

    }

    @Override
    protected void convert(ViewHolder holder, Brand brand, int position) {
        holder.setText(R.id.brand_name, brand.getName());
    }

}
