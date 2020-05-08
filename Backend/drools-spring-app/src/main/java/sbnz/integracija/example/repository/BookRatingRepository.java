package sbnz.integracija.example.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sbnz.integracija.example.facts.BookRating;

public interface BookRatingRepository extends JpaRepository<BookRating,Long>{

	BookRating findByBookIdAndUserId(Long bookId,Long userId);
	
	List<BookRating> findByBookId(Long bookId);
	
}
