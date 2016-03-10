package test;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import mfs.biller.persistence.bean.UserInfoBean;

public class TestEJB {
	public String test() {
		String message = "";
		System.out.println("Start Test Ejb");
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
			env.put("java.naming.provider.url", "t3://10.64.119.4:7141");
			env.put("java.naming.security.principal", "backoffd");// mfsappd
			env.put("java.naming.security.credentials", "weblogic1");

			System.out.println("Before Create innitial");
			Context ctx = new InitialContext(env);
		
			
			UserInfoBean userInfoBean = new UserInfoBean();
			userInfoBean.setIpAddress("10.10.10.10");
			userInfoBean.setName("TestUser");
			
//			RptTransBeanRemote testRemote = (RptTransBeanRemote) ctx.lookup(RptTransBeanRemote.JNDI_WEBLOGIC);
			System.out.println("After Lookup");
		
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
		}
		return message;
	}

	public static void main(String[] args) {
		TestEJB testEjb = new TestEJB();
		System.out.println(testEjb.test());
	}
}
