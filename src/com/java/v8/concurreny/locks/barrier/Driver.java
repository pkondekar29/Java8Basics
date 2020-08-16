package com.java.v8.concurreny.locks.barrier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Driver {
    
    /**
     * 
            A barrier object is used to block a certain code until a number of tasks have been completed
            So, if we divide the computation in 4 thread, we should compute the final result only after the 4 computations are done.
            Here we can add a barrier stating that we should wait for 4 tasks to complete
            So, this Cylic barrier will wait till the time release is called 4 times on this object from 4 different threads
     * 
     *      Once the barrer is opened, it will be reset to initial state
     * 
     *      BrokenBarrierException will be thrown on barrier if any thread waiting on the barrier is interrupted
     *      Manual reset of the barrier will also throw the exception
     *  */ 
    static CyclicBarrier barrier = new CyclicBarrier(4, () -> System.out.println("Barrier opened"));
    
    public static void main(String[] args) {
        
        ExecutorService service = Executors.newFixedThreadPool(4);
        try {
            List<Future<String>> futures = new ArrayList<>();
            for(int i = 0; i < 4; i++) {
                futures.add(service.submit(new Friend(barrier)));
            }
            
            futures.forEach(f -> {
                try {
                    f.get();
                } catch(InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
        } finally {
            service.shutdown();
        }

    }

    public static class Friend implements Callable<String> {

        private CyclicBarrier barrier = new CyclicBarrier(4);

        public Friend(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public String call() throws Exception {
            
            Random rand = new Random();
            // This means that the friend arrived at some place and it took some time to arrive
            Thread.sleep(rand.nextInt(5000));
            System.out.println("I just arrived, waiting for others");

            // This await method can have the timeunit so that it is not blocked indefinatelly
            barrier.await();

            System.out.println("Lets go to cinema");
            return "ok";
        }
        
    }
}