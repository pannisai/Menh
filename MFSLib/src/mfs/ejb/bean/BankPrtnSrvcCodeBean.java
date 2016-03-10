package mfs.ejb.bean;

import java.io.Serializable;

public class BankPrtnSrvcCodeBean implements Serializable{
	
	private Integer ID;
	private String PART_CODE;
	private String BANK_CODE;
	private String TRNS_TYPE;
	private String SRVC_CODE;
	private String IN_PROD_NAME;
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append("ID=").append(ID);
		builder.append(", PART_CODE=").append(PART_CODE);
		builder.append(", BANK_CODE=").append(BANK_CODE);
		builder.append(", TRNS_TYPE=").append(TRNS_TYPE);
		builder.append(", SRVC_CODE=").append(SRVC_CODE);
		builder.append(", IN_PROD_NAME=").append(IN_PROD_NAME);
		builder.append("]");
		return builder.toString();
	}
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getPART_CODE() {
		return PART_CODE;
	}
	public void setPART_CODE(String pART_CODE) {
		PART_CODE = pART_CODE;
	}
	public String getBANK_CODE() {
		return BANK_CODE;
	}
	public void setBANK_CODE(String bANK_CODE) {
		BANK_CODE = bANK_CODE;
	}
	public String getTRNS_TYPE() {
		return TRNS_TYPE;
	}
	public void setTRNS_TYPE(String tRNS_TYPE) {
		TRNS_TYPE = tRNS_TYPE;
	}
	public String getSRVC_CODE() {
		return SRVC_CODE;
	}
	public void setSRVC_CODE(String sRVC_CODE) {
		SRVC_CODE = sRVC_CODE;
	}
	public String getIN_PROD_NAME() {
		return IN_PROD_NAME;
	}
	public void setIN_PROD_NAME(String iN_PROD_NAME) {
		IN_PROD_NAME = iN_PROD_NAME;
	}
	
}
