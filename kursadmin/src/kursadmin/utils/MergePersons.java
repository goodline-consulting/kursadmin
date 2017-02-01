package kursadmin.utils;

import java.io.IOException;
import java.sql.*;

import kursadmin.domain.Elev;
import kursadmin.domain.Person;
public class MergePersons 
{

	/**
	 * @param args
	 * 
	 */
	public static void main(String[] args) 
	{
		int pid1, pid2;
		
		System.out.println("/// Kursadmin Personmerger ver 1.0 (C) JCPro Consulting");
		if (args.length == 0)
		{	
			System.out.println("    Argument saknas!");
			System.out.println("    Syntax: pmerge personid1 personid2");
			System.out.println("    skriv pmerge -help för en hjälptext");
			return;
		}
		if (args[0].startsWith("-h"))
		{	
			System.out.println("    Syntax: pmerge personid1 personid2");
			System.out.println("    Detta program slår ihop två personer i kursadmins databas");
			System.out.println("    Personid1, behålls i systemet och tar över alla kurser ifrån personid2");
			System.out.println("    Personid2 tas efter operationen bort ur databasen.");
			System.out.println("    Programmet skall användas i de fall en person av misstag finns "); 
		    System.out.println("    2 ggr i systemet");
			System.out.println("    Exempel: pmerge 3055 4067");
			System.out.println("    personid som i exemplet hittas vid rubriken databasId under "); 
			System.out.println("    peroner->visa i kursadmin");
			return;
		}	
		if (args.length != 2)
		{
			System.out.println("    Felaktigt antal Argument!");
			System.out.println("    Syntax: pmerge personid1 personid2");
			System.out.println("    skriv pmerge -help för en hjälptext");
			return;
		}
		pid1 = Integer.parseInt(args[0]);
		pid2 = Integer.parseInt(args[1]);
		
		new MergePersons().run(pid1, pid2);
	} // main
	
	private void run(int p1, int p2)
	{
		
		try 
		{
		      Statement stmt;
		      Class.forName("com.mysql.jdbc.Driver");
		      String url = "jdbc:mysql://localhost:3306/kurs";  
		      Connection con = DriverManager.getConnection(url,"root", "alice00li");
		      //Class.forName("org.gjt.mm.mysql.Driver");
		      //String url = "jdbc:mysql://mysql5.c04.levonline.com/c0420900_db2";
		      //Connection con = DriverManager.getConnection(url,"c0420901", "Rob00len");
		      stmt = con.createStatement();		      
		      ResultSet rs = stmt.executeQuery("select * from person where pid = " + p1);
		      rs.next();		      
		      Person person1 = mapPerson(rs);
		      System.out.println("\n" + person1.getPid()+ " " + person1.getFnamn() + " " + person1.getEnamn() + " " + person1.getEmail());
		      rs = stmt.executeQuery("select * from person where pid = " + p2);
		      rs.next();		      
		      Person person2 = mapPerson(rs);
		      System.out.println(person2.getPid()+ " " + person2.getFnamn() + " " + person2.getEnamn() + " " + person2.getEmail());
		      System.out.print("\nOk att slå ihop dessa personer? (J/N)");
		      int svar = System.in.read();
		      if (svar != 74 && svar != 106)
		      {	  
		    	  System.out.println("\nAvbryter operationen!");
		    	  return;
		      }	  
		      stmt.executeUpdate("update elev set pid = " + person1.getPid() + " where pid = " + person2.getPid());
		      stmt.executeUpdate("update nvaro set pid = " + person1.getPid() + " where pid = " + person2.getPid());
		      stmt.executeUpdate("delete from person where pid = " + person2.getPid());
		      System.out.println("\nOperationen är utförd!");
		      stmt.close();
		      con.close();
		}
		catch( Exception e ) 
		{
		      e.printStackTrace();
		} 
	}
	private Person mapPerson(ResultSet rs) throws SQLException
	{
		Person person = new Person();
		person.setPid(rs.getInt("pid"));
		person.setFnamn(rs.getString("fnamn"));
		person.setEnamn(rs.getString("enamn"));
		person.setEmail(rs.getString("email"));
		return person;
	}
}
