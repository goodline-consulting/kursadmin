package kursadmin.domain;

import java.io.Serializable;

public class Anvandare implements Serializable 
{		
	/**
	 * 
	 */
	private static final long serialVersionUID = 2261640307917430077L;
	private String namn;	
	private String passord;
	private String roll;
	
	
	public String getNamn() {
		return namn;
	}


	public void setNamn(String namn) {
		this.namn = namn;
	}


	public String getPassord() {
		return passord;
	}


	public void setPassord(String passord) {
		this.passord = passord;
	}


	public String getRoll() {
		return roll;
	}


	public void setRoll(String roll) {
		this.roll = roll;
	}


	public static long getSerialVersionUID() {	
		return serialVersionUID;
	}
	
}
