package DTO;

import java.util.ArrayList;
import sbnz.integracija.example.facts.Book;
import sbnz.integracija.example.facts.BookTag;

public class BookDTO {

	private Long id;
	private ArrayList<BookTag> tags = new ArrayList<>();
	private double match;
	private int availableNo;

	public BookDTO() {
		super();
	}
	
	public BookDTO(Book b) {
		this.id = b.getId();
		this.match = b.getMatch();
		this.availableNo = b.getAvaivableNo();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public ArrayList<BookTag> getTags() {
		return tags;
	}

	public void setTags(ArrayList<BookTag> tags) {
		this.tags = tags;
	}

	public BookDTO(Long id, ArrayList<BookTag> tags, double match) {
		super();
		this.id = id;
		this.tags = tags;
		this.match = match;
	}

	public double getMatch() {
		return match;
	}

	public void setMatch(double match) {
		this.match = match;
	}

	public int getAvailableNo() {
		return availableNo;
	}

	public void setAvailableNo(int availableNo) {
		this.availableNo = availableNo;
	}

	
	
}
