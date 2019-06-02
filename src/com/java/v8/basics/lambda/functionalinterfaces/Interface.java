package com.java.v8.basics.lambda.functionalinterfaces;

/**
 * A lambda expression is an instance of functional interface.
 * The notion of functional interface is to give a type to lambda expressions.
 * 
 * A functional method is an interface with only with only one abstract method
 * 
 * Functional interfaces are backward compatible to previous JDKs. Eg- Runnable, Comparable, Callable 
 *
 * Definition-> 
 * 	Functional interface is an interface with:
 * 		- with only one abstract method
 * 		- default methods dont count
 *  	- static methods dont count
 *  	- methods from the "Object" class do not count
 *  @FunctionalInterface may be annotated
 * 
 * @param <T>
 */
@FunctionalInterface
public interface Interface<T> {
	
	int compare(T t1, T t2);
	
}
