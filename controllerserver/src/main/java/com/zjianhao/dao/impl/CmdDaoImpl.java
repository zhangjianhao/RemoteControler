package com.zjianhao.dao.impl;

import com.zjianhao.dao.CmdDao;
import com.zjianhao.model.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2017-4-23 21:11.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

public class CmdDaoImpl implements CmdDao {
    @Autowired
    private JdbcTemplate template;

    public JdbcTemplate getTemplate() {
        return template;
    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Key queryKey(int deviceId, String cmd) {
        String sql = "SELECT * FROM cmd WHERE device_id = ? AND cmd = ?";
        SqlRowSet row = template.queryForRowSet(sql, new Object[]{deviceId, cmd});
        Key key = null;
        while (row.next()) {
            key = new Key();
            key.setId(row.getInt("id"));
            key.setDeviceId(deviceId);
            key.setCmd(cmd);
            key.setName(row.getString("name"));
            key.setData(row.getString("data"));
            return key;
        }
        return null;
    }

    @Override
    public void updateKey(Key key) {
        String sql = "UPDATE cmd SET data = ? WHERE id = ?";
        template.update(sql, new Object[]{key.getData(), key.getId()});
    }


    @Override
    public void insert(List<Key> keys) {
        String sql = "INSERT INTO cmd(device_id,cmd,name,data) VALUES (?,?,?,?)";
        for (Key key : keys) {
            template.update(sql, new Object[]{key.getDeviceId(), key.getCmd(), key.getName(), key.getData()});
        }
    }

    @Override
    public List<Key> queryKeyList(int deviceId) {
        String sql = "select * from cmd where device_id = ?";
        SqlRowSet row = template.queryForRowSet(sql, new Object[]{deviceId});
        List<Key> keys = new ArrayList<>();
        Key key = null;
        while (row.next()) {
            key = new Key();
            key.setDeviceId(deviceId);
            key.setCmd(row.getString("cmd"));
            key.setName(row.getString("name"));
            key.setData(row.getString("data"));
            keys.add(key);
        }
        return keys;

    }
}
