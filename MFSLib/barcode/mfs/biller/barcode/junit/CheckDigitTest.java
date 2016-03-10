package mfs.biller.barcode.junit;

import mfs.biller.barcode.validate.CheckDigitException;

import org.junit.Test;

public class CheckDigitTest {

	@Test
	public void testCheckDigitPWA() throws CheckDigitException {
		String institution = "5530211";
		String payeeCode = "1042009";
		String invoiceCode = "4600138814";
		String checkDigit = "40";
		String amount = "000016900"; // length 9
		String duedate = "200356";

		String barcode = institution + payeeCode + invoiceCode + checkDigit
				+ amount + duedate;

		System.out.println("Barcode:" + barcode + "payee="
				+ barcode.substring(7, 14));

		// CheckDigit chk = new CheckDigit(true);
		//
		// System.out.println("Result:"+chk.billerCheckDigit(CheckDigitType.PWA,
		// barcode));
	}

	@Test
	public void testCheckDigitPWA2() throws CheckDigitException {
		String institution = "1184";
		String payeeCode = "11840037132";
		String invoiceCode = "1184560000003";
		String checkDigit = "85";
		String amount = "000008560"; // length 9
		String duedate = "200356";

		String barcode = institution + payeeCode + invoiceCode + checkDigit
				+ amount + duedate;

		System.out.println("Barcode:" + barcode + "payee="
				+ barcode.substring(7, 14));
		//
		// CheckDigit chk = new CheckDigit(true);
		//
		// System.out.println("Result:"+chk.billerCheckDigit(CheckDigitType.PWA,
		// barcode));
	}

	@Test
	public void testCheckDigitPEA() throws CheckDigitException {

		String bar1 = "00708101020000806308511107511202";
		String bar2 = "000001434743000134000002023998";

		// CheckDigit chk = new CheckDigit(true);
		//
		// System.out.println("Result:"+chk.billerCheckDigit("PEA", new
		// String[]{bar1, bar2}));

		// 87081010200008063085111075112020000014347430001340000020239
	}
}
