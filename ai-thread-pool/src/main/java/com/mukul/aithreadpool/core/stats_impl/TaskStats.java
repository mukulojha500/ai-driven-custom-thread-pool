package com.mukul.aithreadpool.core.stats_impl;

import com.mukul.aithreadpool.core.Stats;

public class TaskStats implements Stats {

    private long totalExecutionTime = 0;
    private int executionCount = 0;

    public synchronized void record(long executionTime) {
        totalExecutionTime += executionTime;
        executionCount++;
    }

    public synchronized long averageTime() {
        return executionCount == 0 ? 0 : totalExecutionTime / executionCount;
    }
}
