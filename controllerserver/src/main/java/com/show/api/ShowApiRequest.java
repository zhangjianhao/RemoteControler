package com.show.api;

import com.show.api.util.ShowApiUtils;
import com.show.api.util.WebUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


/**
 * 基于REST的客户端。
 */
public class ShowApiRequest extends NormalRequest {
    private String appSecret;

    public ShowApiRequest(String url, String appid, String appSecret) {
        super(url);
        this.appSecret = appSecret;
        this.addTextPara("showapi_appid", appid);
        this.addHeadPara("User-Agent", "showapi-sdk-java");//设置默认头
    }


    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    private String addSign() throws IOException {
        boolean ismd5 = true;
        if (textMap.get(Constants.SHOWAPI_APPID) == null) return errorMsg(Constants.SHOWAPI_APPID + "不得为空!");
        String signmethod = textMap.get(Constants.SHOWAPI_SIGN_METHOD);
        if (signmethod != null && !signmethod.equals("md5")) ismd5 = false;
        if (signmethod != null && !signmethod.equals("md5") && !signmethod.equals("hmac"))
            return errorMsg("showapi_sign_method参数只能是md5或hmac");

        //现在不写默认值了
//		if(textMap.get(Constants.SHOWAPI_TIMESTAMP)==null){
//			SimpleDateFormat df=new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
//			String timestamp= df.format(new Date());
//			textMap.put(Constants.SHOWAPI_TIMESTAMP,timestamp);
//		}
        if (ismd5) {
            textMap.put(Constants.SHOWAPI_SIGN, ShowApiUtils.signRequest(textMap, appSecret, false));
        } else {
            textMap.put(Constants.SHOWAPI_SIGN, ShowApiUtils.signRequest(textMap, appSecret, true));
        }
        return null;
    }

    public String post() {
        String res = null;
        try {
            byte b[] = postAsByte();
            res = new String(b, "utf-8");
        } catch (Exception e) {
            if (printException) e.printStackTrace();
        }
        return res;
    }

    public byte[] postAsByte() {
        byte res[] = null;
        try {
            String signResult = addSign();
            if (signResult != null) return signResult.getBytes("utf-8");
            res = WebUtils.doPostAsByte(this);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                res = ("{showapi_res_code:-1,showapi_res_error:\"" + e.toString() + "\"}").getBytes("utf-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        }
        return res;
    }


    public String get() {
        String res = null;
        try {
            byte b[] = getAsByte();
            res = new String(b, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            res = "{showapi_res_code:-1,showapi_res_error:\"" + e.toString() + "\"}";
        }
        return res;
    }

    public byte[] getAsByte() {
        byte[] res = null;
        try {
            String signResult = addSign();
            if (signResult != null) return signResult.getBytes("utf-8");
            res = WebUtils.doGetAsByte(this);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                res = ("{showapi_res_code:-1,showapi_res_error:\"" + e.toString() + "\"}").getBytes("utf-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        }
        return res;
    }

    private String errorMsg(String msg) {
        String str = "{" + Constants.SHOWAPI_RES_CODE + ":-1," + Constants.SHOWAPI_RES_ERROR + ":\"" + msg + "\"," + Constants.SHOWAPI_RES_BODY + ":{}}";
        return str;
    }


    public static void main(String adfas[]) throws Exception {
    }


}

