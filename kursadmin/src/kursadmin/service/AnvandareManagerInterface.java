package kursadmin.service;

import kursadmin.domain.Anvandare;
import java.io.Serializable;
import java.util.List;

public interface AnvandareManagerInterface extends Serializable
{
	public Anvandare getAnvandare(String namn);
	
	public List<Anvandare> getAnvandareList();
    
	public void deleteAnvandare(String namn);
	
	public void insertAnvandare(Anvandare anvandare);
	
	public void updateAnvandare(Anvandare anvandare);
	
}










    
    
    
    

