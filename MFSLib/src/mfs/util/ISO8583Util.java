package mfs.util;

import java.math.BigDecimal;

import mfs.bean.FundamoRequestBean;
import mfs.util.biller.ConstantUtil;
import mfs.util.iso.ISODataUtil;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;

public class ISO8583Util {

	public static ISOMsg parseData(String data) throws ISOException {
		GenericPackager packager = new GenericPackager(
				ConstantUtil.ISO_XML_FILE);
		ISOMsg isoMsg = new ISOMsg();
		isoMsg.setPackager(packager);
		isoMsg.unpack(data.getBytes());
		return isoMsg;
	}

	private static String getData(ISOMsg isoMsg, int field, String defValue) {
		return (isoMsg.hasField(field)) ? isoMsg.getString(field) : defValue;
	}

	/**
	 * Convert the Decimal Literal from Inbound to BigDecimal.
	 * 
	 * @param isoMsg
	 * @return
	 */
	private static BigDecimal getTransAmount(ISOMsg isoMsg) {

		String amountString = getData(isoMsg, 4, "0");
		if (amountString != null && amountString.length() > 1) {
			final int splitPoint = amountString.length() - 2;
			final String prePoint = amountString.substring(0, splitPoint);
			final String postPoint = amountString.substring(splitPoint);
			amountString = prePoint + "." + postPoint;
		}
		return new BigDecimal(amountString);

	}

	public static FundamoRequestBean getBillerRequest(ISOMsg isoMsg)
			throws Exception {
		try {

			final FundamoRequestBean bean = new FundamoRequestBean();
			bean.setIsoMsg(isoMsg);

			// --FROM Main TAGs
			bean.setMessageTypeID(isoMsg.getMTI());
			bean.setAccountNo(getData(isoMsg, 2, ""));
			bean.setProcessCode(getData(isoMsg, 3, "00"));
			bean.setTransAmount(getTransAmount(isoMsg));
			bean.setTransDateTime(DateTimeUtil.getDateTimeFromISO8583(getData(
					isoMsg, 7, "")));
			bean.setSystemTraceNo(getData(isoMsg, 11, "")); // 11
			bean.setLocalTransTime(getData(isoMsg, 12, "")); // 12
			bean.setLocalTransDate(getData(isoMsg, 13, ""));// 13

			// 16/7/2013 after TAG 13 is NOT USED.
			// bean.setMerchantType(getData(isoMsg, 18, ""));// 18
			// bean.setPosEntryMode(getData(isoMsg, 22, ""));// 22 point of
			// service
			// bean.setAcquirerIDLength(getData(isoMsg, 32, "")); // 32
			// bean.setRetrievalRefNo(getData(isoMsg, 37, "")); // 37
			bean.setTerminalID(getData(isoMsg, 41, "")); // 41
			// bean.setCardAcceptorID(getData(isoMsg, 42, "")); // 42
			// bean.setCardAcceptorNameLoc(getData(isoMsg, 43, "")); // 43 Name
			// and
			// // location
			// bean.setTransCurrentcyCode(getData(isoMsg, 49, "")); // 49
			// bean.setReceivingInstitutionID(getData(isoMsg, 100, "")); // 100

			bean.setFuncID(Integer.parseInt(bean.getProcessCode()));

			// --Trailing String from POS 120
			setTrailingDataFromISOMsg(bean, isoMsg);

			// TransID is the TransactionRef from FDM in 120 tag.
			bean.setTransID(bean.getTransactionRef());

			bean.setServiceID(Integer.parseInt(bean.getAggregatorID()));

			return bean;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private static void setTrailingDataFromISOMsg(FundamoRequestBean bean,
			ISOMsg isoMsg) throws ISOException {

		String type = getTypeFromISOMSG(isoMsg);

		final String[] pos120Values = ISODataUtil.getArrayFromTrailingString(
				getData(isoMsg, 120, ""), type);

		if (pos120Values != null) {

			int i = 0;
			bean.setTransactionRef(pos120Values[i++]);
			// Currently BillerID is 31 32 33 34 got to be correctly input from
			// Form. Changed From ::
			// bean.setBillerId(Integer.parseInt(getData(isoMsg, 100, "00")));
			// 24/07/2013 here the db should be changed to corrected value,
			// instead of 30 to be like 100001, incoming value should be change
			// to be the same. but without coding changes needed.
			bean.setBillerID(pos120Values[i++]);
			bean.setAggregatorID(pos120Values[i++]);
			bean.setTransactionChannel(pos120Values[i++]);
			bean.setUserAgent(pos120Values[i++]);
			bean.setCustMobileNo(pos120Values[i++]);
			bean.setRef1(pos120Values[i++]);
			bean.setRef2(pos120Values[i++]);
			bean.setRef3(pos120Values[i++]);
			bean.setRef4(pos120Values[i++]);
			bean.setRef5(pos120Values[i++]);
			bean.setRef6(pos120Values[i++]);
			bean.setFundType(pos120Values[i++]);

			if (type.equalsIgnoreCase(ISODataUtil.CONFIRM)) {
				bean.setVatRate(getBigDecimal(pos120Values[i++]));
				bean.setVatAmount(getBigDecimal(pos120Values[i++]));
				bean.setCustFeeAmount(getBigDecimal(pos120Values[i++]));
				bean.setCustFeeExcludeVatAmount(getBigDecimal(pos120Values[i++]));
				bean.setCustFeeVatAmount(getBigDecimal(pos120Values[i++]));
				bean.setBillFeeAmount(getBigDecimal(pos120Values[i++]));
				bean.setBillFeeExclVatAmount(getBigDecimal(pos120Values[i++]));
				bean.setBillFeeVatAmount(getBigDecimal(pos120Values[i++]));
			}

		}
	}

	public static String getTypeFromISOMSG(ISOMsg isoMsg) throws ISOException {
		final String mti = isoMsg.getMTI().trim();
		return getTypeFromMTI(mti);
	}

	public static String getTypeFromMTI(String mti) throws ISOException {
		String type = "";
		if (mti.equalsIgnoreCase("0200") || mti.equalsIgnoreCase("200")) {
			type = ISODataUtil.VERIFY;
		} else if (mti.equalsIgnoreCase("0220") || mti.equalsIgnoreCase("220")) {
			type = ISODataUtil.CONFIRM;
		}
		return type;
	}

	public static String getBillerResponse(FundamoRequestBean bean, String MTI)
			throws Exception {
		GenericPackager packager = new GenericPackager(
				ConstantUtil.ISO_XML_FILE);

		ISOMsg isoMsg = new ISOMsg();
		isoMsg.setPackager(packager);
		isoMsg.setMTI(MTI);

		// Test
		ISOMsg srcMsg = bean.getIsoMsg();
		for (int i = 1; i <= srcMsg.getMaxField(); i++) {
			if (srcMsg.hasField(i)) {
				isoMsg.set(i, srcMsg.getString(i));
			}
		}

		byte[] data = isoMsg.pack();
		return new String(data);
	}

	private static BigDecimal getAmount(String data) {

		if (null == data || "".equals(data))
			return new BigDecimal(0);
		else {
			data = data.trim();
			return new BigDecimal(data).divide(new BigDecimal(100));

		}
	}

	private static BigDecimal getBigDecimal(String data) {

		if (null == data || data.trim().isEmpty())
			return new BigDecimal(0);
		else {
			try {
				data = data.trim();
				return new BigDecimal(data);

			} catch (NumberFormatException nEx) {
				final NumberFormatException bigDecimalEx = new NumberFormatException(
						"Number to create BigDecimal not correct, check VAT and Fee.");
				bigDecimalEx.setStackTrace(nEx.getStackTrace());
				throw bigDecimalEx;
			}
		}
	}

	//

	public static byte[] getNetwork(ISOMsg isoMsgReq) throws Exception {
		// GenericPackager packager = new GenericPackager(FILE_XML);

		// ISOMsg isoMsg = new ISOMsg();
		// isoMsg.setPackager(packager);
		isoMsgReq.setResponseMTI();
		isoMsgReq.set(39, "00");

		// isoMsg.set(3, isoMsgReq.getString(3));
		// isoMsg.set(41, isoMsgReq.getString(41));

		// // Test
		// ISOMsg srcMsg = bean.getIsoMsg();
		// for (int i = 1; i <= srcMsg.getMaxField(); i++) {
		// if (srcMsg.hasField(i)) {
		// isoMsg.set(i, srcMsg.getString(i));
		// }
		// }

		byte[] data = isoMsgReq.pack();

		System.out.println("ISO_RESP:" + new String(data));
		return data;
	}

	public static String getISOMSGString(ISOMsg isomsg) {

		StringBuilder s = new StringBuilder();
		// if (isomsg.isIncoming())
		// s.append("<-- ");
		// else if (isomsg.isOutgoing())
		// s.append("--> ");
		// else
		// s.append("    ");

		for (int i = 0; i <= 120; i++) {
			if (isomsg.hasField(i)) {
				s.append(isomsg.getString(i));
			}
		}
		return s.toString();
	}

}
