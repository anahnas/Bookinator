package sbnz.integracija.example.facts;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tag {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String tagName;
	
	@Column(name = "tag_approved")
	private boolean approved;
	
	@Column
	private String tagValue;

	public Tag() {}

	public Tag(Long id, String tagName, Boolean approved) {
		super();
		this.id = id;
		this.tagName = tagName;
		this.approved = Boolean.FALSE;
	}


		public Tag(Long id, String tagName, String tagValue, Boolean approved) {
		super();
		this.id = id;
		this.tagName = tagName;
		this.tagValue = tagValue;
		this.approved = Boolean.FALSE;
	}
	

	public Tag(Long id, String tagName) {
		super();
		this.id = id;
		this.tagName = tagName;
	}
	
	public Tag(Long id, String tagName, boolean approved) {
		super();
		this.id = id;
		this.tagName = tagName;
		this.approved = approved;
	}

	public Tag(String string) {
		this.tagName=string;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public String getTagValue() {
		return tagValue;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}
		

}
