package org.example;

public class ConcurrencyExample {
    public static class Counter {
        public int count = 0;

        public synchronized void increment() {
            System.out.println(Thread.currentThread().getName());
            count++;
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
        System.out.println("Actual: " + counter.count);
    }
}
// [1][1][1][1][1][1]

/**
 * Ресурс - ресурсом может быть что угодно, поле, объект и т.д.
 * Атомарность - неделимая операция.
 * Грязное чтение - ситуация, когда мы читаем данные, которые еще не были полностью изменены
 * Race Condition - гонка потоков, кто первый успел того и тапки
 * Синхронизация потоков - по сути сделать не атомарную операцию, атомарной
 * Thread Safety/Потокобезопасность - означает, что несколько потоков могут работать корректно одновременно
 */
/*
    - synchronized keyword - самая простая синхронизация потоков
 */