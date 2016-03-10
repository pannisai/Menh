package com.dtac.billerweb.controller;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mfs.biller.persistence.bean.BillerBarcode;
import mfs.biller.persistence.bean.DropdownlistBillservice;
import mfs.biller.util.DateTimeUtil;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.SimpleDateConstraint;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.common.EditPageController;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.exception.BillerWebValidationException;
import com.dtac.billerweb.exception.DialogException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.form.BW1510Form;
import com.dtac.billerweb.listmodel.selectbox.BillerServiceListModel;
import com.dtac.billerweb.service.BillerBarcodeService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW1510Controller extends EditPageController {

	public BW1510Controller() {
		super("Edit Biller Barcode", "1510");
	}

	private Logger log = Logger.getLogger(BW1510Controller.class);
	private BW1510Form bw1510Form = new BW1510Form();

	@WireVariable
	private Page updatePage;
	@Wire
	private Label title;
	@Wire
	private Intbox intBARC_ID;
	@Wire
	private Textbox txBARC_NAME;
	@Wire
	private Listbox lbBillerService;
	@Wire
	private Label lServiceName;
	@Wire
	private Intbox intBARC_LINE_NO;
	@Wire
	private Listbox lbBarcodeType;
	@Wire
	private Radiogroup rdBARC_CRRG_RETN_FLAG;
	@Wire
	private Spinner spBARC_LINE_MAX;
	@Wire
	private Radiogroup rdBARC_PERF_FLAG;
	@Wire
	private Textbox txNewLinePosition;
	@Wire
	private Textbox txServiceCode;
	@Wire
	private Textbox txBARC_PERF_VALUE;
	@Wire
	private Datebox dbBARC_EFFT_DATE;
	@Wire
	private Datebox dbBARC_EXPR_DATE;
	@Wire
	private Listbox lbACT_FLAG;
	@Wire
	private Button btSave;
	@Wire
	private Button btReset;
	@Wire
	private Button btCancel;

	@Override
	protected void init() throws Exception {
		// TODO Auto-generated method stub
		setupPage();
		if (oid == -1) {
			setupNewPage();
		} else {
			setupEditPage();
		}
	}

	private void setupPage() {
		SelectBoxService selectBoxService = null;
		BillerServiceListModel billerServiceListModel = null;
		try {
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			/* ## Set Biller service list ## */
			billerServiceListModel = selectBoxService.getBillerServiceListModel();
			billerServiceListModel.addItemSelect();
			lbBillerService.setModel(billerServiceListModel);

		
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			billerServiceListModel = null;
		}
	}

	private void setupNewPage() {
		try {
			chkInsertPermission(btSave);
			title.setValue(AppMessage.getMessage(AppMessage.BW1510_NEW_TITLE));
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setupEditPage() {
		Include i = null;
		Collection<Component> comps = null;
		try {
			i = (Include) Path.getComponent("/bw1510Dialog/bw1510DialogInclude");
			comps = i != null ? i.getFellows() : updatePage.getFellows();
			if (chkUpdatePermission(comps, btSave, btReset)) {
				title.setValue(AppMessage.getMessage(AppMessage.BW1510_EDIT_TITLE));
			} else {
				title.setValue(AppMessage.getMessage(AppMessage.BW1510_VIEW_TITLE));
			}
			loadFormData(oid);
			refreshForm();
			setDateConstraint();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			i = null;
			comps = null;
		}
	}

	private void loadFormData(Integer oid) {
		log.info(getOperationLogMessage(pageName, "Load Data", ""));
		BillerBarcodeService billerBarcodeService = null;
		BillerBarcode billerBarcode = null;
		try {
			billerBarcodeService = BillerwebServiceFactory.getBillerBarcodeService();
			billerBarcode = billerBarcodeService.getByID(oid, getUserInfo());

			bw1510Form.setBARC_ID(billerBarcode.getBARC_ID());
			bw1510Form.setBillServiceId(billerBarcode.getBLLR_SRVC_ID());
			bw1510Form.setBarcNewLinePost(billerBarcode.getBARC_NEW_LINE_POST());
			bw1510Form.setBarcServiceCode(billerBarcode.getBARC_SRVC_CODE());
			bw1510Form.setBARC_NAME(billerBarcode.getBARC_NAME());
			bw1510Form.setBARC_CRRG_RETN_FLAG(billerBarcode.getBARC_CRRG_RETN_FLAG());
			bw1510Form.setBARC_PERF_FLAG(billerBarcode.getBARC_PERF_FLAG());
			bw1510Form.setBARC_PERF_VALUE(billerBarcode.getBARC_PERF_VALUE());
			bw1510Form.setBARC_LINE_MAX(billerBarcode.getBARC_LINE_MAX());
			bw1510Form.setBarcodeType(billerBarcode.getBARC_TYPE());
			bw1510Form.setACT_FLAG(billerBarcode.getACT_FLAG());
			bw1510Form.setBARC_EFFT_DATE(billerBarcode.getBARC_EFFT_DATE());
			bw1510Form.setBARC_LINE_NO(billerBarcode.getBARC_LINE_NO());
			bw1510Form.setCRTD_BY(billerBarcode.getCRTD_BY());
			bw1510Form.setCRTD_DTTM(billerBarcode.getCRTD_DTTM());
			bw1510Form.setLAST_CHNG_BY(billerBarcode.getLAST_CHNG_BY());
			bw1510Form.setLAST_CHNG_DTTM(billerBarcode.getLAST_CHNG_DTTM());
			bw1510Form.setBARC_EXPR_DATE(billerBarcode.getBARC_EXPR_DATE());

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			log.info(getStopLogMessage(pageName));
		}
	}

	private void refreshForm() {
		BillerServiceListModel<DropdownlistBillservice> billerServiceListModel = null;
		try {

			billerServiceListModel = (BillerServiceListModel) lbBillerService.getModel();
			int billerServiceIndex = billerServiceListModel.findIndexOfId(bw1510Form.getBillServiceId());
			if (billerServiceIndex > -1 && lbBillerService.getItemCount() > billerServiceIndex) {
				billerServiceListModel.addDataToSelection(billerServiceIndex);
			}

			intBARC_ID.setValue(bw1510Form.getBARC_ID());
			txBARC_NAME.setValue(bw1510Form.getBARC_NAME());
			intBARC_LINE_NO.setValue(bw1510Form.getBARC_LINE_NO());
			spBARC_LINE_MAX.setValue(bw1510Form.getBARC_LINE_MAX() == null ? 0 : bw1510Form.getBARC_LINE_MAX());
			txBARC_PERF_VALUE.setValue(bw1510Form.getBARC_PERF_VALUE());
			dbBARC_EFFT_DATE.setValue(bw1510Form.getBARC_EFFT_DATE());
			lbBarcodeType.setSelectedIndex((!AppUtil.isEmpty(bw1510Form.getBarcodeType()) && bw1510Form.getBarcodeType().equalsIgnoreCase("NON-BOT")) ? 1 : 0);
			rdBARC_CRRG_RETN_FLAG.setSelectedIndex((bw1510Form.getBARC_CRRG_RETN_FLAG().equalsIgnoreCase("Y")) ? 0 : 1);
			rdBARC_PERF_FLAG.setSelectedIndex((bw1510Form.getBARC_PERF_FLAG().equalsIgnoreCase("Y")) ? 0 : 1);
			txNewLinePosition.setValue(bw1510Form.getBarcNewLinePost());
			txServiceCode.setValue(bw1510Form.getBarcServiceCode());
			lbACT_FLAG.setSelectedIndex((bw1510Form.getACT_FLAG().equalsIgnoreCase("A")) ? 0 : 1);
			dbBARC_EXPR_DATE.setValue(bw1510Form.getBARC_EXPR_DATE());

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void updateBW1510Form() {
		try {
			bw1510Form.setBARC_ID(intBARC_ID.getValue());
			DropdownlistBillservice billerService = lbBillerService.getSelectedItem().getValue();
			bw1510Form.setBillServiceId(billerService.getBLLR_SRVC_ID());
			bw1510Form.setBarcNewLinePost(txNewLinePosition.getValue());
			bw1510Form.setBarcServiceCode(txServiceCode.getValue());
			bw1510Form.setBARC_NAME(txBARC_NAME.getValue());
			bw1510Form.setBARC_CRRG_RETN_FLAG(rdBARC_CRRG_RETN_FLAG.getSelectedItem().getValue().toString());
			bw1510Form.setBARC_PERF_FLAG(rdBARC_PERF_FLAG.getSelectedItem().getValue().toString());
			bw1510Form.setBARC_PERF_VALUE(txBARC_PERF_VALUE.getValue());
			bw1510Form.setBARC_LINE_MAX(spBARC_LINE_MAX.getValue());
			bw1510Form.setBARC_EFFT_DATE(dbBARC_EFFT_DATE.getValue());
			bw1510Form.setBARC_LINE_NO(intBARC_LINE_NO.getValue());
			bw1510Form.setBarcodeType(lbBarcodeType.getSelectedItem().getValue().toString());
			bw1510Form.setACT_FLAG(lbACT_FLAG.getSelectedItem().getValue().toString());
			bw1510Form.setACT_FLAG_INDEX(lbACT_FLAG.getSelectedIndex());
			bw1510Form.setBARC_CRRG_RETN_FLAG_INDEX(rdBARC_CRRG_RETN_FLAG.getSelectedIndex());
			bw1510Form.setBARC_PERF_FLAG_INDEX(rdBARC_PERF_FLAG.getSelectedIndex());
			bw1510Form.setBARC_EXPR_DATE(dbBARC_EXPR_DATE.getValue());
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setDateConstraint() {
		log.debug("setDateConstraint");
		try {
			
			log.debug("dbBARC_EXPR_DATE.getValue():"+dbBARC_EXPR_DATE.getValue());
			if (dbBARC_EXPR_DATE.getValue() != null) {
				String expireDate = DateTimeUtil.getDateTime(dbBARC_EXPR_DATE.getValue(), "yyyyMMdd");
				SimpleDateConstraint fromDateConstarint = new SimpleDateConstraint("before " + expireDate);
				log.debug("Before:"  + expireDate);
				dbBARC_EFFT_DATE.setConstraint(fromDateConstarint);
			}
			log.debug("dbBARC_EFFT_DATE.getValue():"+dbBARC_EFFT_DATE.getValue());
			if (dbBARC_EFFT_DATE.getValue() != null) {
				String activeDateStr = DateTimeUtil.getDateTime(dbBARC_EFFT_DATE.getValue(), "yyyyMMdd");
				SimpleDateConstraint toDateConstarint = new SimpleDateConstraint("after " + activeDateStr);
				log.debug("After:"  + activeDateStr);
				dbBARC_EXPR_DATE.setConstraint(toDateConstarint);
			}
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	@Listen("onClick = #btSave")
	public void clickSave() {
		try {
			log.info(getOperationLogMessage(pageName, "Save Biller Barcode", ""));
			validateBillerService();
			validateNewLinePosition();
			validateServiceCode();
			updateBW1510Form();
			if (oid == -1) {
				save();
			} else {
				update();
			}
		} catch (BillerWebSessionTimeOutException bwto) {
			throw new BillerWebSessionTimeOutException(bwto);
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Save Biller Barcode", getErrorParames(), ex));
			showErrorMessage(ex);
		} finally {
			log.info(getStopLogMessage(pageName));
		}
	}

	private void save() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Save " + pageName);
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerBarcodeService billerBarcodeService;
					BillerBarcode billerBarcode;
					try {
						billerBarcodeService = BillerwebServiceFactory.getBillerBarcodeService();
						billerBarcode = bw1510Form.toBillerBarcode();
						billerBarcode.setCRTD_BY(getUserName());
						billerBarcode.setLAST_CHNG_BY(getUserName());
						final Integer oid = billerBarcodeService.save(billerBarcode, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("Save OK");
									if (isDialog()) {
										processSaveInDialogMode();
									} else {
										redirect(AppConfiguration.getValue(AppConfiguration.BW1510_REDIRECT_PATH) + "?oid=" + oid);
									}
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Biller Barcode", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Save", requestDate, ex);
					} finally {
						billerBarcodeService = null;
					}

				}

			}
		};
		showSaveConfirmMessage(saveConfirmListener);
		setTxLogMessage(pageCode + "#Save", requestDate, "");
	}

	private void processSaveInDialogMode() {
		Component parent = null;
		Window parentWindow = null;
		try {
			parent = super.getSelf().getParent().getParent();
			if (parent == null) {
				throw new DialogException("Parent is Null");
			}
			log.info("Parent::" + parent);
			log.info("Callback:" + callback);
			parentWindow = (Window) parent;
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1415_REDIRECT_PATH))) {
				Page pBw1415 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1415");
				log.info("Parent Page:" + pBw1415);
				Grid gdBarcode = null;
				Grid gdDetail = null;
				if (pBw1415 != null) {
					gdBarcode = (Grid) pBw1415.getFellow("gdBarcode");
					gdDetail = (Grid) pBw1415.getFellow("gdDetail");
				} else {
					Include i = (Include) Path.getComponent("/bw1415Dialog/bw1415DialogInclude");
					gdBarcode = (Grid) i.getFellow("gdBarcode");
					gdDetail = (Grid) i.getFellow("gdDetail");
				}
				if (pBw1415 != null) {
					Event onRefreshBarcode = new Event("onRefresh", gdBarcode);
					Events.sendEvent(onRefreshBarcode);
					Event onRefreshDetail = new Event("onRefresh", gdDetail);
					Events.sendEvent(onRefreshDetail);
				}
			} else if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1450_REDIRECT_PATH))) {

				Page pBw1450 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1450");
				log.info("Page:" + pBw1450);
				Listbox lbBarcodeType = null;
				if (pBw1450 != null) {
					lbBarcodeType = (Listbox) pBw1450.getFellow("lbBarcodeType");
				} else {
					Include i = (Include) Path.getComponent("/bw1450Dialog/bw1450DialogInclude");
					lbBarcodeType = (Listbox) i.getFellow("lbBarcodeType");
				}
				Event onRefreshBarcodeType = new Event("onRefresh", lbBarcodeType);
				Events.postEvent(onRefreshBarcodeType);
			}
			parentWindow.detach();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "ProcessSaveInDialogMode", "[Parrent:" + parent + ",Callback:" + callback, ex));
			throw new BillerWebException(ex);
		} finally {
			parent = null;
			parentWindow = null;

		}
	}

	private void update() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Update " + pageName);
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerBarcodeService billerBarcodeService;
					BillerBarcode billerBarcode;
					try {
						billerBarcodeService = BillerwebServiceFactory.getBillerBarcodeService();
						billerBarcode = bw1510Form.toBillerBarcode();
						billerBarcode.setLAST_CHNG_BY(getUserName());
						billerBarcodeService.update(billerBarcode, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("update OK");
									if (isDialog()) {
										processUpdateInDialogMode();
									}
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Biller Barcode", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Update", requestDate, ex);
					} finally {
						billerBarcodeService = null;
					}

				}

			}
		};
		showSaveConfirmMessage(saveConfirmListener);
		setTxLogMessage(pageCode + "#Update", requestDate, "");
	}

	private void processUpdateInDialogMode() {
		Component parent = null;
		Window parentWindow = null;
		try {
			parent = super.getSelf().getParent().getParent();
			if (parent == null) {
				throw new DialogException("Parent is Null");
			}
			log.info("Parent::" + parent);
			log.info("Callback:" + callback);

			parentWindow = (Window) parent;
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1415_REDIRECT_PATH))) {
				Page pBw1415 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1415");
				log.info("Parent Page:" + pBw1415);
				Grid gdBarcode = null;
				Grid gdDetail = null;
				if (pBw1415 != null) {
					gdBarcode = (Grid) pBw1415.getFellow("gdBarcode");
					gdDetail = (Grid) pBw1415.getFellow("gdDetail");
				} else {
					Include i = (Include) Path.getComponent("/bw1415Dialog/bw1415DialogInclude");
					gdBarcode = (Grid) i.getFellow("gdBarcode");
					gdDetail = (Grid) i.getFellow("gdDetail");
				}
				if (pBw1415 != null) {
					Event onRefreshBarcode = new Event("onRefresh", gdBarcode);
					Events.sendEvent(onRefreshBarcode);
					Event onRefreshDetail = new Event("onRefresh", gdDetail);
					Events.sendEvent(onRefreshDetail);
				}
			} else if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1450_REDIRECT_PATH))) {

				Page pBw1450 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1450");
				log.info("Page:" + pBw1450);
				Listbox lbBarcodeType = null;
				if (pBw1450 != null) {
					lbBarcodeType = (Listbox) pBw1450.getFellow("lbBarcodeType");
				} else {
					Include i = (Include) Path.getComponent("/bw1450Dialog/bw1450DialogInclude");
					lbBarcodeType = (Listbox) i.getFellow("lbBarcodeType");
				}
				Event onRefreshBarcodeType = new Event("onRefresh", lbBarcodeType);
				Events.sendEvent(onRefreshBarcodeType);
			} else if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1500_REDIRECT_PATH))) {
				Page pBw1500 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1500");
				log.info("Page:" + pBw1500);
				Grid gdResult = null;
				if (pBw1500 != null) {
					gdResult = (Grid) pBw1500.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw1500Dialog/bw1500DialogInclude");
					gdResult = (Grid) i.getFellow("gdResult");
				}

				Event onRefresh = new Event("onRefresh", gdResult);
				Events.sendEvent(onRefresh);
			}

			parentWindow.detach();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "ProcessUpdateInDialogMode", "[Parrent:" + parent + ",Callback:" + callback, ex));
			throw new BillerWebException(ex);
		} finally {
			parent = null;
			parentWindow = null;

		}
	}

	private Map<String, String> getErrorParames() {
		Map<String, String> params = new HashMap<String, String>();
		try {
			log.debug("GetErrorParames::" + bw1510Form.getBARC_NAME());
			params.put("BARC_ID", bw1510Form.getBARC_ID() + "");
			params.put("Service Name", bw1510Form.getBillServiceId() + "");
			params.put("New Line Position", bw1510Form.getBarcNewLinePost() + "");
			params.put("Service Code", bw1510Form.getBarcServiceCode() + "");
			params.put("BARC_NAME", bw1510Form.getBARC_NAME() + "");
			params.put("BARC_CRRG_RETN_FLAG", bw1510Form.getBARC_CRRG_RETN_FLAG() + "");
			params.put("BARC_PERF_FLAG", bw1510Form.getBARC_PERF_FLAG() + "");
			params.put("BARC_PERF_VALUE", bw1510Form.getBARC_PERF_VALUE() + "");
			params.put("BARC_LINE_MAX", bw1510Form.getBARC_LINE_MAX() + "");
			params.put("BARC_EFFT_DATE", bw1510Form.getBARC_EFFT_DATE() + "");
			params.put("BARC_EXPR_DATE", bw1510Form.getBARC_EXPR_DATE() + "");
			params.put("BARC_LINE_NO", bw1510Form.getBARC_LINE_NO() + "");
			params.put("Barcode Type", bw1510Form.getBarcodeType() + "");
			params.put("ACT_FLAG", bw1510Form.getACT_FLAG() + "");
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
		return params;
	}

	@Listen("onClick = #btReset")
	public void reset() {
		log.info("Click Reset Edit Biller Barcode");
		EventListener resetConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					try {
						bw1510Form.setBARC_NAME("");
						bw1510Form.setBillServiceId(null);
						bw1510Form.setBarcNewLinePost(null);
						bw1510Form.setBarcServiceCode(null);
						bw1510Form.setBARC_CRRG_RETN_FLAG("Y");
						bw1510Form.setBARC_PERF_FLAG("Y");
						bw1510Form.setBARC_PERF_VALUE("");
						bw1510Form.setBARC_LINE_MAX(null);
						bw1510Form.setBARC_EFFT_DATE(null);
						bw1510Form.setBARC_LINE_NO(null);
						bw1510Form.setACT_FLAG("A");
						bw1510Form.setBarcodeType("BOT");
						lbBarcodeType.setSelectedIndex(0);
						bw1510Form.setACT_FLAG_INDEX(0);
						bw1510Form.setBARC_CRRG_RETN_FLAG_INDEX(0);
						bw1510Form.setBARC_PERF_FLAG_INDEX(0);
						bw1510Form.setBARC_EXPR_DATE(null);

						refreshForm();

					} catch (Exception ex) {
						log.error(ExceptionUtils.getStackTrace(ex));
						showErrorMessage(ex);
					} finally {

					}
				}
			}
		};
		showResetConfirmMessage(resetConfirmListener);
	}

	@Listen("onClick = #btCancel")
	public void cancel() {
		log.info(getOperationLogMessage(pageName, "Cancel", ""));
		Component parent = null;
		Window parentWindow = null;
		try {
			if (isDialog()) {
				parent = super.getSelf().getParent().getParent();
				if (parent == null) {
					throw new DialogException("Parent is Null");
				}
				log.info("Parent::" + parent);
				parentWindow = (Window) parent;
				parentWindow.detach();
			} else {
				redirect(AppConfiguration.getValue(AppConfiguration.BW1500_REDIRECT_PATH));
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Cancel", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			parentWindow = null;
		}

	}
	
	@Listen("onBlur = #dbBARC_EFFT_DATE")
	public void clickDbBARC_EFFT_DATE() {
		log.info("Click dbBARC_EFFT_DATE " + pageName);
		try {
			setDateConstraint();

		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			showErrorMessage(ex);
		} finally {

		}
	}

	@Listen("onBlur = #dbBARC_EXPR_DATE")
	public void clickDbBARC_EXPR_DATE() {
		log.info("Click DbBARC_EXPR_DATE " + pageName);
		try {
			setDateConstraint();
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			showErrorMessage(ex);
		} finally {

		}
	}
	/* ## Validation ## */
	private void validateBillerService() throws WrongValueException, Exception {
		DropdownlistBillservice value = lbBillerService.getSelectedItem().getValue();
		Validation.isSelectDropdownList(lbBillerService, value.getBLLR_SRVC_ID(), lServiceName.getValue());
	}

	private void validateNewLinePosition() throws WrongValueException, Exception {
		String value = txNewLinePosition.getValue();
		if (AppUtil.isEmpty(value)) {
			return;
		}
		String regex = "^([0-9]+,[0-9]*)+$";
		Pattern pat = Pattern.compile(regex);
		Matcher match = pat.matcher(value);
		if (!match.find()) {
			throw new WrongValueException(txNewLinePosition, AppMessage.getMessage(AppMessage.MSG_INVALID_NEW_LINE_POSITION));
		}
	}

	private void validateServiceCode() throws WrongValueException, Exception {
		String value = txServiceCode.getValue();
		if (AppUtil.isEmpty(value)) {
			return;
		}
		String regex = "^([0-9]+,[0-9]*)+$";
		Pattern pat = Pattern.compile(regex);
		Matcher match = pat.matcher(value);
		if (!match.find()) {
			throw new WrongValueException(txServiceCode, AppMessage.getMessage(AppMessage.MSG_INVALID_SERVICE_CODE));
		}
	}
}
