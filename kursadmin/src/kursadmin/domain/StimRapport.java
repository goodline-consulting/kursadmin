package kursadmin.domain;

public class StimRapport 
{
	
	public int getAntTot() {
		antTot = antUpptill20 + antUpptill40 + ant40Plus;
		return antTot;
	}
	public void setAntTot(int antTot) {
		
		this.antTot = antTot;
	}
	public double getSummaTot() {		
		return summaTot;
	}
	public void setSummaTot(double summaTot) {
		this.summaTot = summaTot;
	}
	public int getAntUpptill20() {
		return antUpptill20;
	}
	public void setAntUpptill20(int antUptill20) {
		this.antUpptill20 = antUptill20;
	}
	public int getAntUpptill40() {
		return antUpptill40;
	}
	public void setAntUpptill40(int antUptill40) {
		this.antUpptill40 = antUptill40;
	}
	public int getAnt40Plus() {
		return ant40Plus;
	}
	public void setAnt40Plus(int ant40Plus) {
		this.ant40Plus = ant40Plus;
	}
	public double getSummaUpptill20() {
		return summaUpptill20;
	}
	public void setSummaUpptill20(double summaUpptill20) {
		this.summaUpptill20 = summaUpptill20;
	}
	public double getSummaUpptill40() {
		return summaUpptill40;
	}
	public void setSummaUpptill40(double summaUpptill40) {
		this.summaUpptill40 = summaUpptill40;
	}
	public double getSumma40Plus() {
		return summa40Plus;
	}
	public void setSumma40Plus(double summa40Plus) {
		this.summa40Plus = summa40Plus;
	}
	public static double getPrisupptill20() {
		return prisUpptill20;
	}
	public static double getPrisupptill40() {
		return prisUpptill40;
	}
	public static double getPris40plus() {
		return pris40Plus;
	}
	public final static double prisUpptill20 = 14.07;
	public final static double prisUpptill40 = 32.79;
	public final static double pris40Plus    = 51.24;
	
	int antUpptill20;
	int antUpptill40;
	int ant40Plus;
	int antTot;
	double summaUpptill20;
	double summaUpptill40;
	double summa40Plus;
	double summaTot;
}
