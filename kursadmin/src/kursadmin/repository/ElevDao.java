package kursadmin.repository;
import java.util.List;
import kursadmin.domain.Elev;
import kursadmin.domain.Person;

public interface ElevDao 
{
    public List<Elev> getElevList(int kid);
    public List<Elev> getEleverPaFaktura(int fakturanr);
    public Elev getElev(int kid, int pid);
    public Elev getElev(int referens);
    public int getAntElever(int kurstyp);
    public boolean elevExists(int kid, int pid);
    public boolean elevExists(int referens);
    public void updateElev(Elev elev);
    public void insertElev(Elev elev);
    public void insertElev(Elev elev, Person person);
    public void deleteElev(int kid, int pid);
    public void flyttaElev(int pid, int fromKid, int toKid);
}













