package org.example;

import java.util.concurrent.locks.StampedLock;

public class StampedLockExample {
    private final StampedLock lock = new StampedLock();

    void read() {
        long stamp = lock.tryOptimisticRead();

        // Reading logic here

        if(!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                // Read again since data was changed
            } finally {
                lock.unlock(stamp);
            }
        }
    }

    void write() {
        long stamp = lock.writeLock();
        try {
            // Writing logic here
        } finally {
            lock.unlockWrite(stamp); // Always release lock!
        }
    }
}
