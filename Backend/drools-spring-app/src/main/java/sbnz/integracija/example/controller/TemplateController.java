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


	@PostMapping(value = "", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<?> newRuleTemplate(@RequestBody TemplateDRLDTO drldto) throws IOException {

		InputStream template = null;
		try {
			File file1 = ResourceUtils.getFile("classpath:template.drt");
			template = new FileInputStream(file1);

			DataProvider dataProvider2 = new ArrayDataProvider(new String[][] {
					new String[] { String.valueOf(drldto.getMinRent()), String.valueOf(drldto.getMaxRent()), "NA" },
					new String[] { String.valueOf(drldto.getBronzeMinRent()), String.valueOf(drldto.getBronzeMaxRent()),
							"BRONZE" },
					new String[] { String.valueOf(drldto.getSilverMinRent()), String.valueOf(drldto.getSilverMaxRent()),
							"SILVER" },
					new String[] { String.valueOf(drldto.getGoldMinRent()), String.valueOf(drldto.getGoldMaxRent()),
							"GOLD" },

			});

			ObjectDataCompiler converter = new ObjectDataCompiler();

			String drl2 = converter.compile(dataProvider2, template);
			System.out.println("***DRL FILE: " + drl2);

			try {
				String s = System.getProperty("user.dir");
				s = s.substring(0, s.length() - 17);
				
				File file = new File(s + "drools-spring-kjar\\src\\main\\resources\\sbnz\\integracija\\template.drl");
				FileWriter fwr = null;
				fwr = new FileWriter(file, false);
				try {
					fwr.write(drl2);
				} finally {
					if (fwr != null) {
						fwr.close();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// KieSession ksession = createKieSessionFromDRL(drl2);

		} catch (IOException e) {
			System.out.println("***exception***");
			e.printStackTrace();
		}

		return ResponseEntity.ok("Restart app to see changes");

	}

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
