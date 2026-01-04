package com.mukul.aithreadpool.core.threadpool_impl;

import com.mukul.aithreadpool.core.TaskQueue;
import com.mukul.aithreadpool.core.ThreadController;
import com.mukul.aithreadpool.core.ThreadPool;
import com.mukul.aithreadpool.core.WorkerThread;
import com.mukul.aithreadpool.core.Stats;
import com.mukul.aithreadpool.core.stats_impl.TaskStats;
import com.mukul.aithreadpool.core.task_executor_impl.AITaskExecutor;
import com.mukul.aithreadpool.core.task_queue_impl.AIPriorityTaskQueue;
import com.mukul.aithreadpool.core.thread_controller_impl.AIThreadController;
import com.mukul.aithreadpool.core.worker_thread_impl.WorkerThreadImpl;

import java.util.ArrayList;
import java.util.List;

public class AIThreadPool implements ThreadPool {

    private final TaskQueue queue;
    private final List<WorkerThread> workers = new ArrayList<>();
    private final ThreadController controller = new AIThreadController();
    private final Stats globalStats = new TaskStats();

    private volatile boolean shutdown = false;

    public AIThreadPool(int initialThreads, int queueCapacity){
        queue = new AIPriorityTaskQueue(queueCapacity);
        for(int i=0; i<initialThreads; i++){
            addWorker();
        }
    }

    private synchronized void addWorker() {
        WorkerThread worker =
                new WorkerThreadImpl(queue, "worker-" + workers.size());
        workers.add(worker);
        worker.runThread();
    }

    private synchronized void removeWorker() {
        if (!workers.isEmpty()) {
            WorkerThread worker = workers.remove(workers.size() - 1);
            worker.shutdown();
        }
    }

    @Override
    public void submit(Runnable task) throws InterruptedException {
        if (shutdown) throw new IllegalStateException("Pool shutdown");
        queue.put(new AITaskExecutor(task, globalStats));
        adjustPoolSize();
    }

    private synchronized void adjustPoolSize() {
        int desired =
                controller.decideThreadCount(
                        workers.size(),
                        queue.size(),
                        globalStats.averageTime()
                );

        while (workers.size() < desired) addWorker();
        while (workers.size() > desired) removeWorker();
    }

    @Override
    public synchronized void shutdown() {
        shutdown = true;
        for (WorkerThread worker : workers) {
            worker.shutdown();
        }
    }
}
