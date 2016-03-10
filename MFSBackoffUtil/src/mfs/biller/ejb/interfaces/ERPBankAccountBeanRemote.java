package mfs.biller.ejb.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mfs.biller.persistence.bean.ERPBankAccount;
import mfs.biller.persistence.bean.ERPBankAccountDtail;
import mfs.biller.persistence.bean.UserInfoBean;

@Remote
public interface ERPBankAccountBeanRemote {
	//public static final String JNDI_WEBLOGIC = "mfs.biller.ejb.ERPBankAccountBean#mfs.biller.ejb.interfaces.ERPBankAccountBeanRemote";
	public ERPBankAccount findERPBankAccount(Integer BLLR_SRVC_FIN_ID, UserInfoBean user) throws Exception ;
	public int insertERPBankAccount(ERPBankAccount bean, UserInfoBean user) throws Exception ;
	public List<ERPBankAccountDtail> searchERPBankAccount(Integer BLLR_SRVC_ID,String BLLR_ACCT_BANK_CODE,String ACT_FLAG, UserInfoBean user) throws Exception ;
	public void updateERPBankAccount(ERPBankAccount bean, UserInfoBean user) throws Exception ;

}
