package sbnz.integracija.example.facts;

import java.util.HashMap;

public class SearchRequestDTO {

	private HashMap<String,Object> searchCriteria = new HashMap<String, Object>();

	public HashMap<String, Object> getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(HashMap<String, Object> searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
	
	public SearchRequestDTO() {
		
	}
	
}
