package com.java.v8.basics.lambda.functionalinterfaces;

/**
 *	So, we used lambda, default methods and static methods to develop brand new family of patterns.
 *	This type of writing makes coding very readable and easy and small 
 * 
 *	New opportunities for our legacy code, and add functionalities to our API
 *
 * @param <T>
 */
@FunctionalInterface
public interface Predicate<T> {

	public boolean test(T t);

	public default Predicate<T> and(Predicate<T> other) {
		/**
		 * The default method should return predicate.
		 * It shuold also give logic of only abstract method. 
		 * 
		 * */
		return t -> (test(t) && other.test(t));
	}
	
	public default Predicate<T> or(Predicate<T> other){
		return t -> test(t) || other.test(t);
	}
	
	public static <U> Predicate<U> isEqualTo(U u){
		return p -> p.equals(u);
	}
	
}
