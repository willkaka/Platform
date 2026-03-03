//package com.hyw.platform.test;
//
//import com.alibaba.fastjson.JSONObject;
//import com.hyw.gdata.DataService;
//import com.hyw.gdata.NQueryWrapper;
//import com.hyw.platform.Application;
//import com.hyw.platform.model.ConfigDatabaseInfo;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
//public class SpringServiceTest {
//
//    @Autowired
//    private DynamicDataSourceService dynamicDataSourceService;
//    @Autowired
//    private DataService dataService;
//    @Autowired
//    private MultiThreadTest multiThreadTest;
//
//    @Test
//    public void test(){
//        multiThreadTest.multiThreadExecute();
//    }
//
//    @Test
//    public void test1(){
//
//        dynamicDataSourceService.createAndSwitchDataSource("sit3_caes",
//                "jdbc:mysql://10.21.16.31:4588/caes?serverTimezone=Asia/Shanghai",
//                "dfdsro",
//                "iCOWaVU6$bJq",
//                "com.mysql.cj.jdbc.Driver");
//
//        ConfigDatabaseInfo configDatabaseInfo = dataService.getOne(new NQueryWrapper<ConfigDatabaseInfo>()
//                .eq(ConfigDatabaseInfo::getDatabaseName, "sit3_caes"));
//
//
//        List<Map<String,String>> paramMapList = new ArrayList<>();
//        for(int threadNo =1;threadNo<=100;threadNo++){
//            Map<String,String> paramMap = new HashMap<>();
//            paramMap.put("threadNum",String.valueOf(threadNo));
//            paramMapList.add(paramMap);
//        }
//
//        int size = 48;
//        for (int index = 0; index < paramMapList.size(); ) {
//
//            List<Map<String,String>> subList = paramMapList.subList(index, Math.min(paramMapList.size(), index = index + size));
//            log.info("{}", JSONObject.toJSONString(subList));
//        }
//    }
//}
