package sbnz.integracija.example.facts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BookTag {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column
	private Long bookId;
	@Column
	private String tagKey;
	@Column(length=1500)
	private String tagValue;
	
	BookTagStatus status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	public String getTagKey() {
		return tagKey;
	}
	public void setTagKey(String tagKey) {
		this.tagKey = tagKey;
	}
	public String getTagValue() {
		return tagValue;
	}
	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

	public BookTag(Long bookId, String tagKey, String tagValue, BookTagStatus status) {
		super();
		this.bookId = bookId;
		this.tagKey = tagKey;
		this.tagValue = tagValue;
		this.status = status;
	}
	public BookTagStatus getStatus() {
		return status;
	}
	public void setStatus(BookTagStatus status) {
		this.status = status;
	}
	public BookTag() {
		super();
	}
	

}
