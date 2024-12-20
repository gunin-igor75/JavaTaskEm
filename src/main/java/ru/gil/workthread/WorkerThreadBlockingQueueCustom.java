package ru.gil.workthread;

import java.util.ArrayList;
public class WorkerThreadBlockingQueueCustom {
    private final BlockingQueue queue = new BlockingQueue();
    private final Thread worker = new Thread(() -> {
        while (!Thread.currentThread().isInterrupted()) {
            Runnable task;
            try {
                task = queue.get();
                task.run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    });

    public void startWorker() {
        worker.start();
    }

    static class BlockingQueue {
        ArrayList<Runnable> tasks = new ArrayList<>();

        public synchronized Runnable get() throws InterruptedException {
            while (tasks.isEmpty()) {
                wait();
            }
            Runnable task = tasks.get(0);
            tasks.remove(task);
            return task;
        }

        public synchronized void put(Runnable task) {
            tasks.add(task);
            notify();
        }
    }
}
