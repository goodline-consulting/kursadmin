package kursadmin.domain;

public class Organisation 
{
	private int oid;
	private int orgtyp;
	private String orgnr;
	private String orgnamn;
	private boolean momsredo;
	private String momsnr;
	private String bankgiro;
	private String plusgiro;
	private String adress;
	private String postnr;
	private String postadress;
	private String telefon;
	private String email;
	private String kontaktperson;
	private String info;
	private static final String[] orgtyper = {"ideell fšrening", "Aktiebolag", "Handelsbolag", "Ekonomisk fšrening"}; 
	private static final int IDEELL_FORENING = 0;
	private static final int AKTIEBOLAG = 1;
	private static final int HANDELSBOLAG = 2;
	private static final int EKONOMISK_FORENING = 3;
	
	public String getVat()
	{
		return "SE" + orgnr.replace("-", "") + "01";
	}
	public String getOrgtypsnamn()
	{
		return orgtyper[orgtyp];
	}
	
	public String getOrgtypsStr(int orgtyp)
	{
		return orgtyper[orgtyp];
	}
	
	public String getMomsredovisar()
	{
		if (momsredo)
			return "Ja";
		else
			return "Nej";
	}
	public String getBankgiro() {
		return bankgiro;
	}
	public void setBankgiro(String bankgiro) {
		this.bankgiro = bankgiro;
	}
	public String getPlusgiro() {
		return plusgiro;
	}
	public void setPlusgiro(String plusgiro) {
		this.plusgiro = plusgiro;
	}
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public int getOrgtyp() {
		return orgtyp;
	}
	public void setOrgtyp(int orgtyp) {
		this.orgtyp = orgtyp;
	}
	public String getOrgnr() {
		return orgnr;
	}
	public void setOrgnr(String orgnr) {
		this.orgnr = orgnr;
	}
	public String getOrgnamn() {
		return orgnamn;
	}
	public void setOrgnamn(String orgnamn) {
		this.orgnamn = orgnamn;
	}
	public boolean isMomsredo() {
		return momsredo;
	}
	public void setMomsredo(boolean momsredo) {
		this.momsredo = momsredo;
	}
	public String getMomsnr() {
		return momsnr;
	}
	public void setMomsnr(String momsnr) {
		this.momsnr = momsnr;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getPostnr() {
		return postnr;
	}
	public void setPostnr(String postnr) {
		this.postnr = postnr;
	}
	public String getPostadress() {
		return postadress;
	}
	public void setPostadress(String postadress) {
		this.postadress = postadress;
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
	public String getKontaktperson() {
		return kontaktperson;
	}
	public void setKontaktperson(String kontaktperson) {
		this.kontaktperson = kontaktperson;
	}

	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
}
