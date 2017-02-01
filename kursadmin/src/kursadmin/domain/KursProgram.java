package kursadmin.domain;

import java.io.Serializable;

public class KursProgram implements Serializable 
{	
	
	private static final long serialVersionUID = -5840368373790491605L;
	private int kid;
	private int seq;
	private int ppid;
	private String namn;
	
	public String getNamn() {
		return namn;
	}
	public void setNamn(String namn) {
		this.namn = namn;
	}
	public int getKid() {
		return kid;
	}
	public void setKid(int kid) {
		this.kid = kid;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getPpid() {
		return ppid;
	}
	public void setPpid(int ppid) {
		this.ppid = ppid;
	}	
			
}
