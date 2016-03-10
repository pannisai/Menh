package mfs.ejb.bean;

import java.io.Serializable;
import java.util.List;

import mfs.config.xml.ReadXmlConfig;

public class GWBankBean implements Serializable{
	
	private int BANK_SRVC_ID;
	private String BANK_SRVC_CODE;
	private String BANK_CODE;
	private String FDM_MTI_CODE;
	private String BANK_SRVC_NAME;
	private int BANK_CUTF_TIME;
	private List<BankSysMainBean> BANK_SYSTEM_MAINTEN;
	
	
	//Inbound
	private String INBN_SRVC_NAME;
	private int INBN_XML_SRC_ID;
	private String INBN_XML_DATA;
	private ReadXmlConfig INBN_XML_CONFIG;
	
	//Gateway
	private String GW_SRVC_NAME;
	private int GW_XML_SRC_ID;
	private String GW_XML_DATA;
	private ReadXmlConfig GW_XML_CONFIG;
	
	//Outbound
	private String OUTB_SRVC_NAME;
	private int OUTB_XML_SRC_ID;
	private String OUTB_XML_DATA;
	private ReadXmlConfig OUTB_XML_CONFIG;
	public int getBANK_SRVC_ID() {
		return BANK_SRVC_ID;
	}
	public void setBANK_SRVC_ID(int bANK_SRVC_ID) {
		BANK_SRVC_ID = bANK_SRVC_ID;
	}
	public String getBANK_SRVC_CODE() {
		return BANK_SRVC_CODE;
	}
	public void setBANK_SRVC_CODE(String bANK_SRVC_CODE) {
		BANK_SRVC_CODE = bANK_SRVC_CODE;
	}
	public String getBANK_CODE() {
		return BANK_CODE;
	}
	public void setBANK_CODE(String bANK_CODE) {
		BANK_CODE = bANK_CODE;
	}
	public String getFDM_MTI_CODE() {
		return FDM_MTI_CODE;
	}
	public void setFDM_MTI_CODE(String fDM_MTI_CODE) {
		FDM_MTI_CODE = fDM_MTI_CODE;
	}
	public String getBANK_SRVC_NAME() {
		return BANK_SRVC_NAME;
	}
	public void setBANK_SRVC_NAME(String bANK_SRVC_NAME) {
		BANK_SRVC_NAME = bANK_SRVC_NAME;
	}
	public int getBANK_CUTF_TIME() {
		return BANK_CUTF_TIME;
	}
	public void setBANK_CUTF_TIME(int bANK_CUTF_TIME) {
		BANK_CUTF_TIME = bANK_CUTF_TIME;
	}
	public List<BankSysMainBean> getBANK_SYSTEM_MAINTEN() {
		return BANK_SYSTEM_MAINTEN;
	}
	public void setBANK_SYSTEM_MAINTEN(List<BankSysMainBean> bANK_SYSTEM_MAINTEN) {
		BANK_SYSTEM_MAINTEN = bANK_SYSTEM_MAINTEN;
	}
	public String getINBN_SRVC_NAME() {
		return INBN_SRVC_NAME;
	}
	public void setINBN_SRVC_NAME(String iNBN_SRVC_NAME) {
		INBN_SRVC_NAME = iNBN_SRVC_NAME;
	}
	public int getINBN_XML_SRC_ID() {
		return INBN_XML_SRC_ID;
	}
	public void setINBN_XML_SRC_ID(int iNBN_XML_SRC_ID) {
		INBN_XML_SRC_ID = iNBN_XML_SRC_ID;
	}
	public String getINBN_XML_DATA() {
		return INBN_XML_DATA;
	}
	public void setINBN_XML_DATA(String iNBN_XML_DATA) {
		INBN_XML_DATA = iNBN_XML_DATA;
	}
	public ReadXmlConfig getINBN_XML_CONFIG() {
		return INBN_XML_CONFIG;
	}
	public void setINBN_XML_CONFIG(ReadXmlConfig iNBN_XML_CONFIG) {
		INBN_XML_CONFIG = iNBN_XML_CONFIG;
	}
	public String getGW_SRVC_NAME() {
		return GW_SRVC_NAME;
	}
	public void setGW_SRVC_NAME(String gW_SRVC_NAME) {
		GW_SRVC_NAME = gW_SRVC_NAME;
	}
	public int getGW_XML_SRC_ID() {
		return GW_XML_SRC_ID;
	}
	public void setGW_XML_SRC_ID(int gW_XML_SRC_ID) {
		GW_XML_SRC_ID = gW_XML_SRC_ID;
	}
	public String getGW_XML_DATA() {
		return GW_XML_DATA;
	}
	public void setGW_XML_DATA(String gW_XML_DATA) {
		GW_XML_DATA = gW_XML_DATA;
	}
	public ReadXmlConfig getGW_XML_CONFIG() {
		return GW_XML_CONFIG;
	}
	public void setGW_XML_CONFIG(ReadXmlConfig gW_XML_CONFIG) {
		GW_XML_CONFIG = gW_XML_CONFIG;
	}
	public String getOUTB_SRVC_NAME() {
		return OUTB_SRVC_NAME;
	}
	public void setOUTB_SRVC_NAME(String oUTB_SRVC_NAME) {
		OUTB_SRVC_NAME = oUTB_SRVC_NAME;
	}
	public int getOUTB_XML_SRC_ID() {
		return OUTB_XML_SRC_ID;
	}
	public void setOUTB_XML_SRC_ID(int oUTB_XML_SRC_ID) {
		OUTB_XML_SRC_ID = oUTB_XML_SRC_ID;
	}
	public String getOUTB_XML_DATA() {
		return OUTB_XML_DATA;
	}
	public void setOUTB_XML_DATA(String oUTB_XML_DATA) {
		OUTB_XML_DATA = oUTB_XML_DATA;
	}
	public ReadXmlConfig getOUTB_XML_CONFIG() {
		return OUTB_XML_CONFIG;
	}
	public void setOUTB_XML_CONFIG(ReadXmlConfig oUTB_XML_CONFIG) {
		OUTB_XML_CONFIG = oUTB_XML_CONFIG;
	}
	
}
