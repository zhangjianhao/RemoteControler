package com.show.api.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.show.api.FileItem;
import com.show.api.NormalRequest;

/**
 * 网络工具类。
 */
public class WebUtils {

    private static final String METHOD_LIST = "LIST";
    //	private static final String METHOD_POST = "POST";
//	private static final String METHOD_POST = "POST";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_GET = "GET";

    public static class VerisignTrustManager implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//			Exception exp = null;
//			for (X509Certificate cert : chain) {
//				cert.checkValidity(); // 验证证书有效期
//				try {
//					cert.verify(verisign.getPublicKey());// 验证签名
//					exp = null;
//					break;
//				} catch (Exception e) {
//					exp = e;
//				}
//			}
//
//			if (exp != null) {
//				throw new CertificateException(exp);
//			}
        }
    }


    public static class TrustAllTrustManager implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }


    public static ResData _doGetAsByte(NormalRequest req) throws IOException {
        HttpURLConnection conn = null;
        byte[] rsp = null;
        ResData res = new ResData();
        try {
            String ctype = "application/x-www-form-urlencoded;charset=" + req.getCharset();
            String query = buildQuery(req.getTextMap(), req.getCharset());
            try {
                conn = getConnection(buildGetUrl(req.getUrl(), query), METHOD_GET, ctype, req);
                conn.setConnectTimeout(req.getConnectTimeout());
                conn.setReadTimeout(req.getReadTimeout());
            } catch (IOException e) {
                ShowApiLogger.logCommError(e, req.getUrl(), req.getTextMap().get("showapi_app_id"), req.getTextMap());
                throw e;
            }

            try {
                Map res_headMap = conn.getHeaderFields();
                rsp = getResponseAsByte(conn);
                req.setRes_headtMap(res_headMap); //设置返回头
            } catch (IOException e) {
                ShowApiLogger.logCommError(e, req.getUrl(), req.getTextMap().get("showapi_app_id"), req.getTextMap());
                throw e;
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        res.setResData(rsp);
        res.setRes_maybe_encoding(getResponseCharset(conn.getContentType()));
        return res;
    }

    public static ResData _doPostAsByte(NormalRequest req) throws IOException {
        ResData res = new ResData();
        byte[] rsp = null;
        HttpURLConnection conn = null;
        OutputStream out = null;
        if (req.getBody() != null) {//直接提交json或二进制流
            String ctype = "application/octet-stream;charset=" + req.getCharset();
            try {
                try {
                    conn = getConnection(new URL(req.getUrl()), METHOD_POST, ctype, req);
                    conn.setConnectTimeout(req.getConnectTimeout());
                    conn.setReadTimeout(req.getReadTimeout());
                    out = conn.getOutputStream();
                    out.write(req.getBody());
                    out.flush();

                    Map res_headMap = conn.getHeaderFields();
                    rsp = getResponseAsByte(conn);
                    req.setRes_headtMap(res_headMap); //设置返回头
                } catch (IOException e) {
                    ShowApiLogger.logCommError(e, req.getUrl(), req.getTextMap().get("showapi_app_id"), new HashMap<String, String>());
                    throw e;
                }
            } finally {
                if (out != null) {
                    out.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            }

        } else if (req.getBodyString() != null) {//直接提交body字符串
            String ctype = "application/octet-stream;charset=" + req.getCharset();
            try {
                try {
                    conn = getConnection(new URL(req.getUrl()), METHOD_POST, ctype, req);
                    conn.setConnectTimeout(req.getConnectTimeout());
                    conn.setReadTimeout(req.getReadTimeout());
                    out = conn.getOutputStream();
                    out.write(req.getBodyString().getBytes(req.getCharset()));
                    out.flush();

                    Map res_headMap = conn.getHeaderFields();
                    rsp = getResponseAsByte(conn);
                    req.setRes_headtMap(res_headMap); //设置返回头
                } catch (IOException e) {
                    ShowApiLogger.logCommError(e, req.getUrl(), req.getTextMap().get("showapi_app_id"), new HashMap<String, String>());
                    throw e;
                }
            } finally {
                if (out != null) {
                    out.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            }

        } else {//普通表单提交
            String ctype = "application/x-www-form-urlencoded;charset=" + req.getCharset();
            String query = buildQuery(req.getTextMap(), req.getCharset());
            byte[] content = null;
            if (query != null) {
                content = query.getBytes(req.getCharset());
            } else {
                content = "".getBytes(req.getCharset());
            }
            try {
                try {
                    conn = getConnection(new URL(req.getUrl()), METHOD_POST, ctype, req);
                    conn.setConnectTimeout(req.getConnectTimeout());
                    conn.setReadTimeout(req.getReadTimeout());
                    out = conn.getOutputStream();
                    out.write(content);

                    Map res_headMap = conn.getHeaderFields();
                    rsp = getResponseAsByte(conn);
                    req.setRes_headtMap(res_headMap); //设置返回头
                } catch (IOException e) {
                    ShowApiLogger.logCommError(e, req.getUrl(), req.getTextMap().get("showapi_app_id"), query);
                    throw e;
                }
            } finally {
                if (out != null) {
                    out.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
        res.setResData(rsp);
        res.setRes_maybe_encoding(getResponseCharset(conn.getContentType()));
        return res;
    }

    private static ResData _doPostWithFileAsByte(NormalRequest req) throws IOException {
        String boundary = String.valueOf(System.nanoTime()); // 随机分隔线
        HttpURLConnection conn = null;
        OutputStream out = null;
        byte[] rsp = null;
        ResData res = new ResData();
        try {
            StringBuilder strBody = new StringBuilder();//仅仅用于日志
            try {
                String ctype = "multipart/form-data;charset=" + req.getCharset() + ";boundary=" + boundary;
                conn = getConnection(new URL(req.getUrl()), METHOD_POST, ctype, req);
                conn.setConnectTimeout(req.getConnectTimeout());
                conn.setReadTimeout(req.getReadTimeout());
                out = conn.getOutputStream();
                byte[] entryBoundaryBytes = ("\r\n--" + boundary + "\r\n").getBytes(req.getCharset());

                // 组装文本请求参数
                Set<Entry<String, String>> textEntrySet = req.getTextMap().entrySet();
                for (Entry<String, String> textEntry : textEntrySet) {
                    strBody.append(textEntry.getKey() + "=" + textEntry.getValue() + "&");
                    byte[] textBytes = getTextEntry(textEntry.getKey(), textEntry.getValue(), req.getCharset());
                    out.write(entryBoundaryBytes);
                    out.write(textBytes);
                }

                // 组装文件请求参数
                Set<Entry<String, File>> fileEntrySet = req.getUploadMap().entrySet();
                for (Entry<String, File> fileEntry : fileEntrySet) {
                    File f = fileEntry.getValue();
                    FileItem fileItem = new FileItem(f);
                    if (fileItem.getContent() == null) {
                        continue;
                    }
                    byte[] fileBytes = getFileEntry(fileEntry.getKey(), fileItem.getFileName(), fileItem.getMimeType(), req.getCharset());
                    out.write(entryBoundaryBytes);
                    out.write(fileBytes);
                    out.write(fileItem.getContent());
                }

                // 添加请求结束标志
                byte[] endBoundaryBytes = ("\r\n--" + boundary + "--\r\n").getBytes(req.getCharset());
                out.write(endBoundaryBytes);
                out.flush();

                Map res_headMap = conn.getHeaderFields();
                rsp = getResponseAsByte(conn);
                req.setRes_headtMap(res_headMap); //设置返回头

            } catch (IOException e) {
                ShowApiLogger.logCommError(e, req.getUrl(), req.getTextMap().get("showapi_app_id"), strBody.toString());
                throw e;
            }
        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        res.setResData(rsp);
        res.setRes_maybe_encoding(getResponseCharset(conn.getContentType()));
        return res;
    }

    /**
     * 执行带文件上传的HTTP POST请求。
     *
     * @param url        请求地址
     * @param textParams 文本请求参数
     * @param fileParams 文件请求参数
     * @return 响应字符串
     */
    public static String doPost(NormalRequest req) throws IOException {
        ResData res;
        if (req.getUploadMap() == null || req.getUploadMap().isEmpty()) {
            res = _doPostAsByte(req);

        } else {
            res = _doPostWithFileAsByte(req);
        }
        return new String(res.getResData(), res.getRes_maybe_encoding());
    }

    public static byte[] doPostAsByte(NormalRequest req) throws IOException {
        if (req.getUploadMap() == null || req.getUploadMap().isEmpty()) {
            return _doPostAsByte(req).getResData();
        } else {
            return _doPostWithFileAsByte(req).getResData();
        }
    }


    /**
     * 执行HTTP GET请求。
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param charset 字符集，如UTF-8, GBK, GB2312
     * @return 响应字符串
     */
    public static String doGet(NormalRequest req) throws IOException {
        ResData res = _doGetAsByte(req);
        return new String(res.getResData(), res.getRes_maybe_encoding());
    }

    /**
     * 执行普通非文件的HTTP POST请求。
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应字符串
     */
    public static byte[] doGetAsByte(NormalRequest req) throws IOException {
        ResData res = _doGetAsByte(req);
        return res.getResData();
    }


    public static ByteArrayOutputStream unzip(InputStream in) {
        ByteArrayOutputStream fout = new ByteArrayOutputStream();
        try {
            // 建立gzip解压工作流
            GZIPInputStream gzin = new GZIPInputStream(in);
            // 建立解压文件输出流

            byte[] buf = new byte[1024];
            int num;

            while ((num = gzin.read(buf, 0, buf.length)) != -1) {
                fout.write(buf, 0, num);
            }
            gzin.close();
            fout.close();
            in.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return fout;
    }

    private static byte[] getTextEntry(String fieldName, String fieldValue, String charset) throws IOException {
        StringBuilder entry = new StringBuilder();
        entry.append("Content-Disposition:form-data; name=\"");
        entry.append(fieldName);
        entry.append("\"\r\nContent-Type:text/plain\r\n\r\n");
        entry.append(fieldValue);
//		System.out.println(entry);
        return entry.toString().getBytes(charset);
    }

    private static byte[] getFileEntry(String fieldName, String fileName, String mimeType, String charset) throws IOException {
        StringBuilder entry = new StringBuilder();
        entry.append("Content-Disposition:form-data; name=\"");
        entry.append(fieldName);
        entry.append("\"; filename=\"");
        entry.append(fileName);
        entry.append("\"\r\nContent-Type:");
        entry.append(mimeType);
        entry.append("\r\n\r\n");
//		System.out.println(entry);
        return entry.toString().getBytes(charset);
    }


    private static HttpURLConnection getConnection(URL url, String method, String ctype, NormalRequest req) throws IOException {
        HttpURLConnection conn;
        Proxy proxy = req.getProxy();
        if (proxy != null) {
            conn = (HttpURLConnection) url.openConnection(proxy);
        } else {
            conn = (HttpURLConnection) url.openConnection();
        }
        if (conn instanceof HttpsURLConnection) {
            HttpsURLConnection connHttps = (HttpsURLConnection) conn;
            try {
                SSLContext ctx = SSLContext.getInstance("TLS");
                ctx.init(null, new TrustManager[]{new TrustAllTrustManager()}, new SecureRandom());
                connHttps.setSSLSocketFactory(ctx.getSocketFactory());
                connHttps.setHostnameVerifier(new HostnameVerifier() {
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
            } catch (Exception e) {
                throw new IOException(e);
            }
            conn = connHttps;
        }
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        if (method.equals(METHOD_POST)) {
            conn.setDoOutput(true);
        }
        conn.setRequestProperty("Accept", "application/json, text/javascript, */*; ");
        conn.setRequestProperty("User-Agent", "showapi-sdk-java");
        conn.setRequestProperty("Content-Type", ctype);
        Map<String, String> headerMap = req.getHeadMap();
        if (headerMap != null) {
            for (Entry<String, String> entry : headerMap.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        conn.setInstanceFollowRedirects(req.isAllowRedirect());
        return conn;
    }

    private static URL buildGetUrl(String strUrl, String query) throws IOException {
        URL url = new URL(strUrl);
        if (StringUtils.isEmpty(query)) {
            return url;
        }

        if (StringUtils.isEmpty(url.getQuery())) {
            if (strUrl.endsWith("?")) {
                strUrl = strUrl + query;
            } else {
                strUrl = strUrl + "?" + query;
            }
        } else {
            if (strUrl.endsWith("&")) {
                strUrl = strUrl + query;
            } else {
                strUrl = strUrl + "&" + query;
            }
        }
        return new URL(strUrl);
    }

    public static String buildQuery(Map params, String charset) {
        if (params == null || params.isEmpty()) {
            return null;
        }
        StringBuilder query = new StringBuilder();
        Set<Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;
        try {
            for (Entry<String, String> entry : entries) {
                String name = entry.getKey();
                String value = entry.getValue();
                // 忽略参数名或参数值为空的参数
                if (name != null && name.length() > 0 && value != null) {
                    if (hasParam) {
                        query.append("&");
                    } else {
                        hasParam = true;
                    }
                    query.append(name).append("=").append(URLEncoder.encode(value, charset));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return query.toString();
    }

    protected static byte[] getResponseAsByte(HttpURLConnection conn) throws IOException {
        InputStream es = conn.getErrorStream();
        if (es == null) {
            if (conn.getContentEncoding() != null && conn.getContentEncoding().toLowerCase().equals("gzip")) {
                byte bbb[] = unzip(conn.getInputStream()).toByteArray();
                return bbb;
            } else {
                if (conn.getResponseCode() >= 400) {
                    return new byte[0];
                }
                return _readByteFromStream(conn.getInputStream());
            }
        } else {
            String charset_res = getResponseCharset(conn.getContentType());
            byte b[] = _readByteFromStream(conn.getInputStream());
            String msg = new String(b, charset_res);
            if (StringUtils.isEmpty(msg)) {
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
            } else {
                throw new IOException(msg);
            }
        }
    }

    protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset_res = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        if (es == null) {
            if (conn.getContentEncoding() != null && conn.getContentEncoding().toLowerCase().equals("gzip")) {
                byte bbb[] = unzip(conn.getInputStream()).toByteArray();
                return new String(bbb, charset_res);
            } else {
                return _readCharString(conn.getInputStream(), charset_res);
            }
        } else {
            String msg = _readCharString(es, charset_res);
            if (StringUtils.isEmpty(msg)) {
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
            } else {
                throw new IOException(msg);
            }
        }
    }

    private static byte[] _readByteFromStream(InputStream stream) throws IOException {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte buf[] = new byte[1024];
            int read = 0;
            while ((read = stream.read(buf)) > 0) {
                out.write(buf, 0, read);
            }
            return out.toByteArray();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    private static String _readCharString(InputStream stream, String charset) throws IOException {
        try {
            Reader reader = new InputStreamReader(stream, charset);
            StringBuilder response = new StringBuilder();

            final char[] buff = new char[1024];
            int read = 0;
            while ((read = reader.read(buff)) > 0) {
                response.append(buff, 0, read);
            }

            return response.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    private static String getResponseCharset(String ctype) {
        String charset = "utf-8";
        if (!StringUtils.isEmpty(ctype)) {
            String[] params = ctype.split(";");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        if (!StringUtils.isEmpty(pair[1])) {
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }

        return charset;
    }


    /**
     * 使用指定的字符集反编码请求参数值。
     *
     * @param value   参数值
     * @param charset 字符集
     * @return 反编码后的参数值
     */
    public static String decode(String value, String charset) {
        String result = null;
        if (!StringUtils.isEmpty(value)) {
            try {
                result = URLDecoder.decode(value, charset);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * 使用指定的字符集编码请求参数值。
     *
     * @param value   参数值
     * @param charset 字符集
     * @return 编码后的参数值
     */
    public static String encode(String value, String charset) {
        String result = null;
        if (!StringUtils.isEmpty(value)) {
            try {
                result = URLEncoder.encode(value, charset);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    private static Map<String, String> getParamsFromUrl(String url) {
        Map<String, String> map = null;
        if (url != null && url.indexOf('?') != -1) {
            map = splitUrlQuery(url.substring(url.indexOf('?') + 1));
        }
        if (map == null) {
            map = new HashMap<String, String>();
        }
        return map;
    }

    /**
     * 从URL中提取所有的参数。
     *
     * @param query URL地址
     * @return 参数映射
     */
    public static Map<String, String> splitUrlQuery(String query) {
        Map<String, String> result = new HashMap<String, String>();

        String[] pairs = query.split("&");
        if (pairs != null && pairs.length > 0) {
            for (String pair : pairs) {
                String[] param = pair.split("=", 2);
                if (param != null && param.length == 2) {
                    result.put(param[0], param[1]);
                }
            }
        }

        return result;
    }

    public static void main(String adfas[]) throws Exception {
        byte[] b = new NormalRequest("http://localhost:80/")
                .setCharset("utf-8")
                .addTextPara("aaa", "bbbb")
                .setBody("{name:'组长三'}".getBytes())
                .setBodyString("{name:'组长三aaa'}")
                .postAsByte();
        String res = new String(b, "gbk");
        System.out.println(res);

    }

}
