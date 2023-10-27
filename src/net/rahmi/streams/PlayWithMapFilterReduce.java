package net.rahmi.streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayWithMapFilterReduce {

	/**
	 * @param args
	 * @throws IOException
	 */
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		List<String> strings = List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten");

		int maxLength = strings.stream() // Stream<String>
				.map(s -> s.length()) // Stream<Integer>
				.max(Comparator.naturalOrder()).orElseThrow();

		System.out.println("La longueur max : " + maxLength);

		// Stream sur un fichier
		String fileName = "files/lines.txt";
		Path path = Path.of(fileName);
		int maxLengthLine = Files.lines(path).mapToInt(String::length).max().orElseThrow();

		System.out.println("\nLa longueur max : " + maxLengthLine);

		String maxLineByLength = Files.lines(path).max(Comparator.comparing(line -> line.length())).orElseThrow();

		System.out.println("\nLine de longueur max : " + maxLineByLength);

		long count = Files.lines(path).count();

		System.out.println("Count : " + count);

		System.out.println("\nstream sur une ligen découpée par un pattern");

		Function<String, Stream<String>> toWords = l -> Pattern.compile(" ").splitAsStream(l);

		String line = "One two tree four";

		Stream<String> stream = toWords.apply(line);
//				Pattern.compile(" ")
//					.splitAsStream(line);
		stream.forEach(System.out::println);

		Stream<Stream<String>> streamOfstream = Files.lines(path) // Stream<String>
				.map(toWords); // Stream<Stream<String>>

		long count2 = streamOfstream.count();
		System.out.println("Count : " + count2);

		Files.lines(path) // Stream<String>
				.map(toWords).forEach(System.out::println);
		
		long countWords = Files.lines(path) // Stream<String>
				.flatMap(toWords) // Stream<String>
				.count();

		System.out.println("Count words : " + countWords);

		List<String> words = 
				Files.lines(path) // Stream<String>
				.flatMap(toWords) // Stream<String>
				.collect(Collectors.toList());
		System.out.println("\nMot du fichier text");
		words.forEach(System.out::println);

		String word = "eleven";

		Stream<Character> letters = word.chars().mapToObj(letter -> (char) letter);

		Function<String, Stream<Character>> toLetters = w -> w.chars().mapToObj(letter -> (char) letter);

		Stream<Character> stream2 = toLetters.apply(word);
		System.out.println("\nLetters avec Function: ");
		stream2.forEach(System.out::println);

		System.out.println("\nLetters : ");
		letters.forEach(System.out::println);
		
		System.out.println("\nLetters du fichier : ");
		Files.lines(path)     // Stream<String> = ligne du fichier
			.flatMap(toWords) // Stream<String> = mots du fichier
			.flatMap(toLetters) // Stream<String> = lettres du fichier
			.distinct()
			.sorted()
			.forEach(System.out::println); 
		 
		 
	}

}
