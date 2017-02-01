package kursadmin.domain;

import java.io.Serializable;

/**
 * @author levinr
 *
 */
public class TackConfig implements Serializable 
{
	
	private String header;
	private String body;
	private String footer;
	private String actionUrl;
	private String logga;
	private int loggaH;
	private int loggaW;
	private String style;
	
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
		
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getFooter() {
		return footer;
	}
	public void setFooter(String footer) {
		this.footer = footer;
	}
	public String getActionUrl() {
		return actionUrl;
	}
	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}
	public String getLogga() {
		return logga;
	}
	public void setLogga(String logga) {
		this.logga = logga;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
		
}
