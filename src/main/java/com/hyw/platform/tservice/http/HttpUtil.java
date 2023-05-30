package com.hyw.platform.tservice.http;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

public class HttpUtil {

    private static final int TIME_OUT = 5 * 1000;             //超时时间
    private static final String GET = "GET";                  //GET请求
    private static final String POST = "POST";                //GET请求
    private static final String CHARSET = "UTF-8";            //编码格式
    private static final String PREFIX = "--";                //前缀
    private static final String LINE_END = "\r\n";            //换行

    /**
     * 对post参数进行编码处理
     */
    private static StringBuilder getStrParams(Map<String, String> strParams, String BOUNDARY) {
        StringBuilder strSb = new StringBuilder();
        for (Map.Entry<String, String> entry : strParams.entrySet()) {
            strSb.append(PREFIX)
                    .append(BOUNDARY)
                    .append(LINE_END)
                    .append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINE_END)
                    .append("Content-Type: text/plain; charset=" + CHARSET + LINE_END)
                    .append("Content-Transfer-Encoding: 8bit" + LINE_END)
                    .append(LINE_END)// 参数头设置完以后需要两个换行，然后才是参数内容
                    .append(entry.getValue())
                    .append(LINE_END);
        }
        return strSb;
    }


    public static String upload(String url, Map<String, String> strParams, File file) {
        String boundary = UUID.randomUUID().toString(); // 文件边界
        String filename = file.getName();
        StringBuffer result = new StringBuffer();
        HttpURLConnection connection = null;
        try {
            URL url1 = new URL(url);
            connection = (HttpURLConnection) url1.openConnection();
            connection.setRequestMethod(POST);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(TIME_OUT);
            connection.setReadTimeout(TIME_OUT);
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Charset", CHARSET);
            connection.setRequestProperty("Content-Type", "multipart/form-data" + ";boundary=" + boundary);
            connection.connect();
            try (DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
                 InputStream is = new FileInputStream(file)) {
                dos.writeBytes(getStrParams(strParams, boundary).toString());
                dos.flush();
                StringBuilder fileSb = new StringBuilder();
                fileSb.append(PREFIX)
                        .append(boundary)
                        .append(LINE_END)
                        .append("Content-Disposition: form-data; name=\"file\"; filename=\""
                                + filename + "\"" + LINE_END)
                        .append("Content-Transfer-Encoding: 8bit" + LINE_END)
                        .append(LINE_END);
                dos.writeBytes(fileSb.toString());
                dos.flush();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    dos.write(buffer, 0, len);
                }
                //请求结束标志
                dos.writeBytes(LINE_END + PREFIX + boundary + PREFIX + LINE_END);
                dos.flush();
            }
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), CHARSET))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                System.out.println(result.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return result.toString();
    }


    public static String post(String url, String content) {
        StringBuffer result = new StringBuffer();
        HttpURLConnection connection = null;
        try {
            URL url1 = new URL(url);
            connection = (HttpURLConnection) url1.openConnection();
            //设置请求方式
            connection.setRequestMethod(POST);
            //DoOutput设置是否向httpUrlConnection输出，DoInput设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 设置是否允许缓存值
            connection.setUseCaches(false);
            //设置连接超时时间
            connection.setConnectTimeout(TIME_OUT);
            //设置读取超时时间
            connection.setReadTimeout(TIME_OUT);
            //设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            connection.connect();
            try (OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), CHARSET)) {
                out.append(content);
                out.flush();
            }
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), CHARSET))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                System.out.println(result.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return result.toString();
    }


    public static String get(String url) {
        StringBuffer stringBuffer = new StringBuffer();
        HttpURLConnection connection = null;
        try {
            URL url1 = new URL(url);
            connection = (HttpURLConnection) url1.openConnection();
            //设置请求方式
            connection.setRequestMethod(GET);
            //设置连接超时时间
            connection.setReadTimeout(TIME_OUT);
            //开始连接
            connection.connect();
            try (BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), CHARSET))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        System.out.println("GET请求返回值：" + stringBuffer.toString());
        return stringBuffer.toString();
    }

}

