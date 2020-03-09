package com.java.v8.concurreny.completionStage;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.java.v8.basics.beans.ExperimentalObject;

public class ChainTasksDriver {

    /**
     *  CompletableFuture supported for different type of tasks - 
     *      1. Runnable - run() - thenRun() 
     *      2. Consumer - accept() - thenAccept()
     *      3. Function - apply() - thenApply()
     * 
     * 
     * Note: The tasks are moved from one task to another. But this can be costly.
     * 
     * @param args
     */
    public static void main(String[] args) {

        CompletableFuture<List<ExperimentalObject>> completableFuture = CompletableFuture
                .supplyAsync(() -> IntStream.range(1, 10))          // This is a model for the input
                // Takes a function 
                .thenApply(streamOfInt -> getObjects(streamOfInt));         // So the result of supplyAsync has been passed to this method where the function is applied
        // This runnable is called after the completableFuture is complete. It takes a Runnable
        completableFuture.thenRun(() -> System.out.println("List of object received"));

        // Also, we can use invoke a Callable with thenAccept()
        completableFuture.thenAccept((listOfObjects) -> listOfObjects.forEach(System.out::println));

        completableFuture.join();

        // Single task chaining
        CompletableFuture.runAsync(() -> System.out.println("What to do?"))         // Runnable
                        .thenRun(() -> System.out.println("Done"))
        // Here we can pass a Consumer. However, there is no value which will not be passed from the above chains. So, null value will be passed.
                        .thenAccept(value -> System.out.println(value));    

        /**
         * CompletableFuture Composition
         * 
         */
        Supplier<List<Long>> userIdsSupplier = () -> remoteService();
        Function<List<Long>, List<ExperimentalObject>> objectsFromIds = (ids) -> readObjects(ids);
        // Here we are first getting the user Ids 
        CompletableFuture.supplyAsync(userIdsSupplier)
                        .thenApply(objectsFromIds);
        
        // thenCompose() method on CompletableFuture works as flatMap methods of Stream, Optionals

        /**
         * Composition APIs 
         * 1. allOf
         * 2. anyOf
         */

         /**
         * Where are the tasks run (on which thread)??
         * Rules - 
         *  1. By default, asynchronous tasks are alwas conducted in Common Fork/Join pool 
         *  2. A task triggered by another thread is executed in the same thread as the triggering thread.
         * 
         * We can change the above behaviour 
         *  1. Allow task to be executed in another thread of the same pool of threads as the triggering task
         *  2. have the task to be executed in another pool of threads
         * 
        */
        CompletableFuture<List<ExperimentalObject>> completableFuture2 = CompletableFuture
                // Executed in common fork join pool
                .supplyAsync(() -> IntStream.range(1, 10))         
                // Will be executed on the same thread in the fork join pool
                .thenApply(streamOfInt -> getObjects(streamOfInt)); 

        ExecutorService service2 = Executors.newSingleThreadExecutor();
        CompletableFuture<List<ExperimentalObject>> completableFuture3 = CompletableFuture
                // Executed in executor service thread pool    
                .supplyAsync(() -> IntStream.range(1, 10), service2)         
                //  Will be executed on the same thread from the passed executor service
                .thenApply(streamOfInt -> getObjects(streamOfInt));                                     

        CompletableFuture<List<ExperimentalObject>> completableFuture4 = CompletableFuture
                // Executed in common fork join pool
                .supplyAsync(() -> IntStream.range(1, 10))              
                // Here as we are calling thenApplyAsync -> the task will be executed in the same Fork/Join pool but on a different thread as the callee
                .thenApplyAsync(streamOfInt -> getObjects(streamOfInt));

        CompletableFuture<List<ExperimentalObject>> completableFuture5 = CompletableFuture
                // Executed in common fork join pool
                .supplyAsync(() -> IntStream.range(1, 10))              
                // Here as we are calling thenApplyAsync -> the task will be executed in the same Fork/Join pool but on a different thread as the callee
                // Also, we are passing a executor service, so the task will be executed on some thread in that pool
                .thenApplyAsync(streamOfInt -> getObjects(streamOfInt), service2);
        // This will be executed in the same thread where supplyAsync is run
        completableFuture5.thenRun(() -> System.out.println("Done"));       
        // This will be executed in the same FJ pool but on a different thread. 
        // So, the thenRun() and thenAcceptAsync() can be triggered at the same time in the same FJ pool but on different threads
        completableFuture5.thenAcceptAsync(list -> list.forEach(System.out::println));

        service2.shutdown();
    }

    private static List<ExperimentalObject> readObjects(List<Long> ids) {
        return ids.stream().map(i -> new ExperimentalObject(i + "", "dummy2", 32)).collect(Collectors.toList());
    }

    private static List<Long> remoteService() {
        return IntStream.range(0, 10).boxed().map(i -> Long.parseLong(i + "")).collect(Collectors.toList());
    }

    private static List<ExperimentalObject> getObjects(IntStream streamOfInt) {
        return streamOfInt.boxed().map(i -> new ExperimentalObject(i + "", "dummy", new Random().nextInt(100)))
            .collect(Collectors.toList());
    }

}