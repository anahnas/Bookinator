package sbnz.integracija.example;

import java.util.Arrays;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

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

		KieBase kbase = kContainer.newKieBase(kbconf);

		return kContainer;
	}

}
