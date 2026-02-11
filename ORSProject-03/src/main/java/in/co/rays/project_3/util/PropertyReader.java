package in.co.rays.project_3.util;

import java.util.ResourceBundle;

public class PropertyReader {

	private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.project_3.bundle.system");

	private static String getValue(String key) {
		String val = null;
		try {
			val = rb.getString(key);
		} catch (Exception e) {
			val = key;

		}
		return val;
	}

	public static String getValue(String key, String param) {
		String msg = getValue(key);
		msg.replace("{0}", param);
		return msg;
	}

	public static String getValue(String key, String[] params) {
		String msg = getValue(key);
		for (int i = 0; i < params.length; i++) {
			msg = msg.replace("{" + i + "}", params[i]);
		}
		return msg;
	}
}
