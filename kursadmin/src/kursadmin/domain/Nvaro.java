package kursadmin.domain;
import java.io.Serializable;
import java.sql.Date;

public class Nvaro implements Serializable 
{		
	private static final long serialVersionUID = -27610922505825812L;
	private int kid;
	private int seq;
	private int pid;
	
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
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
		
}