
package sbnz.integracija.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

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

import events.MembershipExpiredEvent;
import events.TransactionEvent;
import sbnz.integracija.example.facts.Book;
import sbnz.integracija.example.facts.BookTag;
import sbnz.integracija.example.facts.Member;
import sbnz.integracija.example.facts.SearchRequestDTO;
import sbnz.integracija.example.facts.Tag;
import sbnz.integracija.example.repository.BookRepository;
import sbnz.integracija.example.repository.BookTagRepository;
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
	Book book1 = new Book((long) 1);
	Book book2 = new Book((long) 2);
	Tag tag1 = new Tag((long) 1, "author");
	Tag tag2 = new Tag((long) 1, "genre");
	BookTag bt1;
	BookTag bt2;
	Member m = new Member();

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
		HashMap<String, String> map = new HashMap<>();
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