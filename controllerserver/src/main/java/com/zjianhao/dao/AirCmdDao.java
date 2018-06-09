package com.zjianhao.dao;

import com.zjianhao.model.AirKey;

/**
 * Created by 张建浩（Clarence) on 2017-4-26 14:36.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

public interface AirCmdDao {
    public void insert(AirKey airKey);

    public AirKey query(int deviceId, String cmd, String temp);

}
