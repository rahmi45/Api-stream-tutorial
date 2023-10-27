package net.rahmi.streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore.Entry;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.rahmi.streams.model.Commune;
import net.rahmi.streams.model.Departement;


public class PlayWithDepartements 
{
	public static void main(String[] args) throws IOException {
		
		List<Departement> departements = redDepartement("files/departements.txt");
			
		//depts.forEach(System.out::println);
		
		List<Commune> communes = redCommunes("files/communes.txt");
		
		//communes.forEach(System.out::println);
		
		Function<Commune, String> toCodeDepartement = 
				commune -> commune.getCodePostal().startsWith("97") ?
						   commune.getCodePostal().substring(0 , 3) :
						   commune.getCodePostal().substring(0 , 2);	   
		Map<String, List<Commune>>	communeByCodeDepartement= 
				communes.stream()
				      .collect(Collectors.groupingBy(toCodeDepartement)
				      );
		//System.out.println(" # of map : " + communeByCodeDepartement.size());
		
		Map<String, Long> numberOfCommunesByCodeDepartement = communes.stream()
	      .collect(
	    		  Collectors.groupingBy(
	    				  toCodeDepartement, Collectors.counting()
	    				  )
	      );
		
		System.out.println("# communes : " + numberOfCommunesByCodeDepartement);
		
		communeByCodeDepartement.get("974").forEach(System.out::println);
		//System.out.println(communeByCodeDepartement.get("974").size());
		
		//Nombre total de communes
		Consumer<Departement> addCommunesToDepartement = 
				d -> communes.stream()
					.filter(c -> c.getCodePostal().startsWith(d.getCodePostal()))
					.forEach(d::addCommune);	
		departements.forEach(addCommunesToDepartement);
		
		//departements.forEach( d -> System.out.println(d.getName() + " possède " + d.getCommunes().size() + " communes."));
		
		// flap map
		
		Function<Departement,Stream<Commune>> flapMapper = 
				d -> d.getCommunes().stream();
		long countCommunes = 
			departements.stream()
				.flatMap(flapMapper)
				.count(); 
		
		System.out.println(" # communes : " + countCommunes);
		
		// Le departement qui a plus de communes
		//# communes : {78=23, 974=24, 93=12}
		Map.Entry<String, Long> maxEntry = 
				numberOfCommunesByCodeDepartement.entrySet().stream()
				//.max(Comparator.comparing(entry -> entry.getValue()))
				.max(Map.Entry.comparingByValue())
				.orElseThrow();
		
		String maxCodeDepartement = maxEntry.getKey();
		Long maxCountOfCommunes = maxEntry.getValue();
		
		System.out.println(" Le Departement : " + maxCodeDepartement + " a " + maxCountOfCommunes);
		Set<String> keySet = numberOfCommunesByCodeDepartement.keySet();
		Collection<Long> values = numberOfCommunesByCodeDepartement.values();
	}

	/**
	 * @param path
	 * @throws IOException
	 */
	private static List<Commune> redCommunes(String path) throws IOException {
		Path pathToCommunes = Path.of(path);

		Predicate<String> isComment = line -> line.startsWith("#");
		Function<String, String> toNom = l -> l.substring(0, l.indexOf(" ("));
		Function<String, String> toCp = l -> l.substring(l.indexOf(" (") + 2, l.length()-1);
		Function<String, Commune> toCommune = l -> new Commune(toNom.apply(l), toCp.apply(l));
		
		return 
			Files.lines(pathToCommunes)
			.filter(isComment.negate())
			.map(toCommune)	
			.collect(Collectors.toList());
	}

	private static List<Departement> redDepartement(String path) throws IOException 
	{
		Path pathToDepartements = Path.of(path);
		Predicate<String> isComment = line -> line.startsWith("#");
		Function<String, String> toCodePostal = l -> l.substring(0, l.indexOf(" - "));
		Function<String, String> toNom = l -> l.substring(l.indexOf(" - ") + 3 );
		Function<String, Departement> toDepartement = l -> new Departement(toCodePostal.apply(l), toNom.apply(l));
		
		return
				Files.lines(pathToDepartements)
					.filter(isComment.negate())
		 			.map(toDepartement)	
		 			.collect(Collectors.toList());
		
		
	}
}
