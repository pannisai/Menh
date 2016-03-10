package com.dtac.billerweb.data;

public class InboundGatewaySOPK {
	private String inbn_SRVC_ID;
	private Integer srce_SRVC_ID;
	private Integer dest_SRVC_ID;
	
	public Integer getSrce_SRVC_ID() {
		return srce_SRVC_ID;
	}
	public void setSrce_SRVC_ID(Integer srce_SRVC_ID) {
		this.srce_SRVC_ID = srce_SRVC_ID;
	}
	public Integer getDest_SRVC_ID() {
		return dest_SRVC_ID;
	}
	public void setDest_SRVC_ID(Integer dest_SRVC_ID) {
		this.dest_SRVC_ID = dest_SRVC_ID;
	}
	public String getInbn_SRVC_ID() {
		return inbn_SRVC_ID;
	}
	public void setInbn_SRVC_ID(String inbn_SRVC_ID) {
		this.inbn_SRVC_ID = inbn_SRVC_ID;
	}
	
}
