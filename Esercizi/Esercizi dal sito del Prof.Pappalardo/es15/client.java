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
			InetAddress address = InetAddress.getByName("www.dmi.unict.it");
			Socket client = new Socket(address, 80);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			
			out.println("GET /pappalardo/prova/15.aux\n");
			
			Registry reg = LocateRegistry.getRegistry("localhost");
			es15 stub = (es15)reg.lookup("es15");
			
			int count = 0;
			String tmp;
			
			while ((tmp = in.readLine()) != null)
				if (stub.validate(tmp) == 1)
					count++;
			
			System.out.println("Tot: "+count);
			
			in.close();
			out.close();
			client.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
