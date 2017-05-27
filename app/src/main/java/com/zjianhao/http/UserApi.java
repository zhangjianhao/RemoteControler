package com.zjianhao.http;

import com.zjianhao.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 张建浩（Clarence) on 2017-4-21 14:18.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public interface UserApi {

    @FormUrlEncoded
    @POST("user/regist")
    Call<ResponseHeader<Integer>> regist(
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseHeader<User>> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("user/token_verify")
    Call<ResponseHeader<User>> tokenVerify(
            @Field("user_id") String userId,
            @Field("token") String token
    );


}
