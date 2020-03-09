package com.java.v8.concurreny.completionStage;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public class Driver {

    /**
     * The CompletableFuture implements interfaces - 1. Future -> 2. CompletionStage
     * -> This is where the code for chaining tasks are present.
     * 
     * The CompletableFuture has a state - 1. running --- THe task is running 2.
     * completed normally --- The task is completed normally without exceptions 3.
     * completed exceptionally ---- The task is completed but with some exception
     * 
     * API in CompletableFuture From Future 1. get() - blocking 2. get(time,
     * TimeUnit) 3. cancel() 4. isDone() 5. isCancelled()
     * 
     * New APIs 1. join() -- same as get but doesnt throw exception 2. getNow(T
     * valueIfAbsent)
     * 
     * 3. complete(value) -- Checks if the task is done or not. If it is not done,
     * then it completes the task by setting the value passed as parameter 4.
     * obstrudeValue(value) -- If it donw, then forces the returned value to value.
     * If not, then it completes and sets the returned value to value. --- This
     * value should be used in error recovery operation and not in normal
     * operations. 5. completeExceptionally(throwable) -- forces the completion if
     * the task is not done. Forces the completion even if the task is done. --
     * get() and join() will throw exxceptions. It is not the one passed in
     * paramter.
     * 
     * 
     * @param args
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Ways to start a Task -> Runnable/Callable

        /**
         * 1. By creating a Thread 2. Executor pattern Returns Future object for the
         * task
         * 
         */
        Runnable task = () -> System.out.println("Hello World");
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(task);

        // Old way of calling callables
        Callable<String> cTask = () -> "Hello World";
        Future<String> future = service.submit(cTask);
        System.out.println(future.get());

        /**
         * Moving to CompletableFuture
         * 
         * 
         * Instead of Callable, use a Supplier.
         */
        Supplier<String> callableSupplier = () -> "Hello World from supplier. Running on: "
                + Thread.currentThread().getName();
        // So, the callable can be changed to Supplier. Just that it should not throw
        // exception since CompletableFuture doesn't throw exceptions
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(callableSupplier); // Note: Here the
                                                                                                       // threads runs
                                                                                                       // in Common Fork
                                                                                                       // join pool
        System.out.println(completableFuture.join());

        /**
         * All the async calls are run in Common fork/join pool. This is the same pool
         * for parallel stream execution
         */
        // We can even pass the executor service to specify on which thread pool should
        // the task be executed.
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(callableSupplier, service); // Note:
                                                                                                                 // This
                                                                                                                 // is
                                                                                                                 // executed
                                                                                                                 // on
                                                                                                                 // the
                                                                                                                 // Executor
                                                                                                                 // service
                                                                                                                 // thread
                                                                                                                 // pool
        System.out.println(completableFuture1.join());
        service.shutdown();
        CompletableFuture<Void> runnableCompletableFuture = CompletableFuture.runAsync(task); // We also have an API to
                                                                                              // run Runnable task
                                                                                              // instead of Supplier
        // If this message is not called, then the JVM is not stopped since there are
        // threads which are running in our ExecutorService
        Supplier<String> string = () -> {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
            }
            return "Hello World";
        };
        CompletableFuture<String> cFuture = CompletableFuture.supplyAsync(string);
        cFuture.complete("Too long");           // This will complete the task if not completed.

        String str = cFuture.join();
        System.out.print(str);
       
    }

}