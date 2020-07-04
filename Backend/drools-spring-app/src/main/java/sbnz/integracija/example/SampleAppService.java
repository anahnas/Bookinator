package sbnz.integracija.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.drools.core.ClockType;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.internal.command.CommandFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import DTO.BookDTO;
import DTO.BookRecommendDTO;
import DTO.MemberlistDTO;
import DTO.RecommendDTO;
import DTO.TagListDTO;
import DTO.UserDTO;
import enumeration.RoleEnum;
import events.BookLoanExpiredEvent;
import events.BookLoanMade;
import events.MembershipExpiredEvent;
import events.PenaltyEvent;
import events.TransactionEvent;
import sbnz.integracija.example.facts.Book;
import sbnz.integracija.example.facts.BookLoan;
import sbnz.integracija.example.facts.BookRating;
import sbnz.integracija.example.facts.BookTag;
import sbnz.integracija.example.facts.BookTagStatus;
import sbnz.integracija.example.facts.Member;
import sbnz.integracija.example.facts.Penalty;
import sbnz.integracija.example.facts.ReviewRequest;
import sbnz.integracija.example.facts.SearchRequestDTO;
import sbnz.integracija.example.facts.Tag;
import sbnz.integracija.example.facts.User;
import sbnz.integracija.example.repository.BookLoanRepository;
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
	BookRepository bookRepository;

	@Autowired
	BookTagRepository bookTagRepository;

	@Autowired
	BookRatingRepository ratingRepo;

	@Autowired
	BookLoanRepository bookLoanRepository;

	@Autowired
	userRepository userRepo;

	@Autowired
	MemberRepository memberRepo;

	@Autowired
	TagRepository tagRepo;

	@Autowired
	KieContainer kieContainer;

	private String CSV_FILE_NAME = "C:\\data.csv";

	public SampleAppService() {
		log.info("Initialising a new example session.");
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
		if (u != null) {
			if (u.getPassword().equals(user.getPassword())) {
				return u;
			}
		}
		System.out.println("nema");
		return null;
	}

	public User register(User user) {
		User u = this.userRepo.findByUsername(user.getUsername());
		if (u != null) {
			return null;
		}
		user.setUserType(RoleEnum.MEMBER);
		Member member = new Member(user);

		this.memberRepo.save(member);
		payMembership(member.getId());
		return this.memberRepo.getOne(member.getId());
	}

	public void payMembership(Long id) {
		Member member = memberRepo.getOne(id);
		member.setMembershipExpired(false);
		memberRepo.save(member);
		// start the clock again
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

		runRealtimeClock(kSession1, uId);

	}

	private void runRealtimeClock(KieSession kSession1, Long uId) {
		Thread t = new Thread() {
			@Override
			public void run() {
				TransactionEvent t1 = new TransactionEvent(uId);
				kSession1.insert(t1);

				kSession1.fireUntilHalt();

				Collection<?> newEvents = kSession1.getObjects(new ClassObjectFilter(MembershipExpiredEvent.class));
				for (Object o : newEvents) {
					if (o instanceof MembershipExpiredEvent) {
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
			// do nothing
		}
	}

	public List<User> findAll() {

		return userRepo.findAll();
	}

	public User saveUser(User user) {
		User u = this.userRepo.findByUsername(user.getUsername());
		if (u != null) {
			return null;
		}
		return this.userRepo.save(user);
	}

	public ArrayList<BookDTO> getFilteredBooks(SearchRequestDTO searchRequestDTO) {
		ArrayList<Book> books = startSearch(searchRequestDTO);
		ArrayList<BookDTO> bookDTOs = new ArrayList<>();

		Collections.sort(books, Collections.reverseOrder());

		for (Book b : books) {
			System.out.println("book - " + b.toString() + " - " + b.getMatch());
			BookDTO bookDTO = new BookDTO(b);
			ArrayList<BookTag> bookTags = this.bookTagRepository.findTagsByBookId(b.getId());
			for (BookTag bt : bookTags) {
				bookDTO.getTags().add(bt);
			}

			bookDTOs.add(bookDTO);
		}

		return bookDTOs;
	}

	public ArrayList<BookDTO> getBookHistory(Long uId) {
		ArrayList<BookLoan> bookLoans = this.bookLoanRepository.findByUserId(uId);
		ArrayList<BookDTO> bookDTOs = new ArrayList<>();
		// Collections.sort(books, Collections.reverseOrder());

		for (BookLoan bookLoan : bookLoans) {
			System.out.println(bookLoan.getBookId());
			BookDTO bookDTO = new BookDTO(bookRepository.getOne(bookLoan.getBookId()));
			ArrayList<BookTag> bookTags = this.bookTagRepository.findTagsByBookId(bookLoan.getBookId());
			for (BookTag bt : bookTags) {
				bookDTO.getTags().add(bt);
			}

			bookDTOs.add(bookDTO);
		}

		return bookDTOs;
	}

	public ArrayList<Book> startSearch(SearchRequestDTO searchRequestDTO) {
		System.out.println("Initializing virtual assisant...............................");

		KieServices ks = KieServices.Factory.get();
		KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
		kbconf.setOption(EventProcessingOption.STREAM);
		KieBase kbase = kieContainer.newKieBase(kbconf);

		KieSession kSession = kbase.newKieSession();

		List<Book> books = bookRepository.findAll();
		for (Book book : books) {
			book.setMatch((long) 0);
			kSession.getEntryPoint("search").insert(book);
		}

		List<Tag> tags = tagRepo.findAll();
		for (Tag tag : tags) {
			kSession.getEntryPoint("search").insert(tag);
		}

		List<BookTag> bookTags = bookTagRepository.findAll();
		for (BookTag bookTag : bookTags) {
			kSession.getEntryPoint("search").insert(bookTag);
		}
		
		for (Object o : searchRequestDTO.getSearchCriteria().values()) {
			System.out.println(o.getClass());
		}

		kSession.getEntryPoint("search").insert(searchRequestDTO);
		kSession.getAgenda().getAgendaGroup("startSearch").setFocus();

		kSession.fireAllRules();
		List<Book> searchResults = new ArrayList<>();
		QueryResults results = kSession.getQueryResults("getSearchResults");
		for (QueryResultsRow row : results) {
			Book b = (Book) row.get("$result");
			searchResults.add(b);
		}

		System.out.println("rezultati:");
		for (Book b : searchResults)
			System.out.println(b.getMatch() + " : " + b.toString());

		kSession.dispose();
		return (ArrayList<Book>) searchResults;
	}

	public void bookReview(ReviewRequest reviewRequest) {
		System.out.println(reviewRequest.toString());
		Book book = bookRepository.getOne(reviewRequest.getBookId());

		Iterator it = reviewRequest.getTags().entrySet().iterator();
		while (it.hasNext()) {
			HashMap.Entry pair = (HashMap.Entry) it.next();
			// book.getTags().put(pair.getKey().toString(), pair.getValue().toString());
			Tag tag = this.tagRepo.findByTagName(pair.getKey().toString());

			if (tag == null) {
				tagRepo.save(new Tag(pair.getKey().toString()));
				tag = this.tagRepo.findByTagName(pair.getKey().toString());
				tag.setApproved(false);
			}

			this.bookTagRepository.save(new BookTag(reviewRequest.getBookId(), tag.getId(), pair.getValue().toString(),
					BookTagStatus.PENDING));

			it.remove(); // avoids a ConcurrentModificationException

		}

		User user = userRepo.findById(reviewRequest.getUserId()).get();
		BookRating rating = new BookRating(book, user, reviewRequest.getRate());
		this.ratingRepo.save(rating);

		List<BookRating> allRatings = ratingRepo.findByBookId(book.getId());
		float avg = 0;
		int count = allRatings.size();
		for (BookRating r : allRatings) {
			avg += r.getRating();
		}
		float newRating = avg / count;
		book.setRating(newRating);

		this.bookRepository.save(book);
		System.out.println("Book updated!");
	}

	public boolean makeBookLoan(Long userId, Long bookId) {
	
		Member member = this.memberRepo.getOne(userId);
		if(!member.isCanRent())
			return false;
		Book book = this.bookRepository.getOne(bookId);
		book.setAvaivableNo(book.getAvaivableNo()-1);

		BookLoan bookLoan = new BookLoan();
		bookLoan.setReturned(false);
		bookLoan.setBookId(book.getId());
		bookLoan.setUserId(userId);
		bookLoanRepository.save(bookLoan);
		member.setLoan(bookLoan);
		member.setCanRent(false);
		memberRepo.save(member);
		
		bookRepository.save(book);
		
		System.out.println("Initializing book loan rule...............................");

		KieServices ks = KieServices.Factory.get();
		KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
		kbconf.setOption(EventProcessingOption.STREAM);
		KieBase kbase = kieContainer.newKieBase(kbconf);

		KieSessionConfiguration ksconf1 = ks.newKieSessionConfiguration();
		ksconf1.setOption(ClockTypeOption.get(ClockType.REALTIME_CLOCK.getId()));
		KieSession kSession1 = kbase.newKieSession(ksconf1, null);

		Thread t = new Thread() {
			@Override
			public void run() {
				BookLoanMade e1 = new BookLoanMade(member.getId(), bookLoan.getId());
				kSession1.insert(e1);

				kSession1.fireUntilHalt();

				Collection<?> newEvents = kSession1.getObjects(new ClassObjectFilter(BookLoanExpiredEvent.class));
				for (Object o : newEvents) {
					if (o instanceof BookLoanExpiredEvent) {

						BookLoanExpiredEvent b = (BookLoanExpiredEvent) o;

						System.out.println("book loan - " + b.getBookLoanId());
						
						BookLoan bl = bookLoanRepository.getOne(b.getBookLoanId());
						bl.setExpired(true);
						bookLoanRepository.save(bl);
						Member m = memberRepo.getOne(b.getUserId());
						m.setCanRent(false);
						runPenaltyClock(m.getId(), bl.getId());
					}

				}
			}
		};
		t.setDaemon(true);
		t.start();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// do nothing
		}
		return true;
	}

	public void runPenaltyClock(Long uId, Long blId) {
		System.out.println("Initializing penalty rule...............................");

		KieServices ks = KieServices.Factory.get();
		KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
		kbconf.setOption(EventProcessingOption.STREAM);
		KieBase kbase = kieContainer.newKieBase(kbconf);

		KieSessionConfiguration ksconf1 = ks.newKieSessionConfiguration();
		ksconf1.setOption(ClockTypeOption.get(ClockType.REALTIME_CLOCK.getId()));
		KieSession kSession1 = kbase.newKieSession(ksconf1, null);

		Thread t = new Thread() {
			@Override
			public void run() {
				PenaltyEvent penaltyEvent = new PenaltyEvent(uId, blId);
				kSession1.insert(penaltyEvent);
				BookLoan bl = bookLoanRepository.getOne(blId);
				Member m = memberRepo.getOne(uId);
				Penalty penalty = m.getPenalty();
				if(penalty == null) {
					penalty = new Penalty();
				}
				while(isBookLoanExpired(blId)) {
					kSession1.fireUntilHalt();
					
					Collection<?> newEvents = kSession1.getObjects(new ClassObjectFilter(PenaltyEvent.class));
					for (Object o : newEvents) {
						if (o instanceof PenaltyEvent) {
							// PenaltyEvent p = (PenaltyEvent) o;

							bl.setExpired(true);
							bookLoanRepository.save(bl);

							m.setCanRent(false);
							penalty.setAmount(penalty.getAmount() + 10);
							m.setPenalty(penalty);
							memberRepo.save(m);
						}

					}
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// do nothing
					}
					System.out.println("Penalty for member " + m.getUsername() + " is " + penalty.getAmount());
				}
			}
		};
		t.setDaemon(true);
		t.start();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// do nothing
		}
	}

	public boolean isBookLoanExpired(Long blId) {
		return this.bookLoanRepository.getOne(blId).isExpired();
	}

	public void returnBookLoan(Long id) {
		BookLoan bookLoan = this.bookLoanRepository.getOne(id);
		Book book = this.bookRepository.getOne(bookLoan.getBookId());
		book.setAvaivableNo(book.getAvaivableNo()+1);
		bookRepository.save(book);
		Member member = this.memberRepo.getOne(bookLoan.getUserId());
		System.out.println(member.getUsername() + " returned book with id: " + bookLoan.getBookId());
		member.setLoan(null);
		bookLoan.setReturned(true);
		bookLoan.setExpired(false);
		bookLoanRepository.save(bookLoan);
		member.setCanRent(true);
		memberRepo.save(member);
	}

	public ArrayList<BookLoan> getBookLoans(Long uId) {
		return bookLoanRepository.findByUserId(uId);
	}

	public BookDTO getBookLoan(Long uId) {
		try {
			Member m = this.memberRepo.getOne(uId);
			if (m.getLoan() == null) {
				return null;
			} else {
				System.out.println(m.getUsername() + " has loan - ");
				BookLoan bookLoan = this.bookLoanRepository.getOne(m.getLoan().getId());
				BookDTO bookDTO = new BookDTO(bookRepository.getOne(bookLoan.getBookId()));
				ArrayList<BookTag> bookTags = this.bookTagRepository.findTagsByBookId(bookLoan.getBookId());
				for (BookTag bt : bookTags) {
					bookDTO.getTags().add(bt);
				}
				return bookDTO;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

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
	
	public ArrayList<BookRecommendDTO> getRecommendedBooks(Long uId) {

		System.out.println("Recommendation initiated");

		KieServices ks = KieServices.Factory.get();
		KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
		kbconf.setOption(EventProcessingOption.STREAM);
		KieBase kbase = kieContainer.newKieBase(kbconf);
		KieSession kSession = kbase.newKieSession();

		List<String[]> dataLines = new ArrayList<>();
		for (BookRating br : ratingRepo.findAll()) {
			dataLines.add(new String[] { br.getUser().getId().toString(), br.getBook().getId().toString(),
					String.valueOf(br.getRating()) });
		}

		try {
			FileWriter csvWriter = new FileWriter(CSV_FILE_NAME);
			for (String[] rowData : dataLines) {
				csvWriter.append(String.join(",", rowData));
				csvWriter.append("\n");
			}

			csvWriter.flush();
			csvWriter.close();

			DataModel model = new FileDataModel(new File("C:\\data.csv"));
			UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
			UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
			UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

			kSession.getEntryPoint("recommend").insert(recommender);
			kSession.getEntryPoint("recommend").insert(new UserDTO(uId));
			kSession.getAgenda().getAgendaGroup("recommendRules").setFocus();
			kSession.fireAllRules();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RecommendDTO recommendResults = new RecommendDTO();
		QueryResults results = kSession.getQueryResults("getRecommendResults");
		for (QueryResultsRow row : results) {
			recommendResults = (RecommendDTO) row.get("$result");
		}

		ArrayList<BookRecommendDTO> bookDTOs = new ArrayList<>();

		for (RecommendedItem i : recommendResults.getRecommendations()) {
			Book b = (bookRepository.findById(i.getItemID()).get());
			System.out.println("book - " + b.toString());
			BookRecommendDTO bookDTO = new BookRecommendDTO(b);
			ArrayList<BookTag> bookTags = this.bookTagRepository.findTagsByBookId(b.getId());
			for (BookTag bt : bookTags) {
				bookDTO.getTags().add(bt);
			}
			bookDTO.setRecommended(true);
			bookDTOs.add(bookDTO);
		}

		if (bookDTOs.size() == 2) {

			Book b = (bookRepository.random());
			System.out.println("book - " + b.toString());
			BookRecommendDTO bookDTO = new BookRecommendDTO(b);
			ArrayList<BookTag> bookTags = this.bookTagRepository.findTagsByBookId(b.getId());
			for (BookTag bt : bookTags) {
				bookDTO.getTags().add(bt);
			}
			bookDTOs.add(bookDTO);

		} else if (bookDTOs.size() == 1) {

			Book b = (bookRepository.random());
			System.out.println("book - " + b.toString());
			BookRecommendDTO bookDTO = new BookRecommendDTO(b);
			ArrayList<BookTag> bookTags = this.bookTagRepository.findTagsByBookId(b.getId());
			for (BookTag bt : bookTags) {
				bookDTO.getTags().add(bt);
			}
			bookDTOs.add(bookDTO);

			Book b2 = (bookRepository.random());
			System.out.println("book - " + b2.toString());
			BookRecommendDTO bookDTO2 = new BookRecommendDTO(b2);
			ArrayList<BookTag> bookTags2 = this.bookTagRepository.findTagsByBookId(b2.getId());
			for (BookTag bt2 : bookTags2) {
				bookDTO2.getTags().add(bt2);
			}
			bookDTOs.add(bookDTO2);
		} else if (bookDTOs.size() == 0) {

			Book b = (bookRepository.random());
			System.out.println("book - " + b.toString());
			BookRecommendDTO bookDTO = new BookRecommendDTO(b);
			ArrayList<BookTag> bookTags = this.bookTagRepository.findTagsByBookId(b.getId());
			for (BookTag bt : bookTags) {
				bookDTO.getTags().add(bt);
			}
			bookDTOs.add(bookDTO);

			Book b2 = (bookRepository.random());
			System.out.println("book - " + b2.toString());
			BookRecommendDTO bookDTO2 = new BookRecommendDTO(b2);
			ArrayList<BookTag> bookTags2 = this.bookTagRepository.findTagsByBookId(b2.getId());
			for (BookTag bt2 : bookTags2) {
				bookDTO2.getTags().add(bt2);
			}
			bookDTOs.add(bookDTO2);

			Book b3 = (bookRepository.random());
			System.out.println("book - " + b3.toString());
			BookRecommendDTO bookDTO3 = new BookRecommendDTO(b3);
			ArrayList<BookTag> bookTags3 = this.bookTagRepository.findTagsByBookId(b3.getId());
			for (BookTag bt3 : bookTags3) {
				bookDTO3.getTags().add(bt3);
			}
			bookDTOs.add(bookDTO3);
		}

		return bookDTOs;
	}

	public boolean addToWishlist(Long uId, Long bookId) {
		try {
			Member member = this.memberRepo.getOne(uId);
			member.getWishlist().add(this.bookRepository.getOne(bookId));
			this.memberRepo.save(member);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public ArrayList<BookDTO> getWishlist(Long uId) {
		Member m = this.memberRepo.getOne(uId);
		ArrayList<BookDTO> bookDTOs = new ArrayList<>();
		for (Book b : m.getWishlist()) {
			BookDTO bDTO = new BookDTO(b);
			ArrayList<BookTag> tags = this.bookTagRepository.findTagsByBookId(b.getId());
			bDTO.setTags(tags);
			bookDTOs.add(bDTO);
		}
		return bookDTOs;
	}


	public Member usersWithSimilarWishlists(Long uId) {
		Member m = this.memberRepo.getOne(uId);

		MemberlistDTO memberlistDTO = new MemberlistDTO();
		memberlistDTO.setMembers(this.memberRepo.findAll());
		
		KieServices ks = KieServices.Factory.get();
		KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
		kbconf.setOption(EventProcessingOption.STREAM);
		KieBase kbase = kieContainer.newKieBase(kbconf);

		KieSession kSession = kbase.newKieSession();

		kSession.getEntryPoint("similar-wishlists").insert(uId);
		for(Member member : memberlistDTO.getMembers()) {
			kSession.getEntryPoint("similar-wishlists").insert(member);
		}
		//kSession.getEntryPoint("similar-wishlists").insert(memberlistDTO);
	
		kSession.getAgenda().getAgendaGroup("wishlistSimilarityRules").setFocus();

		kSession.fireAllRules();
		QueryResults results = kSession.getQueryResults("getSimilarWishlistsResults");

		Member similarMember = null;
		MemberlistDTO memberlistDTO2 = new MemberlistDTO();
		for (QueryResultsRow row : results) {
			similarMember = (Member) row.get("$result");
		}
		for(Member mem : memberlistDTO2.getMembers()) {
			System.out.println(mem.getUsername());
		}
		similarMember = memberRepo.findByUsername(similarMember.getUsername());
		return similarMember;
	}
	
	
	public HashMap<Long, Integer> recommendFromWishlist() {
		ArrayList<BookDTO> bookDTOs = new ArrayList<>();
		
		MemberlistDTO memberlistDTO = new MemberlistDTO();
		memberlistDTO.setMembers(this.memberRepo.findAll());

		KieServices ks = KieServices.Factory.get();
		KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
		kbconf.setOption(EventProcessingOption.STREAM);
		KieBase kbase = kieContainer.newKieBase(kbconf);

		KieSession kSession = kbase.newKieSession();

		kSession.getEntryPoint("wishlist-recommend").insert(memberlistDTO);
	
		kSession.getAgenda().getAgendaGroup("wishlistRecommendRules").setFocus();

		kSession.fireAllRules();
		HashMap<Long, Integer> occurences = new HashMap<>();
		QueryResults results = kSession.getQueryResults("getWishlistRecommendResults");
		for (QueryResultsRow row : results) {
			occurences = (HashMap<Long, Integer>) row.get("$result");
		}

		return occurences;
	}
	
	public BookDTO getBook(Long id) {
		Book book = this.bookRepository.getOne(id);
		BookDTO bDTO = new BookDTO(book);
		ArrayList<BookTag> tags = this.bookTagRepository.findTagsByBookId(book.getId());
		bDTO.setTags(tags);
		return bDTO;
	}
	
	public int checkWrongTags(Long uId) {
		int retVal = 0;
		
		Member member = this.memberRepo.getOne(uId);
		for(Tag t: member.getWrongTags()) {
			System.out.println(t);
		}
		TagListDTO tagListDTO = new TagListDTO();
		tagListDTO.setTags((ArrayList<Tag>) this.tagRepo.findAll());
		for(Tag t: tagListDTO.getTags()) {
			if(t.getTagName().equals("deaths"))
				System.out.println(t);
		}
		KieServices ks = KieServices.Factory.get();
		KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
		kbconf.setOption(EventProcessingOption.STREAM);
		KieBase kbase = kieContainer.newKieBase(kbconf);

		KieSession kSession = kbase.newKieSession();
		
		kSession.getEntryPoint("wrong-tags").insert(member);

		kSession.getEntryPoint("wrong-tags").insert(tagListDTO);
	
		kSession.getAgenda().getAgendaGroup("wrongTagRules").setFocus();
		
		kSession.fireAllRules();

		QueryResults results = kSession.getQueryResults("getWrongTags");
		
		for (QueryResultsRow row : results) {
			retVal = (int) row.get("$result");
		}
		
		return retVal;
	}
	
}
