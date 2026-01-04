package com.mukul.aithreadpool.core.worker_thread_impl;

import com.mukul.aithreadpool.core.TaskExecutor;
import com.mukul.aithreadpool.core.TaskQueue;
import com.mukul.aithreadpool.core.WorkerThread;

public class WorkerThreadImpl extends Thread implements WorkerThread {

    private final TaskQueue queue;
    private volatile boolean running = true;

    public WorkerThreadImpl(TaskQueue queue, String name){
        super(name);
        this.queue = queue;
    }

    @Override
    public void run(){
        while (running){
            try{
                TaskExecutor task = queue.take();
                task.execute();
            } catch (InterruptedException e) {
                running = false;
                interrupt();
            }
        }
    }

    @Override
    public void runThread(){
        this.start();
    }

    @Override
    public void shutdown(){
        running = false;
        interrupt();
    }
}
