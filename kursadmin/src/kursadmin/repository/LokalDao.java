package kursadmin.repository;
import java.util.List;
import kursadmin.domain.Lokal;

public interface LokalDao 
{
    public List<Lokal> getLokalList();    
    public Lokal getLokal(int lid);
    public void updateLokal(Lokal lokal);
    public void insertLokal(Lokal lokal);
    public void deleteLokal(int lid);
}













