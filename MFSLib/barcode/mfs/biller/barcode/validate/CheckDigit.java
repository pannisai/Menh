package mfs.biller.barcode.validate;

import org.apache.commons.logging.Log;

public class CheckDigit {

	protected final Log log;

	public static enum CheckDigitType {
		PEA, MWA, PWA
	};

	public static String TYPE_PEA = "PEA";
	public static String TYPE_PWA = "PWA";
	public static String TYPE_MEA = "MEA";

	public CheckDigit() {
		this.log = null;
	}

	public CheckDigit(Log log) {
		this.log = log;
	}

	protected void printDebug(String msg) {
		if (log != null)
			log.debug(msg);
	}

	protected CheckDigitException checkDigitException(String msg)
			throws CheckDigitException {

		return new CheckDigitException(msg);
	}

	protected CheckDigitException checkDigitException(String msg, Throwable e)
			throws CheckDigitException {

		return new CheckDigitException(msg, e);
	}

	protected CheckDigitException checkDigitException(Throwable e)
			throws CheckDigitException {

		return new CheckDigitException(e);
	}

	public boolean billerCheckDigit(String check_type, String... barcode)
			throws CheckDigitException {
		CheckDigitType checkDigitType;

		if (TYPE_PEA.equals(check_type)) {
			checkDigitType = CheckDigitType.PEA;
		} else if (TYPE_MEA.equals(check_type)) {
			checkDigitType = CheckDigitType.MWA;
		} else if (TYPE_PWA.equals(check_type)) {
			checkDigitType = CheckDigitType.PWA;
		} else {
			throw checkDigitException("check digit unknow.");
		}

		if (null != barcode && 0 != barcode.length) {
			int barcode_num = barcode.length;
			if (1 == barcode_num) {
				return billerCheckDigit(checkDigitType, barcode[0]);
			} else if (2 == barcode_num) {
				return billerCheckDigit(checkDigitType, barcode[0], barcode[1]);
			} else if (3 == barcode_num) {
				return billerCheckDigit(checkDigitType, barcode[0], barcode[1],
						barcode[3]);
			} else {
				throw checkDigitException("barcode not support.");
			}
		} else {
			throw checkDigitException("barcode is null or zero.");
		}

	}

	public boolean billerCheckDigit(CheckDigitType check_type, String bar_code1)
			throws CheckDigitException {

		if (isNullOrEmpty(bar_code1))
			return false;
		return calcDigit(check_type, bar_code1, "", "");

	}

	public boolean billerCheckDigit(CheckDigitType check_type,
			String bar_code1, String bar_code2) throws CheckDigitException {

		if (isNullOrEmpty(bar_code1, bar_code2))
			return false;
		return calcDigit(check_type, bar_code1, bar_code2, "");
	}

	public boolean billerCheckDigit(CheckDigitType check_type,
			String bar_code1, String bar_code2, String bar_code3)
			throws CheckDigitException {

		if (isNullOrEmpty(bar_code1, bar_code2, bar_code3))
			return false;
		return calcDigit(check_type, bar_code1, bar_code2, bar_code3);
	}

	private boolean isNullOrEmpty(String str) throws CheckDigitException {
		if (null == str || "".equals(str))
			throw checkDigitException("barcode is null or empty.");

		return false;

	}

	private boolean isNullOrEmpty(String... str) throws CheckDigitException {
		for (String s : str)
			isNullOrEmpty(s);
		return false;
	}

	private boolean calcDigit(CheckDigitType check_type, String bar_code1,
			String bar_code2, String bar_code3) throws CheckDigitException {

		switch (check_type) {
		case PEA:
			CheckDigitPEA pea = new CheckDigitPEA(log);
			return pea.calcDigitPEA(bar_code1, bar_code2);

		case MWA:

			CheckDigitMWA mwa = new CheckDigitMWA(log);
			return mwa.calcDigitMWA(bar_code1);

		case PWA:
			CheckDigitPWA pwa = new CheckDigitPWA(log);
			return pwa.calcDigitPWA(bar_code1);

		default:
			throw checkDigitException("Barcode check digit type not found.");
		}

	}

}
