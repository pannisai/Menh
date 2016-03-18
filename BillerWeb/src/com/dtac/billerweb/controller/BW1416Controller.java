package com.dtac.billerweb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.BillerService;
import mfs.biller.persistence.bean.BillerServiceImage;
import mfs.exception.IsExistException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Tab;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.common.EditPageController;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.exception.BillerWebValidationException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.form.BW1416Form;
import com.dtac.billerweb.service.BillerServiceService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.util.HttpClient;
import com.dtac.billerweb.validation.Validation;

public class BW1416Controller extends EditPageController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8042756093459401471L;

	public BW1416Controller() {
		super("Biller Service/Image", "1416");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW1412Controller.class);
	private BW1416Form bw1416Form = new BW1416Form();

	@WireVariable
	private Page updatePage;
	@Wire
	private Label bw1416Title;
	@Wire
	private Button btBack;
	@Wire
	private Button btSave;
	@Wire
	private Button btPublish;
	@Wire
	private Button btWebIconUpload;
	@Wire
	private Button btMobIconUpload;
	@Wire
	private Button btWebInstUpload;
	@Wire
	private Image imWebIcon;
	@Wire
	private Image imWebInst;
	@Wire
	private Image imMobIcon;

	private Integer billerServiceId;

	@Override
	protected void init() throws Exception {
		String serviceID = getParameter("serviceId");
		if (!AppUtil.isEmpty(serviceID)) {
			billerServiceId = Integer.parseInt(serviceID);
		} else {
			billerServiceId = -1;
		}
		if (billerServiceId != -1) {
			loadFormData(billerServiceId);
			if (bw1416Form.getBillerServiceImage() != null && bw1416Form.getBillerServiceImage().getBllrSrvcId() != null) {
				oid = bw1416Form.getBillerServiceImage().getBllrSrvcId();
			} else {
				oid = -1;
			}
		}
		setupPage();
		if (oid == -1) {
			setupNewPage();
		} else {
			setupEditPage();
		}
	}

	private void setupPage() {
		try {
			setPageTitle();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setPageTitle() {
		Page parent = null;
		Label title = null;
		try {
			parent = this.getSelf().getDesktop().getPageIfAny("pBw1410");
			log.info("Parent Page:" + parent);
			if (parent == null) {
				return;
			}
			title = (Label) parent.getFellow("title");
			if (oid == -1) {
				title.setValue(AppMessage.getMessage(AppMessage.BW1416_NEW_TITLE));
				enableActivate(false);
				chkInsertPermission(btSave);
				chkInsertPermission(btPublish);
				chkInsertPermission(btWebIconUpload);
				chkInsertPermission(btMobIconUpload);
				chkInsertPermission(btWebInstUpload);
			} else {
				enableActivate(true);
				if (chkUpdatePermission(updatePage.getFellows(), new Listbox[0], new Button[0], btSave, btPublish, btWebIconUpload, btMobIconUpload, btWebInstUpload)) {
					title.setValue(AppMessage.getMessage(AppMessage.BW1416_EDIT_TITLE));
				} else {
					title.setValue(AppMessage.getMessage(AppMessage.BW1416_VIEW_TITLE));
				}
			}
			bw1416Title.setValue(title.getValue());
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			parent = null;
			title = null;
		}
	}

	private void setupNewPage() {
		try {
			enableActivate(false);
			chkInsertPermission(btSave);
			chkInsertPermission(btPublish);
			chkInsertPermission(btWebIconUpload);
			chkInsertPermission(btMobIconUpload);
			chkInsertPermission(btWebInstUpload);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setupEditPage() {
		try {
			enableActivate(true);
			chkUpdatePermission(updatePage.getFellows(), new Listbox[0], new Button[0], btSave, btPublish, btWebIconUpload, btMobIconUpload, btWebInstUpload);
			// refreshForm();
			// loadFormData(oid);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void loadFormData(Integer oid) {
		log.info(getOperationLogMessage(pageName, "Load Data", ""));
		BillerServiceService billerServiceService = null;
		BillerServiceImage billerServiceImage = null;
		BillerService billerService = null;
		try {
			billerServiceService = BillerwebServiceFactory.getBillerServiceService();
			billerService = billerServiceService.getBillerServiceInfoByID(oid, getUserInfo());
			billerServiceImage = billerServiceService.getBillerServiceImage(oid, getUserInfo());
			billerServiceImage.setBllrSrvcCode(billerService.getBLLR_SRVC_CODE());
			bw1416Form.setBillerServiceImage(billerServiceImage);
			if (bw1416Form.getBillerServiceImage().getBllrSrvcWebIcon() != null) {
				AImage aImage = new AImage("webIcon", billerServiceImage.getBllrSrvcWebIcon());
				imWebIcon.setContent(aImage);
			}
			if (bw1416Form.getBillerServiceImage().getBllrSrvcMobIcon() != null) {
				AImage aImage = new AImage("mobIcon", billerServiceImage.getBllrSrvcMobIcon());
				imMobIcon.setContent(aImage);
			}
			if (bw1416Form.getBillerServiceImage().getBllrSrvcWebInst() != null) {
				AImage aImage = new AImage("instructionIcon", billerServiceImage.getBllrSrvcWebInst());
				imWebInst.setContent(aImage);
			}
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	public void updateBW1416Form() {
		try {
			BillerServiceImage billerServiceImage = bw1416Form.getBillerServiceImage();
			billerServiceImage.setBllrSrvcId(billerServiceId);
			if (imWebIcon.getContent() != null) {
				billerServiceImage.setBllrSrvcWebIcon(imWebIcon.getContent().getByteData());
			}
			if (imMobIcon.getContent() != null) {
				log.debug("update Mob Icon:");
				billerServiceImage.setBllrSrvcMobIcon(imMobIcon.getContent().getByteData());
			}
			if (imWebInst.getContent() != null) {
				billerServiceImage.setBllrSrvcWebInst(imWebInst.getContent().getByteData());
			}
			bw1416Form.setBillerServiceImage(billerServiceImage);
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
			log.info(getOperationLogMessage(pageName, "Save Biller Service/Image", ""));
			updateBW1416Form();
			// validatePaymentMinAllowed();
			// validatePaymentMaxAllowed();
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
			log.error(getErrorLogMessage(pageName, "Save Biller Service/Image", getErrorParames(), ex));
			showErrorMessage(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	@Listen("onClick = #btPublish")
	public void clickPublish() {
		try {
			log.info(getOperationLogMessage(pageName, "Publish Biller Service/Image", ""));
			publish();
		} catch (BillerWebSessionTimeOutException bwto) {
			throw new BillerWebSessionTimeOutException(bwto);
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Publish Biller Service/Image", getErrorParames(), ex));
			showErrorMessage(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private void save() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Save " + "Image");
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerServiceService billerServiceService = null;
					BillerServiceImage billerServiceImage = null;
					try {
						billerServiceService = BillerwebServiceFactory.getBillerServiceService();
						billerServiceImage = bw1416Form.getBillerServiceImage();
						billerServiceImage.setCreateBy(getUserName());
						billerServiceImage.setLastChangeBy(getUserName());
						
						oid = billerServiceService.saveBillerServiceImage(billerServiceImage, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("Save OK");
									setPageTitle();
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Save Biller Service/Image", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BLLR_SRVC_ID")) {
							Validation.isExist(bw1416Title, true, AppUtil.toString(oid));
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Biller Service/image", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Save", requestDate, ex);
					} finally {
						billerServiceService = null;
					}

				}

			}
		};
		showSaveConfirmMessage(saveConfirmListener);
		setTxLogMessage(pageCode + "#Save", requestDate, "");
	}

	private void update() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Update " + "Image");
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerServiceService billerServiceService = null;
					BillerServiceImage billerServiceImage = null;
					try {
						billerServiceService = BillerwebServiceFactory.getBillerServiceService();
						billerServiceImage = bw1416Form.getBillerServiceImage();
						billerServiceImage.setLastChangeBy(getUserName());
						billerServiceService.updateBillerServiceImage(billerServiceImage, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("update OK");
									enableActivate(true);
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Update Service/Image", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BLLR_SRVC_ID")) {
							Validation.isExist(bw1416Title, true, AppUtil.toString(oid));
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Update Service/Image", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Update", requestDate, ex);
					} finally {
						billerServiceService = null;
					}

				}

			}
		};
		showSaveConfirmMessage(saveConfirmListener);
		setTxLogMessage(pageCode + "#Update", requestDate, "");
	}

	private void publish() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Publich " + "Image");
		EventListener publishConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					try {
						log.info(getOperationLogMessage(pageName, "Publish Biller Service/Image", ""));
						String[] urls = AppConfiguration.getValue(AppConfiguration.WEBAGENT_PUBLISH_IMAGE_URL).split("\\|");
						for (String url : urls) {
							log.info("Call publish URL:"+url);
							String response = HttpClient.getResponseString(url);
							if (!StringUtils.trim(response).equals("1")) {
								log.error("Publish Fail,URL:" + url + ",Response:" + response);
								throw new BillerWebException("Publish Fail");
							}
						}
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.info("Publish OK");

								}
							}
						};
						showInfomationMessage("Publish Success", okListener);

					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Publish Biller Service/image", getErrorParames(), ex));
						showErrorMessage(ex);
						setTxErrLogMessage(pageCode + "#Publish", requestDate, ex);
					} finally {

					}
				}

			}
		};
		showPublishConfirmMessage(publishConfirmListener);
		setTxLogMessage(pageCode + "#Save", requestDate, "");
	}

	private Map<String, String> getErrorParames() {
		Map<String, String> params = new HashMap<String, String>();
		try {
			params.put("Biller Service ID", bw1416Form.getBillerServiceImage().getBllrSrvcId() + "");
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
		return params;
	}

	/* ## Event ## */
	@Listen("onUpload = #btWebIconUpload")
	public void onUploadWebIcon(UploadEvent event) throws WrongValueException {
		try {
			log.info(getOperationLogMessage(pageName, "Upload Web Icon", ""));
			Media media = event.getMedia();
			validateWebIcon(media);
			org.zkoss.image.Image image = (org.zkoss.image.Image) media;
			imWebIcon.setContent(image);
			enableActivate(false);
		} catch (BillerWebSessionTimeOutException bwto) {
			throw new BillerWebSessionTimeOutException(bwto);
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Upload Web Icon", getErrorParames(), ex));
			showErrorMessage(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	@Listen("onUpload = #btMobIconUpload")
	public void onUploadMobIcon(UploadEvent event) throws WrongValueException {
		try {
			log.info(getOperationLogMessage(pageName, "Upload Mob Icon", ""));
			Media media = event.getMedia();
			validateMobIcon(media);
			org.zkoss.image.Image image = (org.zkoss.image.Image) media;
			imMobIcon.setContent(image);
			enableActivate(false);
		} catch (BillerWebSessionTimeOutException bwto) {
			throw new BillerWebSessionTimeOutException(bwto);
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Upload Mob Icon", getErrorParames(), ex));
			showErrorMessage(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	@Listen("onUpload = #btWebInstUpload")
	public void onUploadWebInst(UploadEvent event) throws WrongValueException {
		try {
			log.info(getOperationLogMessage(pageName, "Upload Instruction", ""));
			Media media = event.getMedia();
			validateWebInstruction(media);
			org.zkoss.image.Image image = (org.zkoss.image.Image) media;
			imWebInst.setContent(image);
			enableActivate(false);
		} catch (BillerWebSessionTimeOutException bwto) {
			throw new BillerWebSessionTimeOutException(bwto);
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Upload Instruction", getErrorParames(), ex));
			showErrorMessage(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	@Listen("onClick = #btBack")
	public void back() {
		log.info(getOperationLogMessage(pageName, "Back", ""));
		Page parent = null;
		Tab tabBarcode = null;
		try {
			parent = this.getSelf().getDesktop().getPageIfAny("pBw1410");
			log.info("Parent Page:" + parent);
			if (parent == null) {
				return;
			}
			tabBarcode = (Tab) parent.getFellow("tabBarcode");
			Event event = new Event("onSelect");
			Events.sendEvent(tabBarcode, event);

		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Back", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			tabBarcode = null;
		}
	}

	private void validateWebIcon(Media media) throws WrongValueException, Exception {
		String format = media.getFormat();
		String type = media.getName().substring(media.getName().lastIndexOf(".") + 1);
		int maxFileSize = Integer.parseInt(AppConfiguration.getValue(AppConfiguration.MAX_ICON_FILE_SIZE));
		if ((media.getByteData().length / 1024) > maxFileSize) {
			throw new WrongValueException(imWebIcon, AppMessage.getMessage(AppMessage.MSG_INVALID_FILE_SIZE, new String[] { maxFileSize + "" }));
		}
		if (!(media instanceof org.zkoss.image.Image) || !type.equalsIgnoreCase("gif")) {
			throw new WrongValueException(imWebIcon, AppMessage.getMessage(AppMessage.MSG_INVALID_FILE_FORMAT));
		}
	}

	private void validateWebInstruction(Media media) throws WrongValueException, Exception {
		String format = media.getFormat();
		String type = media.getName().substring(media.getName().lastIndexOf(".") + 1);
		int maxFileSize = Integer.parseInt(AppConfiguration.getValue(AppConfiguration.MAX_ICON_FILE_SIZE));
		if ((media.getByteData().length / 1024) > maxFileSize) {
			throw new WrongValueException(imWebInst, AppMessage.getMessage(AppMessage.MSG_INVALID_FILE_SIZE, new String[] { maxFileSize + "" }));
		}
		log.debug("type:" + type);
		if (!(media instanceof org.zkoss.image.Image) || (!type.equalsIgnoreCase("jpeg") && !type.equalsIgnoreCase("jpg"))) {
			throw new WrongValueException(imWebInst, AppMessage.getMessage(AppMessage.MSG_INVALID_FILE_FORMAT));
		}
	}

	private void validateMobIcon(Media media) throws WrongValueException, Exception {
		String format = media.getFormat();
		String type = media.getName().substring(media.getName().lastIndexOf(".") + 1);
		int maxFileSize = Integer.parseInt(AppConfiguration.getValue(AppConfiguration.MAX_ICON_FILE_SIZE));
		if ((media.getByteData().length / 1024) > maxFileSize) {
			throw new WrongValueException(imMobIcon, AppMessage.getMessage(AppMessage.MSG_INVALID_FILE_SIZE, new String[] { maxFileSize + "" }));
		}
		if (!(media instanceof org.zkoss.image.Image) || !type.equalsIgnoreCase("png")) {
			throw new WrongValueException(imMobIcon, AppMessage.getMessage(AppMessage.MSG_INVALID_FILE_FORMAT));
		}
	}

	private void enableActivate(boolean isActive) {
		btSave.setDisabled(isActive);
		btPublish.setDisabled(!isActive);
	}

	public BW1416Form getBw1416Form() {
		return bw1416Form;
	}

	public void setBw1416Form(BW1416Form bw1416Form) {
		this.bw1416Form = bw1416Form;
	}

	public Integer getBillerServiceId() {
		return billerServiceId;
	}

	public void setBillerServiceId(Integer billerServiceId) {
		this.billerServiceId = billerServiceId;
	}

}
