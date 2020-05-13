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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	//private HashMap<String,String> tags = new HashMap<String,String>();
	@Column
	private double rating;
	@Column
	private int avaivableNo;
	
	private int searchMatch=0;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
//	public HashMap<String, String> getTags() {
//		return tags;
//	}
//	public void setTags(HashMap<String, String> tags) {
//		this.tags = tags;
//	}
	public Book(Long id, HashMap<String, String> tags) {
		super();
		this.id = id;
		//this.tags = tags;
	}
	public Book() {
		super();
	}
	@Override
	public String toString() {
		return "Book [id=" + id + ", rating=" + rating + ", avaivableNo=" + avaivableNo + "]";
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public int getAvaivableNo() {
		return avaivableNo;
	}
	public void setAvaivableNo(int avaivableNo) {
		this.avaivableNo = avaivableNo;
	}
	public int getMatch() {
		return searchMatch;
	}
	public void setMatch(int match) {
		this.searchMatch = match;
	}
	
	
}
