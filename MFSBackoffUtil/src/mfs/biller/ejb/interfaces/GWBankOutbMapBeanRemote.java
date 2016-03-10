package mfs.biller.ejb.interfaces;

import javax.ejb.Remote;

import mfs.biller.persistence.bean.ObjMapGWxml;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;

@Remote
public interface GWBankOutbMapBeanRemote {
	  //public static final String JNDI_WEBLOGIC = "mfs.biller.ejb.GWBankOutbMapBean#mfs.biller.ejb.interfaces.GWBankOutbMapBeanRemote";

	  public ObjMapGWxml findGWBankOutboundMap(int DATA_MAP_ID,  UserInfoBean user)
				throws Exception ;
	  public int insertGWBankOutbMap(ObjMapGWxml bean, UserInfoBean user)throws IsExistException, Exception;
	  public void updateGWBankOutbMap(ObjMapGWxml bean, UserInfoBean user)throws IsExistException, Exception;

				
}
