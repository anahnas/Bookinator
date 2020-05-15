package sbnz.integracija.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.xmlbeans.impl.xb.xsdschema.Attribute.Use;
import org.kie.api.runtime.KieContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import DTO.BookDTO;
import sbnz.integracija.example.facts.Book;
import sbnz.integracija.example.facts.BookTag;
import sbnz.integracija.example.facts.BookTagStatus;
import sbnz.integracija.example.facts.ReviewRequest;
import sbnz.integracija.example.facts.SearchRequest;
import sbnz.integracija.example.facts.Tag;
import sbnz.integracija.example.facts.User;
import sbnz.integracija.example.repository.BookRatingRepository;
import sbnz.integracija.example.repository.BookRepository;
import sbnz.integracija.example.repository.BookTagRepository;
import sbnz.integracija.example.repository.TagRepository;
import sbnz.integracija.example.repository.userRepository;

@Service
public class SampleAppService {

	private static Logger log = LoggerFactory.getLogger(SampleAppService.class);
	
	@Autowired
	private final BookRepository bookRepository;
	
	@Autowired
	private final BookTagRepository bookTagRepository;
	
	@Autowired
	BookRatingRepository ratingRepo;
	
	@Autowired
	userRepository userRepo;
	
	@Autowired 
	TagRepository tagRepo;

	private final KieContainer kieContainer;
	
	@Autowired
	public SampleAppService(KieContainer kieContainer, BookRepository bookRepository, BookTagRepository bookTagRepository) {
		log.info("Initialising a new example session.");
		this.kieContainer = kieContainer;
		this.bookRepository = bookRepository;
		this.bookTagRepository = bookTagRepository;
		
	}

//	public Item getClassifiedItem(Item i) {
//		KieSession kieSession = kieContainer.newKieSession();
//		kieSession.insert(i);
//		kieSession.fireAllRules();
//		kieSession.dispose();
//		return i;
//	}
	public User login(User user) {
		User u = this.userRepo.findByUsername(user.getUsername());
		if(u != null) {
			if(u.getPassword().equals(user.getPassword())) {
				return u;
			}
		}
		return null;
	}

	public User register(User user) {
		User u = this.userRepo.findByUsername(user.getUsername());
		if(u != null) {
			return null;
		} 
		return this.userRepo.save(user);
	}
	
	public List<User> findAll() {
		
		return userRepo.findAll();
	}
	
	public User saveUser(User user) {
		User u = this.userRepo.findByUsername(user.getUsername());
		if(u != null) {
			return null;
		} 
		return this.userRepo.save(user);
	}
	
	public ArrayList<BookDTO> getFilteredBooks(SearchRequest searchRequest) {
		ArrayList<Book> books = (ArrayList<Book>) bookRepository.findAll();
		ArrayList<BookDTO> bookDTOs = new ArrayList<>();
		for(Book b : books) {
			BookDTO bookDTO = new BookDTO(b);
			//bookDTO.setTags();
			ArrayList<BookTag> bookTags = this.bookTagRepository.findTagsByBookId(b.getId());
			for(BookTag bt : bookTags) {
				bookDTO.getTags().add(bt);
			}
			
			bookDTOs.add(bookDTO);	
		}		
		
//		KieSession kieSession = kieContainer.newKieSession();
//		kieSession.insert(searchRequest);
//		kieSession.fireAllRules();
//		kieSession.dispose();
		
		return bookDTOs;
	}
	

	public void bookReview(ReviewRequest reviewRequest) {
		System.out.println(reviewRequest.toString());
		Book book = bookRepository.getOne(reviewRequest.getBookId());
		
		Iterator it = reviewRequest.getTags().entrySet().iterator();
	    while (it.hasNext()) {
	        HashMap.Entry pair = (HashMap.Entry)it.next();
	        //book.getTags().put(pair.getKey().toString(), pair.getValue().toString());
	        Tag tag = this.tagRepo.findByTagName(pair.getKey().toString());
	        if (tag==null) {
	        	tagRepo.save(new Tag(pair.getKey().toString()));
	        	 tag = this.tagRepo.findByTagName(pair.getKey().toString());
	        }
	        
	        this.bookTagRepository.save(new BookTag(reviewRequest.getBookId(), tag.getId(), pair.getValue().toString()));
	        it.remove(); // avoids a ConcurrentModificationException

	    }
		
		User user =  userRepo.findById(reviewRequest.getUserId()).get();
		/*BookRating rating=new BookRating(book,user,reviewRequest.getRate());
		this.ratingRepo.save(rating);
		
		List<BookRating> allRatings=ratingRepo.findByBookId(book.getId());
		float avg = 0;
		int count = allRatings.size();
		for (BookRating r:allRatings) {
			avg+=r.getRating();
		}
		float newRating = avg/count;
		book.setRating(newRating);
		*/
	    this.bookRepository.save(book);
	    System.out.println("Book updated!");
	}
	
	public void approveTag(Long id) {
		BookTag bookTag = bookTagRepository.getOne(id);
		bookTag.setStatus(BookTagStatus.APPROVED);
		this.bookTagRepository.save(bookTag);
	}
	

	public void deleteTag(Long id) {
		BookTag bookTag = bookTagRepository.getOne(id);
		bookTag.setStatus(BookTagStatus.REFUSED);
		this.bookTagRepository.delete(bookTag);
	}
	
	
}
