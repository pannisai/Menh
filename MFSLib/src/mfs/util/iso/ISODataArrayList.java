package mfs.util.iso;

import java.util.ArrayList;

/**
 * Arraylist containing the Data Pack Entry of ISO MSG Trailing.
 * 
 * @author Apichart
 * 
 */
public class ISODataArrayList extends ArrayList<ISODataEntry> {

	private static final long serialVersionUID = 1L;

	public void add(String fieldName, int length, String paddingChar,
			String defaultValue) throws IllegalArgumentException {

		int position = this.size();
		add(position, fieldName, length, paddingChar, defaultValue);

	}

	public void add(int position, String fieldName, int length,
			String paddingChar, String defaultValue)
			throws IllegalArgumentException {

		if (position < 0 || fieldName.isEmpty() || length <= 0
				|| paddingChar.length() > 1) {

			System.out
					.println("ISODataArrayList|add|INVALID Input values for creating a Entry.");
			throw new IllegalArgumentException(
					"ISODataArrayList|add|INVALID Input values for creating a Entry.");
		} else if (length < defaultValue.length()) {
			System.out
					.println("ISODataArrayList|add|The default value longer than maximum length. fieldName : ["
							+ fieldName + "]");

			throw new IllegalArgumentException(
					"ISODataArrayList|add|The default value longer than maximum length. fieldName : ["
							+ fieldName + "]");
		}

		add(new ISODataEntry(position, fieldName, length, paddingChar,
				defaultValue));

	}

}
