package sbnz.integracija.example.facts;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "member_pkey")
public class Member extends User implements Serializable{
	
	enum cathegory{NONE,SILVER,GOLD,PLATINUM};
	
	@Column
	private Date joinDate;
	@Column
	private boolean membershipExpired;
	@Column
	private Penalty penalty;
	@Column
	private BookLoan loan;
	 @OneToMany()
	private Set<BookLoan> history;
	@Column
	private cathegory cathegory;
	@Column
	private boolean banned;
	@Column
	private Date banExpiry;
	@Column
	private int wrongTags;

	 @OneToMany()
	private Set<Discount> discounts ;
	
	public Member(String username, String password, Long id) {
		super(username, password, id);
		
		joinDate=new Date();
		penalty=new Penalty();
		loan=new BookLoan();
		cathegory=cathegory.NONE;
		banned=false;
		
	}
	
	public Member(String username, String password, Long id, Date joinDate, Date expiryDate, Penalty penalty,
			BookLoan loan, Set<BookLoan> history, sbnz.integracija.example.facts.Member.cathegory cathegory,
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
	public Set<BookLoan> getHistory() {
		return history;
	}
	public void setHistory(Set<BookLoan> history) {
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
