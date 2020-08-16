package com.java.v8.concurreny.locks.readwritelocks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.java.v8.concurreny.bean.User;

public class Driver {
    
    /**
     * Read write locks are used when we need exclusive locks and allows parallel reads
     * 
     */
    ReadWriteLock lock = new ReentrantReadWriteLock();
    // The following read/write locks form a pair of locks as they come from the same lock
    // And to have read/write guarding the read/write locks should be created from the same lock

    // The read lock can be held by as many threads
    Lock readLock = lock.readLock();
    // The write lock can be held by only one thread. 
    // Also, the read lock cannot be held when write lock is held by some thread
    Lock writeLock = lock.writeLock();

    Map<String, User> cache = new HashMap<>();

    public static void main(String[] args) {
        

    }

    User readCache(String key) {
        try {
            // Here the read is guarded by the read lock. And all the threads holding the read lock can ennter and access the cache
            readLock.lock();
            return cache.get(key);
        } finally{
            readLock.unlock();
        }
    }

    void modifyCache(String key, User user) {
        try {
            // Here the write lock guards the insert into the cache. By doing this, the reads are alse guarded and corrupted values wont be read from the cache.
            // This can also be achieved using ConcurrentHashMap
            writeLock.lock();
            cache.put(key, user);
        } finally {
            writeLock.unlock();
        }
    }

}