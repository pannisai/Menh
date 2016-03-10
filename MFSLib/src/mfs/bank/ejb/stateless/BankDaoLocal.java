package mfs.bank.ejb.stateless;

import java.util.Date;
import javax.ejb.Local;

import org.apache.commons.collections.map.MultiKeyMap;

import mfs.ejb.bean.BankPrtnSrvcCodeBean;
import mfs.ejb.bean.BankTransBean;
import mfs.exception.NotFoundDataException;

@Local
public interface BankDaoLocal {
	public void insertMastTrns(BankTransBean bankTransBean)throws Exception;
	public BankTransBean findMastTrns(String TRNS_ID, Date TRNS_DTTM)throws Exception;
	public void updateMastTrns(BankTransBean bankTransBean)throws Exception;
	public MultiKeyMap getBankGatewayData() throws Exception;
	public BankPrtnSrvcCodeBean getBankPrtnSrvcCode(BankPrtnSrvcCodeBean input) throws NotFoundDataException, Exception;
}
