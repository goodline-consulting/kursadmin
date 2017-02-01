package kursadmin.service;
import java.util.List;

import kursadmin.domain.GrundConfig;
import kursadmin.domain.KursAll;
import kursadmin.domain.Lokal;
import kursadmin.domain.PathConfig;


public interface GenAnmInterface 
{
	String GenAnmWeb(List<KursAll> kursLista, List<Lokal> lokalLista, PathConfig paths, GrundConfig grundConfig);
	
}
