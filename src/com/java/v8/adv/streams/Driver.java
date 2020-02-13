package com.java.v8.adv.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.java.v8.basics.beans.ExperimentalObject;

public class Driver {

	public static void main(String[] args) throws Exception {
		
		/**
		 * Concatenating different streams
		 * 
		 */
		Stream<String> s1 = Stream.of("h", "e", "l", "o");
		Stream<String> s2 = Stream.of("w", "o", "r", "l", "d");
		
		Stream<String> s3 = Stream.concat(s1, s2);		// order is preserved in concat method. Hence it comes at a cost.
		s3.forEach(System.out::print);		/// Streams are auto closable
		
		Stream<String> s4 = Stream.of("h", "e", "l", "o");
		Stream<String> s5 = Stream.of("w", "o", "r", "l", "d");
		Stream<String> s6 = Stream.of(s4, s5)
			.flatMap(Function.identity());		
		s6.forEach(System.out::print);
		
		String line1 = "Hello world, this is palash";
		String line2 = "Hello world, this is pals";
		Stream<String> streamOfl1 = Stream.of(line1.split(" "));
		Stream<String> streamOfl2 = Stream.of(line2.split(" "));
		
		Function<String, Stream<String>> splitIntoWords = l -> Pattern.compile(" ").splitAsStream(l);		// The function takes a String and returns stream of words
		List<String> allWords = 
				Stream.of(streamOfl1, streamOfl2)		// Stream of stream of lines
					.flatMap(Function.identity())		// Stream of lines
					.flatMap(splitIntoWords)			// Stream of words
					.collect(Collectors.toList());					// If this terminal function is not present, then nothing will happen to the stream
		allWords.forEach(System.out::println);
		
		
		/**
		 * State of stream
		 * Spliterator interface describes how we want to access the stream. 
		 * The characteristics of stream tells about the state of the stream.
		 * Below are the states of a stream which are part of Spliterator interface 
		 * 	ORDERED	- Order matters
		 * 	DISTINCT - no duplicates
		 * 	SORTED	- Sorted(Sorted sets, etc)
		 * 	SIZE 	- Size of the collection is known
		 * 	NONNULL	- The stream does not have a nonnull values
		 * 	IMMUTABLE - Stream is not immutable
		 * 	CONCURRENT - Stream is build in a concurrent aware structures
		 * 	SUBSIZED		- Size is known
		 * 
		 * So, each operation on stream will change the above characteristics/state of the stream.
		 */
		/**
		 *	Note: The sorted on streams will only work if the stream is made of comparable objects
		 * (or) we pass a comparator and that will be used 
		 */
		// So, the flatMap is very powerful for merging more than one streams and 
		// once we get all the streams flattened, we can perform various operations using stream apis
		
		/**
		 * Special streams - 
		 * 	IntStream: average(), min(), max() terminal operation
		 * 	LongStream:
		 * 	DoubleStream: 
		 * 
		 * The boxed API in above special streams is present to get back Stream from special streams
		 */
		
		List<ExperimentalObject> objects = new ArrayList<>();
		OptionalDouble avg = objects.stream()
			.map(ExperimentalObject::getValue)
			.mapToInt(i -> i)
			.average();		// This is not present in the Stream API. We converted the stream of objects to IntStream to use some useful terminal operations on the stream
		System.out.print(avg.orElseThrow(Exception::new));
	}

}
