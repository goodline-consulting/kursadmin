package kursadmin.domain;

import java.io.Serializable;

public class Config implements Serializable 
{
	private static final long serialVersionUID = 1295448343081380317L;
	private String kategori;
	private String namn;
	private String varde;
	
	public Config(String kategori, String namn, String varde)
	{
		this.kategori = kategori;
		this.namn     = namn;
		this.varde    = varde;
	}
	public Config()
	{
	}
	public String getKategori() {
		return kategori;
	}
	public void setKategori(String kategori) {
		this.kategori = kategori;
	}
	public String getNamn() {
		return namn;
	}
	public void setNamn(String namn) {
		this.namn = namn;
	}
	public String getVarde() {
		return varde;
	}
	public void setVarde(String varde) {
		this.varde = varde;
	}
	
}
