package mfs.biller.ejb.stateless;

import java.util.Date;
import java.util.HashMap;

import javax.ejb.Remote;

import mfs.bean.ReceiptConfBean;
import mfs.ejb.bean.GWTransBean;

import org.apache.commons.collections.map.MultiKeyMap;

@Remote
public interface GWBillerDaoRemote {
	public static final String JNDI_WEBLOGIC = "biller.ejb.session.GWDbManagerBean#mfs.biller.ejb.stateless.GWBillerDaoRemote";

	public MultiKeyMap getGatewayDataAll() throws Exception;

	public long getSequenceTranId(String billerId) throws Exception;

	public GWTransBean findMasterTrans(String tranId, Date tranDttm,
			String tranDestCode) throws Exception;

	public void insertMasterTrans(GWTransBean inTrans) throws Exception;

	public void updateMasterTrans(GWTransBean inTrans) throws Exception;
	
	public void updateMasterTransPayment(GWTransBean inTrans) throws Exception;

	public HashMap<Integer, ReceiptConfBean> getReceiptConfAll() throws Exception;
}
