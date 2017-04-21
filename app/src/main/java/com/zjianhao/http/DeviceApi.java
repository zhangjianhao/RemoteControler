package com.zjianhao.http;

import com.zjianhao.module.electrical.model.Brand;
import com.zjianhao.module.electrical.model.DeviceType;

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
     *
     * @return
     */
    @GET("device/types")
    Call<ResponseHeader<List<DeviceType>>> getDeviceTypes();


    /**
     * 获取指定类型设备的品牌列表
     *
     * @param typeId
     * @return
     */
    @FormUrlEncoded
    @POST("device/brands")
    Call<ResponseHeader<List<Brand>>> getBrands(
            @Field("type_id") int typeId);

}
