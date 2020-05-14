package DTO;

import javax.persistence.Column;

public class BookTagDTO {
	private Long id;
	private Long bookId;
	private String tagKey;
	private String tagValue;
	private boolean approved;
	
	public BookTagDTO() {
		super();
	}
	public BookTagDTO(Long id, Long bookId, String tagKey, String tagValue, boolean approved) {
		super();
		this.id = id;
		this.bookId = bookId;
		this.tagKey = tagKey;
		this.tagValue = tagValue;
		this.approved = approved;
	}
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
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	
	
}
