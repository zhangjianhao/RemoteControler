package com.zjianhao.model;

/**
 * Created by 张建浩（Clarence) on 2017-4-18 22:24.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 设备的按键信息
 */
public class Key {
    @JsonIgnore
    private int id;
    @JsonIgnore
    private int deviceId;

    private String cmd;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String data;

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
