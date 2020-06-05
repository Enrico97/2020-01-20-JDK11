package it.polito.tdp.artsmia.model;

public class Artists {
	
	private int artist_id;
	private String name;
	
	
	public Artists(int artist_id, String name) {
		super();
		this.artist_id = artist_id;
		this.name = name;
	}


	public int getArtist_id() {
		return artist_id;
	}


	public String getName() {
		return name;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + artist_id;
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
		Artists other = (Artists) obj;
		if (artist_id != other.artist_id)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return artist_id + " " + name;
	}
	
	

}
