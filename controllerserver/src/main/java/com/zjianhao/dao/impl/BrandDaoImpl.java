package com.zjianhao.dao.impl;

import com.zjianhao.dao.BrandDao;
import com.zjianhao.model.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2017-4-20 16:47.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

public class BrandDaoImpl implements BrandDao {
    @Autowired
    private JdbcTemplate template;


    public JdbcTemplate getTemplate() {
        return template;
    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }


    @Override
    public void insert(Brand brand) {
        String sql = "insert into brand(type_id,brand_id,common,name) values(?,?,?,?)";
        template.update(sql, new Object[]{brand.getTypeId(), brand.getBrandId(), brand.getCommon(), brand.getName()});
    }

    @Override
    public void insert(List<Brand> brands) {
        String sql = "insert into brand(type_id,brand_id,common,name) values(?,?,?,?)";
        for (Brand brand : brands) {
            template.update(sql, new Object[]{brand.getTypeId(), brand.getBrandId(), brand.getCommon(), brand.getName()});
        }

    }

    @Override
    public List<Brand> queryBrands(int typeId) {
        String sql = "SELECT * FROM brand WHERE type_id = ?";
        SqlRowSet row = template.queryForRowSet(sql, new Object[]{typeId});
        List<Brand> list = new ArrayList<>();
        Brand brand = null;
        while (row.next()) {
            brand = new Brand();
            brand.setTypeId(row.getInt("type_id"));
            brand.setName(row.getString("name"));
            brand.setBrandId(row.getInt("brand_id"));
            brand.setCommon(row.getInt("common"));
            list.add(brand);
        }
        return list;
    }
}
