package mfs.util.iso;

import java.io.IOException;

import mfs.util.biller.ConstantUtil;

import org.jpos.core.Configuration;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.util.Log4JListener;
import org.jpos.util.LogEvent;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;
import org.jpos.util.Profiler;

public class FDMISOClient {

	// final private static org.apache.log4j.Logger log =
	// org.apache.log4j.Logger
	// .getLogger(SimulateISO.class);

	public ISOMsg invoke(String gatewayHost, int gatewayPort, ISOMsg isoMsg,
			String header) {

		ISOMsg risoMsg = null;
		PADChannel channel = null;
		try {
			Logger logger = new Logger();
			logger.addListener(new Log4JListener());

			GenericPackager packager = new GenericPackager(
					ConstantUtil.ISO_XML_FILE);

			channel = new PADChannel(packager);
			channel.setHeader(header);

			Configuration conf = new ISOConfiguration();
			conf.put(ISOConfiguration.HOST, gatewayHost);
			conf.put(ISOConfiguration.PORT, gatewayPort);
			conf.put(ISOConfiguration.TIMEOUT, 50000);
			conf.put(ISOConfiguration.CONNECT_TIMEOUT, 50000);
			conf.put(ISOConfiguration.KEEP_ALIVE, "true");

			channel.setConfiguration(conf);

			((LogSource) channel).setLogger(null, "client.channel");
			Profiler prof = new Profiler();
			LogEvent evt = new LogEvent(((LogSource) channel),
					"any-transaction", prof);
			prof.checkPoint("initialization");
			channel.connect();

			// System.out.println("connect:send["
			// + ISO8583Util.getISOMSGString(isoMsg) + "]");
			prof.checkPoint("connect");
			channel.send(isoMsg);
			prof.checkPoint("wait receive");

			risoMsg = channel.receive();

			// System.out.println("receive:[" +
			// ISO8583Util.getISOMSGString(risoMsg)
			// + "]");

			// int maxField = risoMsg.getMaxField();
			// fields = risoMsg.getChildren();

			prof.checkPoint("receive");
			Logger.log(evt);

			// log.debug("FDMISOClient|invoke|call end." + risoMsg.toString());
			// System.out.println("FDMISOClient|invoke|call end.");

		} catch (Exception e) {
			e.printStackTrace();
			// log.debug("FDMISOClient|invoke|Exception." + e.getMessage());
			// System.out.println("FDMISOClient|invoke|Exception."
			// + e.getMessage());

		} finally {
			if (channel.isConnected())
				try {
					channel.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return risoMsg;

	}

}
