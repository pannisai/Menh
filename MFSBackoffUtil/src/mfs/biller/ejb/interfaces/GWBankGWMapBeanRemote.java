package mfs.biller.ejb.interfaces;

import javax.ejb.Remote;

import mfs.biller.persistence.bean.ObjMapGWxml;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;

@Remote
public interface GWBankGWMapBeanRemote {
	//public static final String JNDI_WEBLOGIC = "mfs.biller.ejb.GWBankGWMapBean#mfs.biller.ejb.interfaces.GWBankGWMapBeanRemote";
	  public ObjMapGWxml findGWBankGWMap(int DATA_MAP_ID,  UserInfoBean user)
				throws Exception ;
		public int insertGWBankGWMap(ObjMapGWxml bean, UserInfoBean user)throws IsExistException, Exception;
		public void updateGWBankGWMap(ObjMapGWxml bean, UserInfoBean user)throws IsExistException, Exception;
	
}
