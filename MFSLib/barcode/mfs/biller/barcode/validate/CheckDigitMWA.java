package mfs.biller.barcode.validate;

import org.apache.commons.logging.Log;

public class CheckDigitMWA extends CheckDigit {

	// not yet
	public CheckDigitMWA(Log log) {
		super(log);
	}

	private int multiMWA(String str, int[] patten_multi) {
		int sum = 0;
		printDebug(str + ":" + patten_multi);
		for (int i = 0; i < patten_multi.length; i++) {
			int index = new Integer(str.substring(i, i + 1));
			int multi = patten_multi[i];

			int temp_sum = (index * multi);

			printDebug("multiMWA: " + index + "*" + multi + " ="
					+ (index * multi) + " , temp_sum=" + temp_sum);

			if (temp_sum > 9) {
				String sDigit = "" + temp_sum;
				temp_sum = new Integer(sDigit.substring(0, 1))
						+ new Integer(sDigit.substring(1));
			}

			sum += temp_sum;

			printDebug("multiMWA: " + temp_sum + " , sum=" + sum);
		}

		return sum;
	}

	public boolean checkAcc(String acc) throws CheckDigitException {

		// Account pattern
		int[] pattern_acc = { 2, 1, 2, 1, 2, 1, 2 };
         
		int maxLength_acc = 8;

		if (maxLength_acc != acc.length())
			throw checkDigitException("MWA Account invalid length.:" + acc);

		String digit_acc = acc.substring(acc.length() - 1);
		acc = acc.substring(0, acc.length() - 1);

		// Account
		String sumacc = "" + multiMWA(acc, pattern_acc);
		if (sumacc.length() > 1) {
			sumacc = sumacc.substring(sumacc.length() - 1);
			if(!sumacc.equals("0")){
				sumacc = (10 - Integer.valueOf(sumacc))+"";
				}
		}
		
		log.debug("digit account:"+sumacc);
		if (digit_acc.equals(sumacc))
			return true;
		else
			return false;

	}

	public boolean checkInv(String inv) throws CheckDigitException {

		// Invoice pattern
		int[] pattern_inv = { 1, 2, 1, 2, 1, 2 };

		int maxLength_inv = 7;

		if (maxLength_inv != inv.length())
			throw checkDigitException("MWA invoice no. invalid length.:" + inv);

		String digit_inv = inv.substring(inv.length() - 1);
		inv = inv.substring(0, inv.length() - 1);

		// Invoice
		String suminv = "" + multiMWA(inv, pattern_inv);
		int suminv1 = Integer.valueOf(suminv)%10; 
		if(suminv1!=0)
			suminv1 = 10-suminv1;
		/*if (suminv.length() > 1) {
			suminv = suminv.substring(suminv.length() - 1);
			//suminv = (10 - Integer.valueOf(suminv))+"";
			if(!suminv.equals("0")){
				suminv = (10 - Integer.valueOf(suminv))+"";
			}
		}*/
		log.debug("digit Inv:"+suminv);
		if (digit_inv.equals(Integer.toString(suminv1)))
			return true;
		else
			return false;
	}

	public boolean checkAccountAndInvoice(String ref1)
			throws CheckDigitException {

		int maxLength_invacc = 15;

		if (maxLength_invacc != ref1.length())
			throw checkDigitException("MWA invoice no. invalid length.:"
					+ maxLength_invacc);

		if (checkAcc(ref1.substring(0, 6))) {
			if (checkInv(ref1.substring(7))) {
				return true;
			} else {
				throw checkDigitException("MWA Barcode check digit account not match.:"
						+ ref1.substring(7));
			}
		} else {
			throw checkDigitException("MWA Barcode check digit invoice not match.:"
					+ ref1.substring(0, 6));
		}

	}

	public boolean calcDigitMWA(String bar_code1) throws CheckDigitException {
		// boolean result=false;

		// barcode 1
		int maxLength_barcode_1 = 36;

		int bar_code1_length = bar_code1.length();

		String barcode = "";

		if (maxLength_barcode_1 != bar_code1_length)
			throw checkDigitException("MWA Barcode is unknow.:" + barcode);

		if (checkAcc(bar_code1.substring(5, 12))) {
			if (checkInv(bar_code1.substring(13, 19))) {
				return true;
			} else {
				throw checkDigitException("MWA Barcode check digit account not match.:"
						+ bar_code1.substring(13, 19));
			}
		} else {
			throw checkDigitException("MWA Barcode check digit invoice not match.:"
					+ bar_code1.substring(5, 12));
		}

	}
}
