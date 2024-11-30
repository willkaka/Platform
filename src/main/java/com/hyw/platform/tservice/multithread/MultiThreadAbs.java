package com.hyw.platform.tservice.multithread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.GenericTypeResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 多线程执行器
 * @param <I> 输入参数对象
 * @param <O> 输出数据对象
 */
@Slf4j
public abstract class MultiThreadAbs<I,O> {

    /**
     * 线程执行内容
     * @param t 输入参数
     * @return O输出
     */
    protected abstract O execute(I t);

    /**
     * 发起多线程并发执行_等待执行结果
     * @param paramList 参数
     */
    protected Map<Integer,O> executeMultiThread(List<I> paramList,String poolPre) {
        return executeThread(paramList,poolPre);
    }

    /**
     * 发起多线程并发执行_异步_不等待执行结果
     * @param paramList 参数
     */
    protected void executeMultiThreadAsync(List<I> paramList,String poolPre) {
        Executors.newSingleThreadExecutor().execute(() -> this.executeThread(paramList,poolPre));
    }

    private Map<Integer,O> executeThread(List<I> paramList,String poolPre) {
        Class<O> clazz = variableClass();
        if(CollectionUtils.isEmpty(paramList)) return null;
        ExecutorService executorService = genExecuteService(poolPre,8,paramList.size()-8);
        List<Future<Map<String, String>>> futureList = new ArrayList<>();

        int threadNo = -1;
        for (I t : paramList) {
            threadNo++;
            Map<String,String> threadRtnMap = new HashMap<>();
            int finalThreadNo = threadNo;
            futureList.add(executorService.submit(() -> {
                O o = execute(t);
                threadRtnMap.put(String.valueOf(finalThreadNo),JSONObject.toJSONString(o));
                return threadRtnMap;
            }));
            log.info("多线程执行器已提交并发线程，参数:{}！", JSONObject.toJSONString(t));
        }

        Map<Integer,O> threadRtnData = new HashMap<>();
        //检查每个线程返回参数
        for (Future<Map<String, String>> future : futureList) {
            try {
                Map<String, String> rtnMap = future.get();
                int threadNumber;
                for(String k:rtnMap.keySet()){
                    threadNumber = Integer.parseInt(k);
                    O o = JSON.parseObject(rtnMap.get(k),clazz);
                    threadRtnData.put(threadNumber,o);
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        return threadRtnData;
    }

    private ExecutorService genExecuteService(String poolPre,int coreSize,int maxSize){
        String poolNameFormat = poolPre+"-%d";
        return new ThreadPoolExecutor(
                coreSize,
                Math.max(maxSize,coreSize),
                2000L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(16),
                new ThreadFactoryBuilder().setNameFormat(poolNameFormat).build(),
                new ThreadPoolExecutor.AbortPolicy());
    }


    /**
     * 实例化变量
     *
     * @return 返回实例化的变量信息
     */
    public Class<O> variableClass() {
        Class<O> vClass = null;
        try {
            Class[] genericArgs = GenericTypeResolver.resolveTypeArguments(getClass(), MultiThreadAbs.class);
            if(genericArgs.length>1) {
                vClass = genericArgs[1];
            }else{
                vClass = genericArgs[0];
            }
            return vClass;
        } catch (Exception e) {
            throw new RuntimeException(vClass + " 实例化失败", e);
        }
    }
}
