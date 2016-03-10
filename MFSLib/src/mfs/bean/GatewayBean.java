package mfs.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import mfs.config.xml.ReadXmlConfig;

public class GatewayBean implements Serializable {

	private static final long serialVersionUID = 4601850251806143333L;
	private int INBN_SRVC_ID;
	private int SRCE_SRVC_ID;
	private int DEST_SRVC_ID;
	private String INBN_SRVC_NAME;
	private String SEND_RCPT_FLAG;

	private String BLLR_TAX_NUMB;

	private int GW_RCPT_CONF_ID;

	private String INBN_SRVC_CODE;
	private String DEST_SRVC_CODE;

	private int BLLR_CATG_ID;

	// Inbound
	private int INBN_MAP_ID;
	private int INBN_XML_ID;
	private String INBN_XML_DATA;
	private ReadXmlConfig INBN_XML_CONFIG;

	// GW
	private int GW_SRVC_ID;
	private int GW_OUTB_ID;
	private int GW_MAP_ID;
	private int GW_XML_ID;
	private String GW_XML_DATA;
	private ReadXmlConfig GW_XML_CONFIG;

	// Outbound
	private int OUTB_MAP_ID;
	private int OUTB_XML_ID;
	private String OUTB_XML_DATA;
	private ReadXmlConfig OUTB_XML_CONFIG;

	// Payment Validate
	private String BLLR_PMNT_OVER_FLAG;
	private String BLLR_PMNT_FULL_FLAG;
	private String BLLR_PMNT_PART_FLAG;
	private BigDecimal BLLR_PMNT_AMNT_MIN;
	private BigDecimal BLLR_PMNT_AMNT_MAX;

	// Biller Master ID
	private Integer BLLR_MSTR_ID;
	private String BLLR_SRVC_FETR;

	public String getBLLR_SRVC_FETR() {
		return BLLR_SRVC_FETR;
	}

	public void setBLLR_SRVC_FETR(String bLLR_SRVC_FETR) {
		BLLR_SRVC_FETR = bLLR_SRVC_FETR;
	}

	public boolean isPartial() {
		return (BLLR_PMNT_PART_FLAG != null) && (BLLR_PMNT_PART_FLAG.equalsIgnoreCase("Y"));
	}

	public boolean isAdvance() {
		return (BLLR_PMNT_OVER_FLAG != null) && (BLLR_PMNT_OVER_FLAG.equalsIgnoreCase("Y"));
	}

	public int getGW_RCPT_CONF_ID() {
		return GW_RCPT_CONF_ID;
	}

	public void setGW_RCPT_CONF_ID(int gW_RCPT_CONF_ID) {
		GW_RCPT_CONF_ID = gW_RCPT_CONF_ID;
	}

	public int getINBN_SRVC_ID() {
		return INBN_SRVC_ID;
	}

	public void setINBN_SRVC_ID(int iNBN_SRVC_ID) {
		INBN_SRVC_ID = iNBN_SRVC_ID;
	}

	public int getSRCE_SRVC_ID() {
		return SRCE_SRVC_ID;
	}

	public void setSRCE_SRVC_ID(int sRCE_SRVC_ID) {
		SRCE_SRVC_ID = sRCE_SRVC_ID;
	}

	public int getDEST_SRVC_ID() {
		return DEST_SRVC_ID;
	}

	public void setDEST_SRVC_ID(int dEST_SRVC_ID) {
		DEST_SRVC_ID = dEST_SRVC_ID;
	}

	public String getINBN_SRVC_NAME() {
		return INBN_SRVC_NAME;
	}

	public void setINBN_SRVC_NAME(String iNBN_SRVC_NAME) {
		INBN_SRVC_NAME = iNBN_SRVC_NAME;
	}

	public String getSEND_RCPT_FLAG() {
		return SEND_RCPT_FLAG;
	}

	public void setSEND_RCPT_FLAG(String sEND_RCPT_FLAG) {
		SEND_RCPT_FLAG = sEND_RCPT_FLAG;
	}

	public int getINBN_MAP_ID() {
		return INBN_MAP_ID;
	}

	public void setINBN_MAP_ID(int iNBN_MAP_ID) {
		INBN_MAP_ID = iNBN_MAP_ID;
	}

	public int getINBN_XML_ID() {
		return INBN_XML_ID;
	}

	public void setINBN_XML_ID(int iNBN_XML_ID) {
		INBN_XML_ID = iNBN_XML_ID;
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

	public int getGW_SRVC_ID() {
		return GW_SRVC_ID;
	}

	public void setGW_SRVC_ID(int gW_SRVC_ID) {
		GW_SRVC_ID = gW_SRVC_ID;
	}

	public int getGW_OUTB_ID() {
		return GW_OUTB_ID;
	}

	public void setGW_OUTB_ID(int gW_OUTB_ID) {
		GW_OUTB_ID = gW_OUTB_ID;
	}

	public int getGW_MAP_ID() {
		return GW_MAP_ID;
	}

	public void setGW_MAP_ID(int gW_MAP_ID) {
		GW_MAP_ID = gW_MAP_ID;
	}

	public int getGW_XML_ID() {
		return GW_XML_ID;
	}

	public void setGW_XML_ID(int gW_XML_ID) {
		GW_XML_ID = gW_XML_ID;
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

	public int getOUTB_MAP_ID() {
		return OUTB_MAP_ID;
	}

	public void setOUTB_MAP_ID(int oUTB_MAP_ID) {
		OUTB_MAP_ID = oUTB_MAP_ID;
	}

	public int getOUTB_XML_ID() {
		return OUTB_XML_ID;
	}

	public void setOUTB_XML_ID(int oUTB_XML_ID) {
		OUTB_XML_ID = oUTB_XML_ID;
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

	public String getINBN_SRVC_CODE() {
		return INBN_SRVC_CODE;
	}

	public void setINBN_SRVC_CODE(String iNBN_SRVC_CODE) {
		INBN_SRVC_CODE = iNBN_SRVC_CODE;
	}

	public String getDEST_SRVC_CODE() {
		return DEST_SRVC_CODE;
	}

	public void setDEST_SRVC_CODE(String dEST_SRVC_CODE) {
		DEST_SRVC_CODE = dEST_SRVC_CODE;
	}

	public int getBLLR_CATG_ID() {
		return BLLR_CATG_ID;
	}

	public void setBLLR_CATG_ID(int bLLR_CATG_ID) {
		BLLR_CATG_ID = bLLR_CATG_ID;
	}

	public String getBLLR_TAX_NUMB() {
		return BLLR_TAX_NUMB;
	}

	public void setBLLR_TAX_NUMB(String bLLR_TAX_NUMB) {
		BLLR_TAX_NUMB = bLLR_TAX_NUMB;
	}

	public String getBLLR_PMNT_OVER_FLAG() {
		return BLLR_PMNT_OVER_FLAG;
	}

	public void setBLLR_PMNT_OVER_FLAG(String bLLR_PMNT_OVER_FLAG) {
		BLLR_PMNT_OVER_FLAG = bLLR_PMNT_OVER_FLAG;
	}

	public String getBLLR_PMNT_FULL_FLAG() {
		return BLLR_PMNT_FULL_FLAG;
	}

	public void setBLLR_PMNT_FULL_FLAG(String bLLR_PMNT_FULL_FLAG) {
		BLLR_PMNT_FULL_FLAG = bLLR_PMNT_FULL_FLAG;
	}

	public String getBLLR_PMNT_PART_FLAG() {
		return BLLR_PMNT_PART_FLAG;
	}

	public void setBLLR_PMNT_PART_FLAG(String bLLR_PMNT_PART_FLAG) {
		BLLR_PMNT_PART_FLAG = bLLR_PMNT_PART_FLAG;
	}

	public BigDecimal getBLLR_PMNT_AMNT_MIN() {
		return BLLR_PMNT_AMNT_MIN;
	}

	public void setBLLR_PMNT_AMNT_MIN(BigDecimal bLLR_PMNT_AMNT_MIN) {
		BLLR_PMNT_AMNT_MIN = bLLR_PMNT_AMNT_MIN;
	}

	public BigDecimal getBLLR_PMNT_AMNT_MAX() {
		return BLLR_PMNT_AMNT_MAX;
	}

	public void setBLLR_PMNT_AMNT_MAX(BigDecimal bLLR_PMNT_AMNT_MAX) {
		BLLR_PMNT_AMNT_MAX = bLLR_PMNT_AMNT_MAX;
	}

	public Integer getBLLR_MSTR_ID() {
		return BLLR_MSTR_ID;
	}

	public void setBLLR_MSTR_ID(Integer bLLR_MSTR_ID) {
		BLLR_MSTR_ID = bLLR_MSTR_ID;
	}

}
