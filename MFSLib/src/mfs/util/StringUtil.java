package mfs.util;

public class StringUtil {

	public static class ThaiName {
		private String firstName;
		private String lastName;

		public ThaiName(String firstName, String lastName) {
			this.firstName = firstName;
			this.lastName = lastName;
		}

		public String getFirstName() {
			return firstName;
		}

		public String getLastName() {
			return lastName;
		}
	}

	public static ThaiName splitTHName(String thName) {

		final String[] stringArray = thName.split(" ");
		if (stringArray.length < 2) {
			return new ThaiName(thName, "");
		} else if (stringArray.length == 2) {
			return new ThaiName(stringArray[0], stringArray[1]);
		} else if (stringArray.length > 2) {

			final StringBuilder lastNameSb = new StringBuilder();
			for (int i = 1; i < stringArray.length; i++) {
				lastNameSb.append(stringArray[i]);
			}
			return new ThaiName(stringArray[0], lastNameSb.toString());
		} else {
			return new ThaiName("", "");
		}
	}

	public static String getString(String str) {
		if (str == null) {
			return "";
		} else {
			return str.trim();
		}
	}

	/**
	 * From
	 * http://stackoverflow.com/questions/309424/read-convert-an-inputstream
	 * -to-a-string
	 */
	public static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	public static String padSpaceLeft(String str, int fullLenght) {

		return String.format("%" + fullLenght + "s", str);
	}

	public static String padSpaceRight(String str, int fullLenght) {

		return String.format("%-" + fullLenght + "s", str);
	}
	
	public static String padZeroLeft(String str, int fullLenght) {

		return String.format("%" + fullLenght + "s", str).replaceAll(" ","0");
	}

}
