package com.dtac.billerweb.common;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;



import mfs.exception.InitialEJBContextException;

public class EJBInitialContext {
	private static Logger log = Logger.getLogger(EJBInitialContext.class);

	// private static Context context = null;

	private EJBInitialContext() {
	}

	public static Context getInitialContext() throws InitialEJBContextException {
		Context context = null;
		log.info("Create context");
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, AppConfiguration
					.getValue(AppConfiguration.INITIAL_CONTEXT_FACTORY_KEY));
			env.put(Context.PROVIDER_URL, AppConfiguration
					.getValue(AppConfiguration.PROVIDER_URL_KEY));
			env.put(Context.SECURITY_PRINCIPAL, AppConfiguration
					.getValue(AppConfiguration.SECURITY_PRINCIPAL_KEY));
			env.put(Context.SECURITY_CREDENTIALS, AppConfiguration
					.getValue(AppConfiguration.SECURITY_CREDENTIALS_KEY));
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
