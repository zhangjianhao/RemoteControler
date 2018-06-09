package com.zjianhao.dao;

import com.zjianhao.model.Brand;

import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2017-4-20 16:45.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

public interface BrandDao {
    public void insert(Brand brand);

    public void insert(List<Brand> brands);

    public List<Brand> queryBrands(int typeId);
}
