package kursadmin.service;

import kursadmin.domain.Elev;
import kursadmin.domain.Lokal;
import java.io.Serializable;
import java.util.List;

public interface ElevManagerInterface extends Serializable
{
	public Elev getElev(int kid, int pid);
	public Elev getElev(int referens);
	public int getAntElever(int kurstyp);
    public boolean elevExists(int kid, int pid);
    public boolean elevExists(int referens);
	public List<Elev> getElever(int kid); 
	public List<Elev> getEleverPaFaktura(int fakturanr);
	public void deleteElev(int kid, int pid);
	public void updateElev(Elev elev);
	public void insertElev(Elev elev);	 
	public void flyttaElev(int pid, int fromKid, int toKid);
}










    
    
    
    

