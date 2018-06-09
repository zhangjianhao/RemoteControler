package com.show.api.util;

import com.show.api.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

/**
 * 客户端日志
 * 错误格式：time_api_app_ip_os_sdk_urlresponseCode
 */
public class ShowApiLogger {

    private static final Log log = LogFactory.getLog("showapi.log");
    private static boolean needEnableLogger = true;

    public static void setNeedEnableLogger(boolean needEnableLogger) {
        ShowApiLogger.needEnableLogger = needEnableLogger;
    }

    public static void logCommError(Exception e, String url, String appId, Map<String, String> params) {
        if (!needEnableLogger) {
            return;
        }
        if (params == null) return;
        StringBuilder sb = new StringBuilder();
        appendLog(params, sb);
        logCommError(e, url, appId, sb.toString());
    }


    /**
     * 通讯错误日志
     */
    public static void logCommError(Exception e, String urlStr, String appId, String bodyContent) {
        DateFormat df = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone(Constants.DATE_TIMEZONE));
        String rspCode = "";
        StringBuilder sb = new StringBuilder();
        sb.append(df.format(new Date()));// 时间
        sb.append("_");
        sb.append(appId);// APP
        sb.append("_");
        sb.append(urlStr);// 请求URL
        sb.append("_");
        sb.append(rspCode);
        sb.append("_");
        sb.append((e.getMessage() + "").replaceAll("\r\n", " "));
        sb.append(bodyContent);
        log.error(sb.toString());
    }


    private static void appendLog(Map map, StringBuilder sb) {
        boolean first = true;
        Set<Map.Entry<String, String>> set = map.entrySet();
        for (Map.Entry<String, String> entry : set) {
            if (!first) {
                sb.append("&");
            } else {
                first = false;
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
    }
}
