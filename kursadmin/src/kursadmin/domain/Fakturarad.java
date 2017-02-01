package kursadmin.domain;





public class Fakturarad 
{	
	private int radid;
	private int fakturanr;
	private double belopp;
	private double rabatt;
	private double moms;
	private String spec = null;
	private int kid;
	
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}	
	public int getRadid() {
		return radid;
	}
	public void setRadid(int radid) {
		this.radid = radid;
	}
	public int getKid() {
		return kid;
	}
	public void setKid(int kid) {
		this.kid = kid;
	}
	private String info;
	
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
		public double getRabatt() {
		return rabatt;
	}
	public void setRabatt(double rabatt) {
		this.rabatt = rabatt;
	}
	public double getMoms() {
		return moms;
	}
	public void setMoms(double moms) {
		this.moms = moms;
	}
	public int getFakturanr() {
		return fakturanr;
	}
	public void setFakturanr(int fakturanr) {
		this.fakturanr = fakturanr;
	}
	public double getBelopp() {
		return belopp;
	}
	public void setBelopp(double belopp) {
		this.belopp = belopp;
	}
	
}
