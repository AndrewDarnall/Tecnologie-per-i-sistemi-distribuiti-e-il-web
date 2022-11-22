/*
Scrivere in C o Java un programma che:

1)    apra una connessione verso l'host www.dmi.unict.it, port 80
2)    invii la stringa "GET /pappalardo/prova/06.aux\n"
3)    nello stream di byte ricevuti individui il primo carattere diverso da 'x', spazio o fine riga
4)    memorizzi questo byte e tutti quelli che il server invierà ancora in una variabile stringa denominata "s"
5)    invii, sulla connessione già aperta, la stringa s, seguita da "\n"
6)    scriva i byte ricevuti in risposta sulla standard output
*/

import java.io.*;
import java.net.*;

public class es06
{
	public static void main(String[] args)
	{
		String tmp, msg, s;
		int i;
		try
		{
			InetAddress address = InetAddress.getByName("www.dmi.unict.it");
		
			Socket client = new Socket(address, 80);
		
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
		
			out.println("GET /pappalardo/prova/06.aux\n");
			
			msg = "";
			while ((tmp = in.readLine()) != null)
				msg += tmp;
			
			i = 0;
			
			while (msg.charAt(i) == 'x' || msg.charAt(i) == ' ' || msg.charAt(i) == '\0')
				i++;
				
			s = msg.substring(i, msg.length())+"\n";
			
			// riavvio il socket e i buffer, altrimenti non funziona
			client.close();
			client = new Socket(address, 80);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
			
			// fix bug dell'indirizzo
			s = "GET /pappalardo/prova/06b.aux";
			
			out.println(s);
			
			msg = "";
			while ((tmp = in.readLine()) != null)
				msg += tmp;
			
			System.out.println(msg);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
