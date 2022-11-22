package BOOKServer;
import java.io.*;
import java.util.*;
import java.net.*;
import BOOKServer.*;

public class Biblioteca
{
	private HashMap<String,Integer> hm;
	public Biblioteca()
	{
		hm=new HashMap<String,Integer>();
		hm.put("Harry Potter",15);
		hm.put("Dragon ball",10);
		hm.put("My Hero Accademia",5);
		hm.put("Libri degli Iutubers",1);
		hm.put("House of Karts(libro)",80);
		hm.put("Faccio schifo in java Vol.1",18);
	}
	public boolean isAvailable(String titololibro, int n)
    {
        if(hm.get(titololibro)!=null)
        {
            if((n>=1) && (n<=hm.get(titololibro)))
            {
                return true;
            }
        }
        return false;
    }
}
