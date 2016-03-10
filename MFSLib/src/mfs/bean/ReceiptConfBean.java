package mfs.bean;

import java.io.Serializable;

import mfs.config.xml.ReadXmlConfig;

public class ReceiptConfBean implements Serializable {

	private static final long serialVersionUID = 4289517821626984472L;
	private int GW_RCPT_CONF_ID;
	private String GW_RCPT_CONF_URL;

	private ReadXmlConfig GW_RCPT_XML_CONFIG;

	public int getGW_RCPT_CONF_ID() {
		return GW_RCPT_CONF_ID;
	}

	public void setGW_RCPT_CONF_ID(int gW_RCPT_CONF_ID) {
		GW_RCPT_CONF_ID = gW_RCPT_CONF_ID;
	}

	public String getGW_RCPT_CONF_URL() {
		return GW_RCPT_CONF_URL;
	}

	public void setGW_RCPT_CONF_URL(String gW_RCPT_CONF_URL) {
		GW_RCPT_CONF_URL = gW_RCPT_CONF_URL;
	}

	public ReadXmlConfig getGW_RCPT_XML_CONFIG() {
		return GW_RCPT_XML_CONFIG;
	}

	public void setGW_RCPT_XML_CONFIG(ReadXmlConfig gW_RCPT_XML_CONFIG) {
		GW_RCPT_XML_CONFIG = gW_RCPT_XML_CONFIG;
	}

}
