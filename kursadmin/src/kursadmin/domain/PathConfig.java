package kursadmin.domain;

import java.io.Serializable;

/**
 * @author levinr
 *
 */
public class PathConfig implements Serializable 
{
	private static final long serialVersionUID = -9133241536128187350L;
	private String bilagor;
	private String bilder;
	private String programpath;
	private String programurl;
	private String programpunkter;
	
	public String getProgrampunkter() {
		return programpunkter;
	}
	public void setProgrampunkter(String programpunkter) {
		this.programpunkter = programpunkter;
	}
	public String getBilagor() {
		return bilagor;
	}
	public void setBilagor(String bilagor) {
		this.bilagor = bilagor;
	}
	public String getBilder() {
		return bilder;
	}
	public void setBilder(String bilder) {
		this.bilder = bilder;
	}
	public String getProgrampath() {
		return programpath;
	}
	public void setProgrampath(String programpath) {
		this.programpath = programpath;
	}
	public String getProgramurl() {
		return programurl;
	}
	public void setProgramurl(String programurl) {
		this.programurl = programurl;
	}
		
}
