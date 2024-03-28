package org.example;

import java.time.LocalTime;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLockExample {
    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);

    public void write() {
        lock.writeLock().lock();
        var threadName = Thread.currentThread().getName();
        try {
            System.out.printf("WRITE: %s: Locked: %s%n", threadName, LocalTime.now());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.printf("WRITE: %s: Unlocked: %s%n", threadName, LocalTime.now());
            lock.writeLock().unlock();
        }
    }

    public void read() {
        lock.readLock().lock();
        var threadName = Thread.currentThread().getName();
        try {
            System.out.printf("READ %s: Locked: %s%n", threadName, LocalTime.now());
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.printf("READ %s: Unlocked: %s%n", threadName, LocalTime.now());
            lock.readLock().unlock();
        }
    }

    static Thread runInThread(Runnable r) {
        var th = new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                r.run();
            }
        });
        th.start();
        return th;
    }

    public static void main(String[] args) throws InterruptedException {
        RWLockExample m = new RWLockExample();

        var th1 = runInThread(m::read);
        var th2 = runInThread(m::read);
        var th3 = runInThread(m::write);

        th1.join();
        th2.join();
        th3.join();
    }
}
