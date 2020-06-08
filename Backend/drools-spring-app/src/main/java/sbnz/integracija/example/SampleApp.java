package sbnz.integracija.example;
import java.util.Arrays;

import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import DTO.UserDTO;
import sbnz.integracija.example.facts.Book;
import sbnz.integracija.example.facts.BookRating;
import sbnz.integracija.example.facts.BookTag;
import sbnz.integracija.example.facts.SearchRequestDTO;
import sbnz.integracija.example.facts.Tag;
import sbnz.integracija.example.facts.User;
import sbnz.integracija.example.repository.BookRatingRepository;

@SpringBootApplication
public class SampleApp {

	private static Logger log = LoggerFactory.getLogger(SampleApp.class);
	
	public static void main(String[] args) {
		
		
		ApplicationContext ctx = SpringApplication.run(SampleApp.class, args);

		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);

		// StringBuilder sb = new StringBuilder("Application beans:\n");
		// for (String beanName : beanNames) {
		// sb.append(beanName + "\n");
		// }
		// log.info(sb.toString());

	}

	@Bean
	public KieContainer kieContainer() {
		
		KieServices ks = KieServices.Factory.get();
		
		KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
        kbconf.setOption(EventProcessingOption.STREAM);	
        
		
		KieContainer kContainer = ks
				.newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
		KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(10_000);


//		
		KieBase kbase = kContainer.newKieBase(kbconf);
		KieSession kSession = kbase.newKieSession();
//		
//		 kSession.getEntryPoint("search").insert(new Book((long)5));
//		 kSession.getEntryPoint("search").insert(new Tag((long)4,"character"));
//		 kSession.getEntryPoint("search").insert(new Tag((long)1,"author"));
//		 kSession.getEntryPoint("search").insert(new Tag((long)2,"name"));
//		 kSession.getEntryPoint("search").insert(new BookTag((long)5, (long)1, "Ivo Andric"));
//		 kSession.getEntryPoint("search").insert(new BookTag((long)5,(long) 2, "Na Drini Cuprija"));
//		 kSession.getEntryPoint("search").insert(new BookTag((long)5,(long) 4, "Turcin"));
//		 SearchRequestDTO s=new SearchRequestDTO();
//		 s.getSearchCriteria().put("author", "Ivo Andric");
//		 s.getSearchCriteria().put("character","Turci");
//		 kSession.getEntryPoint("recommend").insert(new UserDTO((long)1));
//		 User user= new User();
//		 user.setId((long)2);
//		 User user1= new User();
//		 user1.setId((long)1);
//		 User user3= new User();
//		 user1.setId((long)3);
//		 Book book=new Book();
//		 book.setId((long)1);
//		 kSession.getEntryPoint("recommend").insert(user);
//		 kSession.getEntryPoint("recommend").insert(user1);
//		 kSession.getEntryPoint("recommend").insert(new BookRating(book,user,(float)5));
//		 kSession.getEntryPoint("recommend").insert(new BookRating(book,user,(float)5));
//		 kSession.getEntryPoint("recommend").insert(new UserDTO((long)1));
		 
		 // can be commented out
		// kSession.getAgenda().getAgendaGroup( "startSearch" ).setFocus();
		 //
		// kSession.fireAllRules();
//		 List<Book> searchResults=new ArrayList<>();
//		 QueryResults results = kSession.getQueryResults( "getSearchResults" ); 
//		 for ( QueryResultsRow row : results ) {
//		     Book b = ( Book ) row.get( "$result" ); 
//		     searchResults.add(b);
//		 }
//		 
//		 System.out.println("rezultati:");
//		 for (Book b: searchResults)
//			 System.out.println(b.getMatch());

		return kContainer;
	}

}
	