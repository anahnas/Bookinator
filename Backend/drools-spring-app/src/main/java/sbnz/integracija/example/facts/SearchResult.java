package sbnz.integracija.example.facts;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {

	private List<Book> books = new ArrayList();
	private int searchTagNo;

	public SearchResult(List<Book> books, int searchTagNo) {
		super();
		this.books = books;
		this.searchTagNo = searchTagNo;
	}

	public SearchResult(int searchTagNo) {
		super();
		this.searchTagNo = searchTagNo;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public int getSearchTagNo() {
		return searchTagNo;
	}

	public void setSearchTagNo(int searchTagNo) {
		this.searchTagNo = searchTagNo;
	}

}
