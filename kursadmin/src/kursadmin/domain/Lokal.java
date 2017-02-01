package kursadmin.domain;

import java.io.Serializable;

public class Lokal implements Serializable 
{	
	private static final long serialVersionUID = -8113100418284093983L;
	private int lid;
	private String lokalnamn;	
	private String adress;
	private String postadress;
	private String postnr;
	private String email;	
	private String mobil;
	private String telefon;
	private String kontakt;
	private int    timpris;
	private String info;
	private String vagbesk;
	private String bildurl;
	
	public String getBildurl() {
		return bildurl;
	}
	public void setBildurl(String bildurl) {
		this.bildurl = bildurl;
	}
	public String getVagbesk() {
		return vagbesk;
	}
	public void setVagbesk(String vagbesk) {
		this.vagbesk = vagbesk;
	}
	public int getLid() 
	{
		return lid;
	}
	public void setLid(int lid) 
	{
		this.lid = lid;
	}
	public String getLokalnamn() 
	{
		return lokalnamn;
	}
	public void setLokalnamn(String lokalnamn) 
	{
		this.lokalnamn = lokalnamn;
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
	public void setPostadress(String postadress) 
	{
		this.postadress = postadress;
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
	public String getKontakt() 
	{
		return kontakt;
	}
	public void setKontakt(String kontakt) 
	{
		this.kontakt = kontakt;
	}
	public int getTimpris() 
	{
		return timpris;
	}
	public void setTimpris(int timpris) 
	{
		this.timpris = timpris;
	}
	public String getInfo() 
	{
		return info;
	}
	public void setInfo(String info) 
	{
		this.info = info;
	}		
}
