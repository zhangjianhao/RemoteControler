package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2017-5-2 15:43.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class TestComparable {
    public static void main(String[] args) {
        KeyTest test1 = new KeyTest();
        test1.setOrderno(3);
        KeyTest test2 = new KeyTest();
        test2.setOrderno(1);

        KeyTest test3 = new KeyTest();
        test3.setOrderno(2);

        List<KeyTest> list = new ArrayList<>();
        list.add(test1);
        list.add(test2);
        list.add(test3);

        Collections.sort(list);

        for (KeyTest keyTest : list) {
            System.out.println(keyTest.getOrderno());
        }

    }
}
