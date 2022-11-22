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
			
			out.println("GET /pappalardo/prova/17.aux\n");
			
			String s = in.readLine(), t;
			int count = 0;
			
			Registry reg = LocateRegistry.getRegistry("localhost");
			es17 stub = (es17) reg.lookup("es17");
			
			while (true)
			{
				t = s;
				s = in.readLine();
				if (s == null)	break;
				if (stub.hash(s, 30) == stub.hash(t, 30))
					count ++;
			}
			
			System.out.println("\n"+count+"\n");
			
			/* aumentando nella funzione hash(s, n) il valore di n si riduce il numero di collisioni */
			
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
