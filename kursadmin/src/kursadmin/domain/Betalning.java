package kursadmin.domain;

import java.util.Date;

public class Betalning 
{
	private int id;
	private String refnr;
	private int fakturanr;
	private double amount;
	private double placerat;
	private String name;
	private String betalkanal;
	private Date   betaldatum;
	private StringBuffer info;
	private boolean placerad;
	private int parentid;
	private int pid;
	
	
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getParentid() {
		return parentid;
	}
	public void setParentid(int parentid) {
		this.parentid = parentid;
	}
	public boolean isPlacerad() {
		return placerad;
	}
	public void setPlacerad(boolean placerad) {
		this.placerad = placerad;
	}
	public double getPlacerat() {
		return placerat;
	}
	public void setPlacerat(double placerat) {
		this.placerat = placerat;
	}
	public int getFakturanr() {
		return fakturanr;
	}
	public void setFakturanr(int fakturanr) {
		this.fakturanr = fakturanr;
	}
	
	
	public Betalning()
	{
		info = new StringBuffer();
		fakturanr = 0;		
	}	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getRefnr() {
		return refnr;
	}
	public void setRefnr(String refnr) {
		this.refnr = refnr;
		if (refnr != null && refnr.length() > 0)
			fakturanr =  Integer.parseInt(refnr.substring(0, refnr.length() -1));
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBetalkanal() {
		return betalkanal;
	}
	public void setBetalkanal(String betalkanal) {
		this.betalkanal = betalkanal;
	}
	public Date getBetaldatum() {
		return betaldatum;
	}
	public void setBetaldatum(Date betDat) {
		this.betaldatum = betDat;
	}
	public StringBuffer getInfo() {
		return info;
	}
	public void setInfo(StringBuffer info) {
		this.info = info;
	}
	
}