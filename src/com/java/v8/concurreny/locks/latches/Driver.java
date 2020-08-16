package com.java.v8.concurreny.locks.latches;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Driver {

    /**
     * Use case - We have a service to start which needs other services to starting
     * the main service. For example - ApplicationService needs - AuthService,
     * DataService
     * 
     * Here we can use a latch for starting the ApplicationService
     *** 
     * The main difference b/w latch and barrier is that latches are not reset once
     * they are opened.
     * 
     * 
     */

    static CountDownLatch latch = new CountDownLatch(4);

    public static void main(String[] args) {
        /// This will be called to count down on the latc
        latch.countDown();

        // Here we are going to wait till the time the latch is not opened by everyone
        try {
            latch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}