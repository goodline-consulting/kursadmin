package kursadmin.service;
import java.util.List;
import java.util.Map;

import kursadmin.domain.KursAll;
import kursadmin.domain.Lokal;
import kursadmin.domain.PathConfig;



public interface GenOversiktInterface 
{
	String GenOversiktWeb(List<KursAll> kursLista, Map<Integer, Lokal> lokalMap, PathConfig paths);
	
}
