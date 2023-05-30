package com.hyw.platform.tservice.http;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import okhttp3.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class OkHttpUtil {
//
//    private static volatile OkHttpClient client;
//
//    private static final String DEFAULT_MEDIA_TYPE = "application/json;charset=utf-8";
//
//    private static final int CONNECT_TIMEOUT = 5;
//
//    private static final int READ_TIMEOUT = 7;
//
//    /**
//     * 单例模式  获取类实例
//     *
//     * @return client
//     */
//    private static OkHttpClient getInstance() {
//        if (client == null) {
//            synchronized (OkHttpClient.class) {
//                if (client == null) {
//                    client = new OkHttpClient.Builder()
//                            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)// 设置连接超时时间
//                            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)// 设置读取超时时间
//                            .build();
//                }
//            }
//        }
//        return client;
//    }
//
//    public static String get(String url) {
//        try {
//            Request request = new Request.Builder().url(url).build();
//            Response response = getInstance().newCall(request).execute();
//            return handleHttpResponse(response);
//        } catch (Exception ex) {
//            return StringUtils.EMPTY;
//        }
//    }
//
//    public static String encode(String str) {
//        String encode = null;
//        try {
//            encode = URLEncoder.encode(str, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return encode;
//    }
//
//    /**
//     * get请求 参数转码
//     *
//     * @return
//     * @throws UnsupportedEncodingException
//     */
//    public static String getParams(Map<String, String> params) {
//        StringBuilder strSb = new StringBuilder();
//        boolean index = false;
//        for (Map.Entry<String, String> entry : params.entrySet()) {
//            if (index) {
//                strSb.append("&");
//            }
//            strSb.append(entry.getKey())
//                    .append("=")
//                    .append(encode(entry.getValue()));
//            index = true;
//        }
//        return strSb.toString();
//    }
//
//
//    public static String get(String url, Map<String, String> params) {
//        try {
//            url = url + "?" + getParams(params);
//            System.out.println(url);
//            Request request = new Request.Builder().url(url).build();
//            Response response = getInstance().newCall(request).execute();
//            return handleHttpResponse(response);
//        } catch (Exception ex) {
//            return StringUtils.EMPTY;
//        }
//    }
//
//
//    public static String post(String url, String postBody) {
//        try {
//            MediaType mediaType = MediaType.Companion.parse(DEFAULT_MEDIA_TYPE);
//            RequestBody stringBody = RequestBody.Companion.create(postBody, mediaType);
//            Request request = new Request.Builder()
//                    .url(url)
//                    .post(stringBody)
//                    .build();
//            Response response = getInstance().newCall(request).execute();
//            return handleHttpResponse(response);
//        } catch (Exception ex) {
//            return StringUtils.EMPTY;
//        }
//    }
//
//    public static String upload(String url, Map<String, String> params, File file) throws IOException {
//        MultipartBody.Builder builder = new MultipartBody.Builder();
//        String fileName = file.getName();
//        MediaType mediaType = MediaType.Companion.parse("multipart/form-data");
//        RequestBody fileBody = RequestBody.Companion.create(file, mediaType);
//        for (Map.Entry<String, String> entry : params.entrySet()) {
//            builder.addFormDataPart(entry.getKey(), entry.getValue());  // 上传参数
//        }
//        RequestBody requestBody = builder
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("file", fileName, fileBody)
//                .build();
//        Request request = new Request.Builder()
//                .url(url)
//                .post(requestBody)
//                .build();
//        Response response = getInstance().newCall(request).execute();
//        return handleHttpResponse(response);
//    }
//
//
//    public static void main(String[] args) throws IOException {
//        HashMap<String, String> map = new HashMap<>();
//        map.put("name","小明");
//        System.out.println(upload("http://127.0.0.1:8080/upload", map,new File("C:\\Users\\a\\Pictures\\1.jpg")));
//
//    }
//
//
//    private static String handleHttpResponse(Response response) throws IOException {
//        if (response.body() == null) {
//            throw new IOException("exception in OkHttpUtil,response body is null");
//        }
//        if (!response.isSuccessful()) {
//            throw new RuntimeException("OkHttpUtil request failed");
//        }
//        return response.body().string();
//    }

}

