package sbnz.integracija.example.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sbnz.integracija.example.facts.BookLoan;
import sbnz.integracija.example.facts.BookRating;

public interface BookLoanRepository extends JpaRepository<BookLoan, Long>{
	Optional<BookLoan> findById(Long id);
	List<BookLoan> findAll();
	@Query("SELECT bookLoan FROM BookLoan bookLoan WHERE ?1 = bookLoan.book.id")
	BookLoan findByBookId(Long bookId);
}
