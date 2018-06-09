package com.show.api.util;

public class ResData {
    private byte[] resData = null; //返回流的byte
    private String res_maybe_encoding; //返回数据流可能的编码


    public byte[] getResData() {
        return resData;
    }


    public void setResData(byte[] resData) {
        this.resData = resData;
    }


    public String getRes_maybe_encoding() {
        return res_maybe_encoding;
    }


    public void setRes_maybe_encoding(String res_maybe_encoding) {
        this.res_maybe_encoding = res_maybe_encoding;
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
