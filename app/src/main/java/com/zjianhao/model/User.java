package com.zjianhao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zjianhao.universalcontroller.Constant;

/**
 * Created by 张建浩（Clarence) on 2017-5-24 14:29.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

public class User {
    private String userId;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private String phone;
    private String headImg = "/head_img/default_head.png";
    private String token;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHeadImg() {
        return Constant.PROJECT_URL + headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
