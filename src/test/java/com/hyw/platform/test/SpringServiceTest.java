package com.hyw.platform.test;

import com.alibaba.fastjson.JSONObject;
import com.hyw.platform.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SpringServiceTest {

    @Autowired
    private MultiThreadTest multiThreadTest;

    @Test
    public void test(){
        multiThreadTest.multiThreadExecute();
    }

    @Test
    public void test1(){
        List<Map<String,String>> paramMapList = new ArrayList<>();
        for(int threadNo =1;threadNo<=100;threadNo++){
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("threadNum",String.valueOf(threadNo));
            paramMapList.add(paramMap);
        }

        int size = 48;
        for (int index = 0; index < paramMapList.size(); ) {

            List<Map<String,String>> subList = paramMapList.subList(index, Math.min(paramMapList.size(), index = index + size));
            log.info("{}", JSONObject.toJSONString(subList));
        }
    }
}
