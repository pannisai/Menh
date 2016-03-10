package mfs.util.iso;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.core.ReConfigurable;
import org.jpos.util.LogEvent;
import org.jpos.util.LogListener;

// Referenced classes of package org.jpos.util:
//            LogListener, LogEvent

public class Log4JListener implements LogListener, ReConfigurable {

	public Log4JListener() {
		setLevel(10000);
	}

	public void setLevel(int level) {
		_level = Level.toLevel(level);
	}

	public void setLevel(String level) {
		_level = Level.toLevel(level);
	}

	public void close() {
	}

	public void setConfiguration(Configuration cfg)
			throws ConfigurationException {
		String config = cfg.get("config");
		long watch = cfg.getLong("watch");
		if (watch == 0L)
			watch = 60000L;
		if (config != null && !config.trim().equals(""))
			DOMConfigurator.configureAndWatch(config, watch);
		setLevel(cfg.get("priority"));
	}

	public synchronized LogEvent log(LogEvent ev) {
		// System.out.println("LogEvent:" + ev.getRealm().replace('/', ':'));
		// Logger logger = Logger.getLogger(ev.getRealm().replace('/', ':'));
		Logger logger;
		if (ev.getRealm().indexOf('/') > 0) {
			String logname = ev.getRealm().substring(0,
					ev.getRealm().indexOf('/'));
			// System.out.println("LogEvent:" + logname);
			logger = Logger.getLogger(logname);
		} else {
			logger = Logger.getLogger(ev.getRealm());
		}

		if (logger.isEnabledFor(_level)) {
			ByteArrayOutputStream w = new ByteArrayOutputStream();
			PrintStream p = new PrintStream(w);
			ev.dump(p, "");
			logger.log(_level, w.toString());
		}
		return ev;
	}

	private Level _level;
}