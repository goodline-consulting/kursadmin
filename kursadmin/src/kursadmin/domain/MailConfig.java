package kursadmin.domain;

import java.io.Serializable;

public class MailConfig implements Serializable 
{
	
	private static final long serialVersionUID = 369739068939132107L;
	private String rubrik;
	private String footer;
	private String body;
	private String sender;
	private String ingress;
	private String finish;
	
	
	public String getIngress() {
		return ingress;
	}
	public void setIngress(String ingress) {
		this.ingress = ingress;
	}
	public String getFinish() {
		return finish;
	}
	public void setFinish(String finish) {
		this.finish = finish;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getRubrik() {
		return rubrik;
	}
	public void setRubrik(String rubrik) {
		this.rubrik = rubrik;
	}
	public String getFooter() {
		return footer;
	}
	public void setFooter(String footer) {
		this.footer = footer;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}
