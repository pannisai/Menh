package mfs.ejb.bean;

import java.io.Serializable;

public class BankSysMainBean implements Serializable{
	
	private int BANK_SYSM_MATN_ID;
	private int BANK_STAR_TIME;
	private int BANK_END_TIME;
	
	public int getBANK_SYSM_MATN_ID() {
		return BANK_SYSM_MATN_ID;
	}
	public void setBANK_SYSM_MATN_ID(int bANK_SYSM_MATN_ID) {
		BANK_SYSM_MATN_ID = bANK_SYSM_MATN_ID;
	}
	public int getBANK_STAR_TIME() {
		return BANK_STAR_TIME;
	}
	public void setBANK_STAR_TIME(int bANK_STAR_TIME) {
		BANK_STAR_TIME = bANK_STAR_TIME;
	}
	public int getBANK_END_TIME() {
		return BANK_END_TIME;
	}
	public void setBANK_END_TIME(int bANK_END_TIME) {
		BANK_END_TIME = bANK_END_TIME;
	}
	
}
