package sbnz.integracija.example.facts;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "employee_pkey")
public class Employee extends User implements Serializable{

}
