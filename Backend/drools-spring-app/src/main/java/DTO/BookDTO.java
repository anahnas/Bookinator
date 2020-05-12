package DTO;

import java.util.HashMap;

public class BookDTO {

	private Long id;
	private HashMap<String, String> tags = new HashMap<>();
	
	public BookDTO(Long id, HashMap<String, String> tags) {
		super();
		this.id = id;
		this.tags = tags;
	}
	public BookDTO() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public HashMap<String, String> getTags() {
		return tags;
	}
	public void setTags(HashMap<String, String> tags) {
		this.tags = tags;
	}
	
	
}
