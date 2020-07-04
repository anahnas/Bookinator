package sbnz.integracija.example.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DeflaterOutputStream;

import org.apache.commons.io.IOUtils;
import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.ObjectDataCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import DTO.TemplateDRLDTO;
import sbnz.integracija.example.SampleAppService;
import sbnz.integracija.example.facts.Member;
import sbnz.integracija.example.repository.MemberRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/templates")
@CrossOrigin(origins = "http://localhost:4200")
public class TemplateController {

	@Autowired
	private SampleAppService service;

	@Autowired
	private MemberRepository memberRepo;

	@PostMapping(value = "", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<?> newRuleTemplate(@RequestBody TemplateDRLDTO drldto) throws IOException {
		System.out.println("*******ISPIS za drl **** " + drldto.toString());
		String s1 = System.getProperty("user.dir");
		s1 = s1.substring(0, s1.length() - 25);
		System.out.println("********USER DIR: " + s1);
		// java.net.URL urlToFile =
		// this.getClass().getClassLoader().getResource("/drools-spring-app/src/main/resources/template/template.drt");

		// java.net.URL urlToFile =
		// this.getClass().getClassLoader().getResource("/sbnz/integracija/template/template.drt");
		// InputStream template =
		// this.getClass().getClassLoader().getResourceAsStream("/sbnz/integracija/template/template.drt");
		System.out.println("DRT FILE LOCATION: " + s1
				+ "drools-spring-kjar\\src\\main\\resources\\sbnz\\integracija\\template\\template.drt");
		// InputStream template = TemplateController.class.getResourceAsStream(s1 +
		// "drools-spring-kjar\\src\\main\\resources\\sbnz\\integracija\\template\\template.drt");
		// InputStream template =
		// TemplateController.class.getResourceAsStream("C:\\Users\\Ana\\Desktop\\buki\\Bookinator\\Backend\\drools-spring-kjar\\src\\main\\resources\\sbnz\\integracija\\template\\template.drt");
		// InputStream template =
		// TemplateController.class.getResourceAsStream(urlToFile.toString());

		/*
		 * try { System.out.println("***ULAZI U PRVI TRY***"); template = new
		 * ClassPathResource("/sbnz/integracija/template/template.drt").getInputStream()
		 * ; IOUtils.toString(template); } finally { IOUtils.closeQuietly(template);
		 */

		InputStream template = null;
		try {
			File file1 = ResourceUtils.getFile("classpath:template.drt");
			template = new FileInputStream(file1);


			DataProvider dataProvider2 = new ArrayDataProvider(new String[][] {
					new String[] { String.valueOf(drldto.getMinRent()), String.valueOf(drldto.getMaxRent()), "NA"},
					new String[] { String.valueOf(drldto.getBronzeMinRent()), String.valueOf(drldto.getBronzeMaxRent()), "BRONZE"},
					new String[] { String.valueOf(drldto.getSilverMinRent()), String.valueOf(drldto.getSilverMaxRent()), "SILVER" },
					new String[] { String.valueOf(drldto.getGoldMinRent()), String.valueOf(drldto.getGoldMaxRent()), "GOLD" },


			});

			ObjectDataCompiler converter = new ObjectDataCompiler();
			
			String drl2 = converter.compile(dataProvider2, template);
			System.out.println("IMA LI TE" + drl2);

			try {
				String s = System.getProperty("user.dir");
				s = s.substring(0, s.length() - 17);
				System.out.println(
						"***OPA: " + s + "drools-spring-kjar\\src\\main\\resources\\sbnz\\integracija\\template.drl");
				// System.out.println(System.getProperty("user.dir") +
				// "/drools-spring-kjar/src/main/resources/sbnz/integracija/template.drl");
				// File file = new File(System.getProperty("user.dir") +
				// "/drools-spring-kjar/src/main/resources/sbnz/integracija/template.drl");
				File file = new File(s + "drools-spring-kjar\\src\\main\\resources\\sbnz\\integracija\\template.drl");
				FileWriter fwr = null;
				fwr = new FileWriter(file, false);
				try {
					fwr.write(drl2);
				} finally {
					if (fwr != null) {
						fwr.close();
						// IOUtils.closeQuietly(fwr);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			KieSession ksession = createKieSessionFromDRL(drl2);

			System.out.println("OPPPPP");
			//doTest(ksession);

			/*ArrayList<Member> members = (ArrayList<Member>) memberRepo.findAll();

			for (Member m : members) {
				m.setCathegory(Member.cathegory.BRONZE);
				service.setMemberCategory(m);
				System.out.println("Kategorijaaa: " + m.getCathegory());
			}*/
			// }
		} catch (IOException e) {
			System.out.println("***exception***");
			e.printStackTrace();
		}

		return ResponseEntity.ok("Restart app to see changes");

	}

/*	private void doTest(KieSession ksession) {
		Member member1 = new Member(1);

		ksession.insert(member1);

		ksession.fireAllRules();
	}
*/
	private KieSession createKieSessionFromDRL(String drl) {
		KieHelper kieHelper = new KieHelper();
		kieHelper.addContent(drl, ResourceType.DRL);
		System.out.println("*************" + drl);

		Results results = kieHelper.verify();

		if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)) {
			List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
			for (Message message : messages) {
				System.out.println("Error: " + message.getText());
			}

			throw new IllegalStateException("Compilation errors were found. Check the logs.");
		}

		return kieHelper.build().newKieSession();
	}
}