package kursadmin.repository;
import java.sql.Date;
import java.util.List;
import kursadmin.domain.*;

public interface KursDao 
{
	public List<Kurs> getKursMiniList();
	public List<Kurs> getKursMiniList(int pid, Date startDate);
	public List<Kurs> getCurrentKursList();
	public List<Kurs> getCurrentKursMiniList(int kursTyp, int pid);
	public List<Kurs> getRabattKursMiniList(int pid, int fakturanr);
	public List<Kurs> getDansUtlard(int ppid);
	public List<KursAll> getKursList(Date startDate); 
	public List<KursAll> getKursList(String filter1, String filter2, int filterType);
	public List<KursAll> getKursList(int pid);
	public List<KursAll>  getOfakturerade(int pid);
	public List<KursAll> getKopieKandidater(int tid, int nid);
    public KursAll getKursAll(int kid);
    public Kurs getKurs(int kid);
    public int getOid(int kid);
    public void updateKurs(Kurs kurs);
    public void insertKurs(Kurs kurs);
    public void updateKursAll(KursAll kursAll);    
    public void insertKursAll(KursAll kursAll);
    public void deleteKurs(int kid);
        
}













