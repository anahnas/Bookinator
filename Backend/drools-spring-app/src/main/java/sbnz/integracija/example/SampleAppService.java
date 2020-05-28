package sbnz.integracija.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.commons.csv.writer.CSVWriter;
import org.drools.core.ClockType;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import DTO.BookDTO;
import DTO.BookRecommendDTO;
import enumeration.RoleEnum;
import events.MembershipExpiredEvent;
import events.TransactionEvent;
import DTO.BookTagDTO;
import DTO.RecommendDTO;
import DTO.UserDTO;
import sbnz.integracija.example.facts.Book;
import sbnz.integracija.example.facts.BookRating;
import sbnz.integracija.example.facts.BookTag;
import sbnz.integracija.example.facts.BookTagStatus;
import sbnz.integracija.example.facts.Member;
import sbnz.integracija.example.facts.ReviewRequest;
import sbnz.integracija.example.facts.SearchRequest;
import sbnz.integracija.example.facts.SearchRequestDTO;
import sbnz.integracija.example.facts.Tag;
import sbnz.integracija.example.facts.User;
import sbnz.integracija.example.repository.BookRatingRepository;
import sbnz.integracija.example.repository.BookRepository;
import sbnz.integracija.example.repository.BookTagRepository;
import sbnz.integracija.example.repository.MemberRepository;
import sbnz.integracija.example.repository.TagRepository;
import sbnz.integracija.example.repository.userRepository;

import java.io.File;
import java.util.List;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CityBlockSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

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

	private String CSV_FILE_NAME = "C:\\data.csv";

	@Autowired
	public SampleAppService(KieContainer kieContainer, BookRepository bookRepository,
			BookTagRepository bookTagRepository) {
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
		if (u != null) {
			if (u.getPassword().equals(user.getPassword())) {
				return u;
			}
		}

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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		kSession.getEntryPoint("recommend").insert(new UserDTO(uId));
		kSession.getAgenda().getAgendaGroup("recommendRules").setFocus();
		kSession.fireAllRules();

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

}
