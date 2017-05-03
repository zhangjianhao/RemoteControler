package com.example;

/**
 * Created by 张建浩（Clarence) on 2017-4-18 22:17.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

public class KeyTest implements Comparable<KeyTest> {

    private int typeId;
    private int brandId;

    private int deviceId;


    private String name;
    private int orderno;


    public int getDeviceId() {
        return deviceId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderno() {
        return orderno;
    }

    public void setOrderno(int orderno) {
        this.orderno = orderno;
    }


    @Override
    public int compareTo(KeyTest o) {
        if (this.orderno > o.orderno)
            return 1;
        else if (this.orderno < o.orderno)
            return -1;
        return 0;
    }
}
