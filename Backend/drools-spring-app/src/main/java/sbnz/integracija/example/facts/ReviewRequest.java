package sbnz.integracija.example.facts;

import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ReviewRequest {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column
	private String critique;
	@Column
	private float rate;
	
	private HashMap<String, String> tags = new HashMap<>();
	
	@Column
	private Long bookId;
	
	@Column 
	private Long userId;
	
	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public ReviewRequest() {
		super();
	}


	public ReviewRequest(String critique, float rate, HashMap<String, String> tags, Long bookId) {
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
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
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
		return "ReviewRequest [critique=" + critique + ", rate=" + rate + ",  bookId=" + bookId + "]";
	}
	
}
