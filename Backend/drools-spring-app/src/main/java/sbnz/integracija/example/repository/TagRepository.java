package sbnz.integracija.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sbnz.integracija.example.facts.Tag;

public interface TagRepository extends JpaRepository<Tag,Long>{

	Tag findByName(String name);
}
