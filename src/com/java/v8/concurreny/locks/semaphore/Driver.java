package com.java.v8.concurreny.locks.semaphore;

import java.util.concurrent.Semaphore;

public class Driver {
    
    // Allows multiple number of permits which can execute guarded block of code.
    // We can even pass the fair boolean flag to make it fair
    static Semaphore semaphore = new Semaphore(5);

    public static void main(String[] args) {
        
        try {
            // Guard the block of code to be executed
            semaphore.acquire();
            // Cannot be interrupted
            semaphore.acquireUninterruptibly();
            // This will acquire 2 permits
            semaphore.acquire(2);

            // This is non blocking and can be used to perform some other function
            /**
             * <b>We can even get the collection of waiting threads on this semaphore</b>
             * 
             *  */ 
            if(semaphore.tryAcquire()) {

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
            semaphore.release(2);
        }

    }

}