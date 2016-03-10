package com.dtac.billerweb.controller;

import java.util.Map;

import mfs.biller.persistence.bean.ReportTransDetail;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.dtac.billerweb.common.EditPageController;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.DialogException;
import com.dtac.billerweb.util.AppUtil;

public class BW3030Controller extends EditPageController {

	public BW3030Controller() {
		super("Biller Tran Report Detail","3030");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW3030Controller.class);
	private ReportTransDetail reportTransDetail = new ReportTransDetail();
	private String no="";

	@Wire
	private Button btClose;

	public void doBeforeComposeChildren(Component comp) {
		try {
			super.doBeforeComposeChildren(comp);
			setupPage();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "doBeforeComposeChildren", ex));
			showErrorMessage(ex);
		}

	}

	@Override
	protected void init() throws Exception {
		// TODO Auto-generated method stub

	}

	private void setupPage() {
		Map arg = null;
		try {
			arg = Executions.getCurrent().getArg();
			if (!AppUtil.isEmpty(arg)) {
				reportTransDetail = (ReportTransDetail) arg.get("reportTransDetail");
				no=(String)arg.get("NO");
			}
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			arg = null;
		}
	}

	
	@Listen("onClick = #btClose")
	public void cancel() {
		log.info(getOperationLogMessage(pageName, "Close", ""));
		Component parent = null;
		Window parentWindow = null;
		try {

			parent = super.getSelf().getParent().getParent().getParent();
			if (parent == null) {
				throw new DialogException("Parent is Null");
			}
			log.info("Parent::" + parent);
			parentWindow = (Window) parent;
			parentWindow.detach();

		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Close", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			parentWindow = null;
		}
	}

	
	public ReportTransDetail getReportTransDetail() {
		return reportTransDetail;
	}

	public void setReportTransDetail(ReportTransDetail reportTransDetail) {
		this.reportTransDetail = reportTransDetail;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
}
