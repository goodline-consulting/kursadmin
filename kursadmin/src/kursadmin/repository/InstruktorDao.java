package kursadmin.repository;
import java.util.List;
import kursadmin.domain.Instruktor;

public interface InstruktorDao 
{
	public List<Instruktor> getInstruktorList();    
    public Instruktor getInstruktor(int iid);
    public void updateInstruktor(Instruktor instruktor);
    public void insertInstruktor(Instruktor instruktor);
    public void deleteInstruktor(int iid);
}













