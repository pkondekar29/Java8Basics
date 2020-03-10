package com.java.v8.concurreny.completionStage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.java.v8.concurreny.bean.User;

public class ExceptionWithCompletableFuture {

    /**
     * Exceptions can occur in CompletableFuture API and are fully supported.
     * 
     * CompletableFuture = getUserIds() .thenApply(getUsersFromDB()) // Exception
     * occurs here
     * 
     * If Exception is occured, then the completableFuture is said to complete
     * exceptionally. This means that cf.join() throws CompletionException from
     * CompletableFuture and cf.get() throws ExecutionException from Future
     * 
     * If thenRun(), thenAccept() is called after exception from above, nothing will
     * be executed in there. And they will also complete exceptionally with the same
     * exception. So, the exception is forwarded to all its downstream completable
     * futures.
     * 
     */
    public static void main(String[] args) {

        Supplier<List<Long>> supplierIds = () -> Arrays.asList(1L, 2L, 3L);
        Function<List<Long>, List<User>> userFetch = ids -> {
            sleep(1L);
            //return ids.stream().map(User::new).collect(Collectors.toList());
            throw new RuntimeException();
        };
        CompletableFuture<List<User>> cf = 
            CompletableFuture.supplyAsync(supplierIds)
                            .thenApply(userFetch)
                            // This consumer accepts any exception if raised else the default result is returned
                            .exceptionally(exception -> {
                                System.out.println("Exception occured in exceptionally: " + exception.getMessage());
                                return Collections.emptyList();
                            });
        System.out.println("CfExceptionally -> Done:" + cf.isDone() + " exception:" + cf.isCompletedExceptionally());

        // Both exception handlers have async versions to specify the thread on which the exception should be handled
        CompletableFuture<List<User>> cfException = 
            CompletableFuture.supplyAsync(supplierIds)
                            .thenApply(userFetch)
                            // So, if thenApply is completed exceptionally then whenComplete will also complete exceptionally
                            // whenComplet accepts a BiConsumer<T, Throwable>
                            .whenComplete((users, exception) -> {
                                if(exception != null){      // If run with exception
                                    System.out.println("Exception occured in whenComplete: " + exception.getMessage());
                                } else {
                                    users.forEach(System.out::println);
                                }
                            });
        //cfException.join();
        System.out.println("CfException -> Done:" + cfException.isDone() + " exception:" + cfException.isCompletedExceptionally());

        CompletableFuture<List<User>> cfHandle = 
            CompletableFuture.supplyAsync(supplierIds)
                            .thenApply(userFetch)
                            // Here the exception can be swallowed by handle and accepts a BiFunction<T, Throwable, T>
                            .handle((list, exception) -> {
                                if(exception != null){      
                                    System.out.println("Exception occured in handle: " + exception.getMessage());
                                    return Collections.emptyList();
                                } else {
                                    return list;
                                }
                            });
        System.out.println("CfHandle -> Done:" + cfException.isDone() + " exception:" + cfException.isCompletedExceptionally());
    }

    private static void sleep(long l){
        try {
            Thread.sleep(l);
        } catch (InterruptedException e) {
        }
    }
}