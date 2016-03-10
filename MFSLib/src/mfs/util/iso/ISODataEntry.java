package mfs.util.iso;

/**
 * A Entry of Field in the pack of ISO Trailing Data.
 * 
 * @author Apichart
 * 
 */
public class ISODataEntry {

	public static String BLANK_DEF = "";

	private int order = -1;
	private String fieldName = "";
	private int length = 0;
	private String paddingChar = "";
	private String defaultValue = "";

	public ISODataEntry(int order, String fieldName, int length,
			String paddingChar, String defaultValue) {
		this.order = order;
		this.fieldName = fieldName;
		this.length = length;
		this.paddingChar = paddingChar;
		this.defaultValue = defaultValue;

	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getPaddingChar() {
		return paddingChar;
	}

	public void setPaddingChar(String paddingChar) {
		this.paddingChar = paddingChar;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

}