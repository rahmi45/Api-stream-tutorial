package net.rahmi.streams.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Departement 
{
	
	private String codePostal;
	private String name;
	private List<Commune> communes = new ArrayList<>();
	
	public Departement() 
	{
	}

	public Departement(String codePostal, String name) 
	{
		this.codePostal = codePostal;
		this.name = name;
		
	}
	
	public void addCommune(Commune commune) {
		this.communes.add(commune);
	}

	public List<Commune> getCommunes(){
		return Collections.unmodifiableList(this.communes);
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codePostal, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Departement other = (Departement) obj;
		return Objects.equals(codePostal, other.codePostal) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Departement [name=" + name + ", codePostal=" + codePostal + "]";
	}
	
	
	
	
}
