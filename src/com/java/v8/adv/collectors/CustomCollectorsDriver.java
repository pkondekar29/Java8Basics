package com.java.v8.adv.collectors;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
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
        BiConsumer<List<ExperimentalObject>, ExperimentalObject> accumulator = (list, obj) -> list.add(obj);
        // Combiner is a function which is used in case of parallel streams. 
        // So, it combines the result of partial accumulated objects on different cores of the CPU
        BinaryOperator<List<ExperimentalObject>> combiner = (list1, list2) -> {
            list1.addAll(list2);
            return list2;
        };
        Collector collector = Collector.of(
             supplier,      // Supplier for creating the collection
             accumulator, 
             combiner, 
             Characteristics.IDENTITY_FINISH);
        
        List<ExperimentalObject> listObj = new ArrayList<>();
        listObj.add(new ExperimentalObject("1", "asldkj", 10));
        listObj.add(new ExperimentalObject("2", "asdlk", 40));
        listObj.add(new ExperimentalObject("3", "wepr", 30));
        listObj.add(new ExperimentalObject("1", "wepr", 30));
        listObj.add(new ExperimentalObject("1", "wepr", 40));
        listObj.add(new ExperimentalObject("2", "dfkl", 20));
        listObj.add(new ExperimentalObject("2", "dfkl", 40));
        listObj.add(new ExperimentalObject("2", "cmn", 50));
        
        Supplier<Map<String, TreeSet<ExperimentalObject>>> s = () -> new HashMap<String, TreeSet<ExperimentalObject>>();
        BiConsumer<Map<String, TreeSet<ExperimentalObject>>, ExperimentalObject> a = 
        		(map, e) -> {
        			map.computeIfAbsent(e.getName(), set -> new TreeSet<>(Comparator.comparing(ExperimentalObject::getValue)))
        					.add(e);
        		};
        BinaryOperator<Map<String, TreeSet<ExperimentalObject>>> c =
        		(map1, map2) -> {
        			map2.entrySet()
        				.stream()
        				.forEach(e2 -> {
        					if(map1.containsKey(e2.getKey())){
        						map1.get(e2.getKey()).addAll(e2.getValue());
        					} else {
        						map1.put(e2.getKey(), e2.getValue());
        					}
        				});
        			
        			return map1;
        		};
        Collector cc = Collector.of(s, a, c, Collector.Characteristics.IDENTITY_FINISH);
        
        Map<String, Map<String, TreeSet<ExperimentalObject>>> fm =
        		listObj.stream().parallel()
        			.collect(Collectors.groupingBy(ExperimentalObject::getId, cc));
        
        fm.entrySet()
        	.stream()
        	.forEach(e -> {
        		System.out.println("First key Id: " + e.getKey());
        		e.getValue().entrySet().forEach(e2 -> {
        			System.out.println("Grouped on Name: " + e2.getKey());
        			System.out.println("Sorted on value:");
        			e2.getValue().forEach(eo -> System.out.print(eo.getValue() + ", "));
        			System.out.println();
        		});
        		System.out.println(" ----- End of key ------ ");
        	});
    }

}