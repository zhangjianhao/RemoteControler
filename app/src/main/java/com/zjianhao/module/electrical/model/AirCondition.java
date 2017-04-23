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

    /**
     * 默认空调工作模式为自动
     */
    private String mode = AUTO;

    /**
     * 开机后默认26度
     */
    private int temperature = 26;


    /**
     * 获取当前空调工作模式
     *
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


    public int getTemperature() {
        return temperature;
    }


    public void increaseTemperature() {
        temperature++;
    }

    public void decreaseTemperature() {
        temperature--;
    }


}
