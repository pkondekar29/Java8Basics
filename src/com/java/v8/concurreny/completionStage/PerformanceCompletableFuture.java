package com.java.v8.concurreny.completionStage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import com.java.v8.concurreny.bean.User;

// JCStress - tool for concurrent exceution performace improvement
public class PerformanceCompletableFuture {

    public static void main(String[] args) throws IOException {
        // URL url = new URL("http://www.google.com");
        // HttpsURLConnection httpUrl = (HttpsURLConnection) url.openConnection();
        // httpUrl.setRequestMethod("GET");
        // httpUrl.setDoInput(true);

        // httpUrl.connect();
        // String content = null;
        // try(InputStream io = httpUrl.getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(io))){
        //     content = reader.lines().collect(Collectors.joining("\n"));
        // }
        
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // Supplier for fetching ids
        Supplier<List<Long>> supplyIds = () -> {
            return Arrays.asList(1L, 2L, 3L);
        };
        // Functions to get Users from ids
        Function<List<Long>, List<User>> fetchUsers = ids -> {
            return ids.stream().map(User::new).collect(Collectors.toList());
        };
        // Consumer -> Logs te users
        Consumer<List<User>> displayer = users -> users.forEach(user -> System.out.println(user.toString() + ", running in: " + Thread.currentThread().getName()));

        /**
         * This start completable future is a dummy future for starting the chaining of tasks.
         * If chaining of tasks is costly, then we can use this kind of futures to start chaining of tasks.
         * 
         * Also, the chaining should be done in different threads if the chaining is a costly operation.
         */
        CompletableFuture<Void> start = new CompletableFuture<>();

        // Chaining of above tasks
        CompletableFuture<List<Long>> supply = start.thenApplyAsync(nil -> supplyIds.get(), executor); // Supplier
        CompletableFuture<List<User>> fetch = supply.thenApply(fetchUsers);         // Function
        CompletableFuture<Void> display = fetch.thenAccept(displayer);        // Consumer
        
        start.complete(null);
        executor.shutdown();        
        sleep(1000L);
    }

    private static void sleep(long l){
        try{
            Thread.sleep(l);
        }catch(InterruptedException e){

        }
    }

}