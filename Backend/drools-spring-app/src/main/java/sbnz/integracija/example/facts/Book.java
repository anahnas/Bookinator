package sbnz.integracija.example.facts;

import java.io.Serializable;
import java.util.HashMap;

public class Book implements Serializable{
	
	private Long id;
	private HashMap<String,String> tags = new HashMap<String,String>();
	private double rating;
	private int avaivableNo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public HashMap<String, String> getTags() {
		return tags;
	}
	public void setTags(HashMap<String, String> tags) {
		this.tags = tags;
	}
	public Book(Long id, HashMap<String, String> tags) {
		super();
		this.id = id;
		this.tags = tags;
	}
	public Book() {
		super();
	}
	
	
}
