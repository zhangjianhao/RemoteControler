package com.zjianhao.http;

import com.zjianhao.entity.Device;
import com.zjianhao.model.User;

import java.util.List;

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

    /**
     * 用户注册
     *
     * @param username
     * @param password
     * @param email
     * @return
     */
    @FormUrlEncoded
    @POST("user/regist")
    Call<ResponseHeader<Integer>> regist(
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email
    );

    /**
     * 用户登陆
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseHeader<User>> login(
            @Field("username") String username,
            @Field("password") String password
    );

    /**
     * 验证Id和token是否正确
     * @param userId
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("user/token_verify")
    Call<ResponseHeader<User>> tokenVerify(
            @Field("user_id") String userId,
            @Field("token") String token
    );


    /**
     * 获取最后一次备份的时间戳
     *
     * @param userId
     * @param token
     * @return
     */

    @FormUrlEncoded
    @POST("user/backup_time")
    Call<ResponseHeader<Long>> readBackupTime(
            @Field("user_id") String userId,
            @Field("token") String token
    );

    /**
     * 备份数据
     *
     * @param userId
     * @param token
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("user/backup_device")
    Call<ResponseHeader<Long>> uploadData(
            @Field("user_id") String userId,
            @Field("token") String token,
            @Field("devices") String json
    );

    /**
     * 恢复设备数据
     *
     * @param userId
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("user/restore_device")
    Call<ResponseHeader<List<Device>>> restoreData(
            @Field("user_id") String userId,
            @Field("token") String token
    );



}
