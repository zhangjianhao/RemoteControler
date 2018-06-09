package com.zjianhao.controller;

import com.zjianhao.dao.DeviceTypeDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by 张建浩（Clarence) on 2017-4-24 16:04.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class DeviceControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private DeviceTypeDao deviceTypeDao;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void readDeviceType() throws Exception {
        mockMvc.perform((get("/api/device/types"))).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void readKeyData() throws Exception {
        mockMvc.perform((post("/api/device/key_data")
                .param("device_id", 1129 + "")
                .param("cmd", "power"))).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void readKeyList() throws Exception {
        mockMvc.perform((post("/api/device/key_list")
                .param("device_id", 1129 + "")
        )).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void readAirKeyData() throws Exception {
        mockMvc.perform((post("/api/device/air_data")
                .param("device_id", 1129 + "")
                .param("cmd", "power")
                .param("temp", "26"))).andExpect(status().isOk())
                .andDo(print());
    }

}