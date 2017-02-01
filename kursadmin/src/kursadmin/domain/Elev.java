package kursadmin.domain;

import java.io.Serializable;
import java.util.Date;

import kursadmin.utils.KontrollSiffra;

public class Elev implements Serializable 
{
	private static final long serialVersionUID = 6725413586982603577L;
	private int kid;
	private int pid;
	private int betalt;
	private int pris;
	private boolean aktiv;
	private boolean abekr;
	private boolean bbekr;
	private String info;
	private boolean nvaro[];	
	private int fakturanr;
	private boolean manpris;	
	private Date anmald;
	private Date betaldatum;
	private int logi;
	private int mat;
	private int resa;
	private String rum;
	// dessa fält lafgra inte databasen
	private String namn; // Joinas ifrån person
	private String email; // Joinas ifrån person
	private String beteckning; // joinas ifrån kurs
	private boolean ny = false;
	private boolean andrad = false;
	private boolean enabled = false;
	
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public int getLogi() {
		return logi;
	}
	public void setLogi(int logi) {
		this.logi = logi;
	}
	public int getMat() {
		return mat;
	}
	public void setMat(int mat) {
		this.mat = mat;
	}
	public int getResa() {
		return resa;
	}
	public void setResa(int resa) {
		this.resa = resa;
	}
	public String getRum() {
		return rum;
	}
	public void setRum(String rum) {
		this.rum = rum;
	}
	public boolean isNy() {
		return ny;
	}
	public void setNy(boolean ny) {
		this.ny = ny;
	}
	public boolean isAndrad() {
		return andrad;
	}
	public void setAndrad(boolean andrad) {
		this.andrad = andrad;
	}
	public String getBeteckning() {
		return beteckning;
	}
	public void setBeteckning(String beteckning) {
		this.beteckning = beteckning;
	}
	public boolean isManpris() {
		return manpris;
	}
	public void setManpris(boolean manpris) {
		this.manpris = manpris;
	}
	public String getOcr()
	{
		if (fakturanr == 0)
			return "";
		String tmpRef = "" + fakturanr;
		return tmpRef + KontrollSiffra.calc(tmpRef);
	}
	public int getFakturanr() {
		return fakturanr;
	}
	public void setFakturanr(int fakturanr) {
		this.fakturanr = fakturanr;
	}
	/*
	public int getReferens() {
		return referens;
	}
	public void setReferens(int referens) {
		this.referens = referens;
	}
	*/
	public Date getAnmald() {
		return anmald;
	}
	public void setAnmald(Date anmald) {
		this.anmald = anmald;
	}
	public Date getBetaldatum() {
		return betaldatum;
	}
	public void setBetaldatum(Date betaldatum) {
		this.betaldatum = betaldatum;
	}			
	public boolean[] getNvaro() {
		return nvaro;
	}
	public void setNvaro(boolean[] nvaro) {
		this.nvaro = nvaro;
	}
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
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getBetalt() {
		return betalt;
	}
	public void setBetalt(int betalt) {
		this.betalt = betalt;
	}
	public boolean isAktiv() {
		return aktiv;
	}
	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
	}
	public boolean isAbekr() {
		return abekr;
	}
	public void setAbekr(boolean abekr) {
		this.abekr = abekr;
	}
	public boolean isBbekr() {
		return bbekr;
	}
	public void setBbekr(boolean bbekr) {
		this.bbekr = bbekr;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getPris() {
		return pris;
	}
	public void setPris(int pris) {
		this.pris = pris;
	}
	/*
	public String getRefnr()
	{
		String tmpRef = "" + referens;
		return tmpRef + KontrollSiffra.calc(tmpRef);
	}
	*/
}
