package mfs.ejb.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BankTransBean implements Serializable{
	
	private String TRNS_ID = null;
	private Date TRNS_DTTM = null;
	private String TRNS_BANK_REF = null;
	private String TRNS_FUNC_CODE = null;
	private String TRNS_TYPE_CODE = null;
	private String TRNS_LEG_IDCT = null;
	private String TRNS_BANK_CODE = null;
	private String TRNS_TERM_ID = null;
	private String TRNS_USER_ID = null;
	private String TRNS_MERC_TYPE = null;
	private String TRNS_CURC_CODE = null;
	private String TRNS_ACCT_FROM = null;
	private String TRNS_ACCT_TO = null;
	private BigDecimal TRNS_TNSF_AMNT = null;
	private String TRNS_ACCT_HOLD_NAME = null;
	private String TRNS_BANK_BRNC_CODE = null;
	private BigDecimal TRNS_VAT_RATE = null;
	private Integer TRNS_STTS_FLAG = null;
	private String TRNS_STTS_CODE = null;
	private String TRNS_BANK_STTS_CODE = null;
	private String TRNS_BANK_STTS_MSG = null;
	private Date TRNS_TNSF_DTTM = null;
	private String TRNS_RVSL_STTS_CODE = null;
	private Date TRNS_RVSL_DTTM = null;
	private String CRTD_BY = null;
	private Date CRTD_DTTM = null;
	private String LAST_CHNG_BY = null;
	private Date LAST_CHNG_DTTM = null;
	private String TRNS_BANK_SRVC_CODE = null;
	private Date TRNS_EFCT_DTTM = null;
	private Integer BANK_SRVC_ID = null;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BankTransBean [TRNS_ID=");
		builder.append(TRNS_ID);
		builder.append(", TRNS_DTTM=");
		builder.append(TRNS_DTTM);
		builder.append(", TRNS_BANK_REF=");
		builder.append(TRNS_BANK_REF);
		builder.append(", TRNS_FUNC_CODE=");
		builder.append(TRNS_FUNC_CODE);
		builder.append(", TRNS_TYPE_CODE=");
		builder.append(TRNS_TYPE_CODE);
		builder.append(", TRNS_LEG_IDCT=");
		builder.append(TRNS_LEG_IDCT);
		builder.append(", TRNS_BANK_CODE=");
		builder.append(TRNS_BANK_CODE);
		builder.append(", TRNS_TERM_ID=");
		builder.append(TRNS_TERM_ID);
		builder.append(", TRNS_USER_ID=");
		builder.append(TRNS_USER_ID);
		builder.append(", TRNS_MERC_TYPE=");
		builder.append(TRNS_MERC_TYPE);
		builder.append(", TRNS_CURC_CODE=");
		builder.append(TRNS_CURC_CODE);
		builder.append(", TRNS_ACCT_FROM=");
		builder.append(TRNS_ACCT_FROM);
		builder.append(", TRNS_ACCT_TO=");
		builder.append(TRNS_ACCT_TO);
		builder.append(", TRNS_TNSF_AMNT=");
		builder.append(TRNS_TNSF_AMNT);
		builder.append(", TRNS_ACCT_HOLD_NAME=");
		builder.append(TRNS_ACCT_HOLD_NAME);
		builder.append(", TRNS_BANK_BRNC_CODE=");
		builder.append(TRNS_BANK_BRNC_CODE);
		builder.append(", TRNS_VAT_RATE=");
		builder.append(TRNS_VAT_RATE);
		builder.append(", TRNS_STTS_FLAG=");
		builder.append(TRNS_STTS_FLAG);
		builder.append(", TRNS_STTS_CODE=");
		builder.append(TRNS_STTS_CODE);
		builder.append(", TRNS_BANK_STTS_CODE=");
		builder.append(TRNS_BANK_STTS_CODE);
		builder.append(", TRNS_BANK_STTS_MSG=");
		builder.append(TRNS_BANK_STTS_MSG);
		builder.append(", TRNS_TNSF_DTTM=");
		builder.append(TRNS_TNSF_DTTM);
		builder.append(", TRNS_RVSL_STTS_CODE=");
		builder.append(TRNS_RVSL_STTS_CODE);
		builder.append(", TRNS_RVSL_DTTM=");
		builder.append(TRNS_RVSL_DTTM);
		builder.append(", CRTD_BY=");
		builder.append(CRTD_BY);
		builder.append(", CRTD_DTTM=");
		builder.append(CRTD_DTTM);
		builder.append(", LAST_CHNG_BY=");
		builder.append(LAST_CHNG_BY);
		builder.append(", LAST_CHNG_DTTM=");
		builder.append(LAST_CHNG_DTTM);		
		builder.append(", TRNS_BANK_SRVC_CODE=");
		builder.append(TRNS_BANK_SRVC_CODE);		
		builder.append(", TRNS_EFCT_DTTM=");
		builder.append(TRNS_EFCT_DTTM);		
		builder.append(", BANK_SRVC_ID=");
		builder.append(BANK_SRVC_ID);
		builder.append("]");
		return builder.toString();
	}

	public String getTRNS_ID() {
		return TRNS_ID;
	}

	public void setTRNS_ID(String tRNS_ID) {
		TRNS_ID = tRNS_ID;
	}

	public Date getTRNS_DTTM() {
		return TRNS_DTTM;
	}

	public void setTRNS_DTTM(Date tRNS_DTTM) {
		TRNS_DTTM = tRNS_DTTM;
	}

	public String getTRNS_BANK_REF() {
		return TRNS_BANK_REF;
	}

	public void setTRNS_BANK_REF(String tRNS_BANK_REF) {
		TRNS_BANK_REF = tRNS_BANK_REF;
	}

	public String getTRNS_FUNC_CODE() {
		return TRNS_FUNC_CODE;
	}

	public void setTRNS_FUNC_CODE(String tRNS_FUNC_CODE) {
		TRNS_FUNC_CODE = tRNS_FUNC_CODE;
	}

	public String getTRNS_TYPE_CODE() {
		return TRNS_TYPE_CODE;
	}

	public void setTRNS_TYPE_CODE(String tRNS_TYPE_CODE) {
		TRNS_TYPE_CODE = tRNS_TYPE_CODE;
	}

	public String getTRNS_LEG_IDCT() {
		return TRNS_LEG_IDCT;
	}

	public void setTRNS_LEG_IDCT(String tRNS_LEG_IDCT) {
		TRNS_LEG_IDCT = tRNS_LEG_IDCT;
	}

	public String getTRNS_BANK_CODE() {
		return TRNS_BANK_CODE;
	}

	public void setTRNS_BANK_CODE(String tRNS_BANK_CODE) {
		TRNS_BANK_CODE = tRNS_BANK_CODE;
	}

	public String getTRNS_TERM_ID() {
		return TRNS_TERM_ID;
	}

	public void setTRNS_TERM_ID(String tRNS_TERM_ID) {
		TRNS_TERM_ID = tRNS_TERM_ID;
	}

	public String getTRNS_USER_ID() {
		return TRNS_USER_ID;
	}

	public void setTRNS_USER_ID(String tRNS_USER_ID) {
		TRNS_USER_ID = tRNS_USER_ID;
	}

	public String getTRNS_MERC_TYPE() {
		return TRNS_MERC_TYPE;
	}

	public void setTRNS_MERC_TYPE(String tRNS_MERC_TYPE) {
		TRNS_MERC_TYPE = tRNS_MERC_TYPE;
	}

	public String getTRNS_CURC_CODE() {
		return TRNS_CURC_CODE;
	}

	public void setTRNS_CURC_CODE(String tRNS_CURC_CODE) {
		TRNS_CURC_CODE = tRNS_CURC_CODE;
	}

	public String getTRNS_ACCT_FROM() {
		return TRNS_ACCT_FROM;
	}

	public void setTRNS_ACCT_FROM(String tRNS_ACCT_FROM) {
		TRNS_ACCT_FROM = tRNS_ACCT_FROM;
	}

	public String getTRNS_ACCT_TO() {
		return TRNS_ACCT_TO;
	}

	public void setTRNS_ACCT_TO(String tRNS_ACCT_TO) {
		TRNS_ACCT_TO = tRNS_ACCT_TO;
	}

	public BigDecimal getTRNS_TNSF_AMNT() {
		return TRNS_TNSF_AMNT;
	}

	public void setTRNS_TNSF_AMNT(BigDecimal tRNS_TNSF_AMNT) {
		TRNS_TNSF_AMNT = tRNS_TNSF_AMNT;
	}

	public String getTRNS_ACCT_HOLD_NAME() {
		return TRNS_ACCT_HOLD_NAME;
	}

	public void setTRNS_ACCT_HOLD_NAME(String tRNS_ACCT_HOLD_NAME) {
		TRNS_ACCT_HOLD_NAME = tRNS_ACCT_HOLD_NAME;
	}

	public String getTRNS_BANK_BRNC_CODE() {
		return TRNS_BANK_BRNC_CODE;
	}

	public void setTRNS_BANK_BRNC_CODE(String tRNS_BANK_BRNC_CODE) {
		TRNS_BANK_BRNC_CODE = tRNS_BANK_BRNC_CODE;
	}

	public BigDecimal getTRNS_VAT_RATE() {
		return TRNS_VAT_RATE;
	}

	public void setTRNS_VAT_RATE(BigDecimal tRNS_VAT_RATE) {
		TRNS_VAT_RATE = tRNS_VAT_RATE;
	}

	public Integer getTRNS_STTS_FLAG() {
		return TRNS_STTS_FLAG;
	}

	public void setTRNS_STTS_FLAG(Integer tRNS_STTS_FLAG) {
		TRNS_STTS_FLAG = tRNS_STTS_FLAG;
	}

	public String getTRNS_STTS_CODE() {
		return TRNS_STTS_CODE;
	}

	public void setTRNS_STTS_CODE(String tRNS_STTS_CODE) {
		TRNS_STTS_CODE = tRNS_STTS_CODE;
	}

	public String getTRNS_BANK_STTS_CODE() {
		return TRNS_BANK_STTS_CODE;
	}

	public void setTRNS_BANK_STTS_CODE(String tRNS_BANK_STTS_CODE) {
		TRNS_BANK_STTS_CODE = tRNS_BANK_STTS_CODE;
	}

	public String getTRNS_BANK_STTS_MSG() {
		return TRNS_BANK_STTS_MSG;
	}

	public void setTRNS_BANK_STTS_MSG(String tRNS_BANK_STTS_MSG) {
		TRNS_BANK_STTS_MSG = tRNS_BANK_STTS_MSG;
	}

	public Date getTRNS_TNSF_DTTM() {
		return TRNS_TNSF_DTTM;
	}

	public void setTRNS_TNSF_DTTM(Date tRNS_TNSF_DTTM) {
		TRNS_TNSF_DTTM = tRNS_TNSF_DTTM;
	}

	public String getTRNS_RVSL_STTS_CODE() {
		return TRNS_RVSL_STTS_CODE;
	}

	public void setTRNS_RVSL_STTS_CODE(String tRNS_RVSL_STTS_CODE) {
		TRNS_RVSL_STTS_CODE = tRNS_RVSL_STTS_CODE;
	}

	public Date getTRNS_RVSL_DTTM() {
		return TRNS_RVSL_DTTM;
	}

	public void setTRNS_RVSL_DTTM(Date tRNS_RVSL_DTTM) {
		TRNS_RVSL_DTTM = tRNS_RVSL_DTTM;
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

	public String getTRNS_BANK_SRVC_CODE() {
		return TRNS_BANK_SRVC_CODE;
	}

	public void setTRNS_BANK_SRVC_CODE(String tRNS_BANK_SRVC_CODE) {
		TRNS_BANK_SRVC_CODE = tRNS_BANK_SRVC_CODE;
	}

	public Date getTRNS_EFCT_DTTM() {
		return TRNS_EFCT_DTTM;
	}

	public void setTRNS_EFCT_DTTM(Date tRNS_EFCT_DTTM) {
		TRNS_EFCT_DTTM = tRNS_EFCT_DTTM;
	}

	public Integer getBANK_SRVC_ID() {
		return BANK_SRVC_ID;
	}

	public void setBANK_SRVC_ID(Integer bANK_SRVC_ID) {
		BANK_SRVC_ID = bANK_SRVC_ID;
	}
	
}
