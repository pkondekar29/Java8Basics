package com.java.v8.basics.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class FunctionalInterfacesDriver {

    /**
     * <pre>
     * All functional interfaces are categorised in below four interfaces.<br>
     * 1. Supplier{T}: T get(); Takes no argument and returns object of type T
     *  Types of suppliers: BooleanSupplier, DoubleSupplier, IntSupplier, LongSupplier<br>
     * 2. Consumer{T}: void accept(T t); Consumes an object of type T
     *  Types of consumers: BiConsumer{T, U}, 
     *                      DoubleConsumer, IntConsumer, LongConsumer, 
     *                      ObjDoubleConsumer{T}, ObjIntConsumer{T}, ObjLongConsumer{T}<br>
     * 3. Predicate{T}: boolean test(T t); Takes an object of type T and returns boolean
     *  Types of predicates: BiPredicate{T, U}, 
     *                       DoublePredicate, IntPredicate, LongPredicate<br>
     * 4. Function{T, R}: R apply(T t); Takes an object of type T and returns an object of type R
     *  Types of functions: BiFunction{T, U, R},
     *                      BinaryOperator{T} - Function{T, T, T}, 
     *                      DoubleBinaryOperator, IntBinaryOperator, DoubleBinaryOperator,
     *                      UnaryOperator{T} - Function{T, T}, 
     *                      DoubleUnaryOperator, IntUnaryOperator, LongUnaryOperator,
     *                      DoubleFunction{R}, DoubleToIntFunction, DoubleToLongFunction, 
     *                      ToDoubleBiFunction{T, U}, ToDoubleFunction{T},
     *                      IntFunction{R}, IntToDoubleFunction, IntToLonfFunction, 
     *                      ToIntBiFunction{T, U}, ToIntFunction{T},
     *                      LongFunction{R}, LongToDoubleFunction, LongToIntFunction, 
     *                      ToLongBiFunction{T, U}, ToLongFunction{T}
     * </pre>
     */
    public static void main(String[] args) {
        // Base funcational interfaces
        Supplier<List<String>> listSupplier = () -> new ArrayList<String>();
        listSupplier.get();
        Consumer<String> strPrinter = str -> System.out.println(str);
        strPrinter.accept("Hello world");
        Predicate<String> emptyStringPredicate = str -> (str == null || str.length() == 0);
        emptyStringPredicate.test(null);
        Function<String, Integer> strSizeCalculator = str -> str != null ? str.length() : 0;
        strSizeCalculator.apply("What's my size?");

        // Derived Consumers
        BiConsumer<Long, Integer> divider = (l, i) -> System.out.println(l/i);
        divider.accept(10L, 2);
        // Derived Predicates
        BiPredicate<String, String> strEquator = (str1, str2) -> str1.equals(str2);
        strEquator.test("This is a string", "This is a string");

        // Derived funcations
        BiFunction<Integer, Integer, String> intsToStrConcatenator =  (i1, i2) -> (i1 + i2) + "";
        intsToStrConcatenator.apply(0, 0);
        BinaryOperator<Integer> adder = (i1, i2) -> i1 + i2;        // input params are 2
        adder.apply(10, 5);
        UnaryOperator<Integer> incrementByFive = i -> i + 5;        // input params is 1
        incrementByFive.apply(5);
    }

}