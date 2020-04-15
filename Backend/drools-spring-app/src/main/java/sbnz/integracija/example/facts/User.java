package sbnz.integracija.example.facts;

import java.io.Serializable;

public class User implements Serializable{

	private String username;
	private String password;
	private Long id;
	
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
