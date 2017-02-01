package kursadmin.domain;

import java.io.Serializable;

/**
 * @author levinr
 *
 */
public class GrundConfig implements Serializable 
{
	
	private String fakturaMail;
	private String anmalanMail;
	private String betalMail;
	private boolean fakeMail;
	private String anmActionUrl;
	private String logga;
	private int loggaH;
	private int loggaW;
	private String style;
	private static GrundConfig grundConfig;
	public static synchronized GrundConfig getGrundConfig() {
		if (grundConfig == null) {
			grundConfig = new GrundConfig();
		}
		return grundConfig;
	}
	private GrundConfig() {
		//	 Optional Code
	}

	public String getFakturaMail() {
		return fakturaMail;
	}
	public void setFakturaMail(String fakturaMail) {
		this.fakturaMail = fakturaMail;
	}
	public String getAnmalanMail() {
		return anmalanMail;
	}
	public void setAnmalanMail(String anmalanMail) {
		this.anmalanMail = anmalanMail;
	}
	public String getBetalMail() {
		return betalMail;
	}
	public void setBetalMail(String betalMail) {
		this.betalMail = betalMail;
	}
	public boolean isFakeMail() {
		return fakeMail;
	}
	public void setFakeMail(boolean fakeMail) {
		this.fakeMail = fakeMail;
	}
	public String getAnmActionUrl() {
		return anmActionUrl;
	}
	public void setAnmActionUrl(String anmActionUrl) {
		this.anmActionUrl = anmActionUrl;
	}
	public String getLogga() {
		return logga;
	}
	public void setLogga(String logga) {
		this.logga = logga;
	}
	public int getLoggaH() {
		return loggaH;
	}
	public void setLoggaH(int loggaH) {
		this.loggaH = loggaH;
	}
	public int getLoggaW() {
		return loggaW;
	}
	public void setLoggaW(int loggaW) {
		this.loggaW = loggaW;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
		
}
