package com.hyw.platform.web.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyw.platform.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class HttpUtil {

    /**
     * 获取指定HTML标签的指定属性的值
     * @param source 要匹配的源文本
     * @param element 标签名称
     * @param attr 标签的属性名称
     * @return 属性值列表
     *
     * eg.
     * String source = "<a title=中国体育报 href=''>aaa</a><a title='北京日报' href=''>bbb</a>";
     * List<String> list = match(source, "a", "title");
     */
    public static List<String> match(String source, String element, String attr) {
        List<String> result = new ArrayList<String>();
        String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?\\s.*?>";
        Matcher m = Pattern.compile(reg).matcher(source);
        while (m.find()) {
            String r = m.group(1);
            result.add(r);
        }
        return result;
    }

    /**
     *
     * @param reg 正则表达式
     * @param source 查找源
     * @return 结果集
     */
    public static List<String> search(String reg, String source) {
        //System.out.println(source);
        List<String> result = new ArrayList<String>();
        Matcher m = Pattern.compile(reg).matcher(source);
        while (m.find()) {
            for(int i=0;i<m.groupCount();i++){
                System.out.print(" "+m.group(i));
            }
            System.out.println(" ");
            if(m.groupCount()>0 && StringUtils.isNotBlank(m.group(0))) {
                String r = m.group(0);
                result.add(r);
            }
        }
//        System.out.println();
//        System.out.println(m.matches());
        log.info("HttpUtil.search,result="+result);
        return result;
    }

    public static List<Map<String,String>> searchAllGroup(String reg, String source){
        List<Map<String,String>> resultListMap = new ArrayList<>();
        Matcher m = Pattern.compile(reg).matcher(source);
        while (m.find()) {
            Map<String,String> record = new HashMap<>();
            for(int i=0;i<=m.groupCount();i++){
                record.put(String.valueOf(i),m.group(i));
            }
            resultListMap.add(record);
        }
        return resultListMap;
    }

    /**
     * 读取http地址，返回网页的文本
     * @param urlPath http地址
     * @param requestMethod GET/POST...
     * @return 返回网页的文本
     */
    public static JSONObject getHttpRequestData(String urlPath, String requestMethod, String webCharset, JSONObject param) {
        // 首先抓取异常并处理
        StringBuilder returnString = new StringBuilder();
        try {
            StringBuilder query = new StringBuilder();
            if (requestMethod.equalsIgnoreCase("GET") && param!=null && !param.isEmpty()) {
                // 构建查询字符串
                for (String key : param.keySet()) {
                    if (query.length() > 0) query.append("&");
                    query.append(key).append("=").append(URLEncoder.encode(param.getString(key), StandardCharsets.UTF_8.toString()));
                }
                urlPath = urlPath + "?" + query;
            }
            // 1  创建URL对象,接收用户传递访问地址对象链接
            URL url = new URL(urlPath);
            // 2 打开用户传递URL参数地址
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            // 3 设置HTTP请求的一些参数信息
            connect.setRequestMethod(requestMethod); // 参数必须大写
            // 设置请求头
            if (requestMethod.equalsIgnoreCase("POST")) {
                connect.setRequestProperty("Content-Type", "application/json"); // 根据需要设置Content-Type
                // 写入请求体
                if(param!=null && !param.isEmpty()) {
                    try (OutputStream os = connect.getOutputStream()) {
                        byte[] input = param.getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }
                }
            }

            connect.setRequestProperty("Accept", "application/json");
//            connect.
            connect.connect();
            // 4 获取URL请求到的数据，并创建数据流接收
            InputStream isString = connect.getInputStream();
            // 5 构建一个字符流缓冲对象,承载URL读取到的数据
            BufferedReader isRead = new BufferedReader(new InputStreamReader(isString, webCharset));
            // 6 输出打印获取到的文件流
            String str = "";
            while ((str = isRead.readLine()) != null) {
                returnString.append(str.trim());
            }
            // 7 关闭流
            isString.close();
            connect.disconnect();
            // 8 JSON转List对象
            // do somethings
        }catch(UnknownHostException e){
            throw new BizException("http地址("+urlPath+"),不可用!");
        }catch(Exception e){
            e.printStackTrace();
        }
        return returnString==null?null:JSON.parseObject(String.valueOf(returnString));
    }



    public static void main(String[] args){
        Map<String,String> param = new HashMap<>();
        param.put("codeNo","LoanStatus");
        JSONObject resp = HttpUtil.getHttpRequestData(
                "http://caes-sit3.dsfdc.com/caes/webapi/codeLibaray/codeNoList",
                "GET",
                "UTF-8",
                JSON.parseObject(JSON.toJSONString(param)));
        System.out.println(resp);
    }
}
