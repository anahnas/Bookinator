package DTO;

import java.util.ArrayList;

import sbnz.integracija.example.facts.Book;
import sbnz.integracija.example.facts.BookTag;

public class BookRecommendDTO {

	private Long id;
	private ArrayList<BookTag> tags = new ArrayList<>();
	private boolean recommended = false;

	public BookRecommendDTO() {
		super();
	}

	public BookRecommendDTO(Long id, ArrayList<BookTag> tags, boolean recommended) {
		super();
		this.id = id;
		this.tags = tags;
		this.recommended = recommended;
	}
	
	public BookRecommendDTO(Book b) {
		this.id = b.getId();
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

	public boolean isRecommended() {
		return recommended;
	}

	public void setRecommended(boolean recommended) {
		this.recommended = recommended;
	}
}
