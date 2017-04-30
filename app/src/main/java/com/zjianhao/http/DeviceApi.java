package com.zjianhao.http;

import com.zjianhao.entity.Keyas;
import com.zjianhao.module.electrical.model.Brand;
import com.zjianhao.module.electrical.model.DeviceType;
import com.zjianhao.module.electrical.model.KeyTest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by 张建浩（Clarence) on 2017-4-21 13:35.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public interface DeviceApi {

    /**
     * 获取可遥控的设备类型
     * @return
     */
    @GET("device/types")
    Call<ResponseHeader<List<DeviceType>>> getDeviceTypes();


    /**
     * 获取指定类型设备的品牌列表
     * @param typeId
     * @return
     */
    @FormUrlEncoded
    @POST("device/brands")
    Call<ResponseHeader<List<Brand>>> getBrands(
            @Field("type_id") int typeId);


    /**
     * 获取制定类型品牌的测试按键列表
     * @param typeId
     * @param brandId
     * @return
     */
    @FormUrlEncoded
    @POST("device/key_test")
    Call<ResponseHeader<List<KeyTest>>> getKeyTests(
            @Field("type_id") int typeId,
            @Field("brand_id") int brandId);


    /**
     * 获取普通设备按键列表(不包括空调)
     *
     * @param deviceId
     * @return
     */
    @FormUrlEncoded
    @POST("device/key_list")
    Call<ResponseHeader<List<Keyas>>> getKeyList(
            @Field("device_id") int deviceId);

    /**
     * 获取普通设备按键数据
     *
     * @param deviceId
     * @param cmd
     * @return
     */
    @FormUrlEncoded
    @POST("device/key_data")
    Call<ResponseHeader<String>> getKeyData(
            @Field("device_id") int deviceId,
            @Field("cmd") String cmd
    );

    /**
     * 获取空调按键数据
     *
     * @param deviceId
     * @param cmd
     * @param temp
     * @return
     */

    @FormUrlEncoded
    @POST("device/air_data")
    Call<ResponseHeader<String>> getAirKeyData(
            @Field("device_id") int deviceId,
            @Field("cmd") String cmd,
            @Field("temp") int temp
    );




















}
