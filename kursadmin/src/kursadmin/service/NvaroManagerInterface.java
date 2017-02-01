package kursadmin.service;

import kursadmin.domain.Nvaro;
import java.io.Serializable;
import java.util.List;

public interface NvaroManagerInterface extends Serializable
{	
	public List<Nvaro> getNvaro(int kid);    
	public void setNvaro(int kid, List<Nvaro> lista);	
	public int getAntalNarvarande(int kid, int kurstillf);
}










    
    
    
    

