package sbnz.integracija.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Arrays;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import sbnz.integracija.example.facts.Book;
import sbnz.integracija.example.facts.BookTag;
import sbnz.integracija.example.facts.BookTagStatus;
import sbnz.integracija.example.facts.SearchRequest;
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
		
		
//		  KieServices ks = KieServices.Factory.get(); KieContainer kContainer =
//		  ks.newKieContainer(ks.newReleaseId("drools-spring-v2",
//		  "drools-spring-v2-kjar", "0.0.1-SNAPSHOT")); KieScanner kScanner =
//		  ks.newKieScanner(kContainer); kScanner.start(10_000); 
//		  KieSession kSession =
//		  kContainer.newKieSession();
//		  kSession.fireAllRules();
//		 
	   

	}

	@Bean
	public KieContainer kieContainer() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
				.newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
		KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(10_000);
		 KieSession kSession =
				  kContainer.newKieSession();

		 		kSession.insert(new Book((long)5));
		 		kSession.insert(new Tag((long)4,"character"));
		 		kSession.insert(new Tag((long)1,"author"));
		 		kSession.insert(new Tag((long)2,"name"));
		 		kSession.insert(new BookTag((long)5, (long)1, "Ivo Andric"));
		 		kSession.insert(new BookTag((long)5,(long) 2, "Na Drini Cuprija"));
		 		kSession.insert(new BookTag((long)5,(long) 4, "Turcin"));
		 		kSession.insert(new BookTag((long)5,(long) 5, "HM", BookTagStatus.APPROVED));

		 		SearchRequestDTO s=new SearchRequestDTO();
		 		s.getSearchCriteria().put("author", "Ivo Andric");
		 		s.getSearchCriteria().put("character","Turci");
		 		kSession.insert(s);
		 		
				  kSession.fireAllRules();
		return kContainer;
	}
	
}
