package kursadmin.service;

import kursadmin.domain.Lokal;
import java.io.Serializable;
import java.util.List;

public interface LokalManagerInterface extends Serializable
{
	public Lokal getLokal(int lid);
	
	public List<Lokal> getLokaler();
    
	public void deleteLokal(int lid);
	
	public void updateLokal(Lokal lokal);
	
	public void insertLokal(Lokal lokal);
}










    
    
    
    

