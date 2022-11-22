/*
Scrivere in C o Java un programma che:

1)    apra una connessione verso l'host www.dmi.unict.it, port 80
2)    invii la stringa "GET /pappalardo/prova/05.aux\n"
3)    nello stream di byte ricevuti individui i primi (e unici) compresi tra i caratteri [ e ]
4)    memorizzi questi byte in una variabile stringa denominata "s"
5)    invii, su una nuova connessione verso lo stesso server, la stringa s, terminata da "\n"
6)    scriva i byte ricevuti in risposta sulla standard output

*/

import java.io.*;
import java.net.*;

public class es05
{
	public static void main(String args[])
	{
		String tmp, msg, s;
		int i;
		try
		{
			InetAddress address = InetAddress.getByName("www.dmi.unict.it");
			
			Socket client = new Socket(address, 80);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);

			out.println("GET /pappalardo/prova/05.aux\n");
			
			msg = "";
			while ((tmp = in.readLine()) != null)
				msg += tmp;
			
			i = 0;
			while (msg.charAt(i) != '[')
				i++;
				
			s = "";
			i++;
			
			while (msg.charAt(i) != ']')
			{
				s += msg.charAt(i);
				i++;
			}
			
			
			// riavvio il socket e i buffer, altrimenti non funziona
			client.close();
			client = new Socket(address, 80);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
			
			out.println(s);
			
			System.out.println(in.readLine());
			
			client.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
