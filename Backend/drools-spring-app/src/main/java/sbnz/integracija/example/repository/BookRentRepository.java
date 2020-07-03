package sbnz.integracija.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import sbnz.integracija.example.facts.BookRent;

public interface BookRentRepository extends JpaRepository<BookRent, Long> {
	
	Optional<BookRent> findById(Long id);
	BookRent findByUser(Long userId);
	
	BookRent findByBook(Long bookId);
    List<BookRent> findAll();

    BookRent save(BookRent bookRent);

    @Modifying
    void deleteById(long id);

}
