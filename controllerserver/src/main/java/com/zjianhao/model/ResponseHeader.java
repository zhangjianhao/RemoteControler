package com.zjianhao.model;

/**
 * Created by 张建浩（Clarence) on 2017-4-18 15:41.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class ResponseHeader<T> {
    public static final int RESPONSE_OK = 200;
    public static final int RESPONSE_EXCEPTION = 500;
    private int code = RESPONSE_OK;

    private String errorMsg;

    private T result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
