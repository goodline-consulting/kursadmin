package kursadmin.service;

import kursadmin.domain.Instruktor;
import java.io.Serializable;
import java.util.List;

public interface InstruktorManagerInterface extends Serializable
{
	public Instruktor getInstruktor(int iid);
	
	public List<Instruktor> getInstruktors();
    
	public void deleteInstruktor(int iid);
	
	public void updateInstruktor(Instruktor instruktor);
	
	public void insertInstruktor(Instruktor instruktor);
}










    
    
    
    

