package sbnz.integracija.example.facts;

import java.io.Serializable;
import java.util.Date;

public class BookLoan implements Serializable{

	private Book book;
	private Long id;
	private Date expiryDate;
	private boolean returned;
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
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
	public BookLoan(Book book, Long id, Date expiryDate, boolean returned) {
		super();
		this.book = book;
		this.id = id;
		this.expiryDate = expiryDate;
		this.returned = returned;
	}
	public BookLoan() {
	}
	
	
}
