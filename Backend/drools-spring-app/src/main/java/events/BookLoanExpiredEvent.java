package events;

import java.io.Serializable;

public class BookLoanExpiredEvent implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long userId;
	private Long bookLoanId;
	
	public BookLoanExpiredEvent() {
		super();
	}
	public BookLoanExpiredEvent(Long userId, Long bookLoanId) {
		super();
		this.userId = userId;
		this.bookLoanId = bookLoanId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getBookLoanId() {
		return bookLoanId;
	}
	public void setBookLoanId(Long bookLoanId) {
		this.bookLoanId = bookLoanId;
	}


	 
	
}