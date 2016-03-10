package mfs.util.iso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mfs.util.ISO8583Util;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

public class ISOMsgDataPrinter {

	public static String[] getRequestHeader() throws ISOException {

		final List<String> headerStr = new ArrayList<String>();

		for (ISODataEntry isoData : ISODataUtil.getISOMainTagList()) {
			headerStr.add("" + isoData.getFieldName());
		}
		for (ISODataEntry isoData : ISODataUtil.getISOTrailingDataPackConfirm()) {
			headerStr.add("" + isoData.getFieldName());
		}

		return headerStr.toArray(new String[0]);
	}

	public static String[] getRequestData(ISOMsg isomsg) throws ISOException {

		final List<String> dataStr = new ArrayList<String>();

		for (ISODataEntry isoData : ISODataUtil.getISOMainTagList()) {
			dataStr.add("" + isomsg.getValue(isoData.getOrder()));
		}
		dataStr.addAll(Arrays.asList(ISODataUtil.getArrayFromTrailingString(""
				+ isomsg.getValue(120),
				ISO8583Util.getTypeFromMTI(isomsg.getMTI()))));

		return dataStr.toArray(new String[0]);
	}

	public static String[] getResponseHeader() throws ISOException {

		final List<String> respHeaderStr = new ArrayList<String>();

		// First one is the RespCode
		respHeaderStr.add("RespCode");
		respHeaderStr.add("Amount, Transaction");
		respHeaderStr.add("Transaction DateTime");
		// Last one now is the 120 RespString from BillerGateway.
		respHeaderStr.add("Tag 120");

		return respHeaderStr.toArray(new String[0]);
	}

	public static String[] getResponseData(ISOMsg isomsg) throws ISOException {
		// Not Implemented yet the Packing/ Unpacking of the 120
		// Response/Result!, For now just print it out (Especially 120 tag).

		final List<String> respDataStr = new ArrayList<String>();

		// First one is the RespCode
		respDataStr.add("" + isomsg.getValue(39));
		respDataStr.add("" + isomsg.getValue(3)); // 4, "Amount, Transaction"
		respDataStr.add("" + isomsg.getValue(7)); // 7, "Transaction DateTime"
		// Last one now is the 120 RespString from BillerGateway.
		respDataStr.add("" + isomsg.getValue(120));

		return respDataStr.toArray(new String[0]);
	}
}
