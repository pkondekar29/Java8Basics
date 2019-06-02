package com.java.v8.basics.lambda.functionalinterfaces;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.java.v8.basics.beans.ExperimentalObject;

/**
 * 
 * 4 categories of functional interfaces -
 * 
 * 1. Consumers
 * 		Takes in some object and does not return anything
 * 2. Supplier
 * 		Does not take any i/p and supplies something
 * 3. Functions
 * 		Takes an object of type T and returns object type of R
 * 4. Predicate 
 * 		takes an object of one type and returns a boolean. 
 * 		It is used in the map, filter step while using functional interface
 * 
 * @author I353403
 *
 */
public class DriverProgramJDKexamples {

	public static void main(String[] args) {
		// Consumer example
		Consumer<String> c = s -> System.err.println(s);		// (or) System.err::println;
		c.accept("Hello World!");
		/**
		 * interface Consumer<V>{
		 * 		void accept(V v); 
		 * }
		 * 
		 * interface BiConsumer<V, K>{
		 * 		void accept(V v, K k);
		 * }
		 */
		
		//Supplier example
		/**
		 * interface Supplier<T>{
		 * 		T get();
		 * }
		 */
		Supplier<ExperimentalObject> experimentalObjectgenrator = () -> new ExperimentalObject("1", "test", 10);
				//= ExperimentalObject::new;	/* calls the constructor of the object if there is only one contructor */
		System.out.println(experimentalObjectgenrator.get());
		
		// Function example
		/**
		 * Base functional insterface
		 * Function<T, R>{
		 * 		R apply(T);
		 * }
		 * 
		 * BiFunction<T, V, R>{
		 * 		R apply(T, V);
		 * }
		 * 
		 * UnaryOperator<T> extends Function<T, T>{
		 * 		So, takes in object of same type and returns the same object and does not define any method
		 * }
		 * 
		 * BinaryOprator<T> extends Function<T, T, T>{
		 * 		Same goes here.
		 * }
		 */
		
		// Predicate example
		/**
		 * 
		 * Predicate<V>{
		 * 		Boolean test(V);
		 * }
		 * 
		 */
		Predicate<ExperimentalObject> experimentalObjectValueComparator = o -> (o.getValue() == null) || (o.getValue() < 3);
		System.out.println(experimentalObjectValueComparator.test(experimentalObjectgenrator.get()));
		
		// another way of writing the above as a functional interface
		Consumer<Boolean> filter = System.out::println;
		filter.accept(experimentalObjectValueComparator.test(experimentalObjectgenrator.get()));
	}
	
}
