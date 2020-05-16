package sbnz.integracija.example.facts;

import java.io.Serializable;
import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("2h30m")
public class TransactionEvent  implements Serializable {

    private static final long serialVersionUID = 1L;
    private Date executionTime;
    private Long userId;

    public TransactionEvent() {
        super();
    }
    
    public TransactionEvent(Long userId) {
        super();
        this.executionTime = new Date();
        this.userId = userId;
    }

    public Date getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

