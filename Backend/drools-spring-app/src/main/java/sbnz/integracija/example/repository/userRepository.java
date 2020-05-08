package sbnz.integracija.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sbnz.integracija.example.facts.User;

public interface userRepository extends JpaRepository<User,Long> {
	
	Optional<User> findById(Long id);

}
