package com.hyw.platform.tools.text;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Slf4j
@Service
public class TextReaderService {

    @Resource(name="textReaderThreadPoolInstance")
    private ExecutorService executorService;

    public void mulThreadsRead(File file,int threadCount){
        if(!file.isFile()) return;
        long lineCount = 0L;
        try {
            lineCount = Files.lines(file.toPath()).count();
        }catch (Exception e){
            //
        }

        // 计算每个线程需要处理的行数
        long eachThreadLineCnt = (lineCount+threadCount-1)/threadCount; //(x + y - 1) / y 向上取整


        List<Future<Map<String, String>>> futureList = new ArrayList<>();
        for(long index = 0L;index < lineCount;index=index+eachThreadLineCnt){
            long lineStart = index;
            futureList.add(executorService.submit(() -> executeThread(file, lineStart, eachThreadLineCnt)));
        }
        for(Future<Map<String, String>> future:futureList){
            try {
                Map<String, String> rtnMap = future.get();
                rtnMap.forEach((k,v)->{
                    log.info("PlanThread_信息发送计划{}线程返回：{}",k,v);
                });
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 执行入口
     */
    public Map<String,String> executeThread(File textFile, long readLineStart, long readLineCount){
        Map<String, String> rtnMap = new HashMap<>();

        rtnMap.put(String.valueOf(readLineStart),"success");
        return rtnMap;
    }
}
