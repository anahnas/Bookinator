package sbnz.integracija.example.facts;

import java.util.HashMap;

public class ReviewRequest {
	private String critique;
	private Double rate;
	private HashMap<String, String> tags = new HashMap<>();
	
	private Long bookId;
	
	public ReviewRequest() {
		super();
	}


	public ReviewRequest(String critique, Double rate, HashMap<String, String> tags, Long bookId) {
		super();
		this.critique = critique;
		this.rate = rate;
		this.tags = tags;
		this.bookId = bookId;
	}


	public String getCritique() {
		return critique;
	}
	public void setCritique(String critique) {
		this.critique = critique;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}

	
	public HashMap<String, String> getTags() {
		return tags;
	}


	public void setTags(HashMap<String, String> tags) {
		this.tags = tags;
	}


	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}


	@Override
	public String toString() {
		return "ReviewRequest [critique=" + critique + ", rate=" + rate + ", tags=" + tags + ", bookId=" + bookId + "]";
	}
	
}
