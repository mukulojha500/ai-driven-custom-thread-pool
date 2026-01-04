package com.mukul.aithreadpool.core.task_queue_impl;

import com.mukul.aithreadpool.core.TaskExecutor;
import com.mukul.aithreadpool.core.TaskQueue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AIPriorityTaskQueue implements TaskQueue {

    private final List<TaskExecutor> queue = new ArrayList<>();
    private final int capacity;

    public AIPriorityTaskQueue(int capacity){
        this.capacity = capacity;
    }

    @Override
    public synchronized void put(TaskExecutor task) throws InterruptedException{
        while (queue.size() == capacity){
            wait();
        }

        queue.add(task);
        queue.sort(Comparator.comparingDouble(TaskExecutor::score));

        notifyAll();
    }

    @Override
    public synchronized TaskExecutor take() throws InterruptedException {
        while (queue.isEmpty()){
            wait();
        }

        TaskExecutor task = queue.remove(0);

        notifyAll();

        return task;
    }

    public synchronized int size(){
        return queue.size();
    }
}
