package com.zjianhao.module.pc.model;

/**
 * Created by 张建浩（Clarence) on 2017-4-13 09:36.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class Host {
    private String hostname;
    private String hostIp;
    private String sysType;

    public Host(String hostname, String hostIp, String sysType) {
        this.hostname = hostname;
        this.hostIp = hostIp;
        this.sysType = sysType;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }
}
