package sbnz.integracija.example;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbnz.integracija.example.facts.BookRent;
import sbnz.integracija.example.repository.BookRentRepository;
import sbnz.integracija.example.repository.MemberRepository;

@Service
public class NewRuleService {
	
	private final KieContainer kieContainer;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private BookRentRepository bookRentRepository;


	@Autowired
	public NewRuleService(KieContainer kieContainer) {
		this.kieContainer = kieContainer;
	}

	public BookRent getItem(BookRent br) {
		KieServices ks = KieServices.Factory.get();
		KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
		kbconf.setOption(EventProcessingOption.STREAM);
		KieBase kbase = kieContainer.newKieBase(kbconf);
		KieSession kSession = kbase.newKieSession();	
		kSession.insert(br);
		kSession.fireAllRules();
		kSession.dispose();	
		bookRentRepository.save(br);

		return br;
	}

}
