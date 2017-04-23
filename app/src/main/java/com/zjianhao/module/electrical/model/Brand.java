package com.zjianhao.module.electrical.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zjianhao.utils.HanziToPinyin;

import java.util.ArrayList;

/**
 * Created by 张建浩（Clarence) on 2017-4-18 21:09.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

/**
 * 品牌
 */
public class Brand implements Comparable<Brand> {
    @JsonIgnore
    private int typeId;

    @JsonProperty("id")
    private int brandId;

    @JsonProperty("common")
    private int common;
    @JsonProperty("name")
    private String name;

    private String index;

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

    public int getCommon() {
        return common;
    }

    public void setCommon(int common) {
        this.common = common;
    }

    public String getName() {
        return name;
    }

    public String getLetterIndex() {
        if (index == null) {
            System.out.println(name);
            ArrayList<HanziToPinyin.Token> tokens = HanziToPinyin.getInstance().get(name);
            char[] chars = tokens.get(0).target.toUpperCase().toCharArray();
            index = chars[0] + "";
        }
        return index;

    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int compareTo(Brand o) {
        return this.getLetterIndex().compareTo(o.getLetterIndex());
    }
}
