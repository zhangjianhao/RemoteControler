package com.zjianhao.dao.impl;

import com.zjianhao.dao.DeviceTypeDao;
import com.zjianhao.model.DeviceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2017-4-19 14:53.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

public class DeviceTypeDaoImpl implements DeviceTypeDao {

    @Autowired
    private JdbcTemplate template;


    public JdbcTemplate getTemplate() {
        return template;
    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void insert(List<DeviceType> deviceTypes) {
        String sql = "insert into device_type(type_id,name,img_url) values(?,?,?)";
        for (DeviceType deviceType : deviceTypes) {
            template.update(sql, new Object[]{deviceType.getTypeId(), deviceType.getName(), deviceType.getImgUrl()});
        }
    }

    @Override
    public void insert(DeviceType deviceType) {
        String sql = "insert into device_type(type_id,name,img_url) values(?,?,?)";
        template.update(sql, new Object[]{deviceType.getTypeId(), deviceType.getName(), deviceType.getImgUrl()});
    }


    @Override
    public List<DeviceType> getDeviceTypeList() {
        String sql = "select * from device_type";
        SqlRowSet row = template.queryForRowSet(sql);
        List<DeviceType> list = new ArrayList<>();
        DeviceType deviceType = null;
        while (row.next()) {
            deviceType = new DeviceType();
            deviceType.setTypeId(row.getInt("type_id"));
            deviceType.setName(row.getString("name"));
            deviceType.setImgUrl(row.getString("img_url"));
            list.add(deviceType);
        }
        return list;
    }
}
