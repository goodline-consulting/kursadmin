package kursadmin.domain;

import java.io.Serializable;
import java.util.Date;

public class Person implements Serializable 
{	
	private static final long serialVersionUID = 2940077208797077472L;
	private int    pid;
	private String enamn;
	private String fnamn;
	private String adress;
	private String postadress;
	private String postnr;
	private String email;	
	private String mobil;
	private String telefon;
	private String info;
	private Date   inskriven;
	private double saldo;
	private String forminput1; // för temporär användning
	private String forminput2; // för temporär användning
	
	public String getForminput1() {
		return forminput1;
	}
	public void setForminput1(String forminput) {
		this.forminput1 = forminput;
	}
	public String getForminput2() {
		return forminput2;
	}
	public void setForminput2(String forminput) {
		this.forminput2 = forminput;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public int getPid() 
	{
		return pid;
	}
	public void setPid(int pid) 
	{
		this.pid = pid;
	}
	public String getEnamn() 
	{
		return enamn;
	}
	public void setEnamn(String enamn) 
	{
		this.enamn = enamn;
	}
	public String getFnamn() 
	{
		return fnamn;
	}
	public void setFnamn(String fnamn) 
	{
		this.fnamn = fnamn;
	}
	public String getHeltNamn()
	{
		return fnamn + " " + enamn;
	}
	public String getAdress() 
	{
		return adress;
	}
	public void setAdress(String adress) 
	{
		this.adress = adress;
	}
	public String getPostadress() 
	{
		return postadress;
	}
	public void setPostadress(String adress) 
	{
		this.postadress = adress;
	}
	public String getPostnr() 
	{
		return postnr;
	}
	public void setPostnr(String postnr) 
	{
		this.postnr = postnr;
	}
	public String getEmail() 
	{
		return email;
	}
	public void setEmail(String email) 
	{
		this.email = email;
	}
	public String getMobil() 
	{
		return mobil;
	}
	public void setMobil(String mobil) 
	{
		this.mobil = mobil;
	}
	public String getTelefon() 
	{
		return telefon;
	}
	public void setTelefon(String telefon) 
	{
		this.telefon = telefon;
	}
	public String getInfo() 
	{
		return info;
	}
	public void setInfo(String info) 
	{
		this.info = info;
	}
	public Date getInskriven() {
		return inskriven;
	}
	public void setInskriven(Date inskriven) {
		this.inskriven = inskriven;
	}
	
	


}
