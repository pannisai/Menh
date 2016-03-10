// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 7/2/2013 4:53:41 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   PADChannel.java

package mfs.util.iso;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.SocketException;

import mfs.util.biller.ConfigUtil;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.ISOUtil;
import org.jpos.util.LogEvent;
import org.jpos.util.Logger;

public class PADChannel extends BaseChannel {

	private int packageSize = Integer.parseInt(ConfigUtil.getInstance()
			.getConfig("SERVER_SOCKET_PACKAGE_SIZE"));

	public PADChannel() {
		reader = null;
	}

	public PADChannel(String host, int port, ISOPackager p) {
		super(host, port, p);
		reader = null;
	}

	public PADChannel(ISOPackager p) throws IOException {
		super(p);
		reader = null;
	}

	public PADChannel(ISOPackager p, ServerSocket serverSocket)
			throws IOException {
		super(p, serverSocket);
		reader = null;
	}

	public ISOMsg receive() throws IOException, ISOException, SocketException {

		byte header[];
		ISOMsg m;
		int hLen;
		LogEvent evt;
		header = null;
		m = new ISOMsg();
		m.setPackager(packager);
		m.setSource(this);
		hLen = getHeaderLength();
		evt = new LogEvent(this, "receive");

		byte[] databyte = new byte[packageSize];

		try {
			synchronized (serverIn) {
				// byte[] inputbyte = new byte[4028];
				// serverIn.mark(99);
				// serverIn.read(inputbyte);
				// System.out.println("input data= [" + new String(inputbyte)
				// + "]");
				// serverIn.reset();

				if (hLen > 0) {

					header = new byte[hLen];
					serverIn.readFully(header);
					evt.addMessage((new StringBuilder()).append("<header>")
							.append(ISOUtil.dumpString(header))
							.append("</header>"));

				}
				serverIn.read(databyte);
				evt.addMessage((new StringBuilder()).append("<data>")
						.append(new String(databyte)).append("</data>"));
				m.unpack(databyte);
			}
			m.setHeader(header);
			m.setDirection(1);
			m = applyIncomingFilters(m, evt);
			m.setDirection(1);
			evt.addMessage(m);
			cnt[2]++;
			setChanged();
			notifyObservers(m);

		} catch (ISOException e) {

			if (null != e.getMessage()
					&& e.getMessage().indexOf("SocketException") > 0) {
				// System.out.println("ISOException:" + e.getMessage());
				if (usable) {
					evt.addMessage((new StringBuilder())
							.append("<peer-disconnect>").append(e.getMessage())
							.append("</peer-disconnect>").toString());
					Logger.log(evt);
				}
				throw new SocketException(e.getMessage());
			} else {
				evt.addMessage(e);
				throw e;
			}
		} catch (EOFException e) {
			evt.addMessage("<peer-disconnect/>");
			throw e;
		} catch (InterruptedIOException e) {
			evt.addMessage("<io-timeout/>");
			throw e;
		} catch (IOException e) {

			if (usable)
				evt.addMessage(e);
			throw e;

		} catch (Exception e) {
			evt.addMessage(e);
			throw new ISOException("unexpected exception", e);
		}

		// catch (Exception e) {
		// Logger.log(evt);
		// throw exception1;
		// }
		Logger.log(evt);
		return m;
	}

	// public void setHeader(String header) {
	// super.setHeader(ISOUtil.hex2byte(
	// ISOUtil.STX + ISOUtil.ETX + header.getBytes(), 0,
	// header.length()));
	// }

	BufferedReader reader;
}