package com.java.v8.basics.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Definitions of streams -
 * 
 * A stream does not hold data
 * 	It merely process the data from some source. The source can be anything
 * A stream does not modify the data it processes
 * 	Bcz we want to process data in parallel and it should not lead to any synchronization or visibility issues
 * The source may be unbounded 
 * 	Which means that it is not finite
 * 	i.e, the size of the source is not known at build time.
 * 
 * Stream interface -
 * Stream<T> extends BaseStream<T, Stream<T>>
 * 
 * 
 */
public class Driver {

	public static void main(String[] args) {
		List<Integer> ints = Arrays.asList(0, 1, 2, 3, 4 ,5);
		
		// Patterns to define stream 
		
		/** 
		 * Here the data is not present in the stream but is still in the collection. 
		 * It will be transferred to stream only while processing. 
		 * */
		Stream<Integer> stream = ints.stream();
		
		//empty stream
		Stream<Integer> emptyStream = Stream.empty();
		// stream of several elements
		Stream<Integer> stream1 = Stream.of(0, 1, 2);	
		// Singleton stream
		Stream<Integer> stream2 = Stream.of(4);	
		
		// a constant stream
		Stream.generate(() -> 1);	// Stream with supplier
		
		// takes in Unary operator
		Stream.iterate(1, i -> i + 1);
		
		// returns random int stream
		ThreadLocalRandom.current().ints();
		
		CharSequence book = new String("asdoernc");
		Stream<String> words = Pattern.compile("[+//java]")
										.splitAsStream(book);
		
		Stream.Builder<String> builder = Stream.builder();
		
		builder.add("adsad").add("adaf");
		builder.accept("dasef");
		
		Stream<String> streamBuilded = builder.build();	// Once the stream is builded we cannot add anything to the stream. And exception will be thrown
		streamBuilded.forEach(System.out::println);
		
		// forEach can be used on the stream to consume the stream
		stream.forEach(System.out::println);
		
		// So the map/filter/reduce pattern can be used as-
		ints.stream()					// Create a Stream<Integer>
			.map(i -> (i > 2 ? i : 2))	// maps it to another Stream<Integer>
			.peek(System.out::println)		/** peek method is used for debugging method. It is an intermediate operation. This is an intermediate operation */
			.filter(i -> i > 2)			// and finally filter it in another Stream<Integer>
			.forEach(System.err::println);
		// Here we have created 3 new objects of the stream interface but it is does not hold any data. Hence, it is very light weight.
		
		/** The forEach call is the terminal operation on the stream. It triggers the processing of a stream */
		
		/**
		 * How to recognise a terminal call?
		 * 	1. JavaDoc
		 * 	2. A call that returns a stream, then its an intermediate call.
		 * 
		 */
		Stream.of(ints)
				.skip(3)	// skips first 3 elements
				.limit(3)	// limits the stream size to next 3 elements
				.forEach(System.out::println);
		
		/** Simple reductions */
		/** Match reductions */
		/** These are short-circuited matchers */  
		//stream.allMatch(predicate)
		//stream.anyMatch(predicate)
		//stream.noneMatch(predicate)
		
		/** Find reductions */
		// findFirst(Predicate<>), findAny(Predicate<>)
		
		//It returns an optional which can be empty
		Optional<Integer> opt = ints.stream().findAny();
		System.out.println(opt.get());
		
		/** Reduce reductions */
		//stream.reduce(accumulator)
	}	
	
	
}
