package com.java.v8.collections.framework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Executor {

	public static void main(String[] args) {
		Collection<String> collection = new ArrayList<>();
		collection.add("adew");
		collection.add("dawerh");
		Comparator<String> comparator = new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return Integer.compare(o1.length(), o2.length());
			}
		};
		// Arrays.sort(collection, comparator);
		/*
		 * We are passing some interface and executed it. Basically we are passing code as parameter.
		 * And it is executed afterwards at some point of time.
		 * 
		 * Lambda expression is a way to pass code as
		 *
		 * */
		Runnable r = () -> {		/* lambda expressions */
			int i = 0;
			while(i < 10) {
				System.out.println(i);
				i++;
			}
		};
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		executorService.execute(r);
		
		/*
		 * Alternative to writing lambda expressions
		 * */
		Runnable r2 = new Runnable() {
			
			@Override
			public void run() {
				int i = 0;
				while(i < 10) {
					System.out.println(i);
					i++;
				}
			}
		};
		executorService.execute(r2);
	}
}
