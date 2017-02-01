package kursadmin.domain;

import java.io.Serializable;
import java.util.Date;

public class KursAnmalan implements Serializable 
{		

	private static final long serialVersionUID = 98307284416779207L;
	private int aid;
	private int kid;
	private int pid;
	private String beteckning;	
	private Date tidpunkt;
	private boolean handled;
	private String fnamn;
	private String enamn;
	private String telefon;
	private String email;
	private String info;
	private KursAll kurs;
	
	public KursAll getKurs() {
		return kurs;
	}
	public void setKurs(KursAll kurs) {
		this.kurs = kurs;
	}
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public int getKid() {
		return kid;
	}
	public void setKid(int kid) {
		this.kid = kid;
	}
	public boolean isHandled() {
		return handled;
	}
	public void setHandled(boolean handled) {
		this.handled = handled;
	}
	public String getFnamn() {
		return fnamn;
	}
	public void setFnamn(String fnamn) {
		this.fnamn = fnamn;
	}
	public String getEnamn() {
		return enamn;
	}
	public void setEnamn(String enamn) {
		this.enamn = enamn;
	}
	public String getTelefon() {
		return telefon;
	}
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getTidpunkt() {
		return tidpunkt;
	}
	public void setTidpunkt(Date tidpunkt) {
		this.tidpunkt = tidpunkt;
	}
	public String getBeteckning() {
		return beteckning;
	}
	public void setBeteckning(String beteckning) {
		this.beteckning = beteckning;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	
}
	
	
	