package com.zjianhao.module.pc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 张建浩（Clarence) on 2017-4-13 09:36.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class Host implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.hostname);
        dest.writeString(this.hostIp);
        dest.writeString(this.sysType);
    }

    protected Host(Parcel in) {
        this.hostname = in.readString();
        this.hostIp = in.readString();
        this.sysType = in.readString();
    }

    public static final Parcelable.Creator<Host> CREATOR = new Parcelable.Creator<Host>() {
        @Override
        public Host createFromParcel(Parcel source) {
            return new Host(source);
        }

        @Override
        public Host[] newArray(int size) {
            return new Host[size];
        }
    };
}
