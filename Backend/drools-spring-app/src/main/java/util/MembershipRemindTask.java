package util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MembershipRemindTask {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	@Scheduled(fixedRate = 100)
	public void reportCurrentTime() {
		System.out.println("The time is now {}");
	}
}