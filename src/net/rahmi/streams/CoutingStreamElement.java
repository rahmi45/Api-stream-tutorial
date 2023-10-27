package net.rahmi.streams;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CoutingStreamElement {

	public static void main(String[] args) 
	{
		// stream sur une liste
		//Quel element apparaît le plus souvent dans cette liste.
		List<String> strings = List.of("one", "two" , "three"  , "four" , "five" , 
				"six" , "seven" , "eight" , "nine" , "ten", "seven" , "eight" , "nine",
				"two" , "three"  , "four" , "five" , "one", "two" , "three"  , "four" , "five" ,
				"six" , "seven" , "eight" , "nine", "two" , "three"  , "four" , "five" , 
				"six" , "seven" , "eight" , "nine" , "ten");
		
		//nine -> 4
		//six -> 3
		//four -> 4
		
		Function<String , String> classifier = word -> word ;
		Map<String, Long> map =
	    //Map<String, List<String>> map =
	          strings.stream()
		 		.collect(
		 				Collectors.groupingBy(
		 						classifier , Collectors.counting()
		 						)
		 				);
		 
		 map.forEach((key,value) -> System.out.println(key + " -> " + value));
		 
		 Entry<String, Long> maxEntry = map.entrySet()
		 	.stream()
		 	.max(Map.Entry.comparingByValue())
		 	.orElseThrow();
		 System.out.println("\nMax entry by value :");
		 System.out.println(maxEntry.getKey() + " -> " + maxEntry.getValue());
		
		// Previous map 
		//nine -> 4
		//six -> 3
		//four -> 4
		// New map 
		// 4 -> [nine , four] 
//		 Map<Long, List<Entry<String, Long>>> map2 = 
		 Map<Long, List<String>> map2 = 
			map.entrySet() //Map.Entry<>String, Long>
		 		.stream()
		 		.collect(
		 				Collectors.groupingBy(
		 						entry -> entry.getValue(),
		 						Collectors.mapping(
		 								entry -> entry.getKey(),
		 								Collectors.toList()
		 								)
		 						)
		 			);
		 Entry<Long, List<String>> maxEntry2 = 
				 map2.entrySet()
				 	.stream()
				 	.max(Map.Entry.comparingByKey())
				 	.orElseThrow();
		 System.out.println("\nMax entry by key :");
		 System.out.println(maxEntry2.getKey() + " -> " + maxEntry2.getValue());
		 		
	}

}
