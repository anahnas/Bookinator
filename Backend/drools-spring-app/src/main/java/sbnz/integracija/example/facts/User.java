package sbnz.integracija.example.facts;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import sbnz.integracija.example.facts.enumeration.RoleEnum;

@Entity
@Inheritance(
	    strategy = InheritanceType.JOINED
	)
@Table(name="user_table")
public class User implements Serializable{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String username;
	@Column
	private String password;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private String email;
	@Column(name = "user_type", unique = false)
	private RoleEnum userType;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	public RoleEnum getUserType() {
		return userType;
	}
	public void setUserType(RoleEnum userType) {
		this.userType = userType;
	}
	public User(Long id, String username, String password, String firstName, String lastName, String email, RoleEnum userType) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userType = userType;
	}
	public User(String username, String password, Long id) {
		super();
		this.username = username;
		this.password = password;
		this.id = id;
	}
	public User() {
		super();
	}
	
	
}
