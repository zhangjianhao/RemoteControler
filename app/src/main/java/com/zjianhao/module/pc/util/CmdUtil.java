package com.zjianhao.module.pc.util;

/**
 * Created by 张建浩（Clarence) on 2017-4-22 22:20.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class CmdUtil {
    public static String cmdDesc(String cmd) {
        switch (cmd) {
            case "on":
                return "电源";
            default:
                return cmd;
        }
    }


    public static String getTypeName(int typeId) {
        switch (typeId) {
            case 1:
                return "电视机顶盒";
            case 2:
                return "电视机";
            case 3:
                return "空调";
            case 4:
                return "DVD播放器";
            case 5:
                return "投影仪";
            case 6:
                return "互联网机顶盒";
            case 7:
                return "风扇";
            case 8:
                return "音响";
            case 9:
                return "照相机";
            default:
                return "空调";
        }
    }
}
