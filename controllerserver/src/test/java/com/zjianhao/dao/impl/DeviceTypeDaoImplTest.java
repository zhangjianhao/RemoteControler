package com.zjianhao.dao.impl;

import com.zjianhao.model.DeviceType;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2017-4-19 15:27.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

public class DeviceTypeDaoImplTest {

    private JdbcTemplate template;
    private DeviceTypeDaoImpl deviceTypeDao;

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
    }

    @Test
    public void insert() throws Exception {
        DeviceType deviceType = new DeviceType();
        deviceType.setName("aa");
        deviceType.setImgUrl("bb");
        deviceType.setTypeId(1);
        deviceTypeDao.insert(deviceType);

    }

    @Test
    public void getDeviceTypeList() throws Exception {

        List<DeviceType> deviceTypeList = deviceTypeDao.getDeviceTypeList();
        for (DeviceType deviceType : deviceTypeList) {
            System.out.println(deviceType.getName());
            System.out.println(deviceType.getTypeId());
            System.out.println(deviceType.getImgUrl());
        }
    }

}