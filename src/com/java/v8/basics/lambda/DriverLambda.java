package com.java.v8.basics.lambda;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import com.java.v8.basics.beans.ExperimentalObject;

public class DriverLambda {

	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("10");
		
		/** In case of single return statement */
		Comparator<String> comparator = (String s1, String s2) ->	/** Lambda expression is an expression which allows us to carry code which can be passed to some other code */ 
			Integer.compare(s1.length(), s2.length());
			
		/** In case of multiple statements */	
		Runnable r = () -> {
			int i = 0;
			while(i < 10) {
				System.out.println(i);
				i--;
			}
		};
		/**
		 * We can use final keyword or Annotaion to the i/p in -> expression.
		 * It is not possible to specify the return type of lambda expression.
		 *  
		 * We can also omit the type of paramters.
		 *  */
		Comparator<String> comparatorEnhanced = (s1, s2) -> {
			System.out.println(s1 + s2);
			return Integer.compare(s1.length(), s2.length());
		};
		
		/** Method references */
		/**
		 * 
		 * Both the expressions compile the same and are totally identical. Its just a matter of readability.
		 */
		Function<ExperimentalObject, Integer> f1 = o -> o.getValue();
		Function<ExperimentalObject, Integer> f2 = ExperimentalObject::getValue;	/** Although the lamda expression has nothing to do with static or non-static reference of a method */
		
		/** 
		 * It takes in String and there is no return value, so the consumer does not have a ret value.
		 *  
		 *  */
		Consumer<String> consumer2 = s -> System.out.println(s);
		Consumer<String> consumer = System.out::println; // Both the ways are correct and same
	}
	
}
