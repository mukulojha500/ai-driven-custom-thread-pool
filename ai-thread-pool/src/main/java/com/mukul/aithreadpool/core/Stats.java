package com.mukul.aithreadpool.core;

public interface Stats {
    void record(long executionTime);

    long averageTime();
}
