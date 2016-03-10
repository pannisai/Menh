package com.dtac.bmweb.controller;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Include;
import org.zkoss.zul.Menuitem;

import com.dtac.bmweb.common.BaseControllor;

public class MenuBarController extends BaseControllor {
	private Logger log = Logger.getLogger(MenuBarController.class);

	@Wire
	Include mainContentTemplet;

	public void doAfterCompose(Component com) throws Exception {
		super.doAfterCompose(com);
		log.info("Init Menubar controller");
	}

	@Listen("onClick=menuitem")
	public void onChangeMenu(MouseEvent event) {
		try {
			Include mainContentTemplet = (Include) Path
					.getComponent("/mainContentTemplet");
			log.debug("ClickMenu1:" + mainContentTemplet.getId());
			Menuitem menuItem = (Menuitem) event.getTarget();
			mainContentTemplet.setSrc(menuItem.getValue());
//			 Executions.getCurrent().sendRedirect(menuItem.getValue());
		} catch (Exception ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
			showErrorMessage(ex);
		} finally {

		}

	}

}
