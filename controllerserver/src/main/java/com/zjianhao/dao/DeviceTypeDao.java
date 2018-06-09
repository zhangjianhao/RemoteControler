package com.zjianhao.dao;

import com.zjianhao.model.DeviceType;

import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2017-4-18 14:25.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

public interface DeviceTypeDao {
    /**
     * 批量插入设备类型
     *
     * @param deviceTypes
     */
    public void insert(List<DeviceType> deviceTypes);

    public void insert(DeviceType deviceType);


    /**
     * 读取设备类型列表
     *
     * @return
     */
    public List<DeviceType> getDeviceTypeList();


}
