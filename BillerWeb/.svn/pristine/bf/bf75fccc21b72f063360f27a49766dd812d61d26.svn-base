package com.dtac.billerweb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mfs.biller.persistence.bean.BillerCategory;
import mfs.biller.persistence.bean.BillerServiceParam;
import mfs.biller.persistence.bean.DropdownlistBillMSRT;
import mfs.biller.persistence.bean.DropdownlistBillservice;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.SimpleListModel;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.SearchPageController;
import com.dtac.billerweb.data.BillerServiceSO;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.form.BW1400Form;
import com.dtac.billerweb.listmodel.selectbox.BillerCatalogListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerCodeListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerServiceListModel;
import com.dtac.billerweb.service.BillerServiceService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;

public class BW1400Controller extends SearchPageController {
	private Logger log = Logger.getLogger(BW1400Controller.class);

	public BW1400Controller() {
		super("Biller Service","1400");
		// TODO Auto-generated constructor stub
	}

	private static String SESS_BILLER_SERVICE_FROM = "SESS_BILLER_SERVICE_FROM";
	@Wire
	private Listbox lbBillerCategory;
	@Wire
	private Listbox lbBillerCode;
	@Wire
	private Listbox lbBillerServiceCode;
	@Wire
	private Button btSearch;
	@Wire
	private Button btCreate;

	@Override
	protected void init(Component com) throws Exception {
		setupPage();
	}

	@SuppressWarnings("rawtypes")
	private void setupPage() {
		log.info("Set up Page");
		SelectBoxService selectBoxService = null;
		BillerCatalogListModel billerCatalogListModel = null;

		try {
			/* ## Check Permission## */
			chkSearchPermission(btSearch);
			chkInsertPermission(btCreate);

			gdResult.setPaginal(paging);
			/* set selectbox list */
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			billerCatalogListModel = selectBoxService.getBillerCatalogListModel();
			billerCatalogListModel.addItemAll();
			lbBillerCategory.setModel(billerCatalogListModel);

			this.setBillerCodeSelectBox(new BillerCategory());
			this.setBillerServiceSelectBox(new BillerCategory(), new DropdownlistBillMSRT());

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			billerCatalogListModel = null;
		}
	}

	@Override
	protected void setupPageFromSession() {
//		try {
//			BW1400Form bw1400Form = (BW1400Form) getSession(SESS_BILLER_SERVICE_FROM);
//
//			if (bw1400Form != null && bw1400Form.getBillerCategory() != null && bw1400Form.getBillerCode() != null && bw1400Form.getBillerServiceCode() != null) {
//				try {
//					log.debug("Jose:"+bw1400Form.getBillerCategory().getIndex());
//					log.debug("JoseIndex:"+lbBillerCategory.getIndexOfItem(bw1400Form.getBillerCategory()));
//					lbBillerCategory.setSelectedIndex(lbBillerCategory.getIndexOfItem(bw1400Form.getBillerCategory()));
//					this.setBillerCodeSelectBox(new BillerCategory());					
//					lbBillerCode.setSelectedIndex(lbBillerCode.getIndexOfItem(bw1400Form.getBillerCode()));
//					this.setBillerServiceSelectBox(new BillerCategory(), new DropdownlistBillMSRT());
//					lbBillerServiceCode.setSelectedIndex(lbBillerServiceCode.getIndexOfItem(bw1400Form.getBillerServiceCode()));
//				} catch (ArrayIndexOutOfBoundsException aiobe) {
//
//				}
//				if (getAuthorization().isSearch()) {
//					search();
//				}
//				removeSession(SESS_BILLER_SERVICE_FROM);
//			}
//		} catch (BillerWebSessionTimeOutException stex) {
//			throw stex;
//		} catch (Exception ex) {
//			throw new BillerWebException(ex);
//		} finally {
//
//		}
	}

	private void setCurrentFormSession() {
		log.info(getOperationLogMessage(pageName, "setCurrentFormSession", ""));
		try {
			BW1400Form bw1400Form = new BW1400Form();
			bw1400Form.setBillerCategory(lbBillerCategory.getSelectedItem());
			bw1400Form.setBillerCode(lbBillerCode.getSelectedItem());
			bw1400Form.setBillerServiceCode(lbBillerServiceCode.getSelectedItem());
			setSession(SESS_BILLER_SERVICE_FROM, bw1400Form);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "setCurrentFormSession", ex));
			showErrorMessage(ex);
		} finally {

		}
	}

	/* ## Event ## */
	@Listen("onClick = #btSearch")
	public void clickSearch() {
		setCurrentFormSession();
		search();
	}

	private void search() {
		Date requestDate = AppUtil.getCurrent();
		log.info(getOperationLogMessage(pageName, "Search", ""));
		checkSessionTimeOut("Search " + pageName);
		BillerServiceService billerServiceService = null;
		List<BillerServiceSO> billerServiceSOs = null;
		ListModel<BillerServiceSO> billerServiceListModel = null;
		BillerCategory billerCategory = null;
		DropdownlistBillMSRT dropdownlistBillMSRT = null;
		DropdownlistBillservice dropdownlistBillservice = null;
		try {
			billerServiceService = BillerwebServiceFactory.getBillerServiceService();
			BillerServiceParam criteria = new BillerServiceParam();

			try {
				billerCategory = (BillerCategory) lbBillerCategory.getSelectedItem().getValue();
				criteria.setBLLR_CATG_ID(billerCategory.getBLLR_CATG_ID());
				dropdownlistBillMSRT = (DropdownlistBillMSRT) lbBillerCode.getSelectedItem().getValue();
				criteria.setBLLR_MSTR_ID(dropdownlistBillMSRT.getBLLR_MSTR_ID());
				dropdownlistBillservice = (DropdownlistBillservice) lbBillerServiceCode.getSelectedItem().getValue();
				criteria.setBLLR_SRVC_CODE(dropdownlistBillservice.getBLLR_SRVC_CODE());
			} catch (NullPointerException npe) {

			}
			billerServiceSOs = billerServiceService.searchBillerService(criteria, 0, 0, getUserInfo());
			if(billerServiceSOs != null && billerServiceSOs.size() > 0){
				billerServiceSOs = this.setBillerServiceSOs(billerServiceSOs);
			}			
			billerServiceListModel = new SimpleListModel<BillerServiceSO>(billerServiceSOs);

			setGridResultModel(billerServiceListModel);	
		} catch (Exception ex) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Biller Category ID", AppUtil.toString(billerCategory.getBLLR_CATG_ID()));
			params.put("Biller Code ID", AppUtil.toString(dropdownlistBillMSRT.getBLLR_MSTR_ID()));
			params.put("Biller Service Code", AppUtil.toString(dropdownlistBillservice.getBLLR_SRVC_ID()));
			log.error(getErrorLogMessage(pageName, "Search", params, ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Search", requestDate, ex);
		} finally {
			billerServiceListModel = null;
			billerServiceSOs = null;
			billerServiceListModel = null;
			billerCategory = null;
			dropdownlistBillMSRT = null;
			dropdownlistBillservice = null;
		}
		log.info(getStopLogMessage(pageName));
		setTxLogMessage(pageCode + "#Search", requestDate, "");
	}

	@Listen("onClick = #btCreate")
	public void create() {
		Date requestDate = AppUtil.getCurrent();
		log.info(getOperationLogMessage(pageName, "Create", ""));
		checkSessionTimeOut("Create " + pageName);
		try {
			redirect(AppConfiguration.getValue(AppConfiguration.BW1410_REDIRECT_PATH));
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Create", ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Create", requestDate, ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
		setTxLogMessage(pageCode + "#Create", requestDate, "");
	}

	@Listen("onEdit = #gdResult")
	public void edit(ForwardEvent event) {
		Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Edit " + pageName);
		Row row = new Row();
		try {
			row = (Row) event.getOrigin().getTarget();
			log.info(getOperationLogMessage(pageName, "Edit", "EDIT ID::" + row.getValue()));
			redirect(AppConfiguration.getValue(AppConfiguration.BW1410_REDIRECT_PATH) + "?oid=" + row.getValue());
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Edit", "EDIT ID::" + row.getValue(), ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Edit", requestDate, ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
		setTxLogMessage(pageCode + "#Edit", requestDate, "");
	}

	@SuppressWarnings("unused")
	@Listen("onSelect = #lbBillerCategory")
	public void onBillerCatalogSelect() throws Exception {
		log.info(getOperationLogMessage(pageName, "Select Biller Catalog", ""));
		SelectBoxService selectBoxService = null;
		BillerCategory billerCategory = null;
		try {
			lbBillerCode.setModel(new ListModelArray<DropdownlistBillMSRT>(0));
			lbBillerServiceCode.setModel(new ListModelArray<DropdownlistBillservice>(0));
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			billerCategory = lbBillerCategory.getSelectedItem().getValue();
			/* Set biller code */
			this.setBillerCodeSelectBox(billerCategory);
			/* Set biller Service */
			this.setBillerServiceSelectBox(billerCategory, new DropdownlistBillMSRT());
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Select Biller Catalog", ex));
			showErrorMessage(ex);
		} finally {
			selectBoxService = null;
			billerCategory = null;
		}
		log.info(getStopLogMessage(pageName));
	}

	@SuppressWarnings("unused")
	@Listen("onSelect = #lbBillerCode")
	public void onBillerCodeSelect() throws Exception {
		log.info(getOperationLogMessage(pageName, "Select Biller Code", ""));
		SelectBoxService selectBoxService = null;
		BillerCategory billerCategory = null;
		DropdownlistBillMSRT dropdownlistBillMSRT = null;
		try {
			lbBillerServiceCode.setModel(new ListModelArray<DropdownlistBillservice>(0));
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			billerCategory = lbBillerCategory.getSelectedItem().getValue();
			dropdownlistBillMSRT = lbBillerCode.getSelectedItem().getValue();
			/* Set biller Service */
			this.setBillerServiceSelectBox(billerCategory, dropdownlistBillMSRT);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Select Biller Code", ex));
			showErrorMessage(ex);
		} finally {
			selectBoxService = null;
			billerCategory = null;
			dropdownlistBillMSRT = null;
		}
		log.info(getStopLogMessage(pageName));
	}

	private void setBillerCodeSelectBox(BillerCategory billerCategory) {
		SelectBoxService selectBoxService = null;
		BillerCodeListModel billerCodeListModel = null;
		try {
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			/* Set biller Code */
			billerCodeListModel = selectBoxService.getBillerCodeListModel(billerCategory);
			billerCodeListModel.addItemAll();
			lbBillerCode.setModel(billerCodeListModel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			billerCodeListModel = null;
		}
	}

	@SuppressWarnings("rawtypes")
	private void setBillerServiceSelectBox(BillerCategory billerCategory, DropdownlistBillMSRT dropdownlistBillMSRT) {
		SelectBoxService selectBoxService = null;
		BillerServiceListModel billerServiceListModel = null;

		try {
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			/* Set biller Service */
			billerServiceListModel = selectBoxService.getBillerServiceListModel(billerCategory, dropdownlistBillMSRT);
			billerServiceListModel.addItemAll();
			lbBillerServiceCode.setModel(billerServiceListModel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			billerServiceListModel = null;
		}
	}	

	private List<BillerServiceSO> setBillerServiceSOs(List<BillerServiceSO> billerServiceSO) {
		try {
			for(BillerServiceSO src:billerServiceSO){
				if(src.getBillerFeature() != null){
					if((src.getBillerFeature()&1)==1){
						src.setOffInq(true);
					}
					if((src.getBillerFeature()&2)==2){
						src.setOffPay(true);
					}
					if((src.getBillerFeature()&4)==4){
						src.setOnInq(true);
					}
					if((src.getBillerFeature()&8)==8){
						src.setOnPay(true);
					}
					if((src.getBillerFeature()&16)==16){
						src.setAdvRec(true);
					}
				}
			}
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
		}	
		return 	billerServiceSO;		
	}
}
