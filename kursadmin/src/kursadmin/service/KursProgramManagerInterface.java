package kursadmin.service;

import kursadmin.domain.KursAll;
import kursadmin.domain.KursProgram;
import kursadmin.domain.ProgramPunkt;
import java.util.List;

public interface KursProgramManagerInterface 
{
	public List<ProgramPunkt> getProgramPunktList();
    public List<ProgramPunkt> getProgramPunktList(int tid);   
    public List<ProgramPunkt> getProgramPunktList(int tid, String filter, boolean aktuella);    
    public ProgramPunkt getProgramPunkt(int ppid);
    public void insertProgramPunkt(ProgramPunkt programPunkt);
    public void loadProgramPunkter(List<ProgramPunkt> punktList);
    public boolean deleteProgramPunkt(int ppid);
    public void updateProgramPunkt(ProgramPunkt programPunkt);
    public List<KursProgram> getKursProgram(int kid);
    public void setKursProgram(List<KursProgram> progList);
    public String genKursProgram(KursAll kursAll, List<KursProgram> progLista);
    public String genKursUtbud(int[] kids);
}
 








    
    
    
    

