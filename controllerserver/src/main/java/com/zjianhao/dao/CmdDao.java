package com.zjianhao.dao;

import com.zjianhao.model.Key;

import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2017-4-23 21:10.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

public interface CmdDao {

    public Key queryKey(int deviceId, String cmd);

    public void updateKey(Key key);

    public void insert(List<Key> keys);

    public List<Key> queryKeyList(int deviceId);
}
