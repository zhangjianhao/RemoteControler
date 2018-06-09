package com.zjianhao.controller;

import com.zjianhao.dao.*;
import com.zjianhao.http.LoadApiData;
import com.zjianhao.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2017-4-20 22:35.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

@Controller
@RequestMapping(value = "/api/device")
public class DeviceController {

    private static final int RESPONSE_OK = 200;
    private static final int RESPONSE_EXCEPTION = 500;
    @Autowired
    private DeviceTypeDao deviceTypeDao;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private KeyTestDao keyTestDao;
    @Autowired
    private LoadApiData apiData;
    @Autowired
    private CmdDao cmdDao;
    @Autowired
    private AirCmdDao airCmdDao;


    @RequestMapping(value = "/types", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseHeader readDeviceType() {
        ResponseHeader header = new ResponseHeader();
        header.setCode(RESPONSE_OK);
        List<DeviceType> deviceTypeList = deviceTypeDao.getDeviceTypeList();
        header.setResult(deviceTypeList);
        return header;
    }

    @RequestMapping(value = "/brands", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseHeader readBrands(@RequestParam(value = "type_id", required = true) int typeid) {
        ResponseHeader header = new ResponseHeader();
        try {
            header.setCode(RESPONSE_OK);
            List<Brand> brands = brandDao.queryBrands(typeid);
            if (brands == null || brands.size() == 0) {
                brands = apiData.getBrand(typeid);
                brandDao.insert(brands);
            }
            header.setResult(brands);
        } catch (Exception e) {
            e.printStackTrace();
            header.setCode(RESPONSE_EXCEPTION);
            header.setErrorMsg("数据查询错误");
            return header;
        }
        return header;
    }

    @RequestMapping(value = "/key_test", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseHeader readKeyTests(
            @RequestParam(value = "type_id", required = true) int typeId,
            @RequestParam(value = "brand_id", required = true) int brandId) {
        ResponseHeader header = new ResponseHeader();
        try {

            List<KeyTest> keyTests = keyTestDao.queryKeyTests(typeId, brandId);
            if (keyTests == null || keyTests.size() == 0) {
                System.out.println("------start request network--------");
                keyTests = apiData.getKeyTestList(typeId, brandId, LoadApiData.GOOGLE_FORMAT);
                keyTestDao.insert(keyTests);
            }
            header.setCode(RESPONSE_OK);
            header.setResult(keyTests);
        } catch (Exception e) {
            e.printStackTrace();
            header.setCode(RESPONSE_EXCEPTION);
            header.setErrorMsg("数据获取异常");
            return header;
        }
        return header;
    }

    @RequestMapping(value = "/key_list", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseHeader readKeyList(
            @RequestParam(value = "device_id", required = true) int deviceId) {
        ResponseHeader header = new ResponseHeader();
        try {

            List<Key> keys = cmdDao.queryKeyList(deviceId);
            if (keys == null || keys.size() == 0) {
                System.out.println("------start key list request network--------");
                keys = apiData.getkeyList(deviceId);
                cmdDao.insert(keys);
            }
            header.setCode(RESPONSE_OK);
            header.setResult(keys);
        } catch (Exception e) {
            e.printStackTrace();
            header.setCode(RESPONSE_EXCEPTION);
            header.setErrorMsg("数据获取异常");
            return header;
        }
        return header;
    }

    @RequestMapping(value = "/key_data", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseHeader readDeviceKeyData(
            @RequestParam(value = "device_id", required = true) int deviceId,
            @RequestParam(value = "cmd", required = true) String cmd) {
        ResponseHeader header = new ResponseHeader();
        System.out.println("device_id:" + deviceId + ":cmd:" + cmd);
        header.setCode(RESPONSE_OK);
        try {
            Key key = cmdDao.queryKey(deviceId, cmd);
            if (key != null) {
                if (key.getData() == null) {
                    String data = apiData.getKeyData(deviceId, cmd, LoadApiData.GOOGLE_FORMAT);
                    System.out.println("resut data:" + data);
                    key.setData(data);
                    cmdDao.updateKey(key);
                }
            } else {
                header.setResult(null);
                return header;
            }
            header.setResult(key.getData());
        } catch (Exception e) {
            e.printStackTrace();
            header.setCode(RESPONSE_EXCEPTION);
            header.setErrorMsg("数据获取异常");
            return header;
        }
        return header;
    }

    @RequestMapping(value = "/air_data", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseHeader readAirKeyData(
            @RequestParam(value = "device_id", required = true) int deviceId,
            @RequestParam(value = "cmd", required = true) String cmd,
            @RequestParam(value = "temp", required = true) String temp) {
        ResponseHeader header = new ResponseHeader();
        header.setCode(RESPONSE_OK);
        System.out.println(deviceId + ":" + cmd + ":" + temp);
        try {
            AirKey airKey = airCmdDao.query(deviceId, cmd, temp);

            if (airKey == null || airKey.getData() == null) {
                String data = apiData.getAirData(deviceId, cmd, temp, LoadApiData.GOOGLE_FORMAT);
                System.out.println("request air data:" + cmd + ":" + temp + ":" + data);
                airKey = new AirKey(deviceId, cmd, temp, data);
                airCmdDao.insert(airKey);
            } else {
                header.setResult(airKey.getData());
                return header;
            }
            header.setResult(airKey.getData());
        } catch (Exception e) {
            e.printStackTrace();
            header.setCode(RESPONSE_EXCEPTION);
            header.setErrorMsg("数据获取异常");
            return header;
        }
        return header;
    }


}
