package com.java.v8.concurreny.completionStage;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.java.v8.concurreny.bean.Email;
import com.java.v8.concurreny.bean.User;

public class CompletableFuturesExamples {

    /**
     * CompletionFuture provides APIs for following -
     * 1. Chaining and compositions of tasks
     * 2. Patterns to trigger executions of one task with another task(thenRun(Runnable), thenApply(Function), thenAccept(Consumer), thenCompose(Function<T, CompletionStage<U>>))
     * 3. triggers with 2 tasks(runAfterBoth(Runnable), thenAcceptBoth(BiConsumer), thenComboneBoth(BiFunction),  -- These are run after completion of both tasks
     *                          runAfterEither(Runnable), acceptEiter(Consumer), applyToEither(Function))         -- These are run after either of task completion
     * 4. Gives the control which task should be run on which thread
     * 
     * @param args
     */
    public static void main(String[] args) {
        // Supplier for fetching ids
        Supplier<List<Long>> supplyIds = () -> {
            sleep(100L);        // Simulating DB call
            return Arrays.asList(1L, 2L, 3L);
        };
        // Functions to get Users from ids
        Function<List<Long>, List<User>> fetchUsers = ids -> {
            sleep(200L);        // Simulating DB call
            return ids.stream().map(User::new).collect(Collectors.toList());
        };
        // Consumer -> Logs te users
        Consumer<List<User>> displayer = users -> users.forEach(user -> System.out.println(user.toString() + ", running: " + Thread.currentThread().getName()));

        // Chaining of above tasks
        CompletableFuture<List<Long>> completableFuture = CompletableFuture.supplyAsync(supplyIds); // Supplier
        completableFuture.thenApply(fetchUsers)         // Function
                         .thenAccept(displayer);        // Consumer

        ExecutorService executor1 = Executors.newSingleThreadExecutor();
        ExecutorService executor2 = Executors.newSingleThreadExecutor();
        CompletableFuture<List<Long>> cf = CompletableFuture.supplyAsync(supplyIds);
        cf.thenApply(fetchUsers)
            .thenAcceptAsync(displayer, executor1);     // running in different thread pool
        
        // Functions to get Users from ids
        Function<List<Long>, CompletableFuture<List<User>>> fetchUsersCf = ids -> {
            sleep(200L);        // Simulating DB call
            // The function would be run on the executor passed while calling thenCompose()
            System.out.println("function is running in: " + Thread.currentThread().getName());
            Supplier<List<User>> userSupplier = 
                    () -> {
                        System.out.println("Running in: " + Thread.currentThread().getName());
                        return ids.stream().map(User::new).collect(Collectors.toList());
                    };
            // Wrapping the supplier in CompletableFuture. So, the result will be available when supply is called from the CompletableFuture
            return CompletableFuture.supplyAsync(userSupplier, executor2);  // Here the supplier is running in executor2.
        };

        CompletableFuture<List<Long>> cf2 = CompletableFuture.supplyAsync(supplyIds);
        cf2.thenCompose(fetchUsersCf)       // Here the CompletableFuture list of users is composed
            .thenAcceptAsync(displayer, executor1);
        
        // Function to fetch emails from ids
        Function<List<Long>, CompletableFuture<List<Email>>> fetchEmailsCf = ids -> {
            sleep(100L);        // Simulating DB call for getting emails
            // The function would be run on the executor passed while calling thenCompose()
            System.out.println("function is running in: " + Thread.currentThread().getName());
            Supplier<List<Email>> emailSupplier = 
                    () -> {
                        System.out.println("Running in: " + Thread.currentThread().getName());
                        return ids.stream().map(id -> new Email(id + "")).collect(Collectors.toList());
                    };
            // Wrapping the supplier in CompletableFuture. So, the result will be available when supply is called from the CompletableFuture
            return CompletableFuture.supplyAsync(emailSupplier, executor2);  // Here the supplier is running in executor2.
        };
        // The thenCompose() function 
        CompletableFuture<List<Long>> cf3 = CompletableFuture.supplyAsync(supplyIds);
        CompletableFuture<List<User>> cfUsers = cf3.thenCompose(fetchUsersCf);      // Both 
        CompletableFuture<List<Email>> cfEmails = cf3.thenCompose(fetchEmailsCf);
        
        cfUsers.thenAcceptBoth(cfEmails, (users, emails) -> System.out.println(users.toString() + " " + emails.toString()));
        sleep(3000L);
        executor1.shutdown();
        executor2.shutdown();
    }

    private static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

}