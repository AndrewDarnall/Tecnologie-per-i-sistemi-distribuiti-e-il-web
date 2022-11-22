import java.io.*;
import java.net.*;
import java.rmi.*;
import java.rmi.registry.*;

public class client
{
	public static void main(String args[])
	{
		String tmp;
		int count = 0;
		try
		{
			InetAddress address = InetAddress.getByName("www.dmi.unict.it");
			Socket client = new Socket(address, 80);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
		
			out.println("GET /pappalardo/prova/19.aux\n\n");
			
			Registry reg = LocateRegistry.getRegistry("localhost");
			periodChecker stub = (periodChecker)reg.lookup("pck");
			
			// mi sposto all'inizio dell'INFERNO
			while ((tmp = in.readLine()).compareTo("INFERNO") == 0);
			
			// Calcolo e stampo il numero di periodi dell'INFERNO
			while (((tmp = in.readLine()) != null) && tmp.compareTo("PURGATORIO") != 0)
				if (stub.finiscePeriodo(tmp))
					count++;
			
			System.out.println("Inferno: "+count);
			
			count = 0;
			
			// Calcolo e stampo il numero di periodi del PURGATORIO
			while (((tmp = in.readLine()) != null) && tmp.compareTo("PARADISO") != 0)
				if (stub.finiscePeriodo(tmp))
					count++;
			
			System.out.println("Purgatorio: "+count);
			
			count = 0;
			
			// Calcolo e stampo il numero di periodi del PARADISO
			while (((tmp = in.readLine()) != null))
				if (stub.finiscePeriodo(tmp))
					count++;
			
			System.out.println("Paradiso: "+count);
			
			count = 0;
			
			client.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
