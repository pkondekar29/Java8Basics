package com.java.v8.adv.parallel.streams;

import java.util.stream.Stream;

public class Driver {

	public static void main(String[] args) {
		/**
		 * When stream source is modified, and we are using parallel streams. There will be issues
		 */
		/**
		 * Stateful vs Stateless stream
		 * 
		 * Stateless - To compute something, we dont require anymore information than the lambda input, 
		 * i.e, the operation only works on one element of the stream on which it is working on
		 * 
		 * skip/limit operations will affect the parallel stream. And would not bring any efficiency
		 * 
		 * 
		 * Operations on stream api when executed with parallel streams 
		 * should use only thread-safe implementations of collections,
		 * else there will be chance of race conditions.
		 * Collectors.toList() will handle parallel and synchronization out of the box.
		 */
		
		/**
		 * The parallel stream api is built on top of fork/join pool of threads. And it by default uses all the CPUs for executions.
		 * So, sometimes we may not need these behaviuor.
		 * 
		 * So, in JDK8, new Common fork/join pool is created and it is present always for the stream API.
		 * We can set the property of number of threads present in a pool by some property.
		 * 
		 */
		
		// To limit the number of threads in Fork/join pool we need to add a property to set the number of threads
		System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "2");
		
		Stream.iterate("+", s -> s + "+")
			.parallel()		// This uses the threads in Common fork/join pool. 
							// But the number of threads is not limited for the operation.
			.limit(7)
			.peek(s -> System.out.println(s + " processed by " + Thread.currentThread().getName()))
			.forEach(System.out::println);
		
	}
	
}
