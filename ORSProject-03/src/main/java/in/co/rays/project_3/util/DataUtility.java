package in.co.rays.project_3.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.jasperreports.renderers.SimpleDataRenderer;

public class DataUtility {

	public static final String APP_DATE_FORMATE = "dd/MM/yyyy";
	public static final String APP_TIME_FORMATE = "MM/dd/yyyy HH:mm:ss";

	public static final SimpleDateFormat Formatter = new SimpleDateFormat(APP_DATE_FORMATE);
	public static final SimpleDateFormat timeFormatter = new SimpleDateFormat(APP_TIME_FORMATE);

	public static String getString(String val) {
		if (DataValidator.isNotNull(val)) {
			return val.trim();
		} else {
			return val;
		}
	}

	public static String getStringData(Object val) {
		if (val != null) {
			return val.toString();
		} else {
			return "";
		}
	}

	public static int getInt(String val) {
		if (DataValidator.isInteger(val)) {
			return Integer.parseInt(val);
		} else {
			return 0;
		}
	}

	public static Long getLong(String val) {
		System.out.println("...............in dataUtility............." + val);
		if (DataValidator.isLong(val)) {
			System.out.println("..............in dataUtility" + val + ",,,,,," + Long.parseLong(val));
			return Long.parseLong(val);

		} else {
			return (long) 0;
		}
	}

	public static Date getDate(String val) {
		System.out.println("oooooooooooooooo" + val);
		Date date = null;
		try {
			date = Formatter.parse(val);

		} catch (Exception e) {

		}
		System.out.println("..........pppp" + date);
		return date;
	}

	public static String getDateString(Date date) {
		try {
			return Formatter.format(date);
		} catch (Exception e) {

		}
		return "";
	}

	public static Date getDate(Date date, int day) {
		return null;
	}

	public static Timestamp getTimestamp(String val) {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(timeFormatter.parse(val).getTime());

		} catch (Exception e) {
			return null;
		}
		return timeStamp;
	}

	public static Timestamp getTimeStamp(long l) {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(l);
		} catch (Exception e) {
			return null;
		}
		return timeStamp;
	}

	public static Timestamp getCurrentTimeStamp() {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(new Date().getTime());
		} catch (Exception e) {

		}
		return timeStamp;
	}

	public static long getTimestamp(Timestamp tm) {
		try {
			return tm.getTime();
		} catch (Exception e) {
			return 0;
		}
	}

}
