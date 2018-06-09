package com.zjianhao.dao.impl;

import com.zjianhao.http.LoadApiData;
import com.zjianhao.model.Brand;
import com.zjianhao.model.DeviceType;
import com.zjianhao.model.Key;
import com.zjianhao.model.KeyTest;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2017-4-20 20:45.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

public class KeyTestDaoImplTest {
    private JdbcTemplate template;
    private DeviceTypeDaoImpl deviceTypeDao;
    private BrandDaoImpl brandDao;
    private KeyTestDaoImpl keyTestDao;

    @Before
    public void setUp() throws Exception {
        template = new JdbcTemplate();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/infrared?useUnicode=true&characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("101606");
        template.setDataSource(dataSource);
        deviceTypeDao = new DeviceTypeDaoImpl();
        deviceTypeDao.setTemplate(template);
        brandDao = new BrandDaoImpl();
        brandDao.setTemplate(template);
        keyTestDao = new KeyTestDaoImpl();
        keyTestDao.setTemplate(template);
    }

    @Test
    public void insert() throws Exception {
        LoadApiData api = new LoadApiData();
        List<DeviceType> deviceTypeList = api.getDeviceTypeList();
//        deviceTypeDao.insert(deviceTypeList);
        for (DeviceType deviceType : deviceTypeList) {
            List<Brand> brands = api.getBrand(deviceType.getTypeId());
            brandDao.insert(brands);
//            for (Brand brand : brands) {
//                List<KeyTest> keyTestList = api.getKeyTestList(deviceType.getTypeId(), brand.getBrandId(), "google");
//                keyTestDao.insert(keyTestList);
//            }
        }


    }

    @Test
    public void queryKeyTests() throws Exception {
        List<KeyTest> keyTests = keyTestDao.queryKeyTests(3, 523);
//        System.out.println(keyTests.size());
//        for (KeyTest keyTest : keyTests) {
//            for (Key key : keyTest.getKeys()) {
//                System.out.println("----"+key.getCmd()+":"+key.getName()+":"+key.getData());
//            }
//        }
        String keySql = "SELECT * FROM keyas WHERE device_id = ?";
        SqlRowSet rowSet = template.queryForRowSet(keySql, new Object[]{2679});
        while (rowSet.next()) {
            System.out.println("query---");
            Key key = new Key();
            key.setCmd(rowSet.getString("cmd"));
            key.setName(rowSet.getString("name"));
            key.setData(rowSet.getString("data"));
            System.out.println(key.getData());
        }
    }

}