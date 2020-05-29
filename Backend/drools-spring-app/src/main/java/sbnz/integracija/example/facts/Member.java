package sbnz.integracija.example.facts;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import org.springframework.transaction.annotation.Transactional;

import enumeration.RoleEnum;

@Entity
@PrimaryKeyJoinColumn(name = "member_pkey")
public class Member extends User implements Serializable{
	
	enum cathegory{NONE,SILVER,GOLD,PLATINUM};
	
	@Column
	private Date joinDate;
	@Column
	private boolean membershipExpired = false;
	@Column
	private Penalty penalty;
	@Column
	private BookLoan loan;
	 @OneToMany()
	private Set<BookLoan> history;
	@Column
	private cathegory cathegory;
	@Column
	private boolean banned = false;	
	@Column
	private boolean canRent = true;
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
	
	public Member(String username, String password, String firstName, String lastName, String email,
			RoleEnum userType) {
		super(username, password, firstName, lastName, email, userType);
	}

	public Member(User u) {
		super(u.getUsername(), u.getPassword(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getUserType());
	}
	
	public Member() {
		super();
	}
	
	
	public Member(String username, String password, Long id, Date joinDate, Penalty penalty,
			BookLoan loan, Set<BookLoan> history, sbnz.integracija.example.facts.Member.cathegory cathegory,
			boolean banned) {
		super(username, password, id);
		this.joinDate = joinDate;
		this.penalty = penalty;
		this.loan = loan;
		this.history = history;
		this.cathegory = cathegory;
		this.banned = banned;
	}
	
	public Member(String username, String password, String firstName, String lastName, String email, RoleEnum userType,
			Date joinDate, boolean membershipExpired, Penalty penalty, BookLoan loan, Set<BookLoan> history,
			sbnz.integracija.example.facts.Member.cathegory cathegory, boolean banned, Date banExpiry, int wrongTags,
			Set<Discount> discounts) {
		super(username, password, firstName, lastName, email, userType);
		this.joinDate = joinDate;
		this.membershipExpired = membershipExpired;
		this.penalty = penalty;
		this.loan = loan;
		this.history = history;
		this.cathegory = cathegory;
		this.banned = banned;
		this.banExpiry = banExpiry;
		this.wrongTags = wrongTags;
		this.discounts = discounts;
	}

	public Date getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
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

	public boolean isMembershipExpired() {
		return membershipExpired;
	}

	public void setMembershipExpired(boolean membershipExpired) {
		this.membershipExpired = membershipExpired;
	}

	public Date getBanExpiry() {
		return banExpiry;
	}

	public void setBanExpiry(Date banExpiry) {
		this.banExpiry = banExpiry;
	}

	public int getWrongTags() {
		return wrongTags;
	}

	public void setWrongTags(int wrongTags) {
		this.wrongTags = wrongTags;
	}

	public Set<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(Set<Discount> discounts) {
		this.discounts = discounts;
	}

	public boolean isCanRent() {
		return canRent;
	}

	public void setCanRent(boolean canRent) {
		this.canRent = canRent;
	}
	

}
