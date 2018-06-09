package com.zjianhao.test;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.show.api.ShowApiRequest;
import com.zjianhao.http.LoadApiData;
import com.zjianhao.model.*;
import com.zjianhao.utils.JsonUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2017-4-18 14:58.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

public class TestApi {
    public static void main(String[] args) throws IOException {
//        new TestApi().testDeviceType();
        TestApi api = new TestApi();
//        api.testGetBrand();
//        api.testKeyTest();
        api.testgetKeyList();

    }


    public void testKeyTest() throws IOException {
        String res = new ShowApiRequest("http://route.showapi.com/1183-8", "35846", "b6dc244cac3749818aad528e5000d2a7")
                .addTextPara("typeid", "3")
                .addTextPara("brandid", "523")
                .addTextPara("format", "google")
                .post();
        ResponseHeader header = JsonUtil.getResult(res);
        ObjectMapper mapper = new ObjectMapper();
        JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, KeyTest.class);
        List<KeyTest> keyTests = mapper.readValue(header.getResult().toString(), type);
        for (KeyTest keyTest : keyTests) {
            System.out.println(keyTest.getName());
            System.out.println(keyTest.getDeviceId());
            System.out.println(keyTest.getKeys().get(0).getData());
        }

    }


    @Test
    public void testGetBrand() throws IOException {
        List<DeviceType> devices = testDeviceType();
        for (DeviceType device : devices) {
            System.out.println("id:" + device.getTypeId() + ":" + device.getName());
            String res = new ShowApiRequest("http://route.showapi.com/1183-3", "35846", "b6dc244cac3749818aad528e5000d2a7")
                    .addTextPara("typeid", device.getTypeId() + "")
                    .post();
            ResponseHeader header = JsonUtil.getResult(res);
            ObjectMapper mapper = new ObjectMapper();
            JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, Brand.class);
            List<Brand> brands = mapper.readValue(header.getResult().toString(), type);
            for (Brand brand : brands) {
                brand.setTypeId(device.getTypeId());
                System.out.println(brand.getBrandId() + ":" + brand.getCommon() + ":" + brand.getName());
            }
            System.out.println();
            System.out.println();
        }
    }

    public List<DeviceType> testDeviceType() throws IOException {
        String res = new ShowApiRequest("http://route.showapi.com/1183-2", "35846", "b6dc244cac3749818aad528e5000d2a7")
                .post();

        ResponseHeader header = JsonUtil.getResult(res);
        ObjectMapper mapper = new ObjectMapper();
        JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, DeviceType.class);
        List<DeviceType> list = mapper.readValue(header.getResult().toString(), type);
        for (DeviceType deviceType : list) {
            System.out.println(deviceType.getTypeId() + ":" + deviceType.getName());
        }
        return list;
    }


    public List<Key> testgetKeyList() {
        try {
            String res = new ShowApiRequest("http://route.showapi.com/1183-5", "35846", "b6dc244cac3749818aad528e5000d2a7")
                    .addTextPara("deviceid", 1129 + "")
                    .post();
            ResponseHeader header = JsonUtil.getResult(res);
            ObjectMapper mapper = new ObjectMapper();
            JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, Key.class);
            List<Key> keys = mapper.readValue(header.getResult().toString(), type);
            for (Key key : keys) {
                System.out.println(key.getName() + ":" + key.getCmd() + ":" + key.getData());
            }
            return keys;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<Key>();
        }
    }


    @Test
    public void testGetKeyData() {
        LoadApiData data = new LoadApiData();
        System.out.println(data.getKeyData(2876, "back", LoadApiData.GOOGLE_FORMAT));
    }

    @Test
    public void testAirKeyData() {
        String res = "{\"showapi_res_code\":0,\"showapi_res_error\":\"\",\"showapi_res_body\":{\"result\":\"38000,3447,1684,447,1211,447,1211,447,421,447,421,447,421,447,1211,447,421,447,421,447,1211,447,1211,447,421,447,1211,447,421,447,421,447,1211,447,1211,447,421,447,1211,447,1211,447,421,447,421,447,1211,447,421,447,421,447,1211,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,1211,447,421,447,421,447,1211,447,421,447,421,447,1211,447,1211,447,421,447,421,447,421,447,421,447,421,447,421,447,1211,447,421,447,1211,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,421,447,1211,447,421,447,421,447,421,447,421,447,421,447,1211,447,421,447\",\"ret_code\":0}}";
        ResponseHeader<String> header = JsonUtil.getResult(res);

        System.out.println(header.getResult());
        String result = header.getResult();
    }
}
