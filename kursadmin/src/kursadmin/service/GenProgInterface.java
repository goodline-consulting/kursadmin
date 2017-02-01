package kursadmin.service;
import java.util.List;
import java.util.Map;

import kursadmin.domain.Instruktor;
import kursadmin.domain.KursAll;
import kursadmin.domain.KursProgram;
import kursadmin.domain.Lokal;
import kursadmin.domain.PathConfig;
import kursadmin.domain.ProgramPunkt;


public interface GenProgInterface 
{
	String GenProgWeb(KursAll kursAll, List<KursProgram> progLista, Map<Integer, ProgramPunkt> punktMap, Instruktor instr, PathConfig paths);
	
}
