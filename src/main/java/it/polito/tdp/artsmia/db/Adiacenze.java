package it.polito.tdp.artsmia.db;

import it.polito.tdp.artsmia.model.Artists;

public class Adiacenze implements Comparable <Adiacenze>{
	
	private Artists a1;
	private Artists a2;
	private int peso;
	
	
	public Adiacenze(Artists a1, Artists a2, int peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}


	public Artists getA1() {
		return a1;
	}


	public Artists getA2() {
		return a2;
	}


	public int getPeso() {
		return peso;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a1 == null) ? 0 : a1.hashCode());
		result = prime * result + ((a2 == null) ? 0 : a2.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adiacenze other = (Adiacenze) obj;
		if (a1 == null) {
			if (other.a1 != null)
				return false;
		} else if (!a1.equals(other.a1))
			return false;
		if (a2 == null) {
			if (other.a2 != null)
				return false;
		} else if (!a2.equals(other.a2))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return a1.getArtist_id() + " - " + a2.getArtist_id() + " : " + peso + "\n";
	}


	@Override
	public int compareTo(Adiacenze o) {
		return o.getPeso()-this.getPeso();
	}
	
	

}
