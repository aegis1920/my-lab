package things.zonedatetime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

class ZonedDateTimeTest {
	
	private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static final int DIFF_TIME = 9;
	
	@Test
	void name() {
		String time = LocalDateTime.now().format(DATETIME_FORMAT);
		
		Assertions.assertThat(convertKoreaTimeToUtcTimeWithoutZone(time)).isEqualTo(convertKoreaTimeToUtcTimeWithZone(time));
	}
	
	private String convertKoreaTimeToUtcTimeWithoutZone(String time) {
		return LocalDateTime.parse(time, DATETIME_FORMAT)
				.minusHours(DIFF_TIME)
				.format(DATETIME_FORMAT);
	}
	
	private String convertKoreaTimeToUtcTimeWithZone(String time) {
		return LocalDateTime.parse(time, DATETIME_FORMAT)
				.atZone(ZoneId.of("Asia/Seoul"))
				.withZoneSameInstant(ZoneOffset.UTC)
				.format(DATETIME_FORMAT);
	}
}
