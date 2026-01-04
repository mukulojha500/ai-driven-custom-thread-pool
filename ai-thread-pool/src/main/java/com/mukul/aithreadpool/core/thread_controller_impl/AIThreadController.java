package com.mukul.aithreadpool.core.thread_controller_impl;

import com.mukul.aithreadpool.core.ThreadController;

public class AIThreadController implements ThreadController {

    @Override
    public int decideThreadCount(int currentThread, int queueSize, long avgExecutionTime){
        if (queueSize > currentThread * 2){
            return currentThread + 1;
        }

        if (queueSize == 0 && avgExecutionTime < 50){
            return Math.max(1, currentThread - 1);
        }

        return currentThread;
    }
}
