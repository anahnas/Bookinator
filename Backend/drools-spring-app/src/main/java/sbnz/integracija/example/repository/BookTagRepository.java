package sbnz.integracija.example.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sbnz.integracija.example.facts.BookTag;

public interface BookTagRepository  extends JpaRepository<BookTag, Long>{
	Optional<BookTag> findById(Long id);
	BookTag save(BookTag bookTag);
	@Query("SELECT tag FROM BookTag tag WHERE ?1 = tag.bookId")
	ArrayList<BookTag> findTagsByBookId(Long bookId);
	
	void delete(BookTag entity);
	
 	BookTag findByTagKey(String tagKey); 
	
	List<BookTag> findAll();

	@Query(value = "SELECT bt FROM BookTag bt WHERE bt.book_status = 1", nativeQuery = true)
	List<BookTag> findRequestedTags();

	/*@Modifying
	@Query(value = "UPDATE BookTag bt SET bt.book_status = APPROVED WHERE bt.tag_value = :tagValue", nativeQuery = true)
	    void setConfirmed(@Param("tagValue") String tagValue);*/
}
