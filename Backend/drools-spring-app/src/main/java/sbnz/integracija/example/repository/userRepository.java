package sbnz.integracija.example.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sbnz.integracija.example.facts.BookTag;
import sbnz.integracija.example.facts.User;

public interface userRepository extends JpaRepository<User,Long> {
	
	Optional<User> findById(Long id);
	@Query("SELECT user FROM User user WHERE ?1 = user.username")
	User findByUsername(String username);
	User save(User user);
	List<User> findAll();
}
