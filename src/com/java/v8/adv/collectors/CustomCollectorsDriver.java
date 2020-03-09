package com.java.v8.adv.collectors;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

import com.java.v8.basics.beans.ExperimentalObject;

public class CustomCollectorsDriver {

    /**
     * When we can't use the built in Collectors.
     * Elements required for collectors 
     *  1. A supplier on how the collection will be build
     *  2. How to add each element of stream to the collector
     * 
     *  3. If we want to go parallel, we need a third element to 
     *  merge partial containers(ex - List)
     * 
     * @param args
     */
    public static void main(String[] args) {
        // supplier to generate List of experimental objects
        Supplier<List<ExperimentalObject>> supplier = () -> new ArrayList<>();  
        // Accumulator is a function which takes each element and the result
        BiConsumer<ExperimentalObject, List<ExperimentalObject>> accumulator = (obj, list) -> list.add(obj);
        // Combiner is a function which is used in case of parallel streams. 
        // So, it combines the result of partial accumulated objects on different cores of the CPU
        BinaryOperator<List<ExperimentalObject>> combiner = (list1, list2) -> {
            list1.addAll(list2);
            return list2;
        };
        // Collector collector = Collector.of(
        //     supplier,      // Supplier for creating the collection
        //     accumulator, 
        //     combiner, 
        //     Characteristics.IDENTITY_FINISH);

    }

}