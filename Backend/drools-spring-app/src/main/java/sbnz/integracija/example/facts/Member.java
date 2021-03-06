package sbnz.integracija.example.facts;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import org.springframework.transaction.annotation.Transactional;

import enumeration.RoleEnum;

@Entity
@PrimaryKeyJoinColumn(name = "member_pkey")
public class Member extends User implements Serializable{
	
	public enum cathegory{NA,BRONZE,SILVER,GOLD};
	
	@Column
	private Date joinDate;
	@Column
	private boolean membershipExpired = false;
	@Column
	private Penalty penalty;
	@Column
	private BookLoan loan;
	@OneToMany
	private Set<BookRent> history = new HashSet<BookRent>();
	@ManyToMany
	private Set<Book> wishlist = new HashSet<>();
	@Column
	private cathegory cathegory;
	@Column
	private boolean banned = false;	
	@Column
	private boolean canRent = true;
	@Column
	private Date banExpiry;
	@Column(nullable=true, name="rented")
	private Integer rented;
	private double discount;
	private double membership;
	
	/*@Column
	private double frequency;*/
	@OneToMany
	private Set<Tag> wrongTags;

	 @OneToMany()
	private Set<Discount> discounts ;
	
	
	public Member(String username, String password, Long id) {
		super(username, password, id);
		
		joinDate=new Date();
		penalty=new Penalty();
		loan=new BookLoan();
		cathegory=cathegory.NA;
		banned=false;
		
	}
	
	public Member(Integer i) {
		this.rented = i;
	}
	
	public Member(String username, String password, String firstName, String lastName, String email,
			RoleEnum userType) {
		super(username, password, firstName, lastName, email, userType);
	}

	public Member(User u) {
		super(u.getUsername(), u.getPassword(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getUserType());
	}
	
	public Member(Member u) {
		super(u.getUsername(), u.getPassword(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getUserType());
	}
	
	public Member() {
		super();
	}
	
	
	public Member(String username, String password, Long id, Date joinDate, Penalty penalty,
			BookLoan loan, Set<BookRent> history, sbnz.integracija.example.facts.Member.cathegory cathegory,
			boolean banned, Integer rented) {
		super(username, password, id);
		this.joinDate = joinDate;
		this.penalty = penalty;
		this.loan = loan;
		this.history = history;
		this.cathegory = cathegory;
		this.banned = banned;
		this.rented = rented;
	}
	
	public Member(String username, String password, String firstName, String lastName, String email, RoleEnum userType,
			Date joinDate, boolean membershipExpired, Penalty penalty, BookLoan loan, Set<BookRent> history,
			sbnz.integracija.example.facts.Member.cathegory cathegory, boolean banned, Date banExpiry,  Set<Tag> wrongTags,
			Set<Discount> discounts, Set<Book> wishlist, Integer rented) {
				
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
			this.wishlist = wishlist;
			this.rented = rented;
	}

	
	/*public double getFrequency() {
		return frequency;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}*/

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
	public Set<BookRent> getHistory() {
		return history;
	}
	public void setHistory(Set<BookRent> history) {
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

	public Set<Tag> getWrongTags() {
		return wrongTags;
	}

	public void setWrongTags(Set<Tag> wrongTags) {
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

	public Set<Book> getWishlist() {
		return wishlist;
	}

	public void setWishlist(Set<Book> wishlist) {
		this.wishlist = wishlist;
	}
	
	

	public Integer getRented() {
		return rented;
	}

	public void setRented(Integer rented) {
		this.rented = rented;
	}

	@Override
	public String toString() {
		return "Member [username=" + this.getUsername()+"]";
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getMembership() {
		return membership;
	}

	public void setMembership(double membership) {
		this.membership = membership;
	}
	
	
	
	
	
}
