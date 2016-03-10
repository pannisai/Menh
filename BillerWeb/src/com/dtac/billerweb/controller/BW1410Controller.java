package com.dtac.billerweb.controller;

import mfs.biller.persistence.bean.BillerForm;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Tab;

import com.dtac.billerweb.common.AppConstant;
import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.common.EditPageController;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.service.BillerServiceService;

public class BW1410Controller extends EditPageController {
	public BW1410Controller() {
		super("Create Biller Service", "1410");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW1410Controller.class);

	@Wire
	private Label title;
	@Wire
	private Tab tabPaymentInfo;
	@Wire
	private Tab tabPaymentValidate;
	@Wire
	private Tab tabPaymentChannel;
	@Wire
	private Tab tabInputForm;
	@Wire
	private Tab tabBarcode;
	@Wire
	private Tab tabImage;

	@Wire
	private Include incPaymentValidate;
	@Wire
	private Include incPaymentChannel;
	@Wire
	private Include incInputForm;
	@Wire
	private Include incBarcode;
	@Wire
	private Include incImage;

	private Integer billerFormId;
	private Integer serviceId;

	@Override
	protected void init() throws Exception {
		// TODO Auto-generated method stub
		// loadBillerForm();

	}

	private Integer getServiceId() {
		try {
			Object serviceIdObj = getSession(AppConstant.S_SERVICE_ID);
			Integer serviceId = serviceIdObj == null ? -1 : (Integer) serviceIdObj;
			return serviceId;
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void loadBillerForm(Integer serviceId) {
		log.info(getOperationLogMessage(pageName, "loadBillerForm", ""));
		BillerServiceService billerServiceService = null;
		BillerForm billerForm = null;
		try {
			billerServiceService = BillerwebServiceFactory.getBillerServiceService();
			billerForm = billerServiceService.getBillerFormByServiceID(serviceId, getUserInfo());
			if (billerForm.getBLLR_FORM_ID() != null) {
				billerFormId = billerForm.getBLLR_FORM_ID();
			} else {
				billerFormId = -1;
			}

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			billerServiceService = null;
		}
	}

	@Listen("onSelect=#tabPaymentInfo")
	public void onSelectTabPaymentInfo() {
		log.info(getOperationLogMessage(pageName, "Select onSelectTabPaymentInfo", ""));
		try {
			tabPaymentInfo.setSelected(true);
			Page bw1411Page = this.getSelf().getDesktop().getPageIfAny("pBw1411");
			if (bw1411Page != null) {
				Label bw1411Title = (Label) bw1411Page.getFellow("bw1411Title");
				title.setValue(bw1411Title.getValue());
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Select onSelectTabPaymentInfo", ex));
		}
	}

	@Listen("onSelect=#tabPaymentValidate")
	public void onSelectTabPaymentValidate(Event event) {
		log.info(getOperationLogMessage(pageName, "Select PaymentValidate", ""));

		try {
			serviceId = getServiceId();
			if (serviceId == -1) {
				showInfomationMessage(AppMessage.getMessage(AppMessage.MSG_SAVE_BILLER_SERVICE_BEFORE));
				tabPaymentInfo.setSelected(true);
			} else {
				tabPaymentValidate.setSelected(true);
				incPaymentValidate.setSrc("bw1412.zul?serviceId=" + serviceId);
				Page bw1412Page = this.getSelf().getDesktop().getPageIfAny("pBw1412");
				if (bw1412Page != null) {
					Label bw1412Title = (Label) bw1412Page.getFellow("bw1412Title");
					title.setValue(bw1412Title.getValue());
				}
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Select PaymentValidate", ex));
		}
	}

	@Listen("onSelect=#tabPaymentChannel")
	public void onSelectTabPaymentChannel() {
		log.info(getOperationLogMessage(pageName, "Select PaymentChannel", ""));
		try {
			serviceId = getServiceId();
			if (serviceId == -1) {
				showInfomationMessage(AppMessage.getMessage(AppMessage.MSG_SAVE_BILLER_SERVICE_BEFORE));
				tabPaymentInfo.setSelected(true);
			} else {
				tabPaymentChannel.setSelected(true);
				incPaymentChannel.setSrc("bw1413.zul?serviceId=" + serviceId);
				Page bw1413Page = this.getSelf().getDesktop().getPageIfAny("pBw1413");
				if (bw1413Page != null) {
					Label bw1413Title = (Label) bw1413Page.getFellow("bw1413Title");
					title.setValue(bw1413Title.getValue());
				}
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Select PaymentChannel", ex));
		}
	}

	@Listen("onSelect=#tabInputForm")
	public void onSelectTabInputForm() {
		log.info(getOperationLogMessage(pageName, "Select Input Form", ""));
		try {
			serviceId = getServiceId();
			if (serviceId == -1) {
				showInfomationMessage(AppMessage.getMessage(AppMessage.MSG_SAVE_BILLER_SERVICE_BEFORE));
				tabPaymentInfo.setSelected(true);
			} else {
				tabInputForm.setSelected(true);
				incInputForm.setSrc("bw1414.zul?serviceId=" + serviceId);
				Page bw1414Page = this.getSelf().getDesktop().getPageIfAny("pBw1414");
				if (bw1414Page != null) {
					Label bw1414Title = (Label) bw1414Page.getFellow("bw1414Title");
					title.setValue(bw1414Title.getValue());
				}
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Select Input Form", ex));
		}
	}

	@Listen("onSelect=#tabBarcode")
	public void onSelectTabBarcode() {
		log.info(getOperationLogMessage(pageName, "Select Barcode", ""));
		try {
			serviceId = getServiceId();
			loadBillerForm(serviceId);
			if (serviceId == -1) {
				showInfomationMessage(AppMessage.getMessage(AppMessage.MSG_SAVE_BILLER_SERVICE_BEFORE));
				tabPaymentInfo.setSelected(true);
			} else if (billerFormId == -1) {
				showInfomationMessage(AppMessage.getMessage(AppMessage.MSG_CREATE_INPUT_FORM_BEFORE));
				tabInputForm.setSelected(true);
			} else {
				tabBarcode.setSelected(true);
				incBarcode.setSrc("bw1415.zul?serviceId=" + serviceId + "&formId=" + billerFormId);
				Page bw1415Page = this.getSelf().getDesktop().getPageIfAny("pBw1415");
				log.debug("Page 1415::" + bw1415Page);
				if (bw1415Page != null) {
					Label bw1415Title = (Label) bw1415Page.getFellow("bw1415Title");
					title.setValue(bw1415Title.getValue());
					Listbox lbInputId = (Listbox) bw1415Page.getFellow("lbInputId");
					Event onRefreshInputId = new Event("onRefreshInputId", lbInputId);
					Events.sendEvent(onRefreshInputId);
				}
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Select Input Form", ex));
		}
	}
	
	@Listen("onSelect=#tabImage")
	public void onSelectTabImage() {
		log.info(getOperationLogMessage(pageName, "Select Image", ""));
		try {
			serviceId = getServiceId();
			if (serviceId == -1) {
				showInfomationMessage(AppMessage.getMessage(AppMessage.MSG_SAVE_BILLER_SERVICE_BEFORE));
				tabPaymentInfo.setSelected(true);
			} else {
				tabImage.setSelected(true);
				incImage.setSrc("bw1416.zul?serviceId=" + serviceId);
				Page bw1416Page = this.getSelf().getDesktop().getPageIfAny("pBw1416");
				if (bw1416Page != null) {
					Label bw1416Title = (Label) bw1416Page.getFellow("bw1416Title");
					title.setValue(bw1416Title.getValue());
				}
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Select Image", ex));
		}
	}

}
