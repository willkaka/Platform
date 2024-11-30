package com.hyw.platform.test;

import com.hyw.platform.tservice.multithread.MultiThreadAbs;
import com.hyw.platform.tservice.multithread.dto.ThreadRtnDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MultiThreadTest extends MultiThreadAbs<Map<String,String>,ThreadRtnDto> {

    @Test
    public void multiThreadExecute(){
        List<Map<String,String>> paramMapList = new ArrayList<>();
        for(int threadNo =1;threadNo<=100;threadNo++){
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("threadNum",String.valueOf(threadNo));
            paramMapList.add(paramMap);
        }
        Map<Integer, ThreadRtnDto> rtnMap = executeMultiThread(paramMapList,"kkkk1");
        log.info("1执行完成！");


        List<Map<String,String>> paramMapList2 = new ArrayList<>();
        for(int threadNo =1;threadNo<=10;threadNo++){
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("threadNum",String.valueOf(threadNo));
            paramMapList2.add(paramMap);
        }
        executeMultiThreadAsync(paramMapList2,"kkkk2");
        log.info("2执行完成！");
    }


    /**
     * 每个线程执行内容
     * @param paramMap 输入参数
     * @return ThreadRtnDto
     */
    @Override
    protected ThreadRtnDto execute(Map<String,String> paramMap) {
        ThreadRtnDto rtnDto = new ThreadRtnDto();

        String threadNum = paramMap.get("threadNum");
        log.info("当前执行线程号{}",threadNum);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("当前执行线程号{},已结束。",threadNum);

        rtnDto.setAmount(BigDecimal.valueOf(Integer.valueOf(threadNum)*100));
        rtnDto.setRtnCode("0000");
        rtnDto.setRtnMessage("success");
        return rtnDto;
    }
}
