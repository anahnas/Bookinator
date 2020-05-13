package sbnz.integracija.example.facts;

import java.util.HashMap;

public class SearchRequestDTO {

	private HashMap<String,String> searchCriteria;

	public HashMap<String, String> getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(HashMap<String, String> searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
}
