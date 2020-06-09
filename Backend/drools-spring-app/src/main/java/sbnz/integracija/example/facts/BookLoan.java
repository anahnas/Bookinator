package sbnz.integracija.example.facts;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BookLoan implements Serializable{

	@Column
	private Long bookId;
	@Column
	private Long userId;
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column
	private Date expiryDate;
	@Column
	private boolean returned;
	@Column
	private boolean expired;


	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public boolean isReturned() {
		return returned;
	}
	public void setReturned(boolean returned) {
		this.returned = returned;
	}
	
	public BookLoan(Long bookId, Long userId, Date expiryDate, boolean returned, boolean expired) {
		super();
		this.bookId = bookId;
		this.userId = userId;
		this.expiryDate = expiryDate;
		this.returned = returned;
		this.expired = expired;
	}
	public boolean isExpired() {
		return expired;
	}
	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	public BookLoan() {
	}
	
	
}
