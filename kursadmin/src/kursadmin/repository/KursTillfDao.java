package kursadmin.repository;
import java.util.List;
import kursadmin.domain.*;

public interface KursTillfDao 
{
	public List<KursTillf> getKursTillfList(int kid);    
    public void setKursTillfList(int kid, List<KursTillf> lista);
    public int getAntKurstillf(int kid);
}













