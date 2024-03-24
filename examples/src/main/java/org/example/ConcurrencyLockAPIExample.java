package org.example;

import java.net.http.HttpClient;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class ConcurrencyLockAPIExample {
    public static class Counter {
        private final Object monitor = new Object();
        private Set<String> threads = new HashSet<>();

        private final ReentrantLock lock = new ReentrantLock();
        private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        private final StampedLock stampedLock = new StampedLock();

        public int count = 0;


        // exmaple 1
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

        // reentrancy:
        public void reentrancy() {
            synchronized (monitor) {
                synchronized (monitor) {
                    synchronized (monitor) {
                        synchronized (monitor) {
                            reentrancy();
                        }
                    }
                }
            }
        }

        // example 2
        public void lock(String json) {
            lock.lock();
            count++;
            lock.unlock();

            // parsing json
//            var objectMapper = new ObjectMapper();
//            var data = objectMapper.readValue(json, UserData.class);
//
//            // Make POST request
//            var client = new CustomHttpClient();
//            client.postRequest("https://localhost:8080", data);

            lock.lock();
            count--;
            lock.unlock();
        }

        public void dealyedUnlock(String json) {
            lock.lock();
            count++;
        }

        public void unlock() {
            lock.unlock();
        }

        public void reentrancy2() {
            lock.lock();
            lock.lock();
            lock.lock();
            lock.lock();

            reentrancy2();
        }

        public void tryLockExample() throws InterruptedException {
            if(!lock.tryLock()) {
                return;
            }

            // or

            if(!lock.tryLock(3, TimeUnit.SECONDS)) {
                return;
            }

            lock.unlock();
        }

        public void interraptubleExample() {
            try {
                lock.tryLock(10, TimeUnit.DAYS);
                Thread.sleep(1000000000);
                lock.unlock();
            } catch (InterruptedException e) {
                System.out.println("Thread %s interrupted".formatted(Thread.currentThread().getName()));
                return;
            }
        }

        // fairness
        public void fairness() {
            var lock = new ReentrantLock(true);
        }

        public void rwExample() {
            rwLock.readLock().lock();
            rwLock.writeLock().lock();

            rwLock.readLock().unlock();
            rwLock.writeLock().unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        var c = new Counter();

        var thread1 = new Thread(() -> {
            c.interraptubleExample();
        });
        var thread2 = new Thread(() -> {
            c.interraptubleExample();
        });

        thread1.start();
        thread2.start();

        thread2.interrupt();

        thread1.join();
        thread2.join();
    }
}

/**
 * BankAccountRepository (HashMap<Long, BackAccount>)
 * BankAccount (Long id, Long balance)
 * MoneyTransferService::transfer(Long idFrom, Long idTo, Long amount) - thread safe
 * Tests
 *
 * Написать потокобезопасную логику перевода денег с одного аккаунта на другой.
 * Продумать дизайн классов (могут потребоваться дополнительные классы, не указанные в задании)
 * Помни о SOLID и валидацию
 */
