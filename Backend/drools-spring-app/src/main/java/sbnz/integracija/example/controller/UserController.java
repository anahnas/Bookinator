package sbnz.integracija.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import DTO.BookDTO;
import DTO.BookTagDTO;
import sbnz.integracija.example.SampleAppService;
import sbnz.integracija.example.facts.BookTag;
import sbnz.integracija.example.facts.ReviewRequest;
import sbnz.integracija.example.facts.SearchRequest;
import sbnz.integracija.example.facts.User;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {
	private static Logger log = LoggerFactory.getLogger(UserController.class);

	private final SampleAppService sampleService;

	@Autowired
	public UserController(SampleAppService sampleService) {
		this.sampleService = sampleService;
	}
	
	@RequestMapping(value = "/book", method = RequestMethod.POST)
	public ResponseEntity<ArrayList<BookDTO>> bookSearch(@RequestBody  SearchRequest searchRequest) {
		log.debug("Search request received for: " + searchRequest);
		ArrayList<BookDTO> retVal = sampleService.getFilteredBooks(searchRequest);
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody  User u) {
		User user = sampleService.login(u);
		if(user != null) {
			System.out.println("User has logged in");
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		else 
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(@RequestBody  User u) {
		User user = sampleService.register(u);
		if(user != null) {
			this.sampleService.startMembershipCheck(user.getId());
			return new ResponseEntity<>(user, HttpStatus.OK);	
		}
		else 
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value="/allUsers", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getUsers() {
		List<User> user = sampleService.findAll();
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody  User u) {
		User user = sampleService.saveUser(u);
		if(user != null) 
			return new ResponseEntity<>(user, HttpStatus.OK);
		else 
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
	@RequestMapping(value = "/review", method = RequestMethod.POST)
	public ResponseEntity bookReview(@RequestBody ReviewRequest reviewRequest) {
		System.out.println("reviewing...");
		this.sampleService.bookReview(reviewRequest);
		return new ResponseEntity(HttpStatus.OK);
	}
	

	@RequestMapping(value = "/manageTag", method = RequestMethod.POST)
	public ResponseEntity manageTag(@RequestBody BookTagDTO bookTagDTO) {
		if(bookTagDTO.isApproved()) {
			this.sampleService.approveTag(bookTagDTO.getId());
		} else {
			this.sampleService.deleteTag(bookTagDTO.getId());
		}
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/payMembership/{uId}", method = RequestMethod.GET)
	public ResponseEntity payMembership(@PathVariable("uId") Long uId) {
		this.sampleService.payMembership(uId);
		return new ResponseEntity(HttpStatus.OK);
	}
}
