package DTO;

public class BookLoanRequestDTO {
	private Long bookId;
	private Long userId;
	
	public BookLoanRequestDTO() {
		super();
	}
	public BookLoanRequestDTO(Long bookId, Long userId) {
		super();
		this.bookId = bookId;
		this.userId = userId;
	}
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
	
	
}
