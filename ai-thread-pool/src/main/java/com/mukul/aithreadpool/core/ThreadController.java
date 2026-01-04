package com.mukul.aithreadpool.core;

@FunctionalInterface
public interface ThreadController {
    int decideThreadCount(
            int currentThread,
            int queueSize,
            long avgExecutionTime
    );
}
