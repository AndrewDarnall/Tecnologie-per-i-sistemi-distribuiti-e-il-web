import java.io.*;
import java.net.*;
import java.rmi.*;
import java.rmi.registry.*;

public class client
{
	public static void main(String args[])
	{
		try
		{
			InetAddress indirizzo = InetAddress.getByName("www.dmi.unict.it");
			Socket client = new Socket(indirizzo, 80);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			
			String tmp;
			String sequenza[] = new String[9];
			int i = 0;
			
			out.println("GET /pappalardo/prova/14.aux\n");
			
			while ((tmp = in.readLine()) != null)
			{
				if (tmp.length() == 9 && isInt(tmp))
				{
					sequenza[i] = tmp;
					i++;
					if (i == 9)	break;
				}
				else	i = 0;
			}
			
			Registry reg = LocateRegistry.getRegistry("localhost");
			es14 stub = (es14)reg.lookup("es14");
			
			i = 0;
			for (int j = 0; j < 9; j++)
				if (stub.validate(sequenza[j]) == 1)	
					i++;
					
			System.out.println("Trovate "+i+" righe valide");
			
			stub.validate("123456789");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static boolean isInt(String s)
	{
		try
		{
			Integer.parseInt(s);
		}
		catch(NumberFormatException e)
		{
			return false;
		}
		
		return true;
	}
}
