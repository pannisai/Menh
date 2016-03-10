package mfs.util.iso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Observable;
import java.util.Vector;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.core.ReConfigurable;
import org.jpos.iso.BaseChannelMBean;
import org.jpos.iso.ClientChannel;
import org.jpos.iso.FactoryChannel;
import org.jpos.iso.FilteredChannel;
import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOClientSocketFactory;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOFilter;
import org.jpos.iso.ISOHeader;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.RawIncomingFilter;
import org.jpos.iso.ServerChannel;
import org.jpos.iso.header.BaseHeader;
import org.jpos.util.LogEvent;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;
import org.jpos.util.NameRegistrar;

// Referenced classes of package org.jpos.iso:
//            ISOException, ISOFilter, RawIncomingFilter, ISOChannel, 
//            FilteredChannel, ClientChannel, ServerChannel, FactoryChannel, 
//            BaseChannelMBean, ISOClientSocketFactory, ISOMsg, ISOPackager, 
//            ISOUtil, ISOHeader

public abstract class BaseChannel extends Observable implements
		FilteredChannel, ClientChannel, ServerChannel, FactoryChannel,
		LogSource, ReConfigurable, BaseChannelMBean, Cloneable {

	public BaseChannel() {
		maxPacketLength = 0x186a0;
		serverSocket = null;
		socketFactory = null;
		logger = null;
		realm = null;
		originalRealm = null;
		header = null;
		cnt = new int[3];
		name = "";
		incomingFilters = new Vector();
		outgoingFilters = new Vector();
		setHost(null, 0);
	}

	public BaseChannel(String host, int port, ISOPackager p) {
		this();
		setHost(host, port);
		setPackager(p);
	}

	public BaseChannel(ISOPackager p) throws IOException {
		this();
		setPackager(p);
	}

	public BaseChannel(ISOPackager p, ServerSocket serverSocket)
			throws IOException {
		this();
		setPackager(p);
		setServerSocket(serverSocket);
	}

	public void setHost(String host, int port) {
		this.host = host;
		this.port = port;
		hosts = (new String[] { host });
		ports = (new int[] { port });
	}

	public void setLocalAddress(String iface, int port) {
		localIface = iface;
		localPort = port;
	}

	public void setHost(String host) {
		this.host = host;
		hosts = (new String[] { host });
	}

	public void setPort(int port) {
		this.port = port;
		ports = (new int[] { port });
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public void setPackager(ISOPackager p) {
		packager = p;
	}

	public ISOPackager getPackager() {
		return packager;
	}

	public void setServerSocket(ServerSocket sock) {
		setHost(null, 0);
		serverSocket = sock;
		name = "";
	}

	public void resetCounters() {
		for (int i = 0; i < 3; i++)
			cnt[i] = 0;

	}

	public int[] getCounters() {
		return cnt;
	}

	public boolean isConnected() {
		return socket != null && usable;
	}

	protected void connect(Socket socket) throws IOException, SocketException {
		this.socket = socket;
		applyTimeout();
		setLogger(getLogger(), (new StringBuilder()).append(getOriginalRealm())
				.append("/").append(socket.getInetAddress().getHostAddress())
				.append(":").append(socket.getPort()).toString());
		serverIn = new DataInputStream(new BufferedInputStream(
				socket.getInputStream()));
		serverOut = new DataOutputStream(new BufferedOutputStream(
				socket.getOutputStream(), 2048));
		usable = true;
		cnt[0]++;
		setChanged();
		notifyObservers();
	}

	protected Socket newSocket(String host, int port) throws IOException {
		try {
			if (socketFactory != null)
				return socketFactory.createSocket(host, port);
		} catch (ISOException e) {
			throw new IOException(e.getMessage());
		}
		if (connectTimeout > 0) {
			Socket s = new Socket();
			s.connect(new InetSocketAddress(host, port), connectTimeout);
			return s;
		}
		if (localIface == null && localPort == 0)
			return new Socket(host, port);
		InetAddress addr = localIface != null ? InetAddress
				.getByName(localIface) : InetAddress.getLocalHost();
		return new Socket(host, port, addr, localPort);
	}

	protected Socket newSocket(String hosts[], int ports[], LogEvent evt)
			throws IOException {
		Socket s = null;
		int i = 0;
		do {
			if (i >= hosts.length)
				break;
			try {
				evt.addMessage((new StringBuilder()).append(hosts[i])
						.append(":port=").append(ports[i]).append(":timeout=")
						.append(timeout).append(":keep-Alive=")
						.append(keepAlive).toString());
				s = newSocket(hosts[i], ports[i]);
				break;
			} catch (IOException e) {
				evt.addMessage((new StringBuilder()).append("  ")
						.append(e.getMessage()).toString());
				i++;
			}
		} while (true);
		if (s == null)
			throw new IOException("Unable to connect");
		else
			return s;
	}

	public Socket getSocket() {
		return socket;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setTimeout(int timeout) throws SocketException {
		this.timeout = timeout;
		applyTimeout();
	}

	public int getTimeout() {
		return timeout;
	}

	protected void applyTimeout() throws SocketException {
		if (timeout >= 0 && socket != null)
			socket.setSoTimeout(timeout);
	}

	public void connect() throws IOException {
		LogEvent evt = new LogEvent(this, "connect");
		try {
			if (serverSocket != null) {
				accept(serverSocket);
				evt.addMessage((new StringBuilder()).append("local port ")
						.append(serverSocket.getLocalPort())
						.append(" remote host ")
						.append(socket.getInetAddress()).toString());
			} else {
				connect(newSocket(hosts, ports, evt));
			}
			applyTimeout();
			if (socket != null)
				socket.setKeepAlive(keepAlive);

			Logger.log(evt);
			setChanged();
			notifyObservers();
		} catch (ConnectException e) {
			Logger.log(new LogEvent(this, "connection-refused",
					(new StringBuilder()).append(getHost()).append(":")
							.append(getPort()).toString()));
		} catch (IOException e) {
			evt.addMessage(e.getMessage());
			Logger.log(evt);
			throw e;
		}
	}

	public void accept(ServerSocket s) throws IOException {
		Socket ss = s.accept();
		name = (new StringBuilder())
				.append(ss.getInetAddress().getHostAddress()).append(":")
				.append(ss.getPort()).toString();
		connect(ss);
	}

	public void setUsable(boolean b) {
		Logger.log(new LogEvent(this, "usable", new Boolean(b)));
		usable = b;
	}

	protected ISOPackager getDynamicPackager(ISOMsg m) {
		return packager;
	}

	protected ISOPackager getDynamicPackager(byte image[]) {
		return packager;
	}

	protected ISOHeader getDynamicHeader(byte image[]) {
		return image == null ? null : new BaseHeader(image);
	}

	protected void sendMessageLength(int i) throws IOException {
	}

	protected void sendMessageHeader(ISOMsg m, int len) throws IOException {
		if (!isOverrideHeader() && m.getHeader() != null)
			serverOut.write(m.getHeader());
		else if (header != null)
			serverOut.write(header);
	}

	/**
	 * @deprecated Method sendMessageTrailler is deprecated
	 */

	protected void sendMessageTrailler(ISOMsg isomsg, int i) throws IOException {
	}

	protected void sendMessageTrailler(ISOMsg m, byte b[]) throws IOException {
		sendMessageTrailler(m, b.length);
	}

	protected void getMessageTrailler() throws IOException {
	}

	protected void getMessage(byte b[], int offset, int len)
			throws IOException, ISOException {
		serverIn.readFully(b, offset, len);
	}

	protected int getMessageLength() throws IOException, ISOException {
		return -1;
	}

	protected int getHeaderLength() {
		return header == null ? 0 : header.length;
	}

	protected int getHeaderLength(byte b[]) {
		return 0;
	}

	protected int getHeaderLength(ISOMsg m) {
		return overrideHeader || m.getHeader() == null ? getHeaderLength() : m
				.getHeader().length;
	}

	protected byte[] streamReceive() throws IOException {
		return new byte[0];
	}

	protected void sendMessage(byte b[], int offset, int len)
			throws IOException {
		serverOut.write(b, offset, len);
	}

	public void send(ISOMsg m) throws IOException, ISOException,
			ISOFilter.VetoException {
		LogEvent evt = new LogEvent(this, "send");
		try {
			if (!isConnected())
				throw new ISOException("unconnected ISOChannel");
			m.setDirection(2);
			m = applyOutgoingFilters(m, evt);
			evt.addMessage(m);
			m.setDirection(2);
			m.setPackager(getDynamicPackager(m));
			byte b[] = m.pack();
			synchronized (serverOut) {
				sendMessageLength(b.length + getHeaderLength(m));
				sendMessageHeader(m, b.length);
				sendMessage(b, 0, b.length);
				sendMessageTrailler(m, b);
				serverOut.flush();
			}
			cnt[1]++;
			setChanged();
			notifyObservers(m);
		} catch (ISOFilter.VetoException e) {
			evt.addMessage(m);
			evt.addMessage(e);
			throw e;
		} catch (ISOException e) {
			evt.addMessage(e);
			throw e;
		} catch (IOException e) {
			evt.addMessage(e);
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			evt.addMessage(e);
			throw new ISOException("unexpected exception", e);
		}
		Logger.log(evt);
		// break MISSING_BLOCK_LABEL_207;
		// Exception exception1;
		// exception1;
		// Logger.log(evt);
		// throw exception1;
	}

	public void sendKeepAlive() throws IOException {
		synchronized (serverOut) {
			sendMessageLength(0);
			serverOut.flush();
		}
	}

	protected boolean isRejected(byte b[]) {
		return false;
	}

	protected boolean shouldIgnore(byte b[]) {
		return false;
	}

	protected ISOMsg createMsg() {
		return createISOMsg();
	}

	protected ISOMsg createISOMsg() {
		return packager.createISOMsg();
	}

	protected byte[] readHeader(int hLen) throws IOException {
		byte header[] = new byte[hLen];
		serverIn.readFully(header, 0, hLen);
		return header;
	}

	public ISOMsg receive() throws IOException, ISOException {
		byte b[];
		byte header[];
		LogEvent evt;
		ISOMsg m;
		b = null;
		header = null;
		evt = new LogEvent(this, "receive");
		m = createMsg();
		m.setSource(this);
		try {
			if (!isConnected())
				throw new ISOException("unconnected ISOChannel");
			synchronized (serverIn) {
				int len = getMessageLength();
				int hLen = getHeaderLength();
				if (len == -1) {
					if (hLen > 0)
						header = readHeader(hLen);
					b = streamReceive();
				} else if (len > 0 && len <= getMaxPacketLength()) {
					if (hLen > 0) {
						header = readHeader(hLen);
						len -= header.length;
					}
					b = new byte[len];
					getMessage(b, 0, len);
					getMessageTrailler();
				} else {
					throw new ISOException((new StringBuilder())
							.append("receive length ").append(len)
							.append(" seems strange - maxPacketLength = ")
							.append(getMaxPacketLength()).toString());
				}
			}
			m.setPackager(getDynamicPackager(b));
			m.setHeader(getDynamicHeader(header));
			if (b.length > 0 && !shouldIgnore(header))
				unpack(m, b);
			m.setDirection(1);
			m = applyIncomingFilters(m, header, b, evt);
			m.setDirection(1);
			evt.addMessage(m);
			cnt[2]++;
			setChanged();
			notifyObservers(m);
		} catch (ISOException e) {
			evt.addMessage(e);
			if (header != null) {
				evt.addMessage("--- header ---");
				evt.addMessage(ISOUtil.hexdump(header));
			}
			if (b != null) {
				evt.addMessage("--- data ---");
				evt.addMessage(ISOUtil.hexdump(b));
			}
			throw e;
		} catch (EOFException e) {
			closeSocket();
			evt.addMessage("<peer-disconnect/>");
			throw e;
		} catch (SocketException e) {
			closeSocket();
			if (usable)
				evt.addMessage((new StringBuilder())
						.append("<peer-disconnect>").append(e.getMessage())
						.append("</peer-disconnect>").toString());
			throw e;
		} catch (InterruptedIOException e) {
			closeSocket();
			evt.addMessage("<io-timeout/>");
			throw e;
		} catch (IOException e) {
			closeSocket();
			if (usable)
				evt.addMessage(e);
			throw e;
		} catch (Exception e) {
			evt.addMessage(m);
			evt.addMessage(e);
			throw new ISOException("unexpected exception", e);
		}
		Logger.log(evt);
		// break MISSING_BLOCK_LABEL_475;
		// Exception exception1;
		// exception1;
		// Logger.log(evt);
		// throw exception1;
		return m;
	}

	public int getBytes(byte b[]) throws IOException {
		return serverIn.read(b);
	}

	public void disconnect() throws IOException {
		LogEvent evt = new LogEvent(this, "disconnect");
		if (serverSocket != null)
			evt.addMessage((new StringBuilder()).append("local port ")
					.append(serverSocket.getLocalPort())
					.append(" remote host ")
					.append(serverSocket.getInetAddress()).toString());
		else
			evt.addMessage((new StringBuilder()).append(host).append(":")
					.append(port).toString());
		try {
			usable = false;
			setChanged();
			notifyObservers();
			closeSocket();
			if (serverIn != null) {
				try {
					serverIn.close();
				} catch (IOException ex) {
					evt.addMessage(ex);
				}
				serverIn = null;
			}
			if (serverOut != null) {
				try {
					serverOut.close();
				} catch (IOException ex) {
					evt.addMessage(ex);
				}
				serverOut = null;
			}
		} catch (IOException e) {
			evt.addMessage(e);
			Logger.log(evt);
			throw e;
		}
		socket = null;
	}

	public void reconnect() throws IOException {
		disconnect();
		connect();
	}

	public void setLogger(Logger logger, String realm) {
		this.logger = logger;
		this.realm = realm;
		if (originalRealm == null)
			originalRealm = realm;
	}

	public String getRealm() {
		return realm;
	}

	public Logger getLogger() {
		return logger;
	}

	public String getOriginalRealm() {
		return originalRealm != null ? originalRealm : getClass().getName();
	}

	public void setName(String name) {
		this.name = name;
		NameRegistrar.register(
				(new StringBuilder()).append("channel.").append(name)
						.toString(), this);
	}

	public String getName() {
		return name;
	}

	public void addFilter(ISOFilter filter, int direction) {
		switch (direction) {
		case 1: // '\001'
			incomingFilters.add(filter);
			break;

		case 2: // '\002'
			outgoingFilters.add(filter);
			break;

		case 0: // '\0'
			incomingFilters.add(filter);
			outgoingFilters.add(filter);
			break;
		}
	}

	public void addIncomingFilter(ISOFilter filter) {
		addFilter(filter, 1);
	}

	public void addOutgoingFilter(ISOFilter filter) {
		addFilter(filter, 2);
	}

	public void addFilter(ISOFilter filter) {
		addFilter(filter, 0);
	}

	public void removeFilter(ISOFilter filter, int direction) {
		switch (direction) {
		case 1: // '\001'
			incomingFilters.remove(filter);
			break;

		case 2: // '\002'
			outgoingFilters.remove(filter);
			break;

		case 0: // '\0'
			incomingFilters.remove(filter);
			outgoingFilters.remove(filter);
			break;
		}
	}

	public void removeFilter(ISOFilter filter) {
		removeFilter(filter, 0);
	}

	public void removeIncomingFilter(ISOFilter filter) {
		removeFilter(filter, 1);
	}

	public void removeOutgoingFilter(ISOFilter filter) {
		removeFilter(filter, 2);
	}

	protected ISOMsg applyOutgoingFilters(ISOMsg m, LogEvent evt)
			throws ISOFilter.VetoException {
		for (Iterator iter = outgoingFilters.iterator(); iter.hasNext();)
			m = ((ISOFilter) iter.next()).filter(this, m, evt);

		return m;
	}

	protected ISOMsg applyIncomingFilters(ISOMsg m, LogEvent evt)
			throws ISOFilter.VetoException {
		return applyIncomingFilters(m, null, null, evt);
	}

	protected ISOMsg applyIncomingFilters(ISOMsg m, byte header[],
			byte image[], LogEvent evt) throws ISOFilter.VetoException {
		for (Iterator iter = incomingFilters.iterator(); iter.hasNext();) {
			ISOFilter f = (ISOFilter) iter.next();
			if (image != null && (f instanceof RawIncomingFilter))
				m = ((RawIncomingFilter) f).filter(this, m, header, image, evt);
			else
				m = f.filter(this, m, evt);
		}

		return m;
	}

	protected void unpack(ISOMsg m, byte b[]) throws ISOException {
		m.unpack(b);
	}

	public void setConfiguration(Configuration cfg)
			throws ConfigurationException {
		this.cfg = cfg;
		String h = cfg.get("host");
		int port = cfg.getInt("port");
		maxPacketLength = cfg.getInt("max-packet-length", 0x186a0);
		if (h != null && h.length() > 0) {
			if (port == 0)
				throw new ConfigurationException((new StringBuilder())
						.append("invalid port for host '").append(h)
						.append("'").toString());
			setHost(h, port);

			setLocalAddress(cfg.get("local-iface", null),
					cfg.getInt("local-port"));
			/**
			 * String altHosts[] = cfg.getAll("alternate-host"); int altPorts[]
			 * = cfg.getInts("alternate-port"); hosts = new
			 * String[altHosts.length + 1]; ports = new int[altPorts.length +
			 * 1]; if (hosts.length != ports.length) throw new
			 * ConfigurationException( "alternate host/port misconfiguration");
			 * hosts[0] = host; ports[0] = port; System.arraycopy(altHosts, 0,
			 * hosts, 1, altHosts.length); System.arraycopy(altPorts, 0, ports,
			 * 1, altPorts.length);
			 */
		}
		setOverrideHeader(cfg.getBoolean("override-header", false));
		keepAlive = cfg.getBoolean("keep-alive", false);
		if (socketFactory != this && (socketFactory instanceof Configurable))
			((Configurable) socketFactory).setConfiguration(cfg);
		try {
			setTimeout(cfg.getInt("timeout"));
			connectTimeout = cfg.getInt("connect-timeout", timeout);
		} catch (SocketException e) {
			throw new ConfigurationException(e);
		}
	}

	public Configuration getConfiguration() {
		return cfg;
	}

	public Collection getIncomingFilters() {
		return incomingFilters;
	}

	public Collection getOutgoingFilters() {
		return outgoingFilters;
	}

	public void setIncomingFilters(Collection filters) {
		incomingFilters = new Vector(filters);
	}

	public void setOutgoingFilters(Collection filters) {
		outgoingFilters = new Vector(filters);
	}

	public void setHeader(byte header[]) {
		this.header = header;
	}

	public void setHeader(String header) {
		setHeader(header.getBytes());
	}

	public byte[] getHeader() {
		return header;
	}

	public void setOverrideHeader(boolean overrideHeader) {
		this.overrideHeader = overrideHeader;
	}

	public boolean isOverrideHeader() {
		return overrideHeader;
	}

	public static ISOChannel getChannel(String name)
			throws org.jpos.util.NameRegistrar.NotFoundException {
		return (ISOChannel) NameRegistrar.get((new StringBuilder())
				.append("channel.").append(name).toString());
	}

	public ISOClientSocketFactory getSocketFactory() {
		return socketFactory;
	}

	public void setSocketFactory(ISOClientSocketFactory socketFactory) {
		this.socketFactory = socketFactory;
	}

	public int getMaxPacketLength() {
		return maxPacketLength;
	}

	public void setMaxPacketLength(int maxPacketLength) {
		this.maxPacketLength = maxPacketLength;
	}

	private void closeSocket() throws IOException {
		if (socket != null) {
			try {
				socket.setSoLinger(true, 0);
			} catch (SocketException e) {
			}
			socket.close();
			socket = null;
		}
	}

	public Object clone() {
		try {
			BaseChannel channel = (BaseChannel) super.clone();
			channel.cnt = (int[]) (int[]) cnt.clone();
			return channel;
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}

	private Socket socket;
	private String host;
	private String localIface;
	private String hosts[];
	int ports[];
	private int port;
	private int timeout;
	private int connectTimeout;
	private int localPort;
	private int maxPacketLength;
	private boolean keepAlive;
	private Configuration cfg;
	protected boolean usable;
	protected boolean overrideHeader;
	private String name;
	protected DataInputStream serverIn;
	protected DataOutputStream serverOut;
	protected ISOPackager packager;
	protected ServerSocket serverSocket;
	protected Vector incomingFilters;
	protected Vector outgoingFilters;
	protected ISOClientSocketFactory socketFactory;
	protected int cnt[];
	protected Logger logger;
	protected String realm;
	protected String originalRealm;
	protected byte header[];
}