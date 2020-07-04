package sbnz.integracija.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sbnz.integracija.example.facts.Member;
import sbnz.integracija.example.facts.User;

public interface MemberRepository extends JpaRepository<Member,Long> {
	
	Optional<Member> findById(Long id);
	Member save(Member member);
	Member findByUsername(String username);
}