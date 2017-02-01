package kursadmin.domain;

import java.io.Serializable;

public class MailSetup implements Serializable 
{		
	private static final long serialVersionUID = -7791859863107647474L;
	private String footer;
	private String sender;
	private String fakturaMail;
	private String anmalanMail;
	
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
	
	
	public String getFooter() {
		return footer;
	}
	public void setFooter(String footer) {
		this.footer = footer;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}					
}
