package kursadmin.repository;
import java.util.List;
import kursadmin.domain.Anvandare;


public interface AnvandareDao 
{
	public List<Anvandare> getAnvandareList();    
    public Anvandare getAnvandare(String namn);
    public void insertAnvandare(Anvandare anvandare);
    public void updateAnvandare(Anvandare anvandare);
    public void deleteAnvandare(String namn);
}













