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
	private Long tagKey; //foreign key from Tag table
	@Column(length=1500)
	private String tagValue;
	
 	@Column(name="book_status") 
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
	public Long getTagKey() {
		return tagKey;
	}
	public void setTagKey(Long tagKey) {
		this.tagKey = tagKey;
	}
	public String getTagValue() {
		return tagValue;
	}
	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

	public BookTag(Long bookId, Long tagKey, String tagValue) {
		super();
		this.bookId = bookId;
		this.tagKey = tagKey;
		this.tagValue = tagValue;
	}
	
	public BookTag(Long bookId, Long tagKey, String tagValue, BookTagStatus status) {
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
	
	public BookTag(String tagValue, BookTagStatus status) {
		this.tagValue = tagValue;
		this.status = status;
	}
	
	

}
