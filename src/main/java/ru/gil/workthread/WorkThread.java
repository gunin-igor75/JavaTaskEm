package ru.gil.workthread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class WorkThread implements Runnable {
    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private Thread worker;
    public void init(String nameThread) {
        worker = new Thread(this, nameThread);
        worker.start();
    }

    public void stop() {
        worker.interrupt();
    }

    public void addTask(Runnable task){
        try {
            queue.put(task);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Runnable take;
            try {
                take = queue.take();
                take.run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
