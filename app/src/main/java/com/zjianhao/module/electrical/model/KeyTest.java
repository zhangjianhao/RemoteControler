package com.zjianhao.module.electrical.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zjianhao.entity.Keyas;

import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2017-4-18 22:17.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

public class KeyTest {

    @JsonIgnore
    private int typeId;
    @JsonIgnore
    private int brandId;

    @JsonProperty("id")
    private int deviceId;


    private String name;
    private int orderno;

    @JsonProperty("list")
    private List<Keyas> keys;

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

    public List<Keyas> getKeys() {
        return keys;
    }

    public void setKeys(List<Keyas> keys) {
        this.keys = keys;
    }
}
