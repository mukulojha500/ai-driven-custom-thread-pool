package com.mukul.aithreadpool.spring;

import com.mukul.aithreadpool.core.ThreadPool;
import com.mukul.aithreadpool.core.threadpool_impl.AIThreadPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadPoolAutoConfiguration {

    @Bean(destroyMethod = "shutdown")
    public ThreadPool aiThreadPool(
            @Value("${ai.pool.threads:4}") int threads,
            @Value("${ai.pool.queue:100}") int queueSize
    ) {
        return new AIThreadPool(threads, queueSize);
    }
}

