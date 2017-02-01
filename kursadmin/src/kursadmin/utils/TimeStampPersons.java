package kursadmin.utils;

import java.io.IOException;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

import kursadmin.domain.Elev;
import kursadmin.domain.Person;
public class TimeStampPersons 
{

	/**
	 * @param args
	 * 
	 */
	public static void main(String[] args) 
	{
		int pid1, pid2;
		
		System.out.println("/// Kursadmin SetTimeStamp ver 1.0 (C1) JCPro Consulting");
		
		
		new TimeStampPersons().run();
	} // main
	
	private void run()
	{
		
		try 
		{  
			  System.out.println("1");
		      Statement stmt;
		      // Class.forName("com.mysql.jdbc.Driver");
		      // String url = "jdbc:mysql://localhost:3306/kurs";  
		      // Connection con = DriverManager.getConnection(url,"root", "");
		      
		      Class.forName("org.gjt.mm.mysql.Driver");
		      System.out.println("2");
		      String url = "jdbc:mysql://mysql5.c04.levonline.com/c0420900_db2";
		      Connection con = DriverManager.getConnection(url,"c0420901", "Rob00len");
		      System.out.println("3");
		      stmt = con.createStatement();		      
		      ResultSet rs = stmt.executeQuery("select * from person");
		      while(rs.next())	
		      {
		    	  Timestamp ts = null;
		    	  Person person1 = mapPerson(rs);
		    	  Statement stmt2 = con.createStatement();	
		    	  ResultSet r2 = stmt2.executeQuery("select startdatum from kurs where kid in (select kid from elev where pid = " + person1.getPid() + ") ");
		    	  if (r2.next())
		    	  {	  		    		  
		    		  ts = new Timestamp(r2.getDate("startdatum").getTime());
		    	  }	  
		    	  else
		    		  ts =  new Timestamp(new GregorianCalendar().getTimeInMillis());
		    	  Statement stmt3 = con.createStatement();	
		    	  stmt3.executeUpdate("update person set inskriven = '" + ts + "' where pid = " + person1.getPid() );              
		    	  System.out.println(" " + person1.getFnamn() + " " + person1.getEnamn() + " " + person1.getEmail());
		    	  stmt2.close();
		    	  stmt3.close();
		      }
		      
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
		//person.setInskriven(rs.getTimestamp("inskriven"));
		return person;
	}
}
