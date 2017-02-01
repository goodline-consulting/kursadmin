package kursadmin.domain;
import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class KursTillf implements Serializable 
{	
	private static final long serialVersionUID = 404926153812203639L;
	
	private int seq;
	private int kid;
	private Date dag;
		
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getKid() {
		return kid;
	}
	public void setKid(int kid) {
		this.kid = kid;
	}
	public Date getDag() {
		return dag;
	}
	public void setDag(Date dag) {
		this.dag = dag;
	}
	public int getDay() 
	{
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(dag.getTime());
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	public int getMonth()
	{
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(dag.getTime());
		return cal.get(Calendar.MONTH) + 1;
	}
	
}