package kursadmin.domain;

import java.io.Serializable;

public class KursNiva implements Serializable 
{	
	private static final long serialVersionUID = 2448446451903993269L;
	private int tid;
	private int nid;
	private String namn;
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public int getNid() {
		return nid;
	}
	public void setNid(int nid) {
		this.nid = nid;
	}
	public String getNamn() {
		return namn;
	}
	public void setNamn(String namn) {
		this.namn = namn;
	}
	
	
	

}
	
	
	