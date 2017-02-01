package kursadmin.domain;
import java.io.Serializable;
import java.sql.Date;

public class Kurs implements Serializable 
{	
	private static final long serialVersionUID = -3747010694460268630L;
	private int kid;  
    private String beteckning;
    private String kursnamn;
    private int niva;
    private int pris;
    private int ikost;
    private int lkost;
    private int mkost;
    private int okost;
    private int lokal;
    private int instruktor;
    private String veckodag;
    private Date startdatum;
    private String starttid;
    private int lengd;
    private int tid;
    private int status;
    private boolean thisSemester;
    private String rumsalternativ; // T.ex Enkelrum:300,Dubbelrum:0
    private String resealternativ; // T.ex Buss:0,Egen bil:-300
    private String matalternativ; // T.ex Vanlig:0,Vegitarisk:0,Lactos:0,Gluten:0
    
    public String getRumsalternativ() {
		return rumsalternativ;
	}
	public void setRumsalternativ(String rumsalternativ) {
		this.rumsalternativ = rumsalternativ;
	}
	public String getResealternativ() {
		return resealternativ;
	}
	public void setResealternativ(String resealternativ) {
		this.resealternativ = resealternativ;
	}
	public String getMatalternativ() {
		return matalternativ;
	}
	public void setMatalternativ(String matalternativ) {
		this.matalternativ = matalternativ;
	}
	public int getStatus() {
		return status;
	}   
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatusText() {
			switch(status)
			{
				case 0:	return "Bokningsbar";
				case 1: return "Ej bokningsbar";
				case 2: return "Fullsatt";
			}
			return "Okänd";
	}
	    
	public boolean isThisSemester() {
		return thisSemester;
	}
	public void setThisSemester(boolean thisSemester) {
		this.thisSemester = thisSemester;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public int getKid() {
		return kid;
	}
	public void setKid(int kid) {
		this.kid = kid;
	}
	public String getBeteckning() {
		return beteckning;
	}
	public void setBeteckning(String beteckning) {
		this.beteckning = beteckning;
	}
	public String getKursnamn() {
		return kursnamn;
	}
	public void setKursnamn(String kursnamn) {
		this.kursnamn = kursnamn;
	}
	public int getNiva() {
		return niva;
	}
	public void setNiva(int niva) {
		this.niva = niva;
	}
	public int getPris() {
		return pris;
	}	
	public void setPris(int pris) {
		this.pris = pris;
	}
	public int getLokal() {
		return lokal;
	}
	public void setLokal(int lokal) {
		this.lokal = lokal;
	}
	public int getInstruktor() {
		return instruktor;
	}
	public void setInstruktor(int instruktor) {
		this.instruktor = instruktor;
	}
	public String getVeckodag() {
		return veckodag;
	}
	public void setVeckodag(String veckodag) {
		this.veckodag = veckodag;
	}
	public Date getStartdatum() {
		return startdatum;
	}
	public void setStartdatum(Date startdatum) {
		this.startdatum = startdatum;
	}
	public String getStarttid() {
		return starttid;
	}
	public void setStarttid(String starttid) {
		this.starttid = starttid;
	}
	public int getLengd() {
		return lengd;
	}
	public void setLengd(int lengd) {
		this.lengd = lengd;
	}
	public int getIkost() {
		return ikost;
	}
	public void setIkost(int ikost) {
		this.ikost = ikost;
	}
	public int getLkost() {
		return lkost;
	}
	public void setLkost(int lkost) {
		this.lkost = lkost;
	}
	public int getMkost() {
		return mkost;
	}
	public void setMkost(int mkost) {
		this.mkost = mkost;
	}
	public int getOkost() {
		return okost;
	}
	public void setOkost(int okost) {
		this.okost = okost;
	} 
}