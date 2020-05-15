package sbnz.integracija.example.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sbnz.integracija.example.facts.Employee;
import sbnz.integracija.example.facts.User;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	

	Employee findByUsername(String username);
	
	@Query(value = "SELECT * from User WHERE u.userType = 0", nativeQuery = true)
	Set<User> findMembers();
}
