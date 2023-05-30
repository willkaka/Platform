package com.hyw.platform.tools.text;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class MulThreadPoolConfig {

    @Bean(value = "textReaderThreadPoolInstance")
    public ExecutorService createThreadPoolInstance(){
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("text-reader-thread-pool-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(16,32,2000L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(16), threadFactory, new ThreadPoolExecutor.AbortPolicy());
        return executorService;
    }
//
//    @Bean(value = "messagePlanSubThreadPoolInstance")
//    public ExecutorService createSubThreadPoolInstance(){
//        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("message-plan-sub-thread-pool-%d").build();
//        ExecutorService executorService = new ThreadPoolExecutor(48,96,2000L, TimeUnit.SECONDS,
//                new ArrayBlockingQueue<Runnable>(48), threadFactory, new ThreadPoolExecutor.AbortPolicy());
//        return executorService;
//    }

}
