package mfs.util;

import java.io.PrintWriter;

import mfs.util.biller.ConstantUtil;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;

public class DebugUtil {

	private static DebugUtil instance;

	public static DebugUtil get() {
		if (instance == null) {
			instance = new DebugUtil();
		}
		return instance;
	}

	public void print(PrintWriter out, String textToPrint) {
		if (out != null)
			out.print(textToPrint);

		// System.out.print(textToPrint);

	}

	public void testPackAndUnpack(ISOMsg isoMsg) throws ISOException {
		GenericPackager packager = new GenericPackager(
				ConstantUtil.ISO_XML_FILE);

		isoMsg.setPackager(packager);
		System.out.print("isomsg packing...");
		final String data = new String(isoMsg.pack());

		ISOMsg isoMsgUnpack = new ISOMsg();
		isoMsgUnpack.setPackager(packager);
		System.out.print("isoMsgUnpack unpacking...");
		isoMsgUnpack.unpack(data.getBytes());
		System.out.print("isomsg unpacking done");
		System.out.print("isomsg unpack 2 : [" + isoMsgUnpack.getString(2)
				+ "]");

	}
}
