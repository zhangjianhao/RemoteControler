package com.zjianhao.http;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.show.api.ShowApiRequest;
import com.zjianhao.model.*;
import com.zjianhao.utils.JsonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2017-4-18 15:30.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

public class LoadApiData {

    public static final String GOOGLE_FORMAT = "google";
    private ObjectMapper mapper;

    public LoadApiData() {
        mapper = new ObjectMapper();
    }

    /**
     * 获取设备类型列表
     *
     * @return
     */
    public List<DeviceType> getDeviceTypeList() {
        try {
            String res = new ShowApiRequest("http://route.showapi.com/1183-2", "35846", "b6dc244cac3749818aad528e5000d2a7")
                    .post();
            ResponseHeader header = JsonUtil.getResult(res);
            JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, DeviceType.class);
            return mapper.readValue(header.getResult().toString(), type);
        } catch (IOException e) {
            e.printStackTrace();
            //避免返回空可能造成空指针的异常,此处返回对象
            return new ArrayList<DeviceType>();
        }
    }


    /**
     * 根据设备类型获取品牌列表
     *
     * @param typeId
     * @return
     */
    public List<Brand> getBrand(int typeId) {
        try {
            String res = new ShowApiRequest("http://route.showapi.com/1183-3", "35846", "b6dc244cac3749818aad528e5000d2a7")
                    .addTextPara("typeid", typeId + "")
                    .post();
            ResponseHeader header = JsonUtil.getResult(res);
            JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, Brand.class);
            List<Brand> brands = mapper.readValue(header.getResult().toString(), type);
            for (Brand brand : brands) {
                brand.setTypeId(typeId);
            }
            return brands;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<Brand>();
        }
    }

    public List<KeyTest> getKeyTestList(int typeId, int brandId, String format) {
        try {
            String res = new ShowApiRequest("http://route.showapi.com/1183-8", "35846", "b6dc244cac3749818aad528e5000d2a7")
                    .addTextPara("typeid", typeId + "")
                    .addTextPara("brandid", brandId + "")
                    .addTextPara("format", format + "")
                    .post();
            ResponseHeader header = JsonUtil.getResult(res);
            JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, KeyTest.class);
            List<KeyTest> keyTests = mapper.readValue(header.getResult().toString(), type);
            for (KeyTest keyTest : keyTests) {
                keyTest.setTypeId(typeId);
                keyTest.setBrandId(brandId);
            }
            return keyTests;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<KeyTest>();
        }
    }

    public List<Key> getkeyList(int deviceId) {
        try {
            String res = new ShowApiRequest("http://route.showapi.com/1183-5", "35846", "b6dc244cac3749818aad528e5000d2a7")
                    .addTextPara("deviceid", deviceId + "")
                    .post();
            ResponseHeader header = JsonUtil.getResult(res);
            JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, Key.class);
            List<Key> keys = mapper.readValue(header.getResult().toString(), type);
            for (Key key : keys) {
                key.setDeviceId(deviceId);
            }
            return keys;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<Key>();
        }
    }

    public String getKeyData(int deviceId, String cmd, String format) {
        System.out.println("start request network key data:" + deviceId + ":" + cmd + ":" + format);
        String res = new ShowApiRequest("http://route.showapi.com/1183-6", "35846", "b6dc244cac3749818aad528e5000d2a7")
                .addTextPara("deviceid", deviceId + "")
                .addTextPara("cmd", cmd)
                .addTextPara("format", format)
                .post();
        return JsonUtil.getKeyData(res);
    }


    public String getAirData(int deviceId, String cmd, String temp, String format) {
        String res = new ShowApiRequest("http://route.showapi.com/1183-7", "35846", "b6dc244cac3749818aad528e5000d2a7")
                .addTextPara("deviceid", deviceId + "")
                .addTextPara("cmd", cmd)
                .addTextPara("temp", temp)
                .addTextPara("format", format)
                .post();
        ResponseHeader<String> header = JsonUtil.getResult(res);
        String result = header.getResult();
        return result.substring(result.indexOf('"') + 1, result.lastIndexOf('"'));
    }


}
