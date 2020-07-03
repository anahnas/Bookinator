package sbnz.integracija.example.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.List;

import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sbnz.integracija.example.facts.Member;
import DTO.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/templates")
public class TemplateController {
	
	@PostMapping(value="", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> newRuleTemplate(@RequestBody TemplateDRLDTO drldto){

	
	 InputStream template = TemplateController.class.getResourceAsStream("/template/template.drt");
     
     DataProvider dataProvider = new ArrayDataProvider(new String[][]{
         new String[]{"18", "21", "NA", "NA"},
         new String[]{"22", "30", "NA", "BRONZE"},
         new String[]{"31", "40", "NA", "SILVER"},
         new String[]{"31", "40", "NA", "GOLD"}
     });
	 DataProvider dataProvider2 = new ArrayDataProvider(new String[][]{
         new String[]{String.valueOf(drldto.getMinRent()), String.valueOf(drldto.getMaxRent()), "NA"},
         new String[]{String.valueOf(drldto.getBronzeMinRent()), String.valueOf(drldto.getBronzeMaxRent()), "BRONZE"},
         new String[]{String.valueOf(drldto.getSilverMinRent()), String.valueOf(drldto.getSilverMaxRent()), "SILVER"},
         new String[]{String.valueOf(drldto.getGoldMinRent()), String.valueOf(drldto.getGoldMaxRent()), "GOLD"},

 });
     
     DataProviderCompiler converter = new DataProviderCompiler();
     String drl = converter.compile(dataProvider, template);
     String drl2 = converter.compile(dataProvider2, template);
  
     try {
    	 System.out.println(System.getProperty("user.dir") + "/drools-spring-kjar/src/main/resources/sbnz/integracija/template.drl");
    	 File file = new File(System.getProperty("user.dir") + "/drools-spring-kjar/src/main/resources/sbnz/integracija/template.drl");
    	 FileWriter fwr = null;
    	 fwr = new FileWriter(file, false);
    	 fwr.write(drl2);
    	 fwr.close();
     } catch(Exception e) {
    	 e.printStackTrace();
     }
     KieSession ksession = createKieSessionFromDRL(drl2);
     
     doTest(ksession);
     
     
     return ResponseEntity.ok("Restart app to see changes");
     
     
    
    }
     private void doTest(KieSession ksession){
         Member member1 = new Member(1);
         
         ksession.insert(member1);
        
         
         ksession.fireAllRules();
     }

     private KieSession createKieSessionFromDRL(String drl){
         KieHelper kieHelper = new KieHelper();
         kieHelper.addContent(drl, ResourceType.DRL);
         System.out.println("*************" + drl);
         
         Results results = kieHelper.verify();
         
         if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)){
             List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
             for (Message message : messages) {
                 System.out.println("Error: "+message.getText());
             }
             
             throw new IllegalStateException("Compilation errors were found. Check the logs.");
         }
         
         return kieHelper.build().newKieSession();
     }
}
