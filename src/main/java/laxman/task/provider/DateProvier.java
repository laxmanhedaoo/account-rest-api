package laxman.task.provider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateProvier {

	public static String dateAsString(Date date) {
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		dataFormat.setTimeZone(timeZone);
		return dataFormat.format(date);
	}

}
