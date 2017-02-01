package kursadmin.utils;

public class KontrollSiffra 
{
	    public static String calc(String indata)
	    {
	      int a = 2;
	      int sum = 0;
	      int term;
	      for (int i = indata.length() - 1; i >= 0; i--) 
	      {
	        term = Character.digit(indata.charAt(i),10) * a;
	        if ( term > 9) term -= 9;
	        sum += term;
	        a = 3 - a;
	      }
	      if (sum % 10 == 0)
	    	  return "0";
	      return "" + (10 - (sum % 10));
	    }
}
