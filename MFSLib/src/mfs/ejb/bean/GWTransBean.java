package mfs.ejb.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class GWTransBean implements Serializable {

	private String TRNS_ID = null;
	private String TRNS_REF_ID = null;
	private String TRNS_DEST_CODE = null;
	private String TRNS_SRCE_CODE = null;
	private String TRNS_SRCE_CHNL_CODE = null;
	private String TRNS_TYPE_CODE = null;
	private Date TRNS_DTTM = null;
	private String TRNS_SRVC_CODE = null;
	private String TRNS_FUNC_CODE = null;
	private String TRNS_COMP_CODE = null;
	private String TRNS_REF1 = null;
	private String TRNS_REF2 = null;
	private String TRNS_REF3 = null;
	private String TRNS_REF4 = null;
	private String TRNS_REF5 = null;
	private String TRNS_REF6 = null;
	private BigDecimal TRNS_PAID_AMNT = null;
	private BigDecimal TRNS_EXTR_AMNT = null;
	private BigDecimal TRNS_VAT_AMNT = null;
	private BigDecimal TRNS_MIN_AMNT = null;
	private BigDecimal TRNS_MAX_AMNT = null;
	private BigDecimal TRNS_VAT_RATE = null;
	private BigDecimal TRNS_FEE_AMNT = null;
	private String TRNS_FEE_TYPE = null;
	private BigDecimal TRNS_COMM_AMNT = null;
	private BigDecimal TRNS_TOTL_AMNT = null;
	private BigDecimal TRNS_CRNT_BLNC = null;
	private String TRNS_PAID_AMNT_TYPE = null;
	private String TRNS_USER_CODE = null;
	private String TRNS_USER_GRUP_CODE = null;
	private String TRNS_USER_BRNC_CODE = null;
	private String TRNS_USER_TMNL_CODE = null;
	private String TRNS_ZONE_CODE = null;
	private String TRNS_BRNC_CODE = null;
	private String TRNS_SUB_BRNC_CODE = null;
	private String TRNS_CUST_NAME_TH = null;
	private String TRNS_CUST_NAME_EN = null;
	private String TRNS_CUST_ADDR_1 = null;
	private String TRNS_CUST_ADDR_2 = null;
	private String TRNS_CUST_ACCT = null;
	private String TRNS_INV_NUMB = null;
	private Date TRNS_DUE_DATE = null;
	private String TRNS_INV_TAX_ID = null;
	private String TRNS_CHNL_CODE = null;
	private String TRNS_RCPT_NUMB = null;
	private Date TRNS_RCPT_DTTM = null;
	private String TRNS_STTS_CODE = null;
	private Date TRNS_PYMT_DTTM = null;
	private Date TRNS_REVS_DTTM = null;
	private String TRNS_REMK = null;
	private String CRTD_BY = null;
	private Date CRTD_DTTM = null;
	private String LAST_CHNG_BY = null;
	private Date LAST_CHNG_DTTM = null;
	private boolean TRNS_ADV_AMNT_FLAG = false;
	private boolean TRNS_PART_AMNT_FLAG = false;
	private String TRNS_BARC_CHK_DIGI = null;
	private String TRNS_INSI = null;
	private String TRNS_PROD_TYPE = null;
	private String TRNS_FUND_TYPE = null;
	private String TRNS_RCPT_FLAG = null;
	private String TRNS_DTAC_SHOP = null;
	private String TRNS_TMNL_ID = null;
	private String TRNS_FDM_PROD_NAME = null;
	private BigDecimal TRNS_NON_FDM_FEE = null;
	private String TRNS_PYMT_STTS_CODE = null;
	private String TRNS_INQY_RESP_CODE = null;
	private String TRNS_INQY_RESP_MSG = null;
	private String TRNS_PYMT_RESP_CODE = null;
	private String TRNS_PYMT_RESP_MSG = null;
	private BigDecimal TRNS_DENM_RATE = null;
	private Integer TRNS_NUMB_OF_UNIT = null;
	private Date TRNS_DUE_DATE_TO = null;
	private String TRNSTA = null;

	public BigDecimal getTRNS_EXTR_AMNT() {
		return TRNS_EXTR_AMNT;
	}

	public void setTRNS_EXTR_AMNT(BigDecimal tRNS_EXTR_AMNT) {
		TRNS_EXTR_AMNT = tRNS_EXTR_AMNT;
	}

	public String getTRNS_BARC_CHK_DIGI() {
		return TRNS_BARC_CHK_DIGI;
	}

	public void setTRNS_BARC_CHK_DIGI(String tRNS_BARC_CHK_DIGI) {
		TRNS_BARC_CHK_DIGI = tRNS_BARC_CHK_DIGI;
	}

	public boolean isTRNS_ADV_AMNT_FLAG() {
		return TRNS_ADV_AMNT_FLAG;
	}

	public void setTRNS_ADV_AMNT_FLAG(boolean tRNS_ADV_AMNT_FLAG) {
		TRNS_ADV_AMNT_FLAG = tRNS_ADV_AMNT_FLAG;
	}

	public boolean isTRNS_PART_AMNT_FLAG() {
		return TRNS_PART_AMNT_FLAG;
	}

	public void setTRNS_PART_AMNT_FLAG(boolean tRNS_PART_AMNT_FLAG) {
		TRNS_PART_AMNT_FLAG = tRNS_PART_AMNT_FLAG;
	}

	public BigDecimal getTRNS_MIN_AMNT() {
		return TRNS_MIN_AMNT;
	}

	public void setTRNS_MIN_AMNT(BigDecimal tRNS_MIN_AMNT) {
		TRNS_MIN_AMNT = tRNS_MIN_AMNT;
	}

	public BigDecimal getTRNS_MAX_AMNT() {
		return TRNS_MAX_AMNT;
	}

	public void setTRNS_MAX_AMNT(BigDecimal tRNS_MAX_AMNT) {
		TRNS_MAX_AMNT = tRNS_MAX_AMNT;
	}

	public String getTRNS_REF6() {
		return TRNS_REF6;
	}

	public void setTRNS_REF6(String tRNS_REF6) {
		TRNS_REF6 = tRNS_REF6;
	}

	public String getTRNS_ID() {
		return TRNS_ID;
	}

	public void setTRNS_ID(String tRNS_ID) {
		TRNS_ID = tRNS_ID;
	}

	public String getTRNS_REF_ID() {
		return TRNS_REF_ID;
	}

	public void setTRNS_REF_ID(String tRNS_REF_ID) {
		TRNS_REF_ID = tRNS_REF_ID;
	}

	public String getTRNS_DEST_CODE() {
		return TRNS_DEST_CODE;
	}

	public void setTRNS_DEST_CODE(String tRNS_DEST_CODE) {
		TRNS_DEST_CODE = tRNS_DEST_CODE;
	}

	public String getTRNS_SRCE_CODE() {
		return TRNS_SRCE_CODE;
	}

	public void setTRNS_SRCE_CODE(String tRNS_SRCE_CODE) {
		TRNS_SRCE_CODE = tRNS_SRCE_CODE;
	}

	public String getTRNS_SRCE_CHNL_CODE() {
		return TRNS_SRCE_CHNL_CODE;
	}

	public void setTRNS_SRCE_CHNL_CODE(String tRNS_SRCE_CHNL_CODE) {
		TRNS_SRCE_CHNL_CODE = tRNS_SRCE_CHNL_CODE;
	}

	public String getTRNS_TYPE_CODE() {
		return TRNS_TYPE_CODE;
	}

	public void setTRNS_TYPE_CODE(String tRNS_TYPE_CODE) {
		TRNS_TYPE_CODE = tRNS_TYPE_CODE;
	}

	public Date getTRNS_DTTM() {
		return TRNS_DTTM;
	}

	public void setTRNS_DTTM(Date tRNS_DTTM) {
		TRNS_DTTM = tRNS_DTTM;
	}

	public String getTRNS_SRVC_CODE() {
		return TRNS_SRVC_CODE;
	}

	public void setTRNS_SRVC_CODE(String tRNS_SRVC_CODE) {
		TRNS_SRVC_CODE = tRNS_SRVC_CODE;
	}

	public String getTRNS_FUNC_CODE() {
		return TRNS_FUNC_CODE;
	}

	public void setTRNS_FUNC_CODE(String tRNS_FUNC_CODE) {
		TRNS_FUNC_CODE = tRNS_FUNC_CODE;
	}

	public String getTRNS_COMP_CODE() {
		return TRNS_COMP_CODE;
	}

	public void setTRNS_COMP_CODE(String tRNS_COMP_CODE) {
		TRNS_COMP_CODE = tRNS_COMP_CODE;
	}

	public String getTRNS_REF1() {
		return TRNS_REF1;
	}

	public void setTRNS_REF1(String tRNS_REF1) {
		TRNS_REF1 = tRNS_REF1;
	}

	public String getTRNS_REF2() {
		return TRNS_REF2;
	}

	public void setTRNS_REF2(String tRNS_REF2) {
		TRNS_REF2 = tRNS_REF2;
	}

	public String getTRNS_REF3() {
		return TRNS_REF3;
	}

	public void setTRNS_REF3(String tRNS_REF3) {
		TRNS_REF3 = tRNS_REF3;
	}

	public String getTRNS_REF4() {
		return TRNS_REF4;
	}

	public void setTRNS_REF4(String tRNS_REF4) {
		TRNS_REF4 = tRNS_REF4;
	}

	public String getTRNS_REF5() {
		return TRNS_REF5;
	}

	public void setTRNS_REF5(String tRNS_REF5) {
		TRNS_REF5 = tRNS_REF5;
	}

	public BigDecimal getTRNS_PAID_AMNT() {
		return TRNS_PAID_AMNT;
	}

	public void setTRNS_PAID_AMNT(BigDecimal tRNS_PAID_AMNT) {
		TRNS_PAID_AMNT = tRNS_PAID_AMNT;
	}

	public BigDecimal getTRNS_VAT_AMNT() {
		return TRNS_VAT_AMNT;
	}

	public void setTRNS_VAT_AMNT(BigDecimal tRNS_VAT_AMNT) {
		TRNS_VAT_AMNT = tRNS_VAT_AMNT;
	}

	public BigDecimal getTRNS_VAT_RATE() {
		return TRNS_VAT_RATE;
	}

	public void setTRNS_VAT_RATE(BigDecimal tRNS_VAT_RATE) {
		TRNS_VAT_RATE = tRNS_VAT_RATE;
	}

	public BigDecimal getTRNS_FEE_AMNT() {
		return TRNS_FEE_AMNT;
	}

	public void setTRNS_FEE_AMNT(BigDecimal tRNS_FEE_AMNT) {
		TRNS_FEE_AMNT = tRNS_FEE_AMNT;
	}

	public String getTRNS_FEE_TYPE() {
		return TRNS_FEE_TYPE;
	}

	public void setTRNS_FEE_TYPE(String tRNS_FEE_TYPE) {
		TRNS_FEE_TYPE = tRNS_FEE_TYPE;
	}

	public BigDecimal getTRNS_COMM_AMNT() {
		return TRNS_COMM_AMNT;
	}

	public void setTRNS_COMM_AMNT(BigDecimal tRNS_COMM_AMNT) {
		TRNS_COMM_AMNT = tRNS_COMM_AMNT;
	}

	public BigDecimal getTRNS_TOTL_AMNT() {
		return TRNS_TOTL_AMNT;
	}

	public void setTRNS_TOTL_AMNT(BigDecimal tRNS_TOTL_AMNT) {
		TRNS_TOTL_AMNT = tRNS_TOTL_AMNT;
	}

	public BigDecimal getTRNS_CRNT_BLNC() {
		return TRNS_CRNT_BLNC;
	}

	public void setTRNS_CRNT_BLNC(BigDecimal tRNS_CRNT_BLNC) {
		TRNS_CRNT_BLNC = tRNS_CRNT_BLNC;
	}

	public String getTRNS_PAID_AMNT_TYPE() {
		return TRNS_PAID_AMNT_TYPE;
	}

	public void setTRNS_PAID_AMNT_TYPE(String tRNS_PAID_AMNT_TYPE) {
		TRNS_PAID_AMNT_TYPE = tRNS_PAID_AMNT_TYPE;
	}

	public String getTRNS_USER_CODE() {
		return TRNS_USER_CODE;
	}

	public void setTRNS_USER_CODE(String tRNS_USER_CODE) {
		TRNS_USER_CODE = tRNS_USER_CODE;
	}

	public String getTRNS_USER_GRUP_CODE() {
		return TRNS_USER_GRUP_CODE;
	}

	public void setTRNS_USER_GRUP_CODE(String tRNS_USER_GRUP_CODE) {
		TRNS_USER_GRUP_CODE = tRNS_USER_GRUP_CODE;
	}

	public String getTRNS_USER_BRNC_CODE() {
		return TRNS_USER_BRNC_CODE;
	}

	public void setTRNS_USER_BRNC_CODE(String tRNS_USER_BRNC_CODE) {
		TRNS_USER_BRNC_CODE = tRNS_USER_BRNC_CODE;
	}

	public String getTRNS_USER_TMNL_CODE() {
		return TRNS_USER_TMNL_CODE;
	}

	public void setTRNS_USER_TMNL_CODE(String tRNS_USER_TMNL_CODE) {
		TRNS_USER_TMNL_CODE = tRNS_USER_TMNL_CODE;
	}

	public String getTRNS_ZONE_CODE() {
		return TRNS_ZONE_CODE;
	}

	public void setTRNS_ZONE_CODE(String tRNS_ZONE_CODE) {
		TRNS_ZONE_CODE = tRNS_ZONE_CODE;
	}

	public String getTRNS_BRNC_CODE() {
		return TRNS_BRNC_CODE;
	}

	public void setTRNS_BRNC_CODE(String tRNS_BRNC_CODE) {
		TRNS_BRNC_CODE = tRNS_BRNC_CODE;
	}

	public String getTRNS_SUB_BRNC_CODE() {
		return TRNS_SUB_BRNC_CODE;
	}

	public void setTRNS_SUB_BRNC_CODE(String tRNS_SUB_BRNC_CODE) {
		TRNS_SUB_BRNC_CODE = tRNS_SUB_BRNC_CODE;
	}

	public String getTRNS_CUST_NAME_TH() {
		return TRNS_CUST_NAME_TH;
	}

	public void setTRNS_CUST_NAME_TH(String tRNS_CUST_NAME_TH) {
		TRNS_CUST_NAME_TH = tRNS_CUST_NAME_TH;
	}

	public String getTRNS_CUST_NAME_EN() {
		return TRNS_CUST_NAME_EN;
	}

	public void setTRNS_CUST_NAME_EN(String tRNS_CUST_NAME_EN) {
		TRNS_CUST_NAME_EN = tRNS_CUST_NAME_EN;
	}

	public String getTRNS_CUST_ADDR_1() {
		return TRNS_CUST_ADDR_1;
	}

	public void setTRNS_CUST_ADDR_1(String tRNS_CUST_ADDR_1) {
		TRNS_CUST_ADDR_1 = tRNS_CUST_ADDR_1;
	}

	public String getTRNS_CUST_ADDR_2() {
		return TRNS_CUST_ADDR_2;
	}

	public void setTRNS_CUST_ADDR_2(String tRNS_CUST_ADDR_2) {
		TRNS_CUST_ADDR_2 = tRNS_CUST_ADDR_2;
	}

	// public String getTRNS_CUST_ADDR_3() {
	// return TRNS_CUST_ADDR_3;
	// }

	// public void setTRNS_CUST_ADDR_3(String tRNS_CUST_ADDR_3) {
	// TRNS_CUST_ADDR_3 = tRNS_CUST_ADDR_3;
	// }

	public String getTRNS_CUST_ACCT() {
		return TRNS_CUST_ACCT;
	}

	public void setTRNS_CUST_ACCT(String tRNS_CUST_ACCT) {
		TRNS_CUST_ACCT = tRNS_CUST_ACCT;
	}

	public String getTRNS_INV_NUMB() {
		return TRNS_INV_NUMB;
	}

	public void setTRNS_INV_NUMB(String tRNS_INV_NUMB) {
		TRNS_INV_NUMB = tRNS_INV_NUMB;
	}

	public Date getTRNS_DUE_DATE() {
		return TRNS_DUE_DATE;
	}

	public void setTRNS_DUE_DATE(Date tRNS_DUE_DATE) {
		TRNS_DUE_DATE = tRNS_DUE_DATE;
	}

	public String getTRNS_INV_TAX_ID() {
		return TRNS_INV_TAX_ID;
	}

	public void setTRNS_INV_TAX_ID(String tRNS_INV_TAX_ID) {
		TRNS_INV_TAX_ID = tRNS_INV_TAX_ID;
	}

	public String getTRNS_CHNL_CODE() {
		return TRNS_CHNL_CODE;
	}

	public void setTRNS_CHNL_CODE(String tRNS_CHNL_CODE) {
		TRNS_CHNL_CODE = tRNS_CHNL_CODE;
	}

	public String getTRNS_RCPT_NUMB() {
		return TRNS_RCPT_NUMB;
	}

	public void setTRNS_RCPT_NUMB(String tRNS_RCPT_NUMB) {
		TRNS_RCPT_NUMB = tRNS_RCPT_NUMB;
	}

	public Date getTRNS_RCPT_DTTM() {
		return TRNS_RCPT_DTTM;
	}

	public void setTRNS_RCPT_DTTM(Date tRNS_RCPT_DTTM) {
		TRNS_RCPT_DTTM = tRNS_RCPT_DTTM;
	}

	public String getTRNS_STTS_CODE() {
		return TRNS_STTS_CODE;
	}

	public void setTRNS_STTS_CODE(String tRNS_STTS_CODE) {
		TRNS_STTS_CODE = tRNS_STTS_CODE;
	}

	public Date getTRNS_PYMT_DTTM() {
		return TRNS_PYMT_DTTM;
	}

	public void setTRNS_PYMT_DTTM(Date tRNS_PYMT_DTTM) {
		TRNS_PYMT_DTTM = tRNS_PYMT_DTTM;
	}

	public Date getTRNS_REVS_DTTM() {
		return TRNS_REVS_DTTM;
	}

	public void setTRNS_REVS_DTTM(Date tRNS_REVS_DTTM) {
		TRNS_REVS_DTTM = tRNS_REVS_DTTM;
	}

	public String getTRNS_REMK() {
		return TRNS_REMK;
	}

	public void setTRNS_REMK(String tRNS_REMK) {
		TRNS_REMK = tRNS_REMK;
	}

	public String getCRTD_BY() {
		return CRTD_BY;
	}

	public void setCRTD_BY(String cRTD_BY) {
		CRTD_BY = cRTD_BY;
	}

	public Date getCRTD_DTTM() {
		return CRTD_DTTM;
	}

	public void setCRTD_DTTM(Date cRTD_DTTM) {
		CRTD_DTTM = cRTD_DTTM;
	}

	public String getLAST_CHNG_BY() {
		return LAST_CHNG_BY;
	}

	public void setLAST_CHNG_BY(String lAST_CHNG_BY) {
		LAST_CHNG_BY = lAST_CHNG_BY;
	}

	public Date getLAST_CHNG_DTTM() {
		return LAST_CHNG_DTTM;
	}

	public void setLAST_CHNG_DTTM(Date lAST_CHNG_DTTM) {
		LAST_CHNG_DTTM = lAST_CHNG_DTTM;
	}

	public String getTRNS_INSI() {
		return TRNS_INSI;
	}

	public void setTRNS_INSI(String tRNS_INSI) {
		TRNS_INSI = tRNS_INSI;
	}

	public String getTRNS_PROD_TYPE() {
		return TRNS_PROD_TYPE;
	}

	public void setTRNS_PROD_TYPE(String tRNS_PROD_TYPE) {
		TRNS_PROD_TYPE = tRNS_PROD_TYPE;
	}

	public String getTRNS_FUND_TYPE() {
		return TRNS_FUND_TYPE;
	}

	public void setTRNS_FUND_TYPE(String tRNS_FUND_TYPE) {
		TRNS_FUND_TYPE = tRNS_FUND_TYPE;
	}

	public String getTRNS_RCPT_FLAG() {
		return TRNS_RCPT_FLAG;
	}

	public void setTRNS_RCPT_FLAG(String tRNS_RCPT_FLAG) {
		TRNS_RCPT_FLAG = tRNS_RCPT_FLAG;
	}

	public String getTRNS_DTAC_SHOP() {
		return TRNS_DTAC_SHOP;
	}

	public void setTRNS_DTAC_SHOP(String tRNS_DTAC_SHOP) {
		TRNS_DTAC_SHOP = tRNS_DTAC_SHOP;
	}

	public String getTRNS_TMNL_ID() {
		return TRNS_TMNL_ID;
	}

	public void setTRNS_TMNL_ID(String tRNS_TMNL_ID) {
		TRNS_TMNL_ID = tRNS_TMNL_ID;
	}

	public String getTRNS_FDM_PROD_NAME() {
		return TRNS_FDM_PROD_NAME;
	}

	public void setTRNS_FDM_PROD_NAME(String tRNS_FDM_PROD_NAME) {
		TRNS_FDM_PROD_NAME = tRNS_FDM_PROD_NAME;
	}

	public BigDecimal getTRNS_NON_FDM_FEE() {
		return TRNS_NON_FDM_FEE;
	}

	public void setTRNS_NON_FDM_FEE(BigDecimal tRNS_NON_FDM_FEE) {
		TRNS_NON_FDM_FEE = tRNS_NON_FDM_FEE;
	}

	public String getTRNS_PYMT_STTS_CODE() {
		return TRNS_PYMT_STTS_CODE;
	}

	public void setTRNS_PYMT_STTS_CODE(String tRNS_PYMT_STTS_CODE) {
		TRNS_PYMT_STTS_CODE = tRNS_PYMT_STTS_CODE;
	}
	
	public String getTRNS_INQY_RESP_CODE() {
		return TRNS_INQY_RESP_CODE;
	}

	public void setTRNS_INQY_RESP_CODE(String tRNS_INQY_RESP_CODE) {
		TRNS_INQY_RESP_CODE = tRNS_INQY_RESP_CODE;
	}

	public String getTRNS_INQY_RESP_MSG() {
		return TRNS_INQY_RESP_MSG;
	}

	public void setTRNS_INQY_RESP_MSG(String tRNS_INQY_RESP_MSG) {
		TRNS_INQY_RESP_MSG = tRNS_INQY_RESP_MSG;
	}

	public String getTRNS_PYMT_RESP_CODE() {
		return TRNS_PYMT_RESP_CODE;
	}

	public void setTRNS_PYMT_RESP_CODE(String tRNS_PYMT_RESP_CODE) {
		TRNS_PYMT_RESP_CODE = tRNS_PYMT_RESP_CODE;
	}

	public String getTRNS_PYMT_RESP_MSG() {
		return TRNS_PYMT_RESP_MSG;
	}

	public void setTRNS_PYMT_RESP_MSG(String tRNS_PYMT_RESP_MSG) {
		TRNS_PYMT_RESP_MSG = tRNS_PYMT_RESP_MSG;
	}

	public BigDecimal getTRNS_DENM_RATE() {
		return TRNS_DENM_RATE;
	}

	public void setTRNS_DENM_RATE(BigDecimal tRNS_DENM_RATE) {
		TRNS_DENM_RATE = tRNS_DENM_RATE;
	}

	public Integer getTRNS_NUMB_OF_UNIT() {
		return TRNS_NUMB_OF_UNIT;
	}

	public void setTRNS_NUMB_OF_UNIT(Integer tRNS_NUMB_OF_UNIT) {
		TRNS_NUMB_OF_UNIT = tRNS_NUMB_OF_UNIT;
	}

	public Date getTRNS_DUE_DATE_TO() {
		return TRNS_DUE_DATE_TO;
	}

	public void setTRNS_DUE_DATE_TO(Date tRNS_DUE_DATE_TO) {
		TRNS_DUE_DATE_TO = tRNS_DUE_DATE_TO;
	}

	public String getTRNSTA() {
		return TRNSTA;
	}

	public void setTRNSTA(String tRNSTA) {
		TRNSTA = tRNSTA;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GWTransBean [TRNS_ID=");
		builder.append(TRNS_ID);
		builder.append(", TRNS_REF_ID=");
		builder.append(TRNS_REF_ID);
		builder.append(", TRNS_DEST_CODE=");
		builder.append(TRNS_DEST_CODE);
		builder.append(", TRNS_SRCE_CODE=");
		builder.append(TRNS_SRCE_CODE);
		builder.append(", TRNS_SRCE_CHNL_CODE=");
		builder.append(TRNS_SRCE_CHNL_CODE);
		builder.append(", TRNS_TYPE_CODE=");
		builder.append(TRNS_TYPE_CODE);
		builder.append(", TRNS_DTTM=");
		builder.append(TRNS_DTTM);
		builder.append(", TRNS_SRVC_CODE=");
		builder.append(TRNS_SRVC_CODE);
		builder.append(", TRNS_FUNC_CODE=");
		builder.append(TRNS_FUNC_CODE);
		builder.append(", TRNS_COMP_CODE=");
		builder.append(TRNS_COMP_CODE);
		builder.append(", TRNS_REF1=");
		builder.append(TRNS_REF1);
		builder.append(", TRNS_REF2=");
		builder.append(TRNS_REF2);
		builder.append(", TRNS_REF3=");
		builder.append(TRNS_REF3);
		builder.append(", TRNS_REF4=");
		builder.append(TRNS_REF4);
		builder.append(", TRNS_REF5=");
		builder.append(TRNS_REF5);
		builder.append(", TRNS_REF6=");
		builder.append(TRNS_REF6);
		builder.append(", TRNS_PAID_AMNT=");
		builder.append(TRNS_PAID_AMNT);
		builder.append(", TRNS_EXTR_AMNT=");
		builder.append(TRNS_EXTR_AMNT);
		builder.append(", TRNS_VAT_AMNT=");
		builder.append(TRNS_VAT_AMNT);
		builder.append(", TRNS_MIN_AMNT=");
		builder.append(TRNS_MIN_AMNT);
		builder.append(", TRNS_MAX_AMNT=");
		builder.append(TRNS_MAX_AMNT);
		builder.append(", TRNS_VAT_RATE=");
		builder.append(TRNS_VAT_RATE);
		builder.append(", TRNS_FEE_AMNT=");
		builder.append(TRNS_FEE_AMNT);
		builder.append(", TRNS_FEE_TYPE=");
		builder.append(TRNS_FEE_TYPE);
		builder.append(", TRNS_COMM_AMNT=");
		builder.append(TRNS_COMM_AMNT);
		builder.append(", TRNS_TOTL_AMNT=");
		builder.append(TRNS_TOTL_AMNT);
		builder.append(", TRNS_CRNT_BLNC=");
		builder.append(TRNS_CRNT_BLNC);
		builder.append(", TRNS_PAID_AMNT_TYPE=");
		builder.append(TRNS_PAID_AMNT_TYPE);
		builder.append(", TRNS_USER_CODE=");
		builder.append(TRNS_USER_CODE);
		builder.append(", TRNS_USER_GRUP_CODE=");
		builder.append(TRNS_USER_GRUP_CODE);
		builder.append(", TRNS_USER_BRNC_CODE=");
		builder.append(TRNS_USER_BRNC_CODE);
		builder.append(", TRNS_USER_TMNL_CODE=");
		builder.append(TRNS_USER_TMNL_CODE);
		builder.append(", TRNS_ZONE_CODE=");
		builder.append(TRNS_ZONE_CODE);
		builder.append(", TRNS_BRNC_CODE=");
		builder.append(TRNS_BRNC_CODE);
		builder.append(", TRNS_SUB_BRNC_CODE=");
		builder.append(TRNS_SUB_BRNC_CODE);
		builder.append(", TRNS_CUST_NAME_TH=");
		builder.append(TRNS_CUST_NAME_TH);
		builder.append(", TRNS_CUST_NAME_EN=");
		builder.append(TRNS_CUST_NAME_EN);
		builder.append(", TRNS_CUST_ADDR_1=");
		builder.append(TRNS_CUST_ADDR_1);
		builder.append(", TRNS_CUST_ADDR_2=");
		builder.append(TRNS_CUST_ADDR_2);
		builder.append(", TRNS_CUST_ACCT=");
		builder.append(TRNS_CUST_ACCT);
		builder.append(", TRNS_INV_NUMB=");
		builder.append(TRNS_INV_NUMB);
		builder.append(", TRNS_DUE_DATE=");
		builder.append(TRNS_DUE_DATE);
		builder.append(", TRNS_INV_TAX_ID=");
		builder.append(TRNS_INV_TAX_ID);
		builder.append(", TRNS_CHNL_CODE=");
		builder.append(TRNS_CHNL_CODE);
		builder.append(", TRNS_RCPT_NUMB=");
		builder.append(TRNS_RCPT_NUMB);
		builder.append(", TRNS_RCPT_DTTM=");
		builder.append(TRNS_RCPT_DTTM);
		builder.append(", TRNS_STTS_CODE=");
		builder.append(TRNS_STTS_CODE);
		builder.append(", TRNS_PYMT_DTTM=");
		builder.append(TRNS_PYMT_DTTM);
		builder.append(", TRNS_REVS_DTTM=");
		builder.append(TRNS_REVS_DTTM);
		builder.append(", TRNS_REMK=");
		builder.append(TRNS_REMK);
		builder.append(", CRTD_BY=");
		builder.append(CRTD_BY);
		builder.append(", CRTD_DTTM=");
		builder.append(CRTD_DTTM);
		builder.append(", LAST_CHNG_BY=");
		builder.append(LAST_CHNG_BY);
		builder.append(", LAST_CHNG_DTTM=");
		builder.append(LAST_CHNG_DTTM);
		builder.append(", TRNS_ADV_AMNT_FLAG=");
		builder.append(TRNS_ADV_AMNT_FLAG);
		builder.append(", TRNS_PART_AMNT_FLAG=");
		builder.append(TRNS_PART_AMNT_FLAG);
		builder.append(", TRNS_BARC_CHK_DIGI=");
		builder.append(TRNS_BARC_CHK_DIGI);
		builder.append(", TRNS_INQY_RESP_CODE=");
		builder.append(TRNS_INQY_RESP_CODE);
		builder.append(", TRNS_INQY_RESP_MSG=");
		builder.append(TRNS_INQY_RESP_MSG);
		builder.append(", TRNS_PYMT_RESP_CODE=");
		builder.append(TRNS_PYMT_RESP_CODE);
		builder.append(", TRNS_PYMT_RESP_MSG=");
		builder.append(TRNS_PYMT_RESP_MSG);
		builder.append(", TRNS_DENM_RATE=");
		builder.append(TRNS_DENM_RATE);
		builder.append(", TRNS_NUMB_OF_UNIT=");
		builder.append(TRNS_NUMB_OF_UNIT);
		builder.append(", TRNS_DUE_DATE_TO=");
		builder.append(TRNS_DUE_DATE_TO);
		builder.append("]");
		return builder.toString();
	}

}
