package org.example;

import java.util.concurrent.*;

public class ExecutorsExample {

    public static void sayHello() {
        System.out.println("Hello from " + Thread.currentThread().getName());
    }

    public static String getHello() throws InterruptedException {
        Thread.sleep(1000);
        return "Hello after waiting";
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
//        var executor = Executors.newSingleThreadExecutor();
//        var executor = Executors.newFixedThreadPool(3);
        var executor = Executors.newCachedThreadPool();
//        var executor = Executors.newScheduledThreadPool(3);

        executor.submit(ExecutorsExample::sayHello);
        executor.submit(ExecutorsExample::sayHello);
        executor.submit(ExecutorsExample::sayHello);

        executor.submit(ExecutorsExample::sayHello);

        Future<?> future = executor.submit(ExecutorsExample::sayHello);
        future.get(1, TimeUnit.SECONDS);

        Future<String> futureHello = executor.submit(ExecutorsExample::getHello);
//        futureHello.get(200, TimeUnit.MILLISECONDS); // меньше, чем нужно для вычисления
        System.out.println(futureHello.get());

        // for scheduledThreadPool
//        executor.scheduleAtFixedRate(ExecutorsExample::sayHello, 0, 1, TimeUnit.SECONDS);

        // comment for scheduledThreadPool
        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }
    }
}


// переключение контекстов на одном ядре
// поток 1 5мс
// запускается поток 2
// ОС ставит на паузу поток 1 и дает время на выполнения поток 2