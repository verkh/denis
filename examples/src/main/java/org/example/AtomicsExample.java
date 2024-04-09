package org.example;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicsExample {
    public static class Counter {
        public AtomicInteger counter = new AtomicInteger(0); // CAS: compare and swap
        public AtomicReference<String> ref = new AtomicReference<>();

        public void increment() {
            counter.incrementAndGet();
            ref.compareAndSet("", "New Value");
        }
    }

    public static void incrementCounterBy1000(Counter counter) {
        for (int i = 0; i < 1000; i++) {
            for(int j = 0; j < 10; j++) {
                counter.increment();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final Counter counter = new Counter();

        var th1 = new Thread(() -> incrementCounterBy1000(counter));
        var th2 = new Thread(() -> incrementCounterBy1000(counter));

        th1.start();
        th2.start();

        th1.join();
        th2.join();

        System.out.println("Expected: 20000");
        System.out.println("Actual: " + counter.counter.get());
    }
}
