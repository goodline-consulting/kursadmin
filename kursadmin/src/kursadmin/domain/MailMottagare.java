package kursadmin.domain;

import java.io.Serializable;

public class MailMottagare implements Serializable 
{
	private int pid;	
	private String email;
	private String namn;
	
	public String getNamn() 
	{
		return namn;
	}
	public void setNamn(String namn) {
		this.namn = namn;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}			
}
