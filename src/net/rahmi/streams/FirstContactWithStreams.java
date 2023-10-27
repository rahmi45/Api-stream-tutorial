package net.rahmi.streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FirstContactWithStreams {
	
	public static void main(String...args) throws IOException {
		// stream sur une liste
		List<String> strings = List.of("one", "two" , "three"  , "four" , "five" , 
				"six" , "seven" , "eight" , "nine" , "ten");
		
		strings.stream().forEach(System.out::println);
		
		//stream sur un tableau 
		String[] arrayOfStrings = {"one", "two" , "three"  , "four" , "five" , 
				"six" , "seven" , "eight" , "nine" , "ten"};
		
		//classe factory, toutes ses méthodes sont static
		System.out.println("\nstream sur un tableau");
		Arrays.stream(arrayOfStrings).forEach(System.out::println);
		
		// pattern de stream
		//stream à partir d'elements
		//methode factory de l'interface stream
		System.out.println("\nstream à partir d'elements");
		Stream.of("one", "two" , "three"  , "four").forEach(System.out::println);
		
		// stream sur les lignes d'un fichier 
		//Path path = Path.of("c:/tmp/debug.log"); // chemin absolu
		String fileName = "files/lines.txt";
		Path path = Path.of(fileName); // chemin relatif
		boolean exists = Files.exists(path);
		// classe factory avec un constructeur prive et que des methodes static
		Stream<String> lines = Files.lines(path);
		
		System.out.println("\nstream sur les lignes d'un fichier");
		lines.forEach(System.out::println);
		
		//stream sur un pattern : 
		String line = "one two three four";
		//Pattern 1
		String[] split = line.split(" ");
		System.out.println("\nstream sur une ligen découpée par un split");
		Arrays.stream(split).forEach(System.out::println);
		
		//pattern 2
		Pattern pattern = Pattern.compile(" ");
		System.out.println("\nstream sur une ligen découpée par un pattern");
		pattern.splitAsStream(line).forEach(System.out::println);
		
		
		String s1 = Arrays.stream(split)
		   .filter(s -> s.length() == 3)
		   .findFirst().orElseThrow();
		System.out.println("\nPremière chaîne de longueur 3 : " + s1);
		
		String s2 = Pattern.compile(" ").splitAsStream(line)
			   .filter(s -> s.length() == 3)
			   .findFirst().orElseThrow();
				System.out.println("\nPremière chaîne de longueur 3 : " + s2);
				
		// stream vide 
		Stream<String> emptyStream = strings.stream().filter(s -> s.length() > 50);
		System.out.println("\nEmply stream : " + emptyStream.findFirst());
		
		// regroupement par longueur
		Map<Integer, List<String>> map = strings.stream()
		       .collect(Collectors.groupingBy(s -> s.length())
		    		   );
		
		map.forEach((key, value) -> System.out.println(key + " -> " + value));
		
		
		
	} 

}
