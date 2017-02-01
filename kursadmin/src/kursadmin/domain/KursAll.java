package kursadmin.domain;
import java.util.Date;
import java.util.List;

public class KursAll extends Kurs
{	
	
	
	private static final long serialVersionUID = 5599375163249147808L;	
	private String lokalNamn;
	private String instruktorNamn;
	private String kursTyp;
	private String kursNiva;
	private double momssats;
	private double momsbak;
	private int typavrabatt;
	private boolean rumshantering;
	private boolean resehantering;
	private boolean mathantering;
	
	
	public double getMatPris(int idx)
	{
		if (getMatalternativ() == null)
			return 0;
		return Integer.parseInt(getMatalternativ().split(",")[idx].split(":")[1]);
	}
	public double getResePris(int idx)
	{
		if (getResealternativ() == null)
			return 0;
		return Integer.parseInt(getResealternativ().split(",")[idx].split(":")[1]);
	}
	public double getRumsPris(int idx)
	{
		if (this.getRumsalternativ() == null)
			return 0;
		return Integer.parseInt(getRumsalternativ().split(",")[idx].split(":")[1]);
	}
	public String getFakturaTextMat(int idx)
	{
		if (this.getMatalternativ() == null)
			return "";
		return getMatalternativ().split(",")[idx].split(":")[0];
	}
	public String getFakturaTextResa(int idx)
	{
		if (this.getResealternativ() == null)
			return "";
		return getResealternativ().split(",")[idx].split(":")[0];
	}
	public String getFakturaTextLogi(int idx)
	{
		if (this.getRumsalternativ() == null)
			return "";
		return getRumsalternativ().split(",")[idx].split(":")[0];
	}
	public boolean harPrisTillagg()
	{
		return rumshantering || resehantering || mathantering;
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
	private String rabatter;
	private int	fakturatyp;
	
	
	public int getFakturatyp() {
		return fakturatyp;
	}
	public void setFakturatyp(int fakturatyp) {
		this.fakturatyp = fakturatyp;
	}
	private List<Date> kurstillf = null;
	private int antKursTillf;
	
	public double getMoms()
	{
		return (momsbak * this.getPris()) / 100;
	}
	public double getPrisExMoms()
	{
		return getPris() - getMoms();
	}
	public double getMomssats() {
		return momssats;
	}
	public void setMomssats(double momssats) {
		this.momssats = momssats;
	}
	public double getMomsbak() {
		return momsbak;
	}
	public void setMomsbak(double momsbak) {
		this.momsbak = momsbak;
	}
	
	public String getRabatter() {
		return rabatter;
	}
	public void setRabatter(String rabatter) {
		this.rabatter = rabatter;
	}
	
	public String getLokalNamn() {
		return lokalNamn;
	}
	public void setLokalNamn(String lokalNamn) {
		this.lokalNamn = lokalNamn;
	}
	public String getInstruktorNamn() {
		return instruktorNamn;
	}
	public void setInstruktorNamn(String instruktorNamn) {
		this.instruktorNamn = instruktorNamn;
	}
	public List<Date> getKurstillf() {
		return kurstillf;
	}
	public void setKurstillf(List<Date> kurstillf) 
	{
		this.antKursTillf = kurstillf.size();
		this.kurstillf = kurstillf;
	}    	 
	public void delKurstillf(int idx)
	{
		this.kurstillf.remove(idx);
		this.antKursTillf = kurstillf.size();
	}
	public int getAntKursTillf() {
		return antKursTillf;
	}
	public void setAntKursTillf(int antKursTillf) {
		this.antKursTillf = antKursTillf;
	}
	public String getKursTyp() {
		return kursTyp;
	}
	public void setKursTyp(String kursTyp) {
		this.kursTyp = kursTyp;
	}
	public String getKursNiva() {
		return kursNiva;
	}
	public void setKursNiva(String kursNiva) {
		this.kursNiva = kursNiva;
	}
	
}