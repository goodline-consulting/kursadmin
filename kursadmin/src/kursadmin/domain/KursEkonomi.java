package kursadmin.domain;

public class KursEkonomi 
{
	private int lkost;
	private int ikost;
	private int mkost;
	private int okost;
	private int totkost;
	private int pris;
	private int inkomster;
	private int netto;
	private int elever;
	private int betalande;
	private int breakeven;
	private String beteckning;
	private String kursnamn;
	private String lokal;
	
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
	public int getElever() {
		return elever;
	}
	public void setElever(int elever) {
		this.elever = elever;
	}
	public int getBetalande() {
		return betalande;
	}
	public void setBetalande(int betalande) {
		this.betalande = betalande;
	}
	public int getLkost() {
		return lkost;
	}
	public void setLkost(int lkost) {
		this.lkost = lkost;
	}
	public int getIkost() {
		return ikost;
	}
	public void setIkost(int ikost) {
		this.ikost = ikost;
	}
	public int getInkomster() {
		return inkomster;
	}
	public void setInkomster(int inkomster) {
		this.inkomster = inkomster;
	}
	public int getNetto() {
		return netto;
	}
	public void setNetto(int netto) {
		this.netto = netto;
	}
	public String getLokal() {
		return lokal;
	}
	public void setLokal(String lokal) {
		this.lokal = lokal;
	}
	public int getBreakeven() {
		return breakeven;
	}
	public void setBreakeven(int breakeven) {
		this.breakeven = breakeven;
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
	public int getTotkost() {
		return totkost;
	}
	public void setTotkost(int totKost) {
		this.totkost = totKost;
	}
	public int getPris() {
		return pris;
	}
	public void setPris(int pris) {
		this.pris = pris;
	}
}
