package sbnz.integracija.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sbnz.integracija.example.facts.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	List<Book> findAll();

	void deleteById(Long id);

	Book save(Book book);
	
	Optional<Book> findById(Long id);
	
	@Query(nativeQuery = true, value="select * from Book order by random() limit 1")
	Book random();
	
	@Query(nativeQuery = true, value="select * from Book order by random() limit 1")
	Book random1();
	
	@Query(nativeQuery = true, value="select * from Book order by random() limit 2")
	Book random2();
	
	@Query(nativeQuery = true, value="select * from Book order by random() limit 3")
	Book random3();
}