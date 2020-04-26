package sbnz.integracija.example.facts;

import java.io.Serializable;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
public class Book implements Serializable{
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private HashMap<String,String> tags = new HashMap<String,String>();
	private double rating;
	@Column
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
