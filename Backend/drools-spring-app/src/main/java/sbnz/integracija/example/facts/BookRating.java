package sbnz.integracija.example.facts;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
public class BookRating {
	
	  @EmbeddedId
	    BookRatingKey id;
	    
	    @JsonIgnore
	    @ManyToOne
	    @MapsId("book_id")
	    @JoinColumn(name="book_id")
	    Book book;
	    
	    @JsonIgnore
	    @ManyToOne
	    @MapsId("user_id")
	    @JoinColumn(name="user_id")
	    User user;

	    float rating;
	    
	    

		public BookRating(Book book, User user, float rating) {
			super();
			this.book = book;
			this.user = user;
			this.rating = rating;
		}

		public BookRatingKey getId() {
			return id;
		}

		public void setId(BookRatingKey id) {
			this.id = id;
		}

		public Book getBook() {
			return book;
		}

		public void setBook(Book book) {
			this.book = book;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public float getRating() {
			return rating;
		}

		public void setRating(float rating) {
			this.rating = rating;
		}
	    
	    

}
