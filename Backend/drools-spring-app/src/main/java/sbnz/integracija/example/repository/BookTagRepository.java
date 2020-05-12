package sbnz.integracija.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sbnz.integracija.example.facts.Book;
import sbnz.integracija.example.facts.BookTag;

public interface BookTagRepository  extends JpaRepository<BookTag, Long>{
	Optional<BookTag> findById(Long id);
	BookTag save(BookTag bookTag);

}
