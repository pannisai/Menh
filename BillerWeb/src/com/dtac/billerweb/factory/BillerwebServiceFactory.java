package com.dtac.billerweb.factory;

import com.dtac.billerweb.service.BankChannelService;
import com.dtac.billerweb.service.BankGWInboundMapService;
import com.dtac.billerweb.service.BankGWOutboundMapService;
import com.dtac.billerweb.service.BankGWServiceMapService;
import com.dtac.billerweb.service.BankGatewayService;
import com.dtac.billerweb.service.BankMaintenanceService;
import com.dtac.billerweb.service.BankMasterService;
import com.dtac.billerweb.service.BankReportTranService;
import com.dtac.billerweb.service.BankServiceService;
import com.dtac.billerweb.service.BillerBarcodeService;
import com.dtac.billerweb.service.BillerCategoryService;
import com.dtac.billerweb.service.BillerChannelService;
import com.dtac.billerweb.service.BillerFeeService;
import com.dtac.billerweb.service.BillerInquiryService;
import com.dtac.billerweb.service.BillerMasterService;
import com.dtac.billerweb.service.BillerServiceService;
import com.dtac.billerweb.service.ERPBankAccountService;
import com.dtac.billerweb.service.FileDownloadInquiryService;
import com.dtac.billerweb.service.ImportExcelService;
import com.dtac.billerweb.service.InboundGatewayService;
import com.dtac.billerweb.service.InboundMapIdService;
import com.dtac.billerweb.service.OutboundGatewayService;
import com.dtac.billerweb.service.OutboundMapIdService;
import com.dtac.billerweb.service.ReceiptMapIdService;
import com.dtac.billerweb.service.ReportTransService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.service.ServiceGatewayService;
import com.dtac.billerweb.service.ServiceMapIdService;
import com.dtac.billerweb.serviceImpl.BankChannelServiceImpl;
import com.dtac.billerweb.serviceImpl.BankGWInboundMapServiceImpl;
import com.dtac.billerweb.serviceImpl.BankGWOutboundMapServiceImpl;
import com.dtac.billerweb.serviceImpl.BankGWServiceMapServiceImpl;
import com.dtac.billerweb.serviceImpl.BankGatewayServiceImpl;
import com.dtac.billerweb.serviceImpl.BankMaintenanceServiceImpl;
import com.dtac.billerweb.serviceImpl.BankMasterServiceImpl;
import com.dtac.billerweb.serviceImpl.BankReportTranServiceImpl;
import com.dtac.billerweb.serviceImpl.BankServiceServiceImpl;
import com.dtac.billerweb.serviceImpl.BillerBarcodeServiceImpl;
import com.dtac.billerweb.serviceImpl.BillerCategoryServiceImpl;
import com.dtac.billerweb.serviceImpl.BillerChannelServiceImpl;
import com.dtac.billerweb.serviceImpl.BillerFeeServiceImpl;
import com.dtac.billerweb.serviceImpl.BillerInquiryServiceImpl;
import com.dtac.billerweb.serviceImpl.BillerMasterServiceImpl;
import com.dtac.billerweb.serviceImpl.BillerServiceServiceImpl;
import com.dtac.billerweb.serviceImpl.ERPBankAccountServiceImpl;
import com.dtac.billerweb.serviceImpl.FileDownloadInquiryServiceImpl;
import com.dtac.billerweb.serviceImpl.ImportExcelServiceImpl;
import com.dtac.billerweb.serviceImpl.InboundGatewayServiceImpl;
import com.dtac.billerweb.serviceImpl.InboundMapIdServiceImpl;
import com.dtac.billerweb.serviceImpl.OutboundGatewayServiceImpl;
import com.dtac.billerweb.serviceImpl.OutboundMapIdServiceImpl;
import com.dtac.billerweb.serviceImpl.ReceiptMapIdServiceImpl;
import com.dtac.billerweb.serviceImpl.ReportTransServiceImpl;
import com.dtac.billerweb.serviceImpl.SelectBoxServiceImpl;
import com.dtac.billerweb.serviceImpl.ServiceGatewayServiceImpl;
import com.dtac.billerweb.serviceImpl.ServiceMapIdServiceImpl;

public abstract class BillerwebServiceFactory {
	public static SelectBoxService getSelectBoxService() {
		return new SelectBoxServiceImpl();
	}

	public static FileDownloadInquiryService getFileDownloadInquiryService() {
		return new FileDownloadInquiryServiceImpl();
	}

	public static BillerInquiryService getBillerInquiryService() {
		return new BillerInquiryServiceImpl();
	}

	public static InboundGatewayService getInboundGatewayService() {
		return new InboundGatewayServiceImpl();
	}

	public static OutboundGatewayService getOutboundGatewayService() {
		return new OutboundGatewayServiceImpl();
	}

	public static ServiceGatewayService getServiceGatewayService() {
		return new ServiceGatewayServiceImpl();
	}

	public static BillerCategoryService getBillerCategoryService() {
		return new BillerCategoryServiceImpl();
	}

	public static BillerMasterService getBillerMasterService() {
		return new BillerMasterServiceImpl();
	}

	public static BillerChannelService getBillerChannelService() {
		return new BillerChannelServiceImpl();
	}

	public static BillerServiceService getBillerServiceService() {
		return new BillerServiceServiceImpl();
	}

	public static InboundMapIdService getInboundMapIdService() {
		return new InboundMapIdServiceImpl();
	}

	public static ReceiptMapIdService getReceiptMapIdService() {
		return new ReceiptMapIdServiceImpl();
	}

	public static ServiceMapIdService getServiceMapIdService() {
		return new ServiceMapIdServiceImpl();
	}

	public static OutboundMapIdService getOutboundMapIdService() {
		return new OutboundMapIdServiceImpl();
	}

	public static BillerBarcodeService getBillerBarcodeService() {
		return new BillerBarcodeServiceImpl();
	}

	public static ReportTransService getReportTransService() {
		return new ReportTransServiceImpl();
	}

	public static BankMasterService getBankMasterService() {
		return new BankMasterServiceImpl();
	}

	public static BankChannelService getBankChannelService() {
		return new BankChannelServiceImpl();
	}

	public static BankServiceService getBankServiceService() {
		return new BankServiceServiceImpl();
	}

	public static BankGatewayService getBankGatewayService() {
		return new BankGatewayServiceImpl();
	}

	public static BankGWInboundMapService getBankGWInboundMapService() {
		return new BankGWInboundMapServiceImpl();
	}

	public static BankGWServiceMapService getBankGWServiceMapService() {
		return new BankGWServiceMapServiceImpl();
	}

	public static BankGWOutboundMapService getBankGWOutboundMapService() {
		return new BankGWOutboundMapServiceImpl();
	}

	public static BankMaintenanceService getBankMaintenanceService() {
		return new BankMaintenanceServiceImpl();
	}

	public static BankReportTranService getBankReportTranService() {
		return new BankReportTranServiceImpl();
	}

	public static ERPBankAccountService getERPBankAccountService() {
		return new ERPBankAccountServiceImpl();
	}
	
	public static ImportExcelService getImportExcelService() {
		return new ImportExcelServiceImpl();
	}

	public static BillerFeeService getBillerFeeService() {
		return new BillerFeeServiceImpl();
	}
}
