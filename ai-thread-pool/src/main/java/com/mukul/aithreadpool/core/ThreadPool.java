package com.mukul.aithreadpool.core;

public interface ThreadPool {
    void submit(Runnable task) throws InterruptedException;
    void shutdown();
}
