package com.zjianhao.dao.impl;

import com.zjianhao.model.Key;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2017-4-23 21:21.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

public class CmdDaoImplTest {
    private JdbcTemplate template;
    private CmdDaoImpl cmdDao;

    @Before
    public void setUp() throws Exception {
        template = new JdbcTemplate();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/infrared?useUnicode=true&characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("101606");
        template.setDataSource(dataSource);
        cmdDao = new CmdDaoImpl();
        cmdDao.setTemplate(template);
    }

    @Test
    public void insert() throws Exception {

    }

    @Test
    public void queryKeyList() throws Exception {
        List<Key> keys = cmdDao.queryKeyList(1);
        for (Key key : keys) {
            System.out.println(key.getName() + ":" + key.getCmd());
        }
    }

}