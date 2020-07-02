package sbnz.integracija.example.facts;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Penalty implements Serializable{
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column
	private Double amount;
	
	public Penalty() {
		this.amount = 0.0;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	
}
