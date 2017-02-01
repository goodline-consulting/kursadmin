package kursadmin.repository;
import java.util.List;

import kursadmin.domain.Nvaro;

public interface NvaroDao 
{
	public List<Nvaro> getNvaroList(int kid);    
	public void setNvaro(int kid, List<Nvaro> lista);
	public int getAntalNarvarande(int kid, int kurstillf);
}













