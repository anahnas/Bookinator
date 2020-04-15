package sbnz.integracija.example.facts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Member extends User implements Serializable{
	
	enum cathegory{NONE,SILVER,GOLD,PLATINUM};
	
	private Date joinDate;
	private Date expiryDate;
	private Penalty penalty;
	private BookLoan loan;
	private List<BookLoan> history=new ArrayList<BookLoan>();
	private cathegory cathegory;
	private boolean banned;
	private Date banExpiry;
	private int wrongTags;
	private List<Discount> discounts = new ArrayList<Discount>();
	
	public Member(String username, String password, Long id) {
		super(username, password, id);
		
		joinDate=new Date();
		penalty=new Penalty();
		loan=new BookLoan();
		cathegory=cathegory.NONE;
		banned=false;
		
	}
	
	public Member(String username, String password, Long id, Date joinDate, Date expiryDate, Penalty penalty,
			BookLoan loan, List<BookLoan> history, sbnz.integracija.example.facts.Member.cathegory cathegory,
			boolean banned) {
		super(username, password, id);
		this.joinDate = joinDate;
		this.expiryDate = expiryDate;
		this.penalty = penalty;
		this.loan = loan;
		this.history = history;
		this.cathegory = cathegory;
		this.banned = banned;
	}
	public Date getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Penalty getPenalty() {
		return penalty;
	}
	public void setPenalty(Penalty penalty) {
		this.penalty = penalty;
	}
	public BookLoan getLoan() {
		return loan;
	}
	public void setLoan(BookLoan loan) {
		this.loan = loan;
	}
	public List<BookLoan> getHistory() {
		return history;
	}
	public void setHistory(List<BookLoan> history) {
		this.history = history;
	}
	public cathegory getCathegory() {
		return cathegory;
	}
	public void setCathegory(cathegory cathegory) {
		this.cathegory = cathegory;
	}
	public boolean isBanned() {
		return banned;
	}
	public void setBanned(boolean banned) {
		this.banned = banned;
	}
	
	

}
