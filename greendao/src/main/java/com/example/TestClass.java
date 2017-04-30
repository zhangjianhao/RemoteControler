package com.example;

/**
 * Created by 张建浩（Clarence) on 2017-4-25 12:25.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class TestClass {
    public static void main(String[] args) {
        String data = "38000,553,5263,553";
        String quency = data.substring(0, data.indexOf(","));
        String pattern = data.substring(data.indexOf(",") + 1);

        System.out.println(quency);
        System.out.println(pattern);

    }
}
