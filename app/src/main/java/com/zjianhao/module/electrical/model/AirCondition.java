package com.zjianhao.module.electrical.model;

/**
 * Created by 张建浩（Clarence) on 2017-4-19 14:26.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */

public class AirCondition {
    /**
     * 开关机命令
     */
    public static final String ON = "on";
    public static final String OFF = "off";
    /**
     * 工作模式命令
     */
    public static final String AUTO = "auto";
    public static final String COLD = "cold";
    public static final String HEAT = "heat";
    public static final String FAN = "fan";
    public static final String WATER = "water";


    public static final String STATE_ON = "工作中";
    public static final String STATE_OFF = "已关闭";
    public static final String STATE_UNKNOWN = "未知";

    private String state = STATE_UNKNOWN;

    /**
     * 默认空调工作模式为自动
     */
    private String mode = AUTO;

    /**
     * 开机后默认26度
     */
    private int temperature = 26;


    public String powerOn() {
        this.state = STATE_ON;
        this.temperature = 26;
        this.mode = AUTO;
        return state;
    }

    public String powerOff() {
        this.state = STATE_OFF;
        return state;
    }




    /**
     * 获取当前空调工作模式
     * @return
     */
    public String getCurrentMode() {
        return mode;
    }


    public String changeMode() {
        switch (mode) {
            case AUTO:
                mode = COLD;
                break;
            case COLD:
                mode = HEAT;
                break;
            case HEAT:
                mode = FAN;
                break;
            case FAN:
                mode = WATER;
                break;
            case WATER:
                mode = AUTO;
                break;
        }
        return mode;
    }

    public String getModeIconText() {
        switch (mode) {
            case AUTO:
                return "\ue660";
            case COLD:
                return "\ue8e2";
            case HEAT:
                return "\ue670";
            case FAN:
                return "\ue613";
            case WATER:
                return "\ue60a";
        }
        return "\ue660;";
    }

    public String getModeDesc() {
        switch (mode) {
            case AUTO:
                return "自动";
            case COLD:
                return "制冷";
            case HEAT:
                return "制热";
            case FAN:
                return "送风";
            case WATER:
                return "加湿";
        }
        return "自动";
    }


    public int getTemperature() {
        return temperature;
    }


    public int increaseTemperature() {
        if (temperature == 30)
            return temperature;
        return ++temperature;
    }

    public int decreaseTemperature() {
        if (temperature == 16)
            return temperature;
        return --temperature;
    }

    public String getCurrentState() {
        return state;
    }


}
