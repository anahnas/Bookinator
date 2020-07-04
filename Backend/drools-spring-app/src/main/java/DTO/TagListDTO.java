package DTO;

import java.util.ArrayList;

import sbnz.integracija.example.facts.Tag;

public class TagListDTO {
	private ArrayList<Tag> tags = new ArrayList<>();

	public TagListDTO() {
		super();
	}

	public ArrayList<Tag> getTags() {
		return tags;
	}

	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	} 
	
}
