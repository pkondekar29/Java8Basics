package com.java.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class MyThread implements Callable<Integer>{
	int num;
	
	public MyThread(int i) {
		this.num = i;
	}
	
	@Override
	public Integer call() {
		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		return num;
	}
}

public class SampleRunnable {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		List<Future<Integer>> list = new ArrayList<>();
		for(int i = 0; i < 100; i++) {
			list.add(executorService.submit(new MyThread(i)));
		}
		executorService.shutdown();
		executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
		for (final Future<Integer> future : list) {
			System.out.println(future.get());
		}
	}
	
}
