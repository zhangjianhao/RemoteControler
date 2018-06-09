package com.zjianhao.model;

/**
 * Created by 张建浩（Clarence) on 2017-4-26 14:37.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

public class AirKey {
    private int deviceId;
    private String cmd;
    private String temp;
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

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public AirKey() {
    }

    public AirKey(int deviceId, String cmd, String temp, String data) {
        this.deviceId = deviceId;
        this.cmd = cmd;
        this.temp = temp;
        this.data = data;
    }
}
