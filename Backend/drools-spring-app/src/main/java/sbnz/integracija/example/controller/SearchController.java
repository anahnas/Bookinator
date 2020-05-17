package sbnz.integracija.example.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import DTO.BookDTO;
import sbnz.integracija.example.SampleAppService;
import sbnz.integracija.example.facts.SearchRequest;

@CrossOrigin(origins = "http://localhost:4200")
@RestController("/search")
public class SearchController {
	
	@Autowired
	SampleAppService sampleService;
	
	private static Logger log = LoggerFactory.getLogger(UserController.class);

//	
//	@RequestMapping(value = "/book", method = RequestMethod.POST)
//	public ResponseEntity<ArrayList<BookDTO>> bookSearch(@RequestBody  SearchRequest searchRequest) {
//		log.debug("Search request received for: " + searchRequest);
//		ArrayList<BookDTO> retVal = sampleService.getFilteredBooks(searchRequest);
//		return new ResponseEntity<>(retVal, HttpStatus.OK);
//	}

}
