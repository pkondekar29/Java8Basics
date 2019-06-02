package com.java.v8.basics.lambda.functionalinterfaces;

import java.util.function.Predicate;

/**
 * How does Java 8 compiler knows that it's an functional interface??
 * 	1. It's smart.
 * 	2. If there is only one method, then its functional interface.
 * 	3. The type of the variable gives the type of lambda expression
 * 	4. There is only one method, we know the parameters(their signatures) and also the return type(type of return type).
 * 	5. The same goes for exceptions, if any.
 * 
 */
public class DriverProgram {

	public static void main(String[] args) {
		
		Predicate<String> pred = s -> (s.length() < 10); 	/* So the predicate takes an i/p String and returns boolean */
		System.out.println(pred.test("Hello!"));	/* As lambda expression/functional interface is still an interface */
	}
	
}
