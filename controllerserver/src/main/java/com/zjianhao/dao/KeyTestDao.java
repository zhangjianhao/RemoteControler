package com.zjianhao.dao;

import com.zjianhao.model.KeyTest;

import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2017-4-20 19:47.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

public interface KeyTestDao {
    /**
     * 插入测试按键
     *
     * @param keyTest
     */
    public void insert(KeyTest keyTest);

    public void insert(List<KeyTest> keyTests);


    public List<KeyTest> queryKeyTests(int typeId, int deviceId);
}
