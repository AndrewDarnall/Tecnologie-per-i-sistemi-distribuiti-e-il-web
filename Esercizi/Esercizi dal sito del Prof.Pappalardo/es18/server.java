import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.net.*;
import java.io.*;

public class server implements es18
{
	public int hash(String s, int n)
	{
		int somma = 0;
		
		for (int i = 0; i < s.length(); i++)
			somma += s.charAt(i);
		
		return somma % n;
	}
	
	public int ask(String ip, int port, String msg)
	{
		try
		{
			InetAddress address = InetAddress.getByName(ip);
			Socket client = new Socket(address, port);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			
			String tmp;
			out.println(msg+"\n\n");
			
			// qualsiasi espressione del tipo (valore % 30) da un risultato compreso tra 0 e 29
			// memorizzo in un array la frequenza di ciascun numero
			int risultati[] = new int[30];
			
			while ((tmp = in.readLine()) != null)
				risultati[hash(tmp, 30)]++;
			
			int maxfreq = 0;
			int maxvalue = 0;
			int i;
			
			
			for (i = 0; i < 30; i++)
				if (risultati[i] > maxfreq)
				{
					maxfreq = risultati[i];
					maxvalue = i;
				}
			
			client.close();
			return maxvalue;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public static void main(String args[])
	{
		try
		{
			server obj = new server();
			es18 stub = (es18)UnicastRemoteObject.exportObject(obj, 0);
			
			Registry reg = LocateRegistry.getRegistry("localhost");
			reg.bind("es18", stub);
			
			System.out.println("Server ready (CTRL-C quits)");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}