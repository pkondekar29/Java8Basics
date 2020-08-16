package com.java.v8.concurreny.locks;

import java.util.ArrayDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Driver {

    private static final Object key = new Object();
    public static void main(String[] args) {

        // This is the old way of achieving concurrency
        // Also one caviet over here is that if the thread inside the sync block is blocked 
        // then there is no way to remove the block than to restrart the JVM or interrupting that thread
        synchronized (key) {
            try {
                key.wait();
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                key.notifyAll();
            }
        }


        // By passing a boolean fair -> we control if the lock would be fair to thread waiting on the lock, i.e., 
        // All the threads waiting on the lock will be allowed in the same way as they came. The fair flag brings in more complexity though.
        Lock lock = new ReentrantLock();

        try{
            lock.lock();    // THis can not be interrupted
            lock.lockInterruptibly();   // This can be interrupted
            // do Something
            Thread.sleep(100L);
        } catch(InterruptedException e) {

        } finally {
            lock.unlock();
        }
        
    }


    /**
     * Lock for PC model
     * 
     */
    private ReentrantLock lock = new ReentrantLock();
    Condition isFull = lock.newCondition();
    Condition isEmpty = lock.newCondition();


    public class Producer {

        private ArrayDeque queue;

        Producer(ArrayDeque queue) {
            this.queue = queue;
        }

        void produce() {
            // There are may APIs on the Condition which can be used.
            // This Condition is part of the lock object
            // Also, one lock object can have multiple condition objects
            // A fair lock generates fair condition
            try {
                lock.lock();
                while(queue.size() == 10) {
                    // This await is interruptable. Hence this can be used.
                    /**
                     *  The await with timeout should be used to avoid deadlock scenarios
                     *  */ 
                    isFull.await();
                }
                queue.add(new Object());
                isEmpty.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }

    }

    public class Consumer {

        private ArrayDeque queue;

        Consumer(ArrayDeque queue) {
            this.queue = queue;
        }

        void consume() {
            try {
                lock.lock();
                while(queue.size() == 0) {
                    isEmpty.await();
                }
                queue.poll();
                isFull.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

    }

}