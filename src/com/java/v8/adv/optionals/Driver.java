package com.java.v8.adv.optionals;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Driver {

	public static void main(String[] args) {
		Optional<String> empty = Optional.empty();		// Empty optional
		Optional<String> notEmpty = Optional.of("s");		// optional on the String. This throws NullPointer exception if null is passed to the of method
		Optional<String> couldBeEmpty = Optional.ofNullable("s");	// null can be passed
		
		/* Below are the methods which can be used on Optionals and they look like map,filter,reduce APIs of Streams */
		// So, optional can be visualised as a special stream with only 0 or 1 element
//		notEmpty.map(<Function>)		
//		notEmpty.filter(<Predicate>)
//		notEmpty.ifPresent(<Consumer>)
//		notEmpty.flatMap(Function<T, Optional<U>)
		/**
		 *  Optionals - No nulls, no exceptions, parallel computations -- It can be very efficient
		 *  
		 *  When we visualize Optional as a stream with 0 or 1 element, we can use it very efficiently without any error with the stream API 
		 * */
		
		
		Function<Double, Stream<Double>> invSqrt = 
				d -> NewMath.sqrt(d)			// Returns an Optional<Double> This will return optional of sqrt of double is present, else it will return empty optional.
					.flatMap(NewMath::inverse)	// Returns an Options<Double>  This will flatten the optional
					.map(Stream::of)			// Optional<Stream<Double>>		// This map is called on the Optional class and not stream. Here we are generating an optional of  Stream of the double
					.orElseGet(Stream::empty);	// Stream<Double>	// This will return Stream of double or an empty stream if it is not present
				
		List<Double> list = 
				ThreadLocalRandom.current()
					.doubles(100).boxed()			// Doubles returns DoubleStream. then box it
					.collect(Collectors.toList());
				
		List<Double> invSqrtOfDoubles = 
				list.stream().parallel()		// This happens in parallel, and can be used since 
					.flatMap(invSqrt)			// With the use of optionals, we can create streams with no nulls, no exceptions and also use the parallellism of streams
					.collect(Collectors.toList());
		
		invSqrtOfDoubles.forEach(System.out::println);
	}
	
	private static class NewMath{
		
		public static Optional<Double> sqrt(Double d) {
			return d > 0d ? Optional.of(Math.sqrt(d)) : Optional.empty();
		}
		
		public static Optional<Double> inverse(Double d) {
			return d != 0d ? Optional.of(1d/d) : Optional.empty();
		}
	}

}
