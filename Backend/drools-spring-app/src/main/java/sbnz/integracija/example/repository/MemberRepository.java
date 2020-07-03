package sbnz.integracija.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sbnz.integracija.example.facts.Member;

public interface MemberRepository extends JpaRepository<Member,Long> {
	
	Optional<Member> findById(Long id);
	Member save(Member member);
	List<Member> findAll();

}