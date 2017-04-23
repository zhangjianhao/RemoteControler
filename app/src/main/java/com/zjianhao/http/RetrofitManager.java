package com.zjianhao.http;

import com.zjianhao.universalcontroller.Constant;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by 张建浩（Clarence) on 2017-4-21 13:44.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class RetrofitManager {
    private static String baseUrl = Constant.PROJECT_URL + "/api/";
    private static Retrofit retrofit;
    private static DeviceApi deviceApi;
    private static UserApi userApi;


    public static void init(String baseUrl) {
        OkHttpClient httpClient = new OkHttpClient();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient)
                .build();
    }

    public static void init() {
        OkHttpClient httpClient = new OkHttpClient();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient)
                .build();
    }

    public static DeviceApi getDeviceApi() {
        if (retrofit == null)
            init();
        if (deviceApi == null)
            deviceApi = retrofit.create(DeviceApi.class);
        return deviceApi;
    }


    public static UserApi getUserApi() {
        if (retrofit == null)
            init();
        if (userApi == null)
            userApi = retrofit.create(UserApi.class);
        return userApi;
    }


}
