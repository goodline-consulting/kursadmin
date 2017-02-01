package kursadmin.service;

import kursadmin.domain.Kurs;
import kursadmin.domain.KursAll;
import kursadmin.domain.KursEkonomi;
import kursadmin.domain.StimRapport;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public interface KursManagerInterface extends Serializable
{
	public List<Kurs> getKursMiniList(); 
	public List<Kurs> getKursMiniList(int pid, Date startDate);
	public List<Kurs> getCurrentKursList();
	public List<Kurs> getRabattKursMiniList(int pid, int fakturanr);
	public List<KursAll>  getOfakturerade(int pid);
	public List<KursAll> getKurser(Date startDate);
	public List<KursAll> getKurser(String filter, String filter2, int filterType);
	public List<KursAll> getKurser(int pid);
	public List<KursAll> getKopieKandidater(int tid, int nid);
	public KursAll getKursAll(int kid);
	public Kurs getKurs(int kid);
	public int getOid(int kid);
	public int getAntKursTillf(int kid);
	public void updateKurs(Kurs kurs);	
	public void insertKurs(Kurs kurs);
	public void updateKursAll(KursAll kursAll);    
    public void insertKursAll(KursAll kursAll);
	public void deleteKurs(int kid);	
	public double calcPrice(int pid, int kid);
	public double calcPrice(int pid, int kid, boolean kursbyte);
	public KursEkonomi getEkonomi(int kid);
	public StimRapport getStimRapport(int[] kid);
	public List<KursEkonomi> getEkonomi(int[] kid);
}








    
    
    
    

