package kursadmin.repository;
import java.util.Date;
import java.util.List;
import kursadmin.domain.Betalning;


public interface BetalningDao 
{
	public List<Betalning> getBetalningarTillgodoList(int pid);
	public List<Betalning> getBetalningList(int fakturanr);
	public List<Betalning> getPersonBetalning(String name);
	public List<Betalning> getBetalningList(Date fromDat, Date toDat);
	public List<Betalning> getBetalningList(Date datum);
	public List<Betalning> getOplacerade();
	public List<Date> getDates(); 
    public Betalning getBetalning(int id);
    public void insertBetalningar(List<Betalning> betalningar);   
    public void insertBetalning(Betalning betalning);  
    public void updateBetalning(Betalning betalning);
    public void deleteBetalning(int id);
    public boolean DatumExists(Date datum);
}













