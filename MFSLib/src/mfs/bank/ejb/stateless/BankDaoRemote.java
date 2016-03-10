package mfs.bank.ejb.stateless;

import java.util.Date;

import javax.ejb.Remote;

import mfs.ejb.bean.BankPrtnSrvcCodeBean;
import mfs.ejb.bean.BankTransBean;
import mfs.exception.NotFoundDataException;

import org.apache.commons.collections.map.MultiKeyMap;

@Remote
public interface BankDaoRemote {
	
	public static final String JNDI_WEBLOGIC = "mfs.bank.ejb.stateless.BankDaoBean#mfs.bank.ejb.stateless.BankDaoRemote";
	
	public void insertMastTrns(BankTransBean bankTransBean)throws Exception;
	public BankTransBean findMastTrns(String TRNS_ID, Date TRNS_DTTM)throws Exception;
	public void updateMastTrns(BankTransBean bankTransBean)throws Exception;
	public MultiKeyMap getBankGatewayData() throws Exception;
	public BankPrtnSrvcCodeBean getBankPrtnSrvcCode(BankPrtnSrvcCodeBean input) throws NotFoundDataException, Exception;
	public long getSequenceTranId(String seqId); 
}
