package org.example;

import java.net.http.HttpClient;
import java.util.*;

public class ConcurrencyExample2 {
    public static class Counter {
        private final Object monitor = new Object();
        private Set<String> threads = new HashSet<>();
        public int count = 0;

        public void increment(String arg) {
            synchronized (monitor) {
                // No Race Condition
                threads.add(Thread.currentThread().getName());
                System.out.println(threads);
                count++;
                threads.remove(Thread.currentThread().getName());
            }

            synchronized (arg) { // возможно, но редко оправдано и может быть опасно
                // No Race Condition
                threads.add(Thread.currentThread().getName());
                System.out.println(threads);
                count++;
                threads.remove(Thread.currentThread().getName());
            }

            synchronized (this) { // возможно, но редко оправдано  и может быть опасно
                // No Race Condition
                threads.add(Thread.currentThread().getName());
                System.out.println(threads);
                count++;
                threads.remove(Thread.currentThread().getName());
            }
            // Race Condition again
        }

        public synchronized void process(String json) {
            count++;

            if(json.contains("ERROR")) {
                return;
            }

            count++;

            // parsing json
//            var objectMapper = new ObjectMapper();
//            var data = objectMapper.readValue(json, UserData.class);
//
//            count++;
//
//            // Make POST request
//            var client = new CustomHttpClient();
//            client.postRequest("https://localhost:8080", data);

            count++;
        }
    }

    public static void incrementCounterBy1000(Counter counter) {
        for (int i = 0; i < 1000; i++) {
            counter.increment("");
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

        System.out.println("Expected: 2000");
        System.out.println("Actual: " + counter.count);
    }
}
