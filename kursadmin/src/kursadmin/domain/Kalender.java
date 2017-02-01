package kursadmin.domain;

import java.io.Serializable;
import java.util.Date;

public class Kalender implements Serializable 
{	
	private static final long serialVersionUID = 8988828011080829098L;
	private int cid;
	private Date tidpunkt;
	private int ar;
	private int man;
	private int dag;
	private int tim;
	private int min;
	private String rubrik;
	private String altrubrik;
	private String info;
	private String summering;
	private boolean larm;
	private boolean kvitt;
	private int vikt;
	
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public Date getTidpunkt() {
		return tidpunkt;
	}
	public void setTidpunkt(Date tidpunkt) {
		this.tidpunkt = tidpunkt;
	}
	public String getRubrik() {
		return rubrik;
	}
	public void setRubrik(String rubrik) {
		this.rubrik = rubrik;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public boolean isLarm() {
		return larm;
	}
	public void setLarm(boolean larm) {
		this.larm = larm;
	}
	public boolean isKvitt() {
		return kvitt;
	}
	public void setKvitt(boolean kvitt) {
		this.kvitt = kvitt;
	}
	public int getVikt() {
		return vikt;
	}
	public void setVikt(int vikt) {
		this.vikt = vikt;
	}
	public String getSummering() {
		return summering;
	}
	public void setSummering(String summering) {
		this.summering = summering;
	}
	public int getAr() {
		return ar;
	}
	public void setAr(int ar) {
		this.ar = ar;
	}
	public int getMan() {
		return man;
	}
	public void setMan(int man) {
		this.man = man;
	}
	public int getDag() {
		return dag;
	}
	public void setDag(int dag) {
		this.dag = dag;
	}
	public int getTim() {
		return tim;
	}
	public void setTim(int tim) {
		this.tim = tim;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public String getAltrubrik() {
		return altrubrik;
	}
	public void setAltrubrik(String altrubrik) {
		this.altrubrik = altrubrik;
	}
}	
	