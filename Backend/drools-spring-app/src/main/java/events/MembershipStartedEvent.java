package events;

import java.io.Serializable;

public class MembershipStartedEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long userId;
	

	public MembershipStartedEvent() {
		super();
	}

	public MembershipStartedEvent(Long userId) {
		super();
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	

	
	

}

