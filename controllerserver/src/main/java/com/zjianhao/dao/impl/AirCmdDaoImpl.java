package com.zjianhao.dao.impl;

import com.zjianhao.dao.AirCmdDao;
import com.zjianhao.model.AirKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * Created by 张建浩（Clarence) on 2017-4-30 13:56.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

public class AirCmdDaoImpl implements AirCmdDao {
    @Autowired
    private JdbcTemplate template;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void insert(AirKey airKey) {
        String sql = "insert into air_cmd(device_id,cmd,temp,data) values(?,?,?,?)";
        template.update(sql, new Object[]{airKey.getDeviceId(), airKey.getCmd(), airKey.getTemp(), airKey.getData()});
    }

    @Override
    public AirKey query(int deviceId, String cmd, String temp) {
        String sql = "SELECT * FROM air_cmd WHERE device_id = ? AND cmd = ? AND temp = ?";
        SqlRowSet row = template.queryForRowSet(sql, new Object[]{deviceId, cmd, temp});
        AirKey airKey = null;
        while (row.next()) {
            airKey = new AirKey();
            airKey.setDeviceId(row.getInt("device_id"));
            airKey.setCmd(row.getString("cmd"));
            airKey.setTemp(row.getString("temp"));
            airKey.setData(row.getString("data"));
            return airKey;
        }
        return null;
    }
}
