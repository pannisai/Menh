package mfs.util.iso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import mfs.util.DebugUtil;
import mfs.util.ISO8583Util;
import mfs.util.biller.ConstantUtil;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

/**
 * Class to define the Fundamo Trailing Data Pack which initially is the data in
 * position 120 in ISO MSG.
 * 
 * Referer from "BillpayAPI_withEE.xls document".
 * 
 * @author Apichart
 * 
 */
public class ISODataUtil {

	public static String VERIFY = "VER";
	public static String CONFIRM = "CON";

	private static ISODataArrayList dataMainTagList = null;
	private static ISODataArrayList dataTrailingPackListVerify = null;
	private static ISODataArrayList dataTrailingPackListConfirm = null;
	private static int trailingStringVerifyLength = -1;
	private static int trailingStringConfirmLength = -1;

	/**
	 * This is the data which will be fit into certain position on the ISOMsg
	 * slot. They may be not needed to be padded to fit in the data slot.
	 */
	private static void initialzeDataMainTag() {

		dataMainTagList = new ISODataArrayList();

		final Date currentDate = new Date();
		// The maxlength got to be referenced from the xml and the excel. But it
		// happened that both are not exactly same.
		// somehow currently we not using the padding by this maxlength as not
		// necessary and to avoid this issue.
		// In the end, the extra data we needed in the excel it may got to be
		// packed in the 120 position.
		dataMainTagList.add(2, "PAN Length", 19, "", "923467137031");
		dataMainTagList.add(3, "Processing Codes", 6, "", "720000");
		dataMainTagList.add(4, "Amount, Transaction", 12, "", "000000000500");
		dataMainTagList.add(7, "Transaction DateTime", 10, "",
				new SimpleDateFormat("MMddHHmmss", Locale.US)
						.format(currentDate));
		dataMainTagList.add(11, "Systems Trace No", 6, "", "123456");
		dataMainTagList.add(12, "Time, Local Transaction", 6, "",
				new SimpleDateFormat("HHmmss", Locale.US).format(currentDate));
		dataMainTagList.add(13, "Date, Local Transaction", 4, "",
				new SimpleDateFormat("MMdd", Locale.US).format(currentDate));

		// 16/07/2013 : Used only above. Tag 100, not used for billerID anymore.
		// it moved to somewhere in TAG120.
		// dataMainTagList.add(18, "Merchant Type", 4, "", "6012");
		// dataMainTagList.add(22, "Point of Service Entry Mode", 3, "", "021");
		// dataMainTagList.add(32, "Acquirer Identification Length", 11, "",
		// "639390");
		// dataMainTagList.add(37, "Retrieval Ref No.", 12, "", "936948003429");
		dataMainTagList.add(41, "Terminal Id", 16, "", "92346713");
		// dataMainTagList.add(42, "Card Acceptor ID Code", 15, "",
		// " 923467137031");
		// dataMainTagList.add(43, "Card Acceptor Name and Location", 40, "",
		// "Fundamo Simulation Test");
		// dataMainTagList.add(49, "Currency Code, Transaction", 3, "", "900");
		// // comment 52 as it is a binary, not sure what to fit in here and how
		// by
		// // now. 16 in length as 64bit / 16 hexa but in binary type.
		// // dataMainTagList.add(52, "PIN Data", 16, "", "");
		// dataMainTagList.add(100, "Receiving Institution ID Code", 11, "",
		// "00000000031");

	}

	/**
	 * This is the data which will packed into position 120 of the fundamo iso
	 * msg. This portion of String will needed to be padded to its exact length.
	 */
	private static void initialzeDataTrailingPack() {

		final ISODataArrayList verifyList = new ISODataArrayList();

		verifyList.add("TransactionRef", 15, "", getTransactionRef());
		verifyList.add("BillerID", 10, "", "-dyn-");
		verifyList.add("AggregatorID", 10, "", "73");
		verifyList.add("TransactionChannel", 10, "", "FDMChnl01");
		verifyList.add("UserAgent", 20, "", "UserAgent001");
		verifyList.add("CustMobileNo", 15, "", "66812345678");
		verifyList.add("ref1", 100, "", "ref1");
		verifyList.add("ref2", 100, "", "ref2");
		verifyList.add("ref3", 100, "", "ref3");
		verifyList.add("ref4", 100, "", "ref4");
		verifyList.add("ref5", 100, "", "ref5");
		verifyList.add("ref6", 100, "", "ref6");
		verifyList.add("FundType", 2, "", "01");

		dataTrailingPackListVerify = verifyList;

		// collect the sum of all trailing string.
		trailingStringVerifyLength = 0;
		for (ISODataEntry eachEntry : dataTrailingPackListVerify) {
			trailingStringVerifyLength += eachEntry.getLength();
		}

		final ISODataArrayList confirmList = new ISODataArrayList();
		// Confirm will has more fields.
		confirmList.addAll(verifyList);
		confirmList.add("VAT rate", 2, "0", "07");
		confirmList.add("VAT amount", 11, "0", "35");
		confirmList.add("Fee amount (customer)", 11, "0", "107");
		confirmList.add("Fee VAT Exc(customer)", 11, "0", "100");
		confirmList.add("Fee VAT Amount(customer)", 11, "0", "7");
		confirmList.add("Fee amount (biller)", 11, "0", "214");
		confirmList.add("Fee VAT Exc(biller)", 11, "0", "14");
		confirmList.add("Fee VAT Amount(biller)", 11, "0", "7");

		dataTrailingPackListConfirm = confirmList;
		// collect the sum of all trailing string.
		trailingStringConfirmLength = 0;
		for (ISODataEntry eachEntry : dataTrailingPackListConfirm) {
			trailingStringConfirmLength += eachEntry.getLength();
		}

	}

	/**
	 * Get Tag list of ISOMSG.
	 * 
	 */
	public static ISODataArrayList getISOMainTagList() {
		initialzeDataMainTag();
		return dataMainTagList;
	}

	/**
	 * Get Field list of ISOMSG POS120 Trailing string AS Per Type.
	 * 
	 */
	public static ISODataArrayList getISOTrailingDataPackByType(String type) {

		if (type.trim().equalsIgnoreCase(ISODataUtil.VERIFY)) {
			return getISOTrailingDataPackVerify();
		} else if (type.trim().equalsIgnoreCase(ISODataUtil.CONFIRM)) {
			return getISOTrailingDataPackConfirm();
		} else {
			return new ISODataArrayList();
		}
	}

	/**
	 * Get Field list of ISOMSG POS120 Trailing string For Verify.
	 * 
	 */
	public static ISODataArrayList getISOTrailingDataPackVerify() {
		initialzeDataTrailingPack();
		return dataTrailingPackListVerify;
	}

	/**
	 * Get Field list of ISOMSG POS120 Trailing string For Confirm.
	 * 
	 */
	public static ISODataArrayList getISOTrailingDataPackConfirm() {
		initialzeDataTrailingPack();
		return dataTrailingPackListConfirm;
	}

	private static int getTrailingStringLengthByType(String type) {
		// invoke init to make sure the value is set.
		initialzeDataTrailingPack();
		if (type.trim().equalsIgnoreCase(ISODataUtil.VERIFY)) {
			return trailingStringVerifyLength;
		} else if (type.trim().equalsIgnoreCase(ISODataUtil.CONFIRM)) {
			return trailingStringConfirmLength;
		} else {
			return 0;
		}
	}

	public static String getTransactionRef() {
		String date = new SimpleDateFormat("yyMMdd", Locale.US)
				.format(new Date());
		String time = "" + System.nanoTime();
		time = time.substring(0, 9);
		return date + time;
	}

	// -------------- ISOMSG -----

	/**
	 * To Set ISOMSG with values from the array, controlled by the master list.
	 * 
	 * Tends to be used with the Main Token Index fields only (Not the packing
	 * od pos 120 token.)
	 * 
	 * @param isoMsg
	 * @param masterList
	 * @param valuesList
	 * @throws ISOException
	 */
	public static void setValuesToISOMsg(ISOMsg isoMsg,
			ISODataArrayList masterList, String[] valuesList)
			throws ISOException {

		if (masterList.size() != valuesList.length) {
			throw new ISOException(
					"Size of Master List and Values Array is not equal.");
		}

		final ISODataArrayList isoList = ISODataUtil.getISOMainTagList();
		for (final ISODataEntry eachEntry : isoList) {

			// isoMsg.set(eachEntry.getOrder(), String.format(
			// "%" + eachEntry.getLength() + "s",
			// valuesList[isoList.indexOf(eachEntry)]));
			isoMsg.set(eachEntry.getOrder(),
					valuesList[isoList.indexOf(eachEntry)]);

		}
	}

	/**
	 * Create String from the trailing value array.
	 * 
	 * @param valuesList
	 * @return
	 * @throws ISOException
	 */
	public static String getTrailingStringFromArray(String[] valuesList,
			String type) throws ISOException {

		ISODataArrayList dataTrailingPackList = getDataListByType(type);

		if (dataTrailingPackList.size() != valuesList.length) {
			throw new ISOException(
					"Size of dataTrailingPackList List and Traling Values Array is not equal.");
		}

		final StringBuilder iso120Tag = new StringBuilder();

		for (int i = 0; i < valuesList.length; i++) {
			final String eachField = valuesList[i];

			final ISODataEntry isoEntry = dataTrailingPackList.get(i);

			final String formatString;
			if (isoEntry.getPaddingChar().trim().equalsIgnoreCase("0")) {
				formatString = "%0" + isoEntry.getLength() + "d";
				iso120Tag.append(String.format(formatString,
						Long.parseLong(eachField)));
			} else {
				// %- is for padding right.
				formatString = "%-" + isoEntry.getLength() + "s";
				iso120Tag.append(String.format(formatString, eachField));
			}

		}

		return iso120Tag.toString();
	}

	private static ISODataArrayList getDataListByType(String type) {

		ISODataArrayList dataTrailingPackList = new ISODataArrayList();
		if (type.trim().equalsIgnoreCase(ISODataUtil.VERIFY)) {
			dataTrailingPackList = dataTrailingPackListVerify;
		} else if (type.trim().equalsIgnoreCase(ISODataUtil.CONFIRM)) {
			dataTrailingPackList = dataTrailingPackListConfirm;
		}

		return dataTrailingPackList;
	}

	/**
	 * Get values array from Trailing string bu chopping/substring.
	 * 
	 * @param valuesList
	 * @return
	 * @throws ISOException
	 */
	public static String[] getArrayFromTrailingString(String trailingString,
			String type) throws ISOException {

		if (trailingString.isEmpty()) {
			return null;
		} else if (trailingString.length() != getTrailingStringLengthByType(type)) {

			throw new ISOException(
					"The Trailing String should has exact same length,"
							+ " so the substr can work correctly.. : Length of Trailing String From POS120["
							+ trailingString.length() + "] Expected : ["
							+ getTrailingStringLengthByType(type)
							+ "]\n String=[" + trailingString + "]");
		}

		final ISODataArrayList dataTrailingPackList = getDataListByType(type);

		final String[] valuesArray = new String[dataTrailingPackList.size()];

		try {
			// Unpack by substr.
			int currentIndex = 0;
			for (int i = 0; i < dataTrailingPackList.size(); i++) {
				final ISODataEntry eachField = dataTrailingPackList.get(i);
				final int length = eachField.getLength();
				final int beginIndex = currentIndex;
				// this is diff from stopBit in the input form.
				int endIndex = currentIndex + length;
				// this is also diff from currentBit in the input form.
				currentIndex = endIndex;

				valuesArray[i] = trailingString.substring(beginIndex, endIndex);
				// Trimmed any space padding.
				valuesArray[i] = valuesArray[i].trim();

				DebugUtil.get().print(null,
						"valueArray " + i + ". : [" + valuesArray[i] + "]\n");
			}

		} catch (StringIndexOutOfBoundsException sEx) {

			sEx.printStackTrace();
			throw new ISOException(
					"Exception occurred when do chopping/substring the trailing data of pos 120.");
		}

		return valuesArray;
	}

	/**
	 * Create the ISOMsg object from the parameters.
	 * 
	 * @param mti
	 * @param isoMSGtags
	 * @param isoMSG120Tokens
	 * @return
	 * @throws ISOException
	 */
	public static ISOMsg createISOMSG(String mti, String[] isoMSGtags,
			String[] isoMSG120Tokens) throws ISOException {

		final String type = ISO8583Util.getTypeFromMTI(mti);

		// cross check the parameters.
		if (mti.isEmpty()
				|| isoMSGtags.length != ISODataUtil.getISOMainTagList().size()
				|| isoMSG120Tokens.length != ISODataUtil
						.getISOTrailingDataPackByType(type).size()) {
			throw new ISOException(
					"mti Data is empty OR ISO fields data size from "
							+ "Parameter not matched with the Predefined array."
							+ "DEBUG :: \n\n type["
							+ type
							+ "] \n mti ["
							+ mti
							+ "], isoMSGtags.length["
							+ isoMSGtags.length
							+ "] != ISODataUtil.getISOMainTagList().size()["
							+ ISODataUtil.getISOMainTagList().size()
							+ "], "
							+ "isoMSG120Tokens.length["
							+ isoMSG120Tokens.length
							+ "] != ISODataUtil.getISOTrailingDataPackByType(type).size()["
							+ ISODataUtil.getISOTrailingDataPackByType(type)
									.size() + "]");

		}

		// Here, if the parameter is matched, also the ordering should be the
		// same so we go mapped one by one.
		final ISOMsg isoMsg = new ISOMsg();

		isoMsg.setHeader(ConstantUtil.ISO_HEADER.getBytes());
		// isoMsg.setHeader("ABC123".getBytes());
		isoMsg.setMTI(mti);

		// The Main Tags
		ISODataUtil.setValuesToISOMsg(isoMsg, ISODataUtil.getISOMainTagList(),
				isoMSGtags);
		// Pack the trailing data fields to pos 120 tag.
		isoMsg.set(120,
				ISODataUtil.getTrailingStringFromArray(isoMSG120Tokens, type));

		return isoMsg;
	}

	public static int getDiffSizeVerifyConfirm() {

		return getISOTrailingDataPackConfirm().size()
				- getISOTrailingDataPackVerify().size();
	}

}
