package net.rahmi.streams;

import java.util.Optional;

public class PlayWithOptional {

	public static void main(String[] args) {
		// Type primitif			
		int int10 = 10;
		// classe Wrapper sur les types primifs  : qui enveloppe une valeur et qui permet de rajouter des méthodes sur cette valeur 
		
		Integer i10 = Integer.valueOf(10);
		
		// Class Wrapper qui enveloppe une valeur, la seule différence entre Interger, Optional peut etre vide 
		Optional<String> opt = Optional.of("one");
		
		Optional<String> emptyOpt = Optional.empty();
		
		boolean empty = opt.isEmpty();
		System.out.println("\n Optional vide : " + empty);
		System.out.println("\n Optional vide : " + emptyOpt.isEmpty());
		
		String string = opt.get();
		System.out.println("\n Contenu de opt : " + string);
		String stringEmplty = opt.orElseThrow();
		
		Optional<String> nullOpt = Optional.ofNullable(null);
		System.out.println("\n Contenu de nullOpt : " + nullOpt.orElseThrow());

	}

}
