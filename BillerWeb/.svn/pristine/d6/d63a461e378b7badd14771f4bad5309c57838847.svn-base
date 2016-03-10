/**
 * 
 */
package com.dtac.billerweb.serviceImpl;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.zkoss.zul.ListModelList;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.listmodel.selectbox.TransactionType;
import com.paysbuy.mfs.channelmanagementwebservice.ChannelManagementWebService;
import com.paysbuy.mfs.channelmanagementwebservice.ChannelManagementWebService_Service;
import com.terrafive.ktb.webservice.TransactionInquiryRequestDTO;
import com.terrafive.ktb.webservice.TransactionInquiryResponseDTO;

/**
 * This class is Transaction Inquiry service wrapper
 * 
 * @author Shiv
 *
 */
public class ChannelManagementWS {
	// Create the log object
	private static Logger log = Logger.getLogger(ChannelManagementWS.class);
	
	// The Channel Management Web Service client
	private static ChannelManagementWebService channelManagementWebService = null;

	public ChannelManagementWS() {
		super();
	}
	
	protected ChannelManagementWebService getChannelManagementWebService()
            throws Exception {
        if (channelManagementWebService == null) {
            channelManagementWebService = ChannelManagementWS.initChannelManagementWSConn();
        }
        return channelManagementWebService;
    }

    private static ChannelManagementWebService initChannelManagementWSConn()
            throws Exception {
        ChannelManagementWebService port = null;
        try {
            log.info("Creating ChannelManagementWebService client.");

            ChannelManagementWebService_Service service = new ChannelManagementWebService_Service(
                    new URL(AppConfiguration.getValue(AppConfiguration.CHANNEL_MANAGEMENT_WEB_SERVICE_URL)),
                    new QName(
                            "http://channelmanagementwebservice.mfs.paysbuy.com",
                            "ChannelManagementWebService"));
            port = service.getChannelManagementWebServicePort();

            if (port == null) {
                /*
                 * throw new
                 * MFSServiceException(Constants.CODE_ERROR_CONN_FAILED, null);
                 */
            }
        } catch (Exception e) {
            throw e;
        }
        log.info("Creating ChannelManagementWebService client....COMPLETED!!!");
        return port;
    }

	public List<TransactionInquiryResponseDTO> getOTCTransactions(Date fromDate, Date toDate, String transactionTypeValue, String fundamoTransRef, String bankTransRef) {
		if (fundamoTransRef != null && !fundamoTransRef.isEmpty()) {
			fundamoTransRef = fundamoTransRef.trim();
		} else {
			fundamoTransRef = "";
		}
		
		if (bankTransRef != null && !bankTransRef.isEmpty()) {
			bankTransRef = bankTransRef.trim();
		} else {
			bankTransRef = "";
		}
		
		TransactionInquiryRequestDTO request = new TransactionInquiryRequestDTO();
		request.setFundamoTransactionId(fundamoTransRef);
		request.setBankTransactionId(bankTransRef);
		request.setTransactionType(transactionTypeValue);
		
		if (fromDate != null) {
			fromDate.setHours(0);
			fromDate.setMinutes(0);
			fromDate.setSeconds(0);
			request.setFromDate(this.getDateString(fromDate));
		}
		
		if (toDate != null) {
			toDate.setHours(23);
			toDate.setMinutes(59);
			toDate.setSeconds(59);
			request.setToDate(this.getDateString(toDate));
		}
		
		log.info("In getOTCTransactions method");
		log.info("Search criteria: TransactionType: " + transactionTypeValue 
				+ ", FromDate: " + request.getFromDate() 
				+ ", ToDate: " + request.getToDate() 
				+ ", FundamoTransactionId:" + fundamoTransRef 
				+ ", BankTransactionId: " + bankTransRef);
		
		List<TransactionInquiryResponseDTO> responseList = this.getOTCTransactions(request);
		
		log.info("Search Result size: "+responseList.size());
		return responseList;
	}
	
	private String getDateString(Date date) {
		String dateString = null;
		if(date!=null){
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
			dateString = formatter.format(date);
		}
		return dateString;
	}

	public List<TransactionInquiryResponseDTO> getOTCTransactions(TransactionInquiryRequestDTO request){
		long startTime = System.nanoTime();
		List<TransactionInquiryResponseDTO> response = new ArrayList<TransactionInquiryResponseDTO>();
		try {
			ChannelManagementWebService service = this.getChannelManagementWebService();
			response = service.getOTCTransactions(request);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("Time for getOTCTransactions is " + (System.nanoTime() - startTime)/1000000  + " milliseconds");
		return response;
	}
	
	public boolean resendReleaseCode(String fundamoTransactionRef){
		long startTime = System.nanoTime();
		boolean isSuccess = false;
		try {
			ChannelManagementWebService service = this.getChannelManagementWebService();
			isSuccess = service.resendReleaseCode(fundamoTransactionRef);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("Time for resendReleaseCode is " + (System.nanoTime() - startTime)/1000000  + " milliseconds");
		return isSuccess;
	}
	
	public ListModelList getTransactionTypeList(){
		List<TransactionType> list = new ArrayList<TransactionType>();
		
		TransactionType transactionType = new TransactionType();
		transactionType.setDisplayLabel("Money Transfer Send (OTC to OTC)");
		transactionType.setValue("MTSendOTCToOTC");
		list.add(transactionType);
		
		transactionType = new TransactionType();
		transactionType.setDisplayLabel("Money Transfer Cash Out (OTC)");
		transactionType.setValue("MTCashOut");
		list.add(transactionType);
		
		transactionType = new TransactionType();
		transactionType.setDisplayLabel("Transfer money to KTB account");
		transactionType.setValue("OTC_KTB_ISSUE_FUND");
		list.add(transactionType);
		
		transactionType = new TransactionType();
		transactionType.setDisplayLabel("Transfer money to KTB Counter/ATM");
		transactionType.setValue("OTC_KTB_CARDLESS");
		list.add(transactionType);
		
		transactionType = new TransactionType();
		transactionType.setDisplayLabel("Receive money from KTB (Bank to OTC)");
		transactionType.setValue("KTB_OTC_REDEEM");
		list.add(transactionType);
		
		transactionType = new TransactionType();
		transactionType.setDisplayLabel("Reversal");
		transactionType.setValue("REVERSAL");
		list.add(transactionType);
		ListModelList lml = new ListModelList(list,true);
		return lml;
	}
}