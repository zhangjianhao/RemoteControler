package com.zjianhao.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2017-4-18 15:59.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

public class ResponseBody {
    private int total;
    private List<DeviceType> result;
    @JsonProperty("ret_code")
    private int retCode;

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DeviceType> getResult() {
        return result;
    }

    public void setResult(List<DeviceType> result) {
        this.result = result;
    }
}
