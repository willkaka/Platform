package com.hyw.platform.tservice.http;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
//import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpClientUtil {


    public static void get(String url) {
        // 创建 Http 请求对象
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(3000)//使用连接池来管理连接,从连接池获取连接的超时时间
                .setSocketTimeout(5000)//请求超时,数据传输过程中数据包之间间隔的最大时间
                .setConnectTimeout(5000)//连接超时,连接建立时间,三次握手完成时间
                .build();
        httpGet.setConfig(requestConfig);
        try (// 获得Http客户端(创建 CloseableHttpClient 对象或 CloseableHttpAsyncClient 对象)
             CloseableHttpClient httpClient = HttpClientBuilder.create().build();
             // 调用 execute 方法执行请求，如果是异步请求在执行之前需调用 start 方法
             CloseableHttpResponse response = httpClient.execute(httpGet)) {
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity, StandardCharsets.UTF_8));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * get请求 参数转码
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getParams(Map<String, String> params){
        StringBuilder strSb = new StringBuilder();
        boolean index = false;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (index) {
                strSb.append("&");
            }
            strSb.append(entry.getKey())
                    .append("=")
                    .append(encode(entry.getValue()));
            index = true;
        }
        return strSb.toString();
    }

    public static void get(String url, Map<String, String> params) {
        // 方式一：直接拼接URL
        String param = getParams(params);
        // 创建 Http 请求对象
        HttpGet httpGet = new HttpGet(url + "?" + param);

        // 方式二：使用URI获得HttpGet
//        URI uri = null;
//        try {
//            // 将参数放入键值对类NameValuePair中,再放入集合中
//            List<NameValuePair> param = new ArrayList<>();
//            for (Map.Entry<String, String> entry : params.entrySet()) {
//                param.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//            }
//            // 设置uri信息,并将参数集合放入uri;
//            uri = new URIBuilder().setScheme("http").setHost("localhost")
//                    .setPort(8080).setPath("/Exception")
//                    .setParameters(param).build();
//
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        // 创建Get请求
//        HttpGet httpGet = new HttpGet(uri);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(3000)//使用连接池来管理连接,从连接池获取连接的超时时间
                .setSocketTimeout(5000)//请求超时,数据传输过程中数据包之间间隔的最大时间
                .setConnectTimeout(5000)//连接超时,连接建立时间,三次握手完成时间
                .build();
        httpGet.setConfig(requestConfig);
        try (// 获得Http客户端(创建 CloseableHttpClient 对象或 CloseableHttpAsyncClient 对象)
             CloseableHttpClient httpClient = HttpClientBuilder.create().build();
             // 调用 execute 方法执行请求，如果是异步请求在执行之前需调用 start 方法
             CloseableHttpResponse response = httpClient.execute(httpGet)) {
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity, StandardCharsets.UTF_8));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void post(String url, String params) {
        // 创建Post请求
        HttpPost httpPost = new HttpPost(url);
        // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
        httpPost.setEntity(new StringEntity(params, StandardCharsets.UTF_8));
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
             CloseableHttpResponse response = httpClient.execute(httpPost)) {
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity, StandardCharsets.UTF_8));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String encode(String str) {
        String encode = null;
        try {
            encode = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encode;
    }

//    public static void upload(String url, Map<String, String> params, File file) {
//        HttpPost httpPost = new HttpPost(url);
//        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
//        String filesKey = "file";
//        // (多个文件的话，使用同一个key就行，后端用数组或集合进行接收即可)
//        // 文件名其实是放在请求头的Content-Disposition里面进行传输的，如其值为form-data; name="files"; filename="头像.jpg"
//        multipartEntityBuilder.addBinaryBody(filesKey, file, ContentType.DEFAULT_BINARY, encode(file.getName()));
//        // 其它参数(注:自定义contentType，设置UTF-8是为了防止服务端拿到的参数出现乱码)
//        ContentType contentType = ContentType.create("text/plain", Charset.forName("UTF-8"));
//        for (Map.Entry<String, String> entry : params.entrySet()) {
//            multipartEntityBuilder.addTextBody(entry.getKey(), entry.getValue(), contentType);
//        }
//        HttpEntity httpEntity = multipartEntityBuilder.build();
//        httpPost.setEntity(httpEntity);
//        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//             CloseableHttpResponse response = httpClient.execute(httpPost)) {
//            HttpEntity responseEntity = response.getEntity();
//            System.out.println("HTTPS响应状态为:" + response.getStatusLine());
//            if (responseEntity != null) {
//                String responseStr = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
//                System.out.println("HTTPS响应内容为:" + responseStr);
//            }
//        } catch (ParseException | IOException e) {
//            e.printStackTrace();
//        }
//    }


}
