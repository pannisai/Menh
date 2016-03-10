package mfs.biller.persistence.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheType;

@Entity
@IdClass(value = BillerServiceChannelPK.class)
@Table(name="BILLER_SERVICE_CHANNEL")
@Cache(type=CacheType.NONE)
public class BillerServiceChannel implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	private Integer BLLR_SRVC_ID;
	
	@Id
	private Integer BLLR_CHNL_ID;
	
	@Column(name="BLLR_SRVC_CHNL_BARC_FLAG", nullable=true)
	private String BLLR_SRVC_CHNL_BARC_FLAG;
	
	@Column(name="BLLR_SRVC_CHNL_KEYN_FLAG", nullable=true)
	private String BLLR_SRVC_CHNL_KEYN_FLAG;
	
	@Column(name="BLLR_SRVC_CHNL_CODE", nullable=true)
	private String BLLR_SRVC_CHNL_CODE;
	
	@Column(name="ACT_FLAG", nullable=true)
	private String ACT_FLAG;
	
	@Column(name="CRTD_BY", nullable=true)
    private String CRTD_BY;
	
	@Column(name="CRTD_DTTM", nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
    private Date CRTD_DTTM;
	
	@Column(name="LAST_CHNG_BY", nullable=true)
    private String LAST_CHNG_BY;
	
	@Column(name="LAST_CHNG_DTTM", nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
    private Date LAST_CHNG_DTTM;
	
	public String toString() {
		return "BLLR_SRVC_ID:" + BLLR_SRVC_ID 
				+ "|BLLR_CHNL_ID:" + BLLR_CHNL_ID 
				+ "|BLLR_SRVC_CHNL_BARC_FLAG:" + BLLR_SRVC_CHNL_BARC_FLAG 
				+ "|BLLR_SRVC_CHNL_KEYN_FLAG:" + BLLR_SRVC_CHNL_KEYN_FLAG 
				+ "|BLLR_SRVC_CHNL_CODE:" + BLLR_SRVC_CHNL_CODE 
				+ "|ACT_FLAG:" + ACT_FLAG 
				+ "|CRTD_BY:" + CRTD_BY 
				+ "|CRTD_DTTM:" + CRTD_DTTM 
				+ "|LAST_CHNG_BY:" + LAST_CHNG_BY 
				+ "|LAST_CHNG_DTTM:" + LAST_CHNG_DTTM;
	}

	public Integer getBLLR_SRVC_ID() {
		return BLLR_SRVC_ID;
	}

	public void setBLLR_SRVC_ID(Integer bLLR_SRVC_ID) {
		BLLR_SRVC_ID = bLLR_SRVC_ID;
	}

	public Integer getBLLR_CHNL_ID() {
		return BLLR_CHNL_ID;
	}

	public void setBLLR_CHNL_ID(Integer bLLR_CHNL_ID) {
		BLLR_CHNL_ID = bLLR_CHNL_ID;
	}

	public String getBLLR_SRVC_CHNL_BARC_FLAG() {
		return BLLR_SRVC_CHNL_BARC_FLAG;
	}

	public void setBLLR_SRVC_CHNL_BARC_FLAG(String bLLR_SRVC_CHNL_BARC_FLAG) {
		BLLR_SRVC_CHNL_BARC_FLAG = bLLR_SRVC_CHNL_BARC_FLAG;
	}

	public String getBLLR_SRVC_CHNL_KEYN_FLAG() {
		return BLLR_SRVC_CHNL_KEYN_FLAG;
	}

	public void setBLLR_SRVC_CHNL_KEYN_FLAG(String bLLR_SRVC_CHNL_KEYN_FLAG) {
		BLLR_SRVC_CHNL_KEYN_FLAG = bLLR_SRVC_CHNL_KEYN_FLAG;
	}

	public String getBLLR_SRVC_CHNL_CODE() {
		return BLLR_SRVC_CHNL_CODE;
	}

	public void setBLLR_SRVC_CHNL_CODE(String bLLR_SRVC_CHNL_CODE) {
		BLLR_SRVC_CHNL_CODE = bLLR_SRVC_CHNL_CODE;
	}

	public String getACT_FLAG() {
		return ACT_FLAG;
	}

	public void setACT_FLAG(String aCT_FLAG) {
		ACT_FLAG = aCT_FLAG;
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
	
}
