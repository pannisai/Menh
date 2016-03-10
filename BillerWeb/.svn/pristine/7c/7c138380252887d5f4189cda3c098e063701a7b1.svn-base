package com.dtac.billerweb.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;
import org.zkoss.util.resource.Labels;

public class InitialListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent e) {

		try {
			// PropertyConfigurator.configureAndWatch("D:/Dtaclog/billerWebLog4j.properties",10000);
			PropertyConfigurator.configureAndWatch(AppConstant.USER_HOME + org.zkoss.lang.Library.getProperty("app.log.location"), 10000);
			Labels.register(new AppMessageLocator(AppConstant.USER_HOME + org.zkoss.lang.Library.getProperty("app.message.location")));

		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

}
