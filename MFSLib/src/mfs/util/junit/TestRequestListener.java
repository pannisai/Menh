package mfs.util.junit;

import java.io.IOException;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;

public class TestRequestListener implements ISORequestListener {
	public TestRequestListener() {
		super();
	}

	public boolean process(ISOSource source, ISOMsg isoMsg) {
		try {

			System.out.println("DATA MTI:" + isoMsg.getMTI());

			if ("0800".equals(isoMsg.getMTI())) {

				isoMsg.setResponseMTI();
				isoMsg.set(39, "00");
			}

			source.send(isoMsg);
		} catch (ISOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

}