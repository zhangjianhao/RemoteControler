package com.zjianhao.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjianhao.model.ResponseHeader;

import java.io.IOException;

/**
 * Created by 张建浩（Clarence) on 2017-4-18 20:45.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

public class JsonUtil {
    public static ResponseHeader getResult(String json) {
        ObjectMapper mapper = new ObjectMapper();
        ResponseHeader header = new ResponseHeader();
        try {
            JsonNode node = mapper.readTree(json);
            int code = node.get("showapi_res_code").intValue();
            header.setCode(code);
            header.setErrorMsg(node.get("showapi_res_error").textValue());
            String result = node.get("showapi_res_body").get("result").toString();
            header.setResult(result);
            return header;
        } catch (IOException e) {
            e.printStackTrace();
            return header;
        }
    }

    public static String getKeyData(String json) {
        try {
            System.out.println(json);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);
            JsonNode body = node.get("showapi_res_body");
            JsonNode data = body.get("data");
            if (data == null)
                return null;
            return data.textValue();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
