package com.zjianhao.dao.impl;

import com.zjianhao.dao.KeyTestDao;
import com.zjianhao.model.Key;
import com.zjianhao.model.KeyTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2017-4-20 19:49.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

public class KeyTestDaoImpl implements KeyTestDao {
    @Autowired
    private JdbcTemplate template;


    public JdbcTemplate getTemplate() {
        return template;
    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }


    @Override
    public void insert(KeyTest keyTest) {
        String sql = "INSERT INTO key_test(device_id,name,order_no) VALUE (?,?,?)";
        String keySql = "INSERT INTO keyas(device_id,cmd,name,data) VALUE (?,?,?,?)";
        template.update(sql, new Object[]{keyTest.getDeviceId(), keyTest.getName(), keyTest.getOrderno()});
        List<Key> keys = keyTest.getKeys();
        if (keys == null)
            return;
        for (int i = 0; i < keys.size(); i++) {
            Key key = keys.get(i);
            template.update(keySql, new Object[]{key.getDeviceId(), key.getCmd(), key.getName(), key.getData()});
        }
    }

    @Override
    public void insert(List<KeyTest> keyTests) {
        String sql = "INSERT INTO key_test(type_id,brand_id,device_id,name,order_no) VALUES (?,?,?,?,?)";
        String keySql = "INSERT INTO keyas(device_id,cmd,name,data) VALUES (?,?,?,?)";
        for (KeyTest keyTest : keyTests) {
            template.update(sql, new Object[]{keyTest.getTypeId(), keyTest.getBrandId(), keyTest.getDeviceId(), keyTest.getName(), keyTest.getOrderno()});
            List<Key> keys = keyTest.getKeys();
            for (int i = 0; i < keys.size(); i++) {
                Key key = keys.get(i);
                template.update(keySql, new Object[]{keyTest.getDeviceId(), key.getCmd(), key.getName(), key.getData()});
            }
        }

    }

    @Override
    public List<KeyTest> queryKeyTests(int typeId, int brandId) {
        String sql = "SELECT * FROM key_test WHERE type_id = ? AND brand_id = ?";
        String keySql = "SELECT * FROM keyas WHERE device_id = ?";
        SqlRowSet row = template.queryForRowSet(sql, new Object[]{typeId, brandId});
        List<KeyTest> list = new ArrayList<>();
        while (row.next()) {
            KeyTest test = new KeyTest();
            test.setTypeId(row.getInt("type_id"));
            test.setBrandId(row.getInt("brand_id"));
            test.setDeviceId(row.getInt("device_id"));
            test.setName(row.getString("name"));
            test.setOrderno(row.getInt("order_no"));
            List<Key> keys = new ArrayList<>();
            SqlRowSet rowSet = template.queryForRowSet(keySql, new Object[]{test.getDeviceId()});
            while (rowSet.next()) {
                Key key = new Key();
                key.setDeviceId(test.getDeviceId());
                key.setCmd(rowSet.getString("cmd"));
                key.setName(rowSet.getString("name"));
                key.setData(rowSet.getString("data"));
                keys.add(key);
            }
            test.setKeys(keys);
            list.add(test);
        }
        return list;
    }
}
