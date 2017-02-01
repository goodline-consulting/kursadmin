package kursadmin.domain;

import java.io.Serializable;

public class KursTyp implements Serializable 
{	
	public final static int ingenFaktura = 0;
	public final static int textFaktura = 1;
	public final static int htmlFaktura = 2;
	public final static int pdfFaktura = 3;
	public final static int ingenrabatt = 0;
	public final static int procentrabatt = 1;
	public final static int kronrabatt = 2;
	
	private static final long serialVersionUID = -3730682361026089862L;
	private int tid;
	private String namn;
	private String info;
	private double momssats;
	private double momsbak;
	private String rabatter;
	private int typavrabatt;
	private int fakturatyp;
	private boolean rumshantering;
	private boolean resehantering;
	private boolean mathantering;
	private int oid;
	private String fakturaklass;
	
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public String getFakturaklass() {
		return fakturaklass;
	}
	public void setFakturaklass(String fakturaklass) {
		this.fakturaklass = fakturaklass;
	}		
	public boolean isRumshantering() {
		return rumshantering;
	}
	public void setRumshantering(boolean rumshantering) {
		this.rumshantering = rumshantering;
	}
	public boolean isResehantering() {
		return resehantering;
	}
	public void setResehantering(boolean resehantering) {
		this.resehantering = resehantering;
	}
	public boolean isMathantering() {
		return mathantering;
	}
	public void setMathantering(boolean mathantering) {
		this.mathantering = mathantering;
	}
	public int getTypavrabatt() {
		return typavrabatt;
	}
	public void setTypavrabatt(int typavrabatt) {
		this.typavrabatt = typavrabatt;
	}
	public int getFakturatyp() {
		return fakturatyp;
	}
	public void setFakturatyp(int fakturatyp) {
		this.fakturatyp = fakturatyp;
	}
	public double getMomssats() {
		return momssats;
	}
	public void setMomssats(double momsSats) {
		this.momssats = momsSats;
	}
	public double getMomsbak() {
		return momsbak;
	}
	public void setMomsbak(double momsBak) {
		this.momsbak = momsBak;
	}
	public String getRabatter() {
		return rabatter;
	}
	public void setRabatter(String rabatter) {
		this.rabatter = rabatter;
	}

	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getNamn() {
		return namn;
	}
	public void setNamn(String namn) {
		this.namn = namn;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}

}
	
	
	