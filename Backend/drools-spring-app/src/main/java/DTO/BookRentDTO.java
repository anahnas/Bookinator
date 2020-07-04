package DTO;

public class BookRentDTO {
	private Long userId;
	private Long bookId;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	public BookRentDTO() {
		super();
	}
	public BookRentDTO(Long userId, Long bookId) {
		super();
		this.userId = userId;
		this.bookId = bookId;
	}
	
	
	
	

}
