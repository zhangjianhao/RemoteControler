package com.zjianhao.dao.impl;

import com.zjianhao.model.Brand;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2017-4-20 19:42.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

public class BrandDaoImplTest {
    private JdbcTemplate template;
    private BrandDaoImpl brandDao;

    @Before
    public void setUp() throws Exception {
        template = new JdbcTemplate();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/infrared?useUnicode=true&characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("101606");
        template.setDataSource(dataSource);
        brandDao = new BrandDaoImpl();
        brandDao.setTemplate(template);
    }


    @Test
    public void insert() throws Exception {
        Brand brand = new Brand();
        brand.setTypeId(1);
        brand.setCommon(1);
        brand.setName("brand");
        brand.setBrandId(2);
        brandDao.insert(brand);
    }

    @Test
    public void queryBrands() throws Exception {
        List<Brand> brands = brandDao.queryBrands(1);
        for (Brand brand : brands) {
            System.out.println(brand.getName());
        }
    }

}