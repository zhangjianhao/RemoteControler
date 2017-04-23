package com.zjianhao.module.electrical.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zjianhao.universalcontroller.Constant;

/**
 * Created by 张建浩（Clarence) on 2017-4-18 14:26.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class DeviceType {


    @JsonProperty("id")
    private int typeId;
    @JsonProperty("nama")
    private String name;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private String imgUrl;


    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return Constant.PROJECT_URL + imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
