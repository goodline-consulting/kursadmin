package kursadmin.domain;

import java.io.Serializable;

public class ProgramPunkt implements Serializable 
{	
	private static final long serialVersionUID = -5350949566007885800L;
	private int ppid;	
	private int tid;
	private String kurstyp;
	private String namn;
	private String header;
	private String url;
	private String url2 = "";
	private String info;
	private boolean aktuell;
	
	public int getPpid() {
		return ppid;
	}
	public void setPpid(int ppid) {
		this.ppid = ppid;
	}
	public String getNamn() {
		return namn;
	}
	public void setNamn(String namn) {
		this.namn = namn;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getKurstyp() {
		return kurstyp;
	}
	public void setKurstyp(String kurstyp) {
		this.kurstyp = kurstyp;
	}
	public String getUrl2() {
		return url2;
	}
	public void setUrl2(String url2) {
		this.url2 = url2;
	}
	public boolean isAktuell() {
		return aktuell;
	}
	public void setAktuell(boolean aktuell) {
		this.aktuell = aktuell;
	}
		
}
