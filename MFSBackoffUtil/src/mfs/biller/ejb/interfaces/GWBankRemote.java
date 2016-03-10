package mfs.biller.ejb.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mfs.biller.persistence.bean.GWBankBean;
import mfs.biller.persistence.bean.GWBankDetail;
import mfs.biller.persistence.bean.UserInfoBean;

@Remote
public interface GWBankRemote {
	//public static final String JNDI_WEBLOGIC = "mfs.biller.ejb.GWBank#mfs.biller.ejb.interfaces.GWBankRemote";
	 public GWBankBean findGWBank(int BANK_SRVC_ID, UserInfoBean user)throws Exception;
	 public int insertGWBank(GWBankBean bean, UserInfoBean user)throws  Exception;
	 public List<GWBankDetail> searchGWBankAll(GWBankDetail PARAM, UserInfoBean user)throws Exception;
	 public void updateGWBank(GWBankBean bean, UserInfoBean user)throws  Exception;
}
