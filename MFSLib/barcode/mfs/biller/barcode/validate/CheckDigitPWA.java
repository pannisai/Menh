package mfs.biller.barcode.validate;

import org.apache.commons.logging.Log;

public class CheckDigitPWA extends CheckDigit {

	public CheckDigitPWA(Log log) {
		super(log);
	}

	private int multiPWA(String str, int[] patten_multi) {
		int sum = 0;
		printDebug(str + ":" + patten_multi);
		for (int i = 0; i < patten_multi.length; i++) {
			int index = new Integer(str.substring(i, i + 1));
			int multi = patten_multi[i];

			sum += (index * multi);

			printDebug("multiPWA: " + index + "*" + multi + " ="
					+ (index * multi) + " , sum=" + sum);
		}

		return sum;
	}

	private boolean checkCustomerNoPWA(String payee) throws CheckDigitException {
		// Check Customer num digit
		int[] payee_running_digit_multiplier = { 1, 2, 3, 5, 7, 11 };
		String payee_running = payee.substring(4, 10);

		int sum_payee_runing = multiPWA(payee_running,
				payee_running_digit_multiplier);

		int check_digit = 0;
		// mod 10
		if (sum_payee_runing > 9) {
			String s = "" + sum_payee_runing;
			check_digit = new Integer(s.substring(s.length() - 1));
		} else {
			check_digit = sum_payee_runing;
		}

		int payee_check_digit = new Integer(payee.substring(payee.length() - 1));

		if (payee_check_digit == check_digit) {
			return true;
		} else {
			printDebug("payee code check digit not match.:" + payee_check_digit
					+ " != " + check_digit);
			throw checkDigitException("payee code check digit not match.:"
					+ payee_check_digit + " != " + check_digit);
		}

	}

	public boolean calcDigitPWA(String bar_code1) throws CheckDigitException {
		// boolean result=false;
		// Old barcode
		int maxLength_barcode_type1 = 41;
		// New Barcode
		int maxLength_barcode_type2 = 45;

		// Barcode Type 1
		if (maxLength_barcode_type1 == bar_code1.length()) {
			printDebug("calcDigitPWA Type 1  length:" + maxLength_barcode_type1);
			// initail value
			int sumDigit = 0;
			int multiplier = 52;
			// int sub_stirng_digit = 2 ;

			// Step 1: Multi and Sum
			// Multi and Sum 1.1: Institution code
			int[] institution_patten_multi = { 0, 1, 2, 3, 4, 5, 6 };
			String institution = bar_code1.substring(0,
					institution_patten_multi.length);

			printDebug("Step 1: institution =" + institution + " length="
					+ institution.length());

			sumDigit += multiPWA(institution, institution_patten_multi);

			printDebug("Step 1: institution sum" + sumDigit);

			// Multi and Sum 1.2: payee code
			int[] payee_digit_multiplier = { 0, 1, 2, 3, 4, 5, 6 };
			String payee = bar_code1.substring(7,
					7 + payee_digit_multiplier.length);
			printDebug("Step 1: payee code =" + payee + " length="
					+ payee.length());

			sumDigit += multiPWA(payee, payee_digit_multiplier);

			printDebug("Step 1: payee sum" + sumDigit);

			// Multi and Sum 1.3: invoice code
			int[] invc_digit_multiplier = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
			String invc = bar_code1.substring(14,
					14 + invc_digit_multiplier.length);
			printDebug("Step 1: invoice code =" + invc + " length="
					+ invc.length());
			sumDigit += multiPWA(invc, invc_digit_multiplier);

			printDebug("Step 1: invc sum" + sumDigit);

			// Multi and Sum 1.4: amount
			int[] amnt_digit_multiplier = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
			String amnt = bar_code1.substring(26, 26 + 9);

			printDebug("Step 1: actual amount  =" + amnt + " length="
					+ amnt.length());

			// Multi and Sum 1.4.1: amount Remove Zero
			amnt = "" + new Integer(amnt);

			int[] new_amnt_digit_multiplier = new int[amnt.length()];

			for (int i = 0; i < amnt.length(); i++) {
				new_amnt_digit_multiplier[i] = amnt_digit_multiplier[i];
			}

			printDebug("Step 1: after remove zero amount =" + amnt + " length="
					+ amnt.length());

			sumDigit += multiPWA(amnt, new_amnt_digit_multiplier);

			printDebug("Step 1: summary =" + sumDigit);

			// Step 2: Multi
			int sum_multi = sumDigit * multiplier;

			printDebug("Step 2: Multi " + sumDigit + "*" + multiplier + " ="
					+ sum_multi);

			int check_digit = 0;
			// Step 3: Mod
			if (sum_multi > 99) {
				String s = "" + sum_multi;
				check_digit = new Integer(s.substring(s.length() - 2));
			} else {
				check_digit = sum_multi;
			}

			printDebug("Step 3: Check Digit = " + check_digit);

			// Step 1: compare check digit

			int check_digit_length = 2;

			int bar_code1_check_digit = new Integer(bar_code1.substring(24,
					24 + check_digit_length));

			if (bar_code1_check_digit == check_digit) {
				return true;
			} else {
				throw new CheckDigitException(
						"PWA Barcode check digit not match.:" + bar_code1
								+ " != " + check_digit);
			}

			// Check Invoice digit

			// Barcode Type 2
		} else if (maxLength_barcode_type2 == bar_code1.length()) {

			printDebug("calcDigitPWA Type 2  length:" + maxLength_barcode_type2);
			// initail value
			int sumDigit = 0;
			int multiplier = 57;
			// int sub_stirng_digit = 2 ;

			// Step 1: Multi and Sum

			// Multi and Sum 1.1: invoice code
			int[] invc_digit_multiplier = { 1, 2, 3, 5, 7, 11, 13, 17, 19, 23,
					29, 31, 41 };
			String invc = bar_code1.substring(15,
					15 + invc_digit_multiplier.length);
			printDebug("Step 1: invoice code =" + invc + " length="
					+ invc.length());
			sumDigit += multiPWA(invc, invc_digit_multiplier);

			printDebug("Step 1: invc sum" + sumDigit);

			// Multi and Sum 1.2: payee code
			int[] payee_digit_multiplier = { 1, 2, 3, 5, 7, 11, 13, 17, 19, 23,
					29 };
			String payee = bar_code1.substring(4,
					4 + payee_digit_multiplier.length);
			printDebug("Step 1: payee code =" + payee + " length="
					+ payee.length());

			sumDigit += multiPWA(payee, payee_digit_multiplier);

			printDebug("Step 1: payee sum" + sumDigit);

			// Multi and Sum 1.3: Institution code
			int[] institution_patten_multi = { 1, 2, 3, 5 };
			String institution = bar_code1.substring(0,
					institution_patten_multi.length);

			printDebug("Step 1: institution =" + institution + " length="
					+ institution.length());

			sumDigit += multiPWA(institution, institution_patten_multi);

			printDebug("Step 1: institution sum" + sumDigit);

			// Multi and Sum 1.4: amount
			int[] amnt_digit_multiplier = { 1, 2, 3, 5, 7, 11, 13, 17, 19 };
			String amnt = bar_code1.substring(30, 30 + 9);

			printDebug("Step 1: actual amount  =" + amnt + " length="
					+ amnt.length());

			// Multi and Sum 1.4.1: amount Remove Zero
			amnt = "" + new Integer(amnt);

			int[] new_amnt_digit_multiplier = new int[amnt.length()];

			for (int i = 0; i < amnt.length(); i++) {
				new_amnt_digit_multiplier[i] = amnt_digit_multiplier[i];
			}

			printDebug("Step 1: after remove zero amount =" + amnt + " length="
					+ amnt.length());

			sumDigit += multiPWA(amnt, new_amnt_digit_multiplier);

			printDebug("Step 1: summary =" + sumDigit);

			// Step 2: Multi
			int sum_multi = sumDigit * multiplier;

			printDebug("Step 2: Multi " + sumDigit + "*" + multiplier + " ="
					+ sum_multi);

			int check_digit = 0;
			// Step 3: Mod
			if (sum_multi > 99) {
				String s = "" + sum_multi;
				check_digit = new Integer(s.substring(s.length() - 2));
			} else {
				check_digit = sum_multi;
			}

			printDebug("Step 3: Check Digit = " + check_digit);

			// Step 1: compare check digit

			int check_digit_length = 2;

			int bar_code1_check_digit = new Integer(bar_code1.substring(28,
					28 + check_digit_length));

			if (bar_code1_check_digit == check_digit) {
				return checkCustomerNoPWA(payee);
			} else {
				throw checkDigitException("PWA Barcode check digit not match.:"
						+ bar_code1 + " != " + check_digit);
			}

		} else {
			// Barcode type not match
			throw checkDigitException("PWA Barcode is unknow.:" + bar_code1);
		}

	}

}
