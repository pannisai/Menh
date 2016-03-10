package mfs.util.junit;

import java.io.IOException;

import mfs.util.biller.ConstantUtil;
import mfs.util.iso.FDMPackager;
import mfs.util.iso.ISOConfiguration;
import mfs.util.iso.PADChannel;

import org.jpos.core.Configuration;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOServer;
import org.jpos.iso.ServerChannel;
import org.jpos.util.LogEvent;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;
import org.jpos.util.Profiler;
import org.jpos.util.SimpleLogListener;
import org.junit.Test;

public class IsoJunit {
	private static final String FILE_XML = ConstantUtil.ISO_XML_FILE;

	// @Test
	public void test() throws ISOException {
		// fcail("Not yet implemented");

		// ISO8583Util.class.getResourceAsStream(FILE_XML);

		// GenericPackager packager = new GenericPackager(FILE_XML);

		// java.net.SocketException: Connection reset
		if ("java.net.SocketException: Connection reset"
				.indexOf("SocketException") > 0) {
			System.out.print("yes");
		}
	}

	private int PORT = 9999;

	@Test
	public void TestServer() throws Exception {

		Logger logger = new Logger();
		logger.addListener(new SimpleLogListener(System.out));

		// logger.addListener(new Log4JListener());

		// GenericPackager packager = new GenericPackager(FILE_XML);
		// FDMPackager packager = new FDMPackager();

		ServerChannel channel = new PADChannel(new FDMPackager());
		((LogSource) channel).setLogger(logger, "server-channel");
		ISOServer server = new ISOServer(PORT, channel, null);
		server.setLogger(logger, "server");

		Configuration conf = new ISOConfiguration();
		conf.put(ISOConfiguration.PORT, PORT);
		conf.put(ISOConfiguration.TIMEOUT, 1000);
		conf.put(ISOConfiguration.CONNECT_TIMEOUT, 5000);
		conf.put(ISOConfiguration.KEEP_ALIVE, "true");

		server.setConfiguration(conf);

		server.addISORequestListener(new TestRequestListener());

		new Thread(server).start();

	}

	@Test
	public void testChannel() throws ISOException, IOException,
			InterruptedException {
		// Thread.sleep(9000);
		// System.out.println("HOME:" + System.getProperty("user.home"));
		Logger logger = new Logger();
		logger.addListener(new SimpleLogListener(System.out));
		// logger.addListener(new Log4JListener());
		// ISOChannel channel = new XMLChannel("192.168.0.2", 9999,
		// new ISO87APackager());

		// GenericPackager packager = new GenericPackager(FILE_XML);

		// FDMPackager packager = new FDMPackager();

		// ISOChannel channel = new PADChannel("osiris12", PORT, new
		// FDMPackager());

		PADChannel channel = new PADChannel(new FDMPackager());

		Configuration conf = new ISOConfiguration();
		conf.put(ISOConfiguration.HOST, "localhost");
		conf.put(ISOConfiguration.PORT, PORT);
		conf.put(ISOConfiguration.TIMEOUT, 1000);
		conf.put(ISOConfiguration.CONNECT_TIMEOUT, 5000);
		conf.put(ISOConfiguration.KEEP_ALIVE, "true");

		channel.setConfiguration(conf);

		// ISOChannel channel = new PADChannel("localhost", PORT, packager);

		// ISOChannel channel = new XMLChannel("localhost", PORT,
		// new XMLPackager());

		((LogSource) channel).setLogger(logger, "client-channel");
		Profiler prof = new Profiler();
		LogEvent evt = new LogEvent(((LogSource) channel), "any-transaction",
				prof);
		prof.checkPoint("initialization");
		channel.connect();

		prof.checkPoint("connect");

		ISOMsg m = new ISOMsg();
		m.setMTI("0800");
		m.set(3, "000001");
		m.set(41, "00000001");
		m.set(70, "301");
		prof.checkPoint("before send");
		channel.send(m);
		prof.checkPoint("after send");

		prof.checkPoint("wait receive");
		ISOMsg r = channel.receive();
		prof.checkPoint("receive");
		Logger.log(evt);

		Thread.sleep(2000);
		if (channel.isConnected()) {
			ISOMsg m2 = new ISOMsg();
			m2.setMTI("0200");
			m2.set(2, "923467137031");
			m2.set(3, "730000");
			m2.set(49, "THB");
			m2.set(42, "923447088032");
			m2.set(43, "Fundamo mobile Banking Terminal");
			m2.set(102, "6393900000044621");
			m2.set(120, "MEPC0O00");
			channel.send(m2);

			r = channel.receive();
		}

		// Thread.sleep(60000);
		// System.out.println("MTI:" + r.getMTI() + System.getProperty("home"));

		if (channel.isConnected())
			channel.disconnect();

	}
}
