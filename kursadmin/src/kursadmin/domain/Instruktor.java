package kursadmin.domain;

import java.io.Serializable;

public class Instruktor implements Serializable 
{	
	private static final long serialVersionUID = 1808801597227056405L;
	private int iid;
	private String namn;	
	private String adress;
	private String postadress;
	private String postnr;
	private String email;	
	private String mobil;
	private String telefon;
	private String info;
	private String bild;
	private int    bildx;
	private int    bildy;
	
	public String getBild() {
		return bild;
	}
	public void setBild(String bild) {
		this.bild = bild;
	}
	public int getIid() 
	{
		return iid;
	}
	public void setIid(int iid) 
	{
		this.iid = iid;
	}
	public String getNamn() 
	{
		return namn;
	}
	public void setNamn(String namn) 
	{
		this.namn = namn;
	}
	public String getAdress() 
	{
		return adress;
	}
	public void setAdress(String adress) 
	{
		this.adress = adress;
	}
	public String getPostadress() {
		return postadress;
	}
	public void setPostadress(String postadress) {
		this.postadress = postadress;
	}
	public String getPostnr() {
		return postnr;
	}
	public void setPostnr(String postnr) {
		this.postnr = postnr;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobil() {
		return mobil;
	}
	public void setMobil(String mobil) {
		this.mobil = mobil;
	}
	public String getTelefon() {
		return telefon;
	}
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getBildx() {
		return bildx;
	}
	public void setBildx(int bildx) {
		this.bildx = bildx;
	}
	public int getBildy() {
		return bildy;
	}
	public void setBildy(int bildy) {
		this.bildy = bildy;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
}
