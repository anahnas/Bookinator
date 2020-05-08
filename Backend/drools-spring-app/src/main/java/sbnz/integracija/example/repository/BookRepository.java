package sbnz.integracija.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sbnz.integracija.example.facts.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAll();
    void deleteById(Long id);
    Book save(Book book);
    
    @Query("SELECT coalesce(max(b.id), 0) FROM Book b")
    Long getMaxId();

}