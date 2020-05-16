package sbnz.integracija.example;

<<<<<<< HEAD
=======
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
>>>>>>> 834cbbceba6674f9d215dd9c9bfa0f825f5dabb8
import java.util.Arrays;
import java.util.List;

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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import sbnz.integracija.example.facts.Book;
import sbnz.integracija.example.facts.BookTag;
import sbnz.integracija.example.facts.SearchRequestDTO;
import sbnz.integracija.example.facts.Tag;

@SpringBootApplication
public class SampleApp {

	private static Logger log = LoggerFactory.getLogger(SampleApp.class);

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SampleApp.class, args);

		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		
		//StringBuilder sb = new StringBuilder("Application beans:\n");
		//for (String beanName : beanNames) {
		//	sb.append(beanName + "\n");
		//}
		//log.info(sb.toString());	   

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
<<<<<<< HEAD
		
		KieBase kbase = kContainer.newKieBase(kbconf);
		KieSession kSession = kbase.newKieSession();

 		kSession.insert(new Book((long)5));
 		kSession.insert(new Tag((long)4,"character"));
 		kSession.insert(new Tag((long)1,"author"));
 		kSession.insert(new Tag((long)2,"name"));
 		kSession.insert(new BookTag((long)5, (long)1, "Ivo Andric"));
 		kSession.insert(new BookTag((long)5,(long) 2, "Na Drini Cuprija"));
 		kSession.insert(new BookTag((long)5,(long) 4, "Turcin"));
 		SearchRequestDTO s=new SearchRequestDTO();
 		s.getSearchCriteria().put("author", "Ivo Andric");
 		s.getSearchCriteria().put("character","Turci");
 		kSession.insert(s);

        kSession.fireAllRules();
=======
		 KieSession kSession =
				  kContainer.newKieSession();
		 
		 kSession.getEntryPoint("search").insert(new Book((long)5));
		 kSession.getEntryPoint("search").insert(new Tag((long)4,"character"));
		 kSession.getEntryPoint("search").insert(new Tag((long)1,"author"));
		 kSession.getEntryPoint("search").insert(new Tag((long)2,"name"));
		 kSession.getEntryPoint("search").insert(new BookTag((long)5, (long)1, "Ivo Andric"));
		 kSession.getEntryPoint("search").insert(new BookTag((long)5,(long) 2, "Na Drini Cuprija"));
		 kSession.getEntryPoint("search").insert(new BookTag((long)5,(long) 4, "Turcin"));
		 SearchRequestDTO s=new SearchRequestDTO();
		 s.getSearchCriteria().put("author", "Ivo Andric");
		 s.getSearchCriteria().put("character","Turci");
		 kSession.getEntryPoint("search").insert(s);
		 // can be commented out
		 kSession.getAgenda().getAgendaGroup( "startSearch" ).setFocus();
		 //
		 kSession.fireAllRules();
		 List<Book> searchResults=new ArrayList<>();
		 QueryResults results = kSession.getQueryResults( "getSearchResults" ); 
		 for ( QueryResultsRow row : results ) {
		     Book b = ( Book ) row.get( "$result" ); 
		     searchResults.add(b);
		 }
		 
		 System.out.println("rezultati:");
		 for (Book b: searchResults)
			 System.out.println(b.getMatch());
>>>>>>> 834cbbceba6674f9d215dd9c9bfa0f825f5dabb8
		return kContainer;
	}
	
}
