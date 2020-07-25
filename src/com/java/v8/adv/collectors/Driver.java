package com.java.v8.adv.collectors;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.Collector.Characteristics;

import com.java.v8.basics.beans.ExperimentalObject;

public class Driver{

    /**
     * Collectors are used in the last step of map,filter,reduce algos.
     * 
     * Collectors class is a factory class with many static collectors.
     * Collector is special type of reduction. 
     * Once the collector is called, the stream computation is triggered.
     * 
     * "Collecting data is about gathering data in a mutable container*
     * For ex - List
     *       String concatenation(StringBuilder)
     * 
     */
    public static void main(final String[] args) {
        final Stream<Integer> stream = IntStream.range(15, 30).boxed();
        final List<Integer> integerList = stream.filter(i -> i > 20).collect(Collectors.toList());

        //integerList.forEach(System.out::print);
        final double avg = 
            integerList.stream()
                .collect(Collectors.averagingInt(i -> i));
        System.out.println("Average: " + avg + "\n");

        Optional<Integer> max = 
            integerList.stream()
                .collect(Collectors.maxBy(Comparator.comparing(Function.identity())));
        System.out.println("Max:" + max.get() + "\n");

        Stream<String> strStream = Stream.of("a", "b", "c");
        String result = 
            strStream
                .collect(Collectors.joining(","));      // Collecting streams
        System.out.println("Joined string: " + result + "\n");

        /**
         *  Collectors for maps
         *  */ 
        Map<Boolean, List<Integer>> partitionMap = 
            integerList.stream()
                // Partition by takes a predicate, so the stream would be partitioned only on true or false
                .collect(Collectors.partitioningBy(i -> i > 60));       
        System.out.println("Partitioned Map: \n");
        partitionMap.entrySet().forEach(System.out::println);
        System.out.println();

        List<ExperimentalObject> listObj = new ArrayList<>();
        listObj.add(new ExperimentalObject("1", "asldkj", 10));
        listObj.add(new ExperimentalObject("2", "asdlk", 40));
        listObj.add(new ExperimentalObject("3", "wepr", 30));
        listObj.add(new ExperimentalObject("3", "wepr", 30));
        listObj.add(new ExperimentalObject("3", "wepr", 30));
        listObj.add(new ExperimentalObject("2", "dfkl", 20));
        listObj.add(new ExperimentalObject("2", "dfkl", 20));
        listObj.add(new ExperimentalObject("5", "cmn", 50));
        
        Map<Integer, List<ExperimentalObject>> objMap = 
            listObj.stream()
                // The list of objects is grouped on the basis of value being the key
                .collect(Collectors.groupingBy(ExperimentalObject::getValue));
        objMap.entrySet().forEach(System.out::println);
        System.out.println();

        Map<Integer, Long> objByValuesCount = 
            listObj.stream()
                .collect(
                    Collectors.groupingBy(ExperimentalObject::getValue,
                    Collectors.counting())      // Here we are passing one more collector to the groupingBy collection to return the count
                );      // This collector is called the downstream collector
        objByValuesCount.entrySet().forEach(System.out::println);
        System.out.println();

        Map<Integer, TreeSet<String>> objByValuesSortedMap = 
            listObj.stream()
                // Here the downstream collector can also map the elements before collecting them. 
                // So, the elements are mapped to their and then collected using TreeSet supplier
                .collect(Collectors.groupingBy(ExperimentalObject::getValue,
                        Collectors.mapping(ExperimentalObject::getName, Collectors.toCollection(TreeSet::new))));
        objByValuesSortedMap.entrySet().forEach(System.out::println);
        System.out.println();

        TreeMap<Integer, TreeSet<String>> sortedKeyValueMap = 
            listObj.stream()
                .collect(Collectors.groupingBy(ExperimentalObject::getValue,
                    TreeMap::new,       // Here we also specified the supplier for collecting the map key, value pairs
                    Collectors.mapping(ExperimentalObject::getName, Collectors.toCollection(TreeSet::new))));
        sortedKeyValueMap.entrySet().forEach(System.out::println);
        System.out.println();

        // Collectors.collectingAndThen() -> can pass unmidifyable map as supplier to create unmodifyable maps
        Map<String, Optional<ExperimentalObject>> maxValueMap = listObj.stream()
        		.collect(Collectors.groupingBy(ExperimentalObject::getId,
        				Collectors.reducing((o1, o2) -> o1)
        		));
        maxValueMap.entrySet()
			.stream()
			// Filter the tickets by checking if the optional is present
			.filter(entry -> entry.getValue().isPresent())
			// Map the optional with its TicketGsap value
			.map(entry -> entry.getValue().get())
			// Collect it to a list
			.forEach(System.out::println);
    }

}