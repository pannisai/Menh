package com.dtac.billerweb.controller;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.BillerCategory;
import mfs.exception.IsExistException;

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
import org.zkoss.zul.Grid;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
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
import com.dtac.billerweb.form.BW1110Form;
import com.dtac.billerweb.listmodel.selectbox.BillerCatalogListModel;
import com.dtac.billerweb.service.BillerCategoryService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW1110Controller extends EditPageController {
	public BW1110Controller() {
		super("Edit Biller Category","1110");
	}

	private Logger log = Logger.getLogger(BW1110Controller.class);
	private BW1110Form bw1110Form = new BW1110Form();

	@WireVariable
	private Page updatePage;
	@Wire
	private Label title;
	@Wire
	private Intbox intBillerCategoryId;
	@Wire
	private Textbox txBillerCategoryName;
	@Wire
	private Label lbCategoryName;
	@Wire
	private Textbox txBillerCategoryDesc;
	@Wire
	private Spinner spCategoryMenuSeq;
	@Wire
	private Label lbCategoryMenuSeq;
	@Wire
	private Listbox lbActiveFlag;
	@Wire
	private Button btSave;
	@Wire
	private Button btReset;
	@Wire
	private Button btCancel;

	@Override
	protected void init() throws Exception {
		// TODO Auto-generated method stub
		if (oid == -1) {
			setupNewPage();
		} else {
			setupEditPage();
		}
	}

	private void setupNewPage() {
		try {
			chkInsertPermission(btSave);
			title.setValue(AppMessage.getMessage(AppMessage.BW1110_NEW_TITLE));
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setupEditPage() {
		Include i = null;
		Collection<Component> comps = null;
		try {
			i = (Include) Path.getComponent("/bw1110Dialog/bw1110DialogInclude");
			comps = i != null ? i.getFellows() : updatePage.getFellows();
			if (chkUpdatePermission(comps, btSave, btReset)) {
				title.setValue(AppMessage.getMessage(AppMessage.BW1110_EDIT_TITLE));
			} else {
				title.setValue(AppMessage.getMessage(AppMessage.BW1110_VIEW_TITLE));
			}
			loadFormData(oid);
			refreshForm();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			i = null;
			comps = null;
		}
	}

	private void loadFormData(Integer oid) {
		log.info(getOperationLogMessage(pageName, "Load Data", ""));
		BillerCategoryService billerCategoryService = null;
		BillerCategory billerCategory = null;
		try {
			billerCategoryService = BillerwebServiceFactory.getBillerCategoryService();
			billerCategory = billerCategoryService.getByID(oid, getUserInfo());
			bw1110Form.setBillerCategoryId(billerCategory.getBLLR_CATG_ID());
			bw1110Form.setCategoryMenuSeq(billerCategory.getBLLR_CATG_MENU_SEQ());
			bw1110Form.setBillerCategoryDesc(billerCategory.getBLLR_CATG_DESC());
			bw1110Form.setBillerCategoryName(billerCategory.getBLLR_CATG_NAME());
			bw1110Form.setActiveFlag(billerCategory.getACT_FLAG());
			bw1110Form.setUpdateBy(billerCategory.getLAST_CHNG_BY());
			bw1110Form.setUpdateDate(billerCategory.getLAST_CHNG_DTTM());
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private void refreshForm() {
		try {
			intBillerCategoryId.setValue(bw1110Form.getBillerCategoryId());
			txBillerCategoryName.setValue(bw1110Form.getBillerCategoryName());
			txBillerCategoryDesc.setValue(bw1110Form.getBillerCategoryDesc());
			spCategoryMenuSeq.setValue(bw1110Form.getCategoryMenuSeq());
			if (bw1110Form.getActiveFlag().equalsIgnoreCase("A")) {
				lbActiveFlag.setSelectedIndex(0);
			} else {
				lbActiveFlag.setSelectedIndex(1);
			}
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void updateBW1110Form() {
		try {
			bw1110Form.setActiveFlag(lbActiveFlag.getSelectedItem().getValue().toString());
			bw1110Form.setActiveFlagIndex(lbActiveFlag.getSelectedIndex());
			bw1110Form.setBillerCategoryDesc(txBillerCategoryDesc.getValue());
			bw1110Form.setBillerCategoryId(intBillerCategoryId.getValue());
			bw1110Form.setBillerCategoryName(txBillerCategoryName.getValue());
			bw1110Form.setCategoryMenuSeq(spCategoryMenuSeq.getValue());
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	/* ## Event ## */
	@Listen("onClick = #btSave")
	public void clickSave() {		
		try {
			log.info(getOperationLogMessage(pageName, "Save Biller Category", ""));
			updateBW1110Form();
			validateCategName();
			validateMenuSeq();
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
			log.error(getErrorLogMessage(pageName, "Save Biller Category", getErrorParames(), ex));
			showErrorMessage(ex);			
		} finally {

		}
		log.info(getStopLogMessage(pageName));
		
	}

	private void save() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Save " + pageName);
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerCategoryService billerCategoryService;
					BillerCategory billerCategory;
					try {
						billerCategoryService = BillerwebServiceFactory.getBillerCategoryService();
						billerCategory = bw1110Form.toBillerCategory();
						billerCategory.setCRTD_BY(getUserName());
						billerCategory.setLAST_CHNG_BY(getUserName());
						final Integer oid = billerCategoryService.save(billerCategory, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("Save OK");
									if (isDialog()) {
										processSaveInDialogMode();
									} else {
										redirect(AppConfiguration.getValue(AppConfiguration.BW1110_REDIRECT_PATH) + "?oid=" + oid);
									}
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Save Biller Category", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BLLR_CATG_NAME")) {
							Validation.isExist(txBillerCategoryName, true, txBillerCategoryName.getValue());
						} else if (errorMessage.equalsIgnoreCase("BLLR_CATG_MENU_SEQ")) {
							Validation.isExist(spCategoryMenuSeq, true, spCategoryMenuSeq.getValue() + "");
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Biller Category", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Save", requestDate, ex);
					} finally {
						billerCategoryService = null;
						// billerCategory = null;
					}

				}

			}
		};
		showSaveConfirmMessage(saveConfirmListener);
		setTxLogMessage(pageCode + "#Save", requestDate, "");
	}

	private void processSaveInDialogMode() {
		SelectBoxService selectBoxService = null;
		Component parent = null;
		Window parentWindow = null;
		BillerCatalogListModel<BillerCategory> billerCatalogListModel = null;
		try {
			parent = super.getSelf().getParent().getParent();
			if (parent == null) {
				throw new DialogException("Parent is Null");
			}
			log.info("Parent::" + parent);
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			parentWindow = (Window) parent;
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1411_REDIRECT_PATH))) {
				Page pBw1411 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1411");
				log.info("Page:" + pBw1411);
				Listbox lbBillerCategoryId = null;
				if (pBw1411 != null) {
					lbBillerCategoryId = (Listbox) pBw1411.getFellow("lbBillerCategoryId");
				} else {
					Include i = (Include) Path.getComponent("/bw1411Dialog/bw1411DialogInclude");
					lbBillerCategoryId = (Listbox) i.getFellow("lbBillerCategoryId");
				}
				lbBillerCategoryId = (Listbox) pBw1411.getFellow("lbBillerCategoryId");
				billerCatalogListModel = selectBoxService.getBillerCatalogListModel();
				billerCatalogListModel.addItemSelect();
				billerCatalogListModel.addItemCreateNew();
				lbBillerCategoryId.setModel(billerCatalogListModel);
			} else if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1100_REDIRECT_PATH))) {
				Page pBw1100 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1100");
				log.info("Page:" + pBw1100);
				Grid gdResult = null;
				if (pBw1100 != null) {
					gdResult = (Grid) pBw1100.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw1100Dialog/bw1100DialogInclude");
					gdResult = (Grid) i.getFellow("gdResult");
				}

				Event onRefresh = new Event("onRefresh", gdResult);
				Events.sendEvent(onRefresh);
			}
			parentWindow.detach();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "ProcessSaveInDialogMode", "[Parrent:" + parent + ",Callback:" + callback, ex));
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			parent = null;
			parentWindow = null;
			billerCatalogListModel = null;
		}
	}

	private void update() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Update " + pageName);
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerCategoryService billerCategoryService;
					BillerCategory billerCategory;
					try {
						billerCategoryService = BillerwebServiceFactory.getBillerCategoryService();
						billerCategory = bw1110Form.toBillerCategory();
						billerCategory.setLAST_CHNG_BY(getUserName());
						log.debug("billerCategory.getBLLR_CATG_DESC()::" + billerCategory.getBLLR_CATG_DESC());
						billerCategoryService.update(billerCategory, getUserInfo());
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
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Update Biller Category", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BLLR_CATG_NAME")) {
							Validation.isExist(txBillerCategoryName, true, txBillerCategoryName.getValue());
						} else if (errorMessage.equalsIgnoreCase("BLLR_CATG_MENU_SEQ")) {
							Validation.isExist(spCategoryMenuSeq, true, spCategoryMenuSeq.getValue() + "");
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Biller Category", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Update", requestDate, ex);
					} finally {
						billerCategoryService = null;
					}

				}

			}
		};
		showSaveConfirmMessage(saveConfirmListener);
		setTxLogMessage(pageCode + "#Update", requestDate, "");
	}

	private void processUpdateInDialogMode() {
		SelectBoxService selectBoxService = null;
		Component parent = null;
		Window parentWindow = null;
		BillerCatalogListModel<BillerCategory> billerCatalogListModel = null;
		try {
			parent = super.getSelf().getParent().getParent();
			if (parent == null) {
				throw new DialogException("Parent is Null");
			}
			log.info("Parent::" + parent);
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			parentWindow = (Window) parent;
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1411_REDIRECT_PATH))) {
				Page pBw1411 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1411");
				log.info("Page:" + pBw1411);
				Listbox lbBillerCategoryId = null;
				if (pBw1411 != null) {
					lbBillerCategoryId = (Listbox) pBw1411.getFellow("lbBillerCategoryId");
				} else {
					Include i = (Include) Path.getComponent("/bw1411Dialog/bw1411DialogInclude");
					lbBillerCategoryId = (Listbox) i.getFellow("lbBillerCategoryId");
				}
				int currentBillerCategoryId = lbBillerCategoryId.getSelectedIndex();
				billerCatalogListModel = selectBoxService.getBillerCatalogListModel();
				billerCatalogListModel.addItemSelect();
				billerCatalogListModel.addItemCreateNew();
				billerCatalogListModel.addDataToSelection(currentBillerCategoryId);
				lbBillerCategoryId.setModel(billerCatalogListModel);
			} else if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1100_REDIRECT_PATH))) {
				Page pBw1100 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1100");
				log.info("Page:" + pBw1100);
				Grid gdResult = null;
				if (pBw1100 != null) {
					gdResult = (Grid) pBw1100.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw1100Dialog/bw1100DialogInclude");
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
			selectBoxService = null;
			parent = null;
			parentWindow = null;
			billerCatalogListModel = null;
		}
	}

	private Map<String, String> getErrorParames() {
		Map<String, String> params = new HashMap<String, String>();
		try {
			log.debug("GetErrorParames::" + bw1110Form.getBillerCategoryName());
			params.put("BillerCategoryID", bw1110Form.getBillerCategoryId() + "");
			params.put("BillerCategoryName", bw1110Form.getBillerCategoryName() + "");
			params.put("BillerCategoryDesc", bw1110Form.getBillerCategoryDesc() + "");
			params.put("CategoryMenuSeq", bw1110Form.getCategoryMenuSeq() + "");
			params.put("ActiveFlag", bw1110Form.getActiveFlag() + "");
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
		return params;
	}

	@Listen("onClick = #btReset")
	public void reset() {
		log.info(getOperationLogMessage(pageName, "Reset", ""));
		EventListener resetConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					try {
						bw1110Form.setActiveFlag("A");
						bw1110Form.setActiveFlagIndex(0);
						bw1110Form.setBillerCategoryDesc("");
						bw1110Form.setBillerCategoryName("");
						bw1110Form.setCategoryMenuSeq(null);
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
				redirect(AppConfiguration.getValue(AppConfiguration.BW1100_REDIRECT_PATH));
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Cancel", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			parentWindow = null;
		}

	}

	@Listen("onBlur= #txBillerCategoryName")
	public void onBlurBillerCategoryName(Event event) {
		try {
			validateCategName();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	@Listen("onBlur= #intCategoryMenuSeq")
	public void onBlurCategoryMenuSeq(Event event) {
		try {
			validateMenuSeq();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void validateCategName() throws WrongValueException, Exception {
		BillerCategoryService billerCategoryService = null;
		boolean result = false;
		Validation.isNotEmpty(txBillerCategoryName, txBillerCategoryName.getValue(), lbCategoryName.getValue());
		billerCategoryService = BillerwebServiceFactory.getBillerCategoryService();
		result = billerCategoryService.isExistBllrCatgName(oid, txBillerCategoryName.getValue(), getUserInfo());
		if (result) {
			Validation.isExist(txBillerCategoryName, result, txBillerCategoryName.getValue());
		}
		billerCategoryService = null;
	}

	private void validateMenuSeq() throws WrongValueException, Exception {
		BillerCategoryService billerCategoryService = null;
		boolean result = false;
		Validation.isNotEmpty(spCategoryMenuSeq, spCategoryMenuSeq.getValue(), lbCategoryMenuSeq.getValue());
		Validation.isStrNumber(spCategoryMenuSeq, spCategoryMenuSeq.getValue() + "");
		billerCategoryService = BillerwebServiceFactory.getBillerCategoryService();
		result = billerCategoryService.isExistBillMenuSeq(oid, spCategoryMenuSeq.getValue(), getUserInfo());
		if (result) {
			Validation.isExist(spCategoryMenuSeq, result, spCategoryMenuSeq.getValue() + "");
		}

		billerCategoryService = null;
	}

}
