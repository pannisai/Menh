//package mfs.util.iso;
//
//import java.util.Observable;
//import java.util.Observer;
//
//import org.jpos.core.ReConfigurable;
//import org.jpos.iso.ISOServerMBean;
//import org.jpos.util.LogSource;
//import org.jpos.util.Loggeable;
//
//// Referenced classes of package org.jpos.iso:
////            FilteredChannel, ISOChannel, ServerChannel, BaseChannel, 
////            ISOServerMBean, ISOServerSocketFactory, ISOUtil, ISOPackager, 
////            ISORequestListener
//
//public class ISOServer extends Observable implements LogSource, Runnable,
//		Observer, ISOServerMBean, ReConfigurable, Loggeable {
//	/* member class not found */
//	class Session {
//	}
//
//	// public ISOServer(int port, ServerChannel clientSide, ThreadPool pool) {
//	// socketFactory = null;
//	// shutdown = false;
//	// this.port = port;
//	// clientSideChannel = clientSide;
//	// clientPackager = clientSide.getPackager();
//	// if (clientSide instanceof FilteredChannel) {
//	// FilteredChannel fc = (FilteredChannel) clientSide;
//	// clientOutgoingFilters = fc.getOutgoingFilters();
//	// clientIncomingFilters = fc.getIncomingFilters();
//	// }
//	// this.pool = pool != null ? pool : new ThreadPool(1, 100);
//	// listeners = new Vector();
//	// name = "";
//	// channels = new HashMap();
//	// cnt = new int[1];
//	// }
//	//
//	// protected Session createSession(ServerChannel channel) {
//	// return new Session(channel);
//	// }
//	//
//	// public void addISORequestListener(ISORequestListener l) {
//	// listeners.add(l);
//	// }
//	//
//	// public void removeISORequestListener(ISORequestListener l) {
//	// listeners.remove(l);
//	// }
//	//
//	// public void shutdown()
//	// {
//	// shutdown = true;
//	// (new Object("ISOServer-shutdown") /* anonymous class not found */
//	// class _anm1 {}
//	//
//	// ).start();
//	// }
//	//
//	// private void shutdownServer() {
//	// try {
//	// if (serverSocket != null)
//	// serverSocket.close();
//	// if (pool != null)
//	// pool.close();
//	// } catch (IOException e) {
//	// Logger.log(new LogEvent(this, "shutdown", e));
//	// }
//	// }
//	//
//	// private void shutdownChannels() {
//	// Iterator iter = channels.entrySet().iterator();
//	// do {
//	// if (!iter.hasNext())
//	// break;
//	// java.util.Map.Entry entry = (java.util.Map.Entry) iter.next();
//	// WeakReference ref = (WeakReference) entry.getValue();
//	// ISOChannel c = (ISOChannel) ref.get();
//	// if (c != null)
//	// try {
//	// c.disconnect();
//	// } catch (IOException e) {
//	// Logger.log(new LogEvent(this, "shutdown", e));
//	// }
//	// } while (true);
//	// }
//	//
//	// private void purgeChannels() {
//	// Iterator iter = channels.entrySet().iterator();
//	// do {
//	// if (!iter.hasNext())
//	// break;
//	// java.util.Map.Entry entry = (java.util.Map.Entry) iter.next();
//	// WeakReference ref = (WeakReference) entry.getValue();
//	// ISOChannel c = (ISOChannel) ref.get();
//	// if (c == null || !c.isConnected())
//	// iter.remove();
//	// } while (true);
//	// }
//	//
//	// public void run()
//	// {
//	// _
//	// if(shutdown)
//	// break; /* Loop/switch isn't completed */
//	// serverSocket = socketFactory == null ? new ServerSocket(port, backlog,
//	// bindAddr) : socketFactory.createServerSocket(port);
//	// Logger.log(new LogEvent(this, "iso-server", (new
//	// StringBuilder()).append("listening on ").append(bindAddr == null ?
//	// "port " : (new
//	// StringBuilder()).append(bindAddr).append(":").toString()).append(port).append(backlog
//	// <= 0 ? "" : (new
//	// StringBuilder()).append(" backlog=").append(backlog).toString()).toString()));
//	//
//	// if(shutdown)
//	// continue; /* Loop/switch isn't completed */
//	// if(pool.getAvailableCount() > 0) goto _L2; else goto _L1
//	// _
//	// SocketException e;
//	// int i;
//	// try
//	// {
//	// serverSocket.close();
//	// }
//	// // Misplaced declaration of an exception variable
//	// catch(SocketException e)
//	// {
//	// Logger.log(new LogEvent(this, "iso-server", e));
//	// relax();
//	// }
//	// i = 0;
//	// _
//	// if(pool.getAvailableCount() > 0) goto _L4; else goto _L3
//	// _
//	// ISOUtil.sleep(250L);
//	// if(shutdown)
//	// break; /* Loop/switch isn't completed */
//	// if(i % 240 == 0)
//	// {
//	// LogEvent evt = new LogEvent(this, "warn");
//	// evt.addMessage((new
//	// StringBuilder()).append("pool exhausted ").append(serverSocket.toString()).toString());
//	// evt.addMessage(pool);
//	// Logger.log(evt);
//	// }
//	// i++;
//	// goto _L5
//	// _L4:
//	// serverSocket = socketFactory == null ? new ServerSocket(port, backlog,
//	// bindAddr) : socketFactory.createServerSocket(port);
//	// _L2:
//	// ServerChannel channel = (ServerChannel)clientSideChannel.clone();
//	// channel.accept(serverSocket);
//	// if(cnt[0]++ % 100 == 0)
//	// purgeChannels();
//	// WeakReference wr = new WeakReference(channel);
//	// channels.put(channel.getName(), wr);
//	// channels.put(":last", wr);
//	// pool.execute(createSession(channel));
//	// setChanged();
//	// notifyObservers(this);
//	// if(channel instanceof Observable)
//	// ((Observable)channel).addObserver(this);
//	// goto _L6
//	// wr;
//	// if(!shutdown)
//	// Logger.log(new LogEvent(this, "iso-server", wr));
//	// goto _L6
//	// wr;
//	// Logger.log(new LogEvent(this, "iso-server", wr));
//	// relax();
//	// goto _L6
//	// wr;
//	// Logger.log(new LogEvent(this, "iso-server", wr));
//	// relax();
//	// if(true) goto _L8; else goto _L7
//	// _L7:
//	// }
//	//
//	// private void relax() {
//	// try {
//	// Thread.sleep(5000L);
//	// } catch (InterruptedException e) {
//	// }
//	// }
//	//
//	// public void setName(String name) {
//	// this.name = name;
//	// NameRegistrar
//	// .register((new StringBuilder()).append("server.").append(name)
//	// .toString(), this);
//	// }
//	//
//	// public static ISOServer getServer(String name)
//	// throws org.jpos.util.NameRegistrar.NotFoundException {
//	// return (ISOServer) NameRegistrar.get((new StringBuilder())
//	// .append("server.").append(name).toString());
//	// }
//	//
//	// public String getName() {
//	// return name;
//	// }
//	//
//	// public void setLogger(Logger logger, String realm) {
//	// this.logger = logger;
//	// this.realm = realm;
//	// realmChannel = (new StringBuilder()).append(realm).append(".channel")
//	// .toString();
//	// }
//	//
//	// public String getRealm() {
//	// return realm;
//	// }
//	//
//	// public Logger getLogger() {
//	// return logger;
//	// }
//	//
//	// public void update(Observable o, Object arg) {
//	// setChanged();
//	// notifyObservers(arg);
//	// }
//	//
//	// public ISOServerSocketFactory getSocketFactory() {
//	// return socketFactory;
//	// }
//	//
//	// public void setSocketFactory(ISOServerSocketFactory socketFactory) {
//	// this.socketFactory = socketFactory;
//	// }
//	//
//	// public int getPort() {
//	// return port;
//	// }
//	//
//	// public void resetCounters() {
//	// cnt = new int[1];
//	// }
//	//
//	// public int getConnectionCount() {
//	// return cnt[0];
//	// }
//	//
//	// public int getJobCount() {
//	// return pool.getJobCount();
//	// }
//	//
//	// public int getPoolSize() {
//	// return pool.getPoolSize();
//	// }
//	//
//	// public int getMaxPoolSize() {
//	// return pool.getMaxPoolSize();
//	// }
//	//
//	// public int getIdleCount() {
//	// return pool.getIdleCount();
//	// }
//	//
//	// public int getPendingCount() {
//	// return pool.getPendingCount();
//	// }
//	//
//	// public int getActiveConnections() {
//	// return pool.getActiveCount();
//	// }
//	//
//	// public ISOChannel getLastConnectedISOChannel() {
//	// return getISOChannel(":last");
//	// }
//	//
//	// public ISOChannel getISOChannel(String name) {
//	// WeakReference ref = (WeakReference) channels.get(name);
//	// if (ref != null)
//	// return (ISOChannel) ref.get();
//	// else
//	// return null;
//	// }
//	//
//	// public void setConfiguration(Configuration cfg)
//	// throws ConfigurationException {
//	// this.cfg = cfg;
//	// allow = cfg.getAll("allow");
//	// backlog = cfg.getInt("backlog", 0);
//	// ignoreISOExceptions = cfg.getBoolean("ignore-iso-exceptions");
//	// String ip = cfg.get("bind-address", null);
//	// if (ip != null)
//	// try {
//	// bindAddr = InetAddress.getByName(ip);
//	// } catch (UnknownHostException e) {
//	// throw new ConfigurationException((new StringBuilder())
//	// .append("Invalid bind-address ").append(ip).toString(),
//	// e);
//	// }
//	// if (socketFactory != this && (socketFactory instanceof Configurable))
//	// ((Configurable) socketFactory).setConfiguration(cfg);
//	// }
//	//
//	// public String getISOChannelNames() {
//	// StringBuffer sb = new StringBuffer();
//	// Iterator iter = channels.entrySet().iterator();
//	// for (int i = 0; iter.hasNext(); i++) {
//	// java.util.Map.Entry entry = (java.util.Map.Entry) iter.next();
//	// WeakReference ref = (WeakReference) entry.getValue();
//	// ISOChannel c = (ISOChannel) ref.get();
//	// if (c == null || ":last".equals(entry.getKey()))
//	// continue;
//	// if (i > 0)
//	// sb.append(' ');
//	// sb.append(entry.getKey());
//	// }
//	//
//	// return sb.toString();
//	// }
//	//
//	// public String getCountersAsString() {
//	// StringBuffer sb = new StringBuffer();
//	// Iterator iter = channels.entrySet().iterator();
//	// int cnt[] = new int[2];
//	// int connectedChannels = 0;
//	// for (int i = 0; iter.hasNext(); i++) {
//	// java.util.Map.Entry entry = (java.util.Map.Entry) iter.next();
//	// WeakReference ref = (WeakReference) entry.getValue();
//	// ISOChannel c = (ISOChannel) ref.get();
//	// if (c == null || ":last".equals(entry.getKey()) || !c.isConnected())
//	// continue;
//	// connectedChannels++;
//	// if (c instanceof BaseChannel) {
//	// int cc[] = ((BaseChannel) c).getCounters();
//	// cnt[0] += cc[2];
//	// cnt[1] += cc[1];
//	// }
//	// }
//	//
//	// sb.append((new StringBuilder()).append("connected=")
//	// .append(connectedChannels).toString());
//	// sb.append((new StringBuilder()).append(", rx=")
//	// .append(Integer.toString(cnt[0])).toString());
//	// sb.append((new StringBuilder()).append(", tx=")
//	// .append(Integer.toString(cnt[1])).toString());
//	// return sb.toString();
//	// }
//	//
//	// public String getCountersAsString(String isoChannelName) {
//	// ISOChannel channel = getISOChannel(isoChannelName);
//	// StringBuffer sb = new StringBuffer();
//	// if (channel instanceof BaseChannel) {
//	// int counters[] = ((BaseChannel) channel).getCounters();
//	// append(sb, "rx=", counters[2]);
//	// append(sb, ", tx=", counters[1]);
//	// append(sb, ", connects=", counters[0]);
//	// }
//	// return sb.toString();
//	// }
//	//
//	// public void dump(PrintStream p, String indent) {
//	// p.println((new StringBuilder()).append(indent)
//	// .append(getCountersAsString()).toString());
//	// Iterator iter = channels.entrySet().iterator();
//	// String inner = (new StringBuilder()).append(indent).append("  ")
//	// .toString();
//	// for (int i = 0; iter.hasNext(); i++) {
//	// java.util.Map.Entry entry = (java.util.Map.Entry) iter.next();
//	// WeakReference ref = (WeakReference) entry.getValue();
//	// ISOChannel c = (ISOChannel) ref.get();
//	// if (c != null && !":last".equals(entry.getKey()) && c.isConnected()
//	// && (c instanceof BaseChannel)) {
//	// StringBuffer sb = new StringBuffer();
//	// int cc[] = ((BaseChannel) c).getCounters();
//	// sb.append(inner);
//	// sb.append(entry.getKey());
//	// sb.append(": rx=");
//	// sb.append(Integer.toString(cc[2]));
//	// sb.append(", tx=");
//	// sb.append(Integer.toString(cc[1]));
//	// p.println(sb.toString());
//	// }
//	// }
//	//
//	// }
//	//
//	// private void append(StringBuffer sb, String name, int value) {
//	// sb.append(name);
//	// sb.append(value);
//	// }
//	//
//	// int port;
//	// ISOChannel clientSideChannel;
//	// ISOPackager clientPackager;
//	// Collection clientOutgoingFilters;
//	// Collection clientIncomingFilters;
//	// Collection listeners;
//	// ThreadPool pool;
//	// public static final int DEFAULT_MAX_THREADS = 100;
//	// public static final String LAST = ":last";
//	// String name;
//	// protected Logger logger;
//	// protected String realm;
//	// protected String realmChannel;
//	// protected ISOServerSocketFactory socketFactory;
//	// public static final int CONNECT = 0;
//	// public static final int SIZEOF_CNT = 1;
//	// private int cnt[];
//	// private String allow[];
//	// private InetAddress bindAddr;
//	// private int backlog;
//	// protected Configuration cfg;
//	// private boolean shutdown;
//	// private ServerSocket serverSocket;
//	// private Map channels;
//	// private boolean ignoreISOExceptions;
//
// }