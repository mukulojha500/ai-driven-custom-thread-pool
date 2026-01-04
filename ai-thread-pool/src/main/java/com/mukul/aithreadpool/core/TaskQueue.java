package com.mukul.aithreadpool.core;

public interface TaskQueue {
    void put(TaskExecutor task) throws InterruptedException;
    TaskExecutor take() throws InterruptedException;
    int size();
}
