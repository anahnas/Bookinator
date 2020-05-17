package sbnz.integracija.example.facts;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="employee_table")
@PrimaryKeyJoinColumn(name = "employee_pkey")

public class Employee extends User implements Serializable{
	
	public Employee() {}

}
