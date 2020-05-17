package sbnz.integracija.example.facts;

import java.util.ArrayList;
import java.util.Date;

public class SearchRequest {

	private String name; 
	private String author;
    private String genre;
    private String authorOrigin;
    private String yearPublished;
    private String  publisher;
    private ArrayList<String> characters;
    private String periodSet;
    private String motives;
    private String lesson;
    private String targetAgeGroup;
    private String style;
    private boolean available;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getAuthorOrigin() {
		return authorOrigin;
	}
	public void setAuthorOrigin(String authorOrigin) {
		this.authorOrigin = authorOrigin;
	}
	public String getYearPublished() {
		return yearPublished;
	}
	public void setYearPublished(String yearPublished) {
		this.yearPublished = yearPublished;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public ArrayList<String> getCharacters() {
		return characters;
	}
	public void setCharacters(ArrayList<String> characters) {
		this.characters = characters;
	}
	public String getPeriodSet() {
		return periodSet;
	}
	public void setPeriodSet(String periodSet) {
		this.periodSet = periodSet;
	}

	public String getMotives() {
		return motives;
	}
	public void setMotives(String motives) {
		this.motives = motives;
	}
	public String getLesson() {
		return lesson;
	}
	public void setLesson(String lesson) {
		this.lesson = lesson;
	}
	public String getTargetAgeGroup() {
		return targetAgeGroup;
	}
	public void setTargetAgeGroup(String targetAgeGroup) {
		this.targetAgeGroup = targetAgeGroup;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public SearchRequest(String name, String author) {
		super();
		this.name = name;
		this.author = author;
	}
	public SearchRequest() {
		super();
	}
	
}
