package sbnz.integracija.example.facts;

import java.io.Serializable;

import javax.persistence.Entity;
<<<<<<< HEAD
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "employee_pkey")
=======
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="employee_table")
@PrimaryKeyJoinColumn(name = "employee_pkey")

>>>>>>> 834cbbceba6674f9d215dd9c9bfa0f825f5dabb8
public class Employee extends User implements Serializable{
	
	public Employee() {}

}
