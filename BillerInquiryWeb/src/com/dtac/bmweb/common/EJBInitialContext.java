package com.dtac.bmweb.common;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import mfs.exception.InitialEJBContextException;

import org.apache.log4j.Logger;

public class EJBInitialContext {
	private static Logger log = Logger.getLogger(EJBInitialContext.class);

	// private static Context context = null;

	private EJBInitialContext() {
		
	}

	public static Context getInitialContext() throws InitialEJBContextException {
		org.apache.log4j.PropertyConfigurator.configure(AppConstant.USER_HOME+"/conf/MFSWebBillerEJB/MFSWebBillerEJBlog4j.properties");
		Context context = null;
		log.info("Create context");
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, AppConfig
					.getValue(AppConfig.INITIAL_CONTEXT_FACTORY_KEY));
			env.put(Context.PROVIDER_URL, AppConfig
					.getValue(AppConfig.PROVIDER_URL_KEY));
			env.put(Context.SECURITY_PRINCIPAL, AppConfig
					.getValue(AppConfig.SECURITY_PRINCIPAL_KEY));
			env.put(Context.SECURITY_CREDENTIALS, AppConfig
					.getValue(AppConfig.SECURITY_CREDENTIALS_KEY));
			//context = new InitialContext(env);
			context = new InitialContext();
			log.info("Create context Success");
		} catch (Exception ex) {
			log.info("Create context Fail");
			throw new InitialEJBContextException("Can't create EJB Context", ex);
		}
		return context;
	}

	public static Object lookup(String jndiName)
			throws InitialEJBContextException, NamingException {
		Context ctx = getInitialContext();
		Object obj = null;
		log.info("Lookup for:" + jndiName);
		try {
			obj = ctx.lookup(jndiName);
		} catch (NamingException ex) {
			log.info("Can't find :" + jndiName);
			throw new NamingException(ex.getMessage());
		}finally{
			ctx.close();
		}
		return obj;
	}

}
