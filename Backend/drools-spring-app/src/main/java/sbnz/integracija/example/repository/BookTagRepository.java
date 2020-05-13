package sbnz.integracija.example.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sbnz.integracija.example.facts.BookTag;

public interface BookTagRepository  extends JpaRepository<BookTag, Long>{
	Optional<BookTag> findById(Long id);
	BookTag save(BookTag bookTag);
	@Query("SELECT tag FROM BookTag tag WHERE ?1 = tag.bookId")
	ArrayList<BookTag> findTagsByBookId(Long bookId);

}
