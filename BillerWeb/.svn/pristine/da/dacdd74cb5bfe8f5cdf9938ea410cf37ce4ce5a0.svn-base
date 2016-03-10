package com.dtac.billerweb.controller;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class TestController   extends SelectorComposer<Component> {
	Logger log=Logger.getLogger(TestController.class);
	@Wire 
	Window modalDialog;
	public void doAfterCompose(Component com) throws Exception {
		super.doAfterCompose(com);
		try {
			log.info("Start Do TestController");
			modalDialog.detach();
			log.debug("Parent id::");
			log.info("End Do AffterCompose");
			
		} catch (Exception ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
		
		} finally {

		}
	}
	public  void test(){
		Messagebox.show("Hello World!");	
	}
}
