package sbnz.integracija.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import org.drools.core.ClockType;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import DTO.BookDTO;
import enumeration.RoleEnum;
import events.MembershipExpiredEvent;
import events.TransactionEvent;
import DTO.BookTagDTO;
import sbnz.integracija.example.facts.Book;
import sbnz.integracija.example.facts.BookRating;
import sbnz.integracija.example.facts.BookTag;
import sbnz.integracija.example.facts.BookTagStatus;
import sbnz.integracija.example.facts.Member;
import sbnz.integracija.example.facts.ReviewRequest;
import sbnz.integracija.example.facts.SearchRequest;
import sbnz.integracija.example.facts.Tag;
import sbnz.integracija.example.facts.User;
import sbnz.integracija.example.repository.BookRatingRepository;
import sbnz.integracija.example.repository.BookRepository;
import sbnz.integracija.example.repository.BookTagRepository;
import sbnz.integracija.example.repository.MemberRepository;
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
	MemberRepository memberRepo; 
	
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
		user.setUserType(RoleEnum.MEMBER);
		Member member = new Member(user);
		//return this.userRepo.save(user);
		return this.memberRepo.save(member);
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
 	        	 tag.setApproved(false); 
	        }
	       
	        // this.bookTagRepository.save(new BookTag(reviewRequest.getBookId(), tag.getId(), pair.getValue().toString()));
	        this.bookTagRepository.save(new BookTag(reviewRequest.getBookId(), tag.getId(), pair.getValue().toString(), BookTagStatus.PENDING));
	        
	        it.remove(); // avoids a ConcurrentModificationException

	    }
		
		User user =  userRepo.findById(reviewRequest.getUserId()).get();
		BookRating rating=new BookRating(book,user,reviewRequest.getRate());
		this.ratingRepo.save(rating);
		
		List<BookRating> allRatings=ratingRepo.findByBookId(book.getId());
		float avg = 0;
		int count = allRatings.size();
		for (BookRating r:allRatings) {
			avg+=r.getRating();
		}
		float newRating = avg/count;
		book.setRating(newRating);
		
	    this.bookRepository.save(book);
	    System.out.println("Book updated!");
	}
	
	
	public void approveTag(Long id) {
		BookTag bookTag = bookTagRepository.getOne(id);
		bookTag.setStatus(BookTagStatus.APPROVED);
		this.bookTagRepository.save(bookTag);
	}
	
	public void approveJustTag(String name) {
		Tag tag = tagRepo.findByTagName(name);
		tag.setApproved(true);
		this.tagRepo.save(tag);
		// bookTagRepository.setConfirmed(tag.getTagValue());
	}
	

	public void deleteTag(Long id) {
		BookTag bookTag = bookTagRepository.getOne(id);
		bookTag.setStatus(BookTagStatus.REFUSED);
		this.bookTagRepository.delete(bookTag);
	}
	
	public void deleteJustTag(String name) {
		Tag tag = tagRepo.findByTagName(name);
		tag.setApproved(false);
		this.tagRepo.delete(tag);
	}

	public List<BookTag> findAllTags() {
		return bookTagRepository.findAll();
	}

	public List<BookTag> findRequestedTags() {
		return bookTagRepository.findRequestedTags();
	}

	public List<Tag> findTags() {
		return tagRepo.findTags();
	}
	
	
	
	public void payMembership(Long id) {
		Member member = memberRepo.getOne(id);
		member.setMembershipExpired(false);
		memberRepo.save(member);
		//start the clock again
		startMembershipCheck(id);
	}
	
	
	public void startMembershipCheck(Long uId) {
		System.out.println("Initializing membership check rule...............................");
		
		KieServices ks = KieServices.Factory.get();
		KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
        kbconf.setOption(EventProcessingOption.STREAM);		
		KieBase kbase = kieContainer.newKieBase(kbconf);
		
		KieSessionConfiguration ksconf1 = ks.newKieSessionConfiguration();
	    ksconf1.setOption(ClockTypeOption.get(ClockType.REALTIME_CLOCK.getId()));
	    KieSession kSession1 = kbase.newKieSession(ksconf1, null);
	        
	    // runRealtimeClock(kSession1, uId);
	   
	}

	private void runRealtimeClock(KieSession kSession1, Long uId) {
        Thread t = new Thread() {
            @Override
            public void run() {
            	TransactionEvent t1 = new TransactionEvent(uId);
            	kSession1.insert(t1);
            	
                kSession1.fireUntilHalt();

                Collection<?> newEvents = kSession1.getObjects(new ClassObjectFilter(MembershipExpiredEvent.class));
        	    for(Object o : newEvents) {
        	    	if(o instanceof MembershipExpiredEvent) {
        	    		MembershipExpiredEvent m = (MembershipExpiredEvent) o;
        	    		Member member = memberRepo.getOne(m.getUserId());
        	    		member.setMembershipExpired(true);
        	    		memberRepo.save(member);
        	    		
        	    	}
        	    		
        	    }
            }
        };
        t.setDaemon(true);
        t.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            //do nothing
        }
    }


	
	
}
