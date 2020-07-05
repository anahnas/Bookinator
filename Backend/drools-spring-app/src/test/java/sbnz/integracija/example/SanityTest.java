
package sbnz.integracija.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.drools.core.ClockType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import DTO.BookDTO;
import DTO.MemberlistDTO;
import events.MembershipExpiredEvent;
import events.TransactionEvent;
import sbnz.integracija.example.facts.Book;
import sbnz.integracija.example.facts.BookRent;
import sbnz.integracija.example.facts.BookTag;
import sbnz.integracija.example.facts.Member;
import sbnz.integracija.example.facts.SearchRequestDTO;
import sbnz.integracija.example.facts.Tag;
import sbnz.integracija.example.repository.BookRepository;
import sbnz.integracija.example.repository.BookTagRepository;
import sbnz.integracija.example.repository.MemberRepository;
import sbnz.integracija.example.repository.TagRepository;

@SpringBootTest // tells spring boot to look for a main config class (annotated with
				// @SpringBootApplication to start a Spring app context)
@RunWith(SpringRunner.class)

public class SanityTest {

	@Mock
	private BookRepository bookRepo;

	@Mock
	private BookTagRepository bookTagRepo;
	
	@Mock
	private MemberRepository memberRepo;

	@Mock
	private TagRepository tagRepo;

	@Autowired
	KieContainer kieContainer;

//	@Autowired
//	KieContainer kieContainer;

	@InjectMocks
	private SampleAppService service;

	List<Book> books = new ArrayList<Book>();
	List<Tag> tags = new ArrayList<Tag>();
	List<BookTag> bookTags = new ArrayList<BookTag>();
	List<BookTag> bookTags2 = new ArrayList<BookTag>();
	List<Member> members = new ArrayList<Member>();
	Set<BookRent> history = new HashSet<BookRent>();
	Book book1 = new Book((long) 1);
	Book book2 = new Book((long) 2);
	Tag tag1 = new Tag((long) 1, "author");
	Tag tag2 = new Tag((long) 1, "genre");
	BookTag bt1;
	BookTag bt2;
	Member m = new Member();
	BookRent br;

	@Before
	public void initialise() {

		book1.setMatch(0);
		book2.setMatch(0);
		books.add(book1);
		books.add(book2);

		tags.add(tag1);
		tags.add(tag2);

		bookTags.add(bt1);
		bookTags2.add(bt2);
		
		m.setId((long)1);
		m.setUsername("test");
		m.setMembershipExpired(false);
		
		br = new BookRent();
		br.setBook(book1);
		br.setUser(m);
		
		history.add(br);
		m.setHistory(history);
		m.setDiscount(0.0);
		m.setMembership(100.0);

		
		Member m1 = new Member();
		m1.setId(1L);
		Set<Book> wishlist1 = new HashSet<Book>();

		Book b1 = new Book();
		b1.setAvaivableNo(0);
		b1.setId(5L);
		wishlist1.add(b1);

		Book b2 = new Book();
		b2.setAvaivableNo(0);
		b2.setId(4L);
		wishlist1.add(b2);
		
		m1.setWishlist(wishlist1);
		members.add(m1);
		
		Member m2 = new Member();
		m2.setId(2L);
		Set<Book> wishlist2 = new HashSet<Book>();

		b1 = new Book();
		b1.setAvaivableNo(0);
		b1.setId(1L);
		wishlist2.add(b1);

		b2 = new Book();
		b2.setAvaivableNo(0);
		b2.setId(4L);
		wishlist2.add(b2);
		
		m2.setWishlist(wishlist2);
		members.add(m2);
		
		Member m3 = new Member();
		m3.setId(3L);
		Set<Book> wishlist3 = new HashSet<Book>();
	
		b1 = new Book();
		b1.setAvaivableNo(0);
		b1.setId(1L);
		wishlist3.add(b1);

		b2 = new Book();
		b2.setAvaivableNo(0);
		b2.setId(4L);
		wishlist3.add(b2);
		
		Book b3 = new Book();
		b3.setAvaivableNo(0);
		b3.setId(5L);
		wishlist3.add(b3);
		
		m3.setWishlist(wishlist3);
		members.add(m3);
	}

	@Test
	public void testSearchRules() {
		KieServices ks = KieServices.Factory.get();
		KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
		kbconf.setOption(EventProcessingOption.STREAM);
		KieBase kbase = kieContainer.newKieBase(kbconf);

		KieSession kSession = kbase.newKieSession();

		bt1 = new BookTag((long) 1, (long) (1), "Ivo Andric"); // AUTHOR OF THE BOOK 1 IS IVO ANDRIC
		bt2 = new BookTag((long) 2, (long) (1), "J. R. R. Tolkien");
		kSession.getEntryPoint("search").insert(book1);
		kSession.getEntryPoint("search").insert(book2);
		kSession.getEntryPoint("search").insert(tag1);
		kSession.getEntryPoint("search").insert(tag2);
		kSession.getEntryPoint("search").insert(bt1);
		kSession.getEntryPoint("search").insert(bt2);

		SearchRequestDTO searchRequest = new SearchRequestDTO();
		HashMap<String, Object> map = new HashMap<>();
		map.put("author", "Ivo");
		searchRequest.setSearchCriteria(map);
		kSession.getEntryPoint("search").insert(searchRequest);
		kSession.getAgenda().getAgendaGroup("startSearch").setFocus();
		kSession.fireAllRules();

		List<Book> searchResults = new ArrayList<>();
		QueryResults results = kSession.getQueryResults("getSearchResults");
		for (QueryResultsRow row : results) {
			Book b = (Book) row.get("$result");
			searchResults.add(b);
		}
		kSession.dispose();

		assert (searchResults.get(0).getId().equals(((long) 1))); // best match is book 1

	}
	
	@Test
	public void testMembershipDiscount() {
		
		KieServices ks = KieServices.Factory.get();
		KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
		kbconf.setOption(EventProcessingOption.STREAM);
		KieBase kbase = kieContainer.newKieBase(kbconf);
		KieSession kSession = kbase.newKieSession();
		kSession.getEntryPoint("discount").insert(m);
		kSession.getAgenda().getAgendaGroup("membership-discount").setFocus();
				
		kSession.fireAllRules();
		
		kSession.dispose();
		assert(m.getDiscount() == 10.0);
		assert(m.getMembership() == 90.0);

	}
	
	@Test
	public void testMembershipRule() {
		Long uId = 1L;
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
				TransactionEvent t1 = new TransactionEvent(uId); 	//member paying membership
				kSession1.insert(t1);
				kSession1.fireUntilHalt();
				Collection<?> newEvents = kSession1.getObjects(new ClassObjectFilter(MembershipExpiredEvent.class));
				for (Object o : newEvents) {
					if (o instanceof MembershipExpiredEvent) {
						MembershipExpiredEvent e = (MembershipExpiredEvent) o;
						assert (e.getUserId().equals(m.getId()));	//assert that membership-expired event triggered for test user	
						m.setMembershipExpired(true);
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
	
	@Test
	public void testRecommendFromWishlist() {
		
		MemberlistDTO memberlistDTO = new MemberlistDTO();
		memberlistDTO.setMembers(members);

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
		assert(occurences.values().size() == 3);
		assert(occurences.get(1L).equals(2));
		assert(occurences.get(4L).equals(3));
		assert(occurences.get(5L).equals(2));

	}

//	@Test
//	public void searchTest() {
//
//		Mockito.when(bookRepo.findAll()).thenReturn(books);
//		Mockito.when(tagRepo.findAll()).thenReturn(tags);
//		Mockito.when(bookTagRepo.findTagsByBookId((long) 1)).thenReturn((ArrayList<BookTag>) bookTags);
//		Mockito.when(bookTagRepo.findTagsByBookId((long) 2)).thenReturn((ArrayList<BookTag>) bookTags2);
//		BookTag bt2 = new BookTag((long) 2, (long) (1), "J. R. R. Tolkien");
//		bookTags.add(bt2);
//		Mockito.when(bookTagRepo.findAll()).thenReturn(bookTags);
//
//
//		SearchRequestDTO searchRequest = new SearchRequestDTO();
//		HashMap<String, String> map = new HashMap<>();
//		map.put("author", "Ivo");
//		searchRequest.setSearchCriteria(map);
//
//		List<Book> searchRes = service.startSearch(searchRequest);
//		//assert (searchRes.get(0).getId().equals(((long) 1))); // best match is book 1
//
//	}

}
