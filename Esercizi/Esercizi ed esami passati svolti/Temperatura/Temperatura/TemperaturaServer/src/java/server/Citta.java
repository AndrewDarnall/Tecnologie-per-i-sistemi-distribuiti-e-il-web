package server;
import java.io.*;
import java.util.*;
import server.*;

public class Citta
{
	private HashMap<String,Double> citta;
	public Citta()
	{
		citta=new HashMap<String,Double>();
		citta.put("Milano",34.7);
		citta.put("Catania",5.6);
		citta.put("New York",31.3);
		citta.put("Brucoli",24.3);
		citta.put("Giarre",2.4);
		citta.put("Parigi",-4.6);
		citta.put("Roma",7.3);
		citta.put("Messina",4.5);
	}
	public void setTempo(String c,double t)
	{
		citta.replace(c,t);
	}
        public double getTempo(String c)
        {
            return citta.get(c);
        }
        public int getSize()
        {
            return citta.size();
        }
        public String[] getAllCitta()
        {
            int j=0;
            String c[]=new String[citta.size()];
            Iterator i=citta.entrySet().iterator();
            while(i.hasNext())
            {
                Map.Entry entry=(Map.Entry)i.next();
                c[j]=entry.getKey()+"";
                j++;
            }
            return c;
        }
        public double[] getAllTempo()
        {
            int j=0;
            double t[]=new double[citta.size()];
            Iterator i=citta.entrySet().iterator();
            while(i.hasNext())
            {
                Map.Entry entry=(Map.Entry)i.next();
                t[j]=(double)entry.getValue();
                j++;
            }
            return t;
        }
}
