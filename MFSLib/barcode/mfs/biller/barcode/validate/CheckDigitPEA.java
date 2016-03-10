package mfs.biller.barcode.validate;

import org.apache.commons.logging.Log;

public class CheckDigitPEA extends CheckDigit {

	public CheckDigitPEA(Log log) {
		super(log);
	}

	private int multiPEA(String str, int[] patten_multi) {
		int sum = 0;
		printDebug(str + ":" + patten_multi);
		for (int i = 0; i < patten_multi.length; i++) {
			int index = new Integer(str.substring(i, i + 1));
			int multi = patten_multi[i];

			int temp_sum = (index * multi);

			printDebug("multiPEA: " + index + "*" + multi + " ="
					+ (index * multi) + " , temp_sum=" + temp_sum);

			if (temp_sum > 9) {
				String sDigit = "" + temp_sum;
				temp_sum = new Integer(sDigit.substring(0, 1))
						+ new Integer(sDigit.substring(1));
			}

			sum += temp_sum;

			printDebug("multiPEA: " + temp_sum + " , sum=" + sum);
		}

		return sum;
	}

	public boolean calcDigitPEA(String bar_code1, String bar_code2)
			throws CheckDigitException {
		// boolean result=false;

		// barcode 1
		int maxLength_barcode_1 = 32;
		// Barcode 2
		int maxLength_barcode_2 = 30;
		// Barcode all max length
		int barcode_max_length = maxLength_barcode_1 + maxLength_barcode_2;
		// Barcode patten_multi
		int[] patten_multi = { 5, 7, 8, 5, 7, 8, 5, 7, 8, 5, 7, 8, 5, 7, 8, 5,
				7, 8, 5, 7, 8, 5, 7, 8, 5, 7, 8, 5, 7, 8, 5, 7, 8, 5, 7, 8, 5,
				7, 8, 5, 7, 8, 5, 7, 8, 5, 7, 8, 5, 7, 8, 5, 7, 8, 5, 7, 8, 5 };
		printDebug("multiPEA patten length: " + patten_multi.length);

		int bar_code1_length = bar_code1.length();

		int bar_code2_length = bar_code2.length();

		String barcode = "";

		if (maxLength_barcode_1 == bar_code1_length) {
			barcode = new StringBuffer(bar_code1).append(bar_code2).toString();
		} else if (maxLength_barcode_1 == bar_code2_length) {
			barcode = new StringBuffer(bar_code2).append(bar_code1).toString();
		}

		if (barcode_max_length != barcode.length())
			throw checkDigitException("PEA Barcode is unknow.:" + barcode);

		String barcode_checkDigit = barcode.substring(barcode_max_length - 2);

		int barcode_head = new Integer(barcode.substring(0, 3));

		int mod3 = barcode_head / 3;
		printDebug("PEA mod3:" + mod3 + " value=" + barcode_head);

		barcode = barcode.substring(3, barcode.length() - 2);

		switch (mod3) {
		case 0:
			barcode = "8" + barcode;
			break;

		case 1:
			barcode = "5" + barcode;
			break;

		case 2:
			barcode = "7" + barcode;
			break;

		default:
			throw checkDigitException("PEA Barcode  mod(3) not found.");
		}

		// String barcode

		printDebug("PEA check Digit: " + barcode_checkDigit
				+ " ,barcode length" + barcode.length());

		int sum = multiPEA(barcode, patten_multi);

		int sum_multi = sum * 11;

		String sum_barcode_checkDigit = "";

		if (sum_multi > 99) {
			String s_sum_multi = "" + sum_multi;
			sum_barcode_checkDigit = s_sum_multi
					.substring(s_sum_multi.length() - 2);
		} else {
			sum_barcode_checkDigit = "" + sum_multi;
		}

		printDebug("PEA SUM =" + sum + " *11 =" + sum_multi + " / 100 ="
				+ sum_barcode_checkDigit);

		if (barcode_checkDigit.equals(sum_barcode_checkDigit)) {
			return true;
		} else {
			throw checkDigitException("PEA Barcode check digit not match.:"
					+ barcode_checkDigit + " != " + sum_barcode_checkDigit);
		}

	}
}
