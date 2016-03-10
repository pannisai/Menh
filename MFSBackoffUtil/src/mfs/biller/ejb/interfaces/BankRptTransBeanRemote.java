package mfs.biller.ejb.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mfs.biller.persistence.bean.BankFdmCode;
import mfs.biller.persistence.bean.BankReportTransDetail;
import mfs.biller.persistence.bean.BankReportTransParam;
import mfs.biller.persistence.bean.UserInfoBean;

@Remote
public interface BankRptTransBeanRemote {
	
//	public static final String JNDI_WEBLOGIC = "mfs.biller.ejb.BankRptTransBean#mfs.biller.ejb.interfaces.BankRptTransBeanRemote";
	public List<BankFdmCode> getBankFdmCodeAll(UserInfoBean user)throws Exception;

	public List<BankReportTransDetail> searchBankReportTrans(BankReportTransParam PARAM, UserInfoBean user)throws Exception;
	public BankReportTransDetail findBankMasterTrans(String TRNS_ID, UserInfoBean user)throws Exception ;
	public int countRowBankReportTrans(BankReportTransParam PARAM, UserInfoBean user)throws Exception;
}
