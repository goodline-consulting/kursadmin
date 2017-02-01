package kursadmin.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kursadmin.utils.KontrollSiffra;

public class Faktura 
{
	public static final int vanligFaktura = 0;
	public static final int tillaggsFaktura = 1;
	public static final int kreditFaktura = 2;
	private int fakturanr;
	private int pid;
	private int oid;
	private double belopp;
	private double betalt;
	private double rabatt;
	private double moms; 
	private double krediterat;
	private Date skapad;
	private Date skickad;
	private Date betald;
	private List<Fakturarad> rader = null;
	private double tillgodo;
	private double skuld;
	private Date betalas;
	private int fakturatyp;
	private int kopplad;
	private String info;
	private String kids;
	
	
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public boolean isMultiFaktura()
	{		
		if (rader == null || rader.size() == 1)
			return false;
		Set<Integer> set = new HashSet<Integer>();
		for (Fakturarad rad: rader)
		{
			if (set.size() == 0)
			{	
				set.add(rad.getKid());
				continue;
			}	
			if (set.contains(rad.getKid()) == false)
				return true;
		}
		return false;
	}
	public double getKrediterat() {
		return krediterat;
	}
	public void setKrediterat(double krediterat) {
		this.krediterat = krediterat;
	}
	public String getKids() {
		return kids;
	}
	public void setKids(String kids) {
		this.kids = kids;
	}
	public List<Fakturarad> getRader() {
		return rader;
	}
	public void setRader(List<Fakturarad> rader) {
		this.rader = rader;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getFakturatyp() {
		return fakturatyp;
	}
	public void setFakturatyp(int fakturatyp) {
		this.fakturatyp = fakturatyp;
	}
	public boolean isKreditFaktura()
	{
		return fakturatyp == kreditFaktura;
	}
	public boolean isVanligFaktura()
	{
		return fakturatyp == vanligFaktura;
	}
	public boolean isTillaggsFaktura()
	{
		return fakturatyp == tillaggsFaktura;
	}
	public int getKopplad() {
		return kopplad;
	}
	public void setKopplad(int kopplad) {
		this.kopplad = kopplad;
	}
	public Date getBetalas() {
		return betalas;
	}
	public void setBetalas(Date betalas) {
		this.betalas = betalas;
	}
	public double getTillgodo() {
		return tillgodo;
	}
	public void setTillgodo(double tillgodo) {
		this.tillgodo = tillgodo;
	}
	public double getSkuld() {
		return skuld;
	}
	public void setSkuld(double skuld) {
		this.skuld = skuld;
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
	public Date getBetald() {
		return betald;
	}
	public void setBetald(Date betald) {
		this.betald = betald;
	}
	public String getOcr()
	{
		String tmpRef = "" + fakturanr;
		return tmpRef + KontrollSiffra.calc(tmpRef);
	}
	public int getFakturanr() {
		return fakturanr;
	}
	public void setFakturanr(int fakturanr) {
		this.fakturanr = fakturanr;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public double getBelopp() {
		return belopp;
	}
	public void setBelopp(double belopp) {
		this.belopp = belopp;
	}
	public double getBetalt() {
		return betalt;
	}
	public void setBetalt(double betalt) {
		this.betalt = betalt;
	}
	public Date getSkapad() {
		return skapad;
	}
	public void setSkapad(Date skapad) {
		this.skapad = skapad;
	}
	public Date getSkickad() {
		return skickad;
	}
	public void setSkickad(Date skickad) {
		this.skickad = skickad;
	}
	
}
