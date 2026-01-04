package com.mukul.aithreadpool.core.task_executor_impl;

import com.mukul.aithreadpool.core.Stats;
import com.mukul.aithreadpool.core.TaskExecutor;

public class AITaskExecutor implements TaskExecutor, Runnable {

    private final Runnable task;
    private final Stats stats;
    private final long enqueueTime;

    public AITaskExecutor(Runnable task, Stats stats){
        this.task = task;
        this.stats = stats;
        this.enqueueTime = System.currentTimeMillis();
    }

    public long queueWaitTime(){
        return System.currentTimeMillis() - enqueueTime;
    }

    public double score(){
        return 0.7 * stats.averageTime() + 0.3 * queueWaitTime();
    }

    @Override
    public void run(){
        long start = System.currentTimeMillis();
        try{
            task.run();
        }finally {
            stats.record(System.currentTimeMillis() - start);
        }
    }

    @Override
    public void execute(){
        this.run();
    }
}
