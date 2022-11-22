/*
Scrivere in C o Java un programma che:

    apra una connessione verso l'host www.dmi.unict.it, port 80
    invii la stringa "GET /pappalardo/prova/16.aux\n"
    nello stream di byte ricevuti individui il primo carattere diverso da 'x', spazio o fine riga
    memorizzi questo byte e tutti quelli che il server invierà ancora in una variabile stringa denominata "s"
    invii, sulla connessione già aperta, la stringa s, seguita da \n
    scriva i byte ricevuti in risposta sulla standard output
*/

import java.io.*;
import java.net.*;

public class es16
{
	public static void main(String args[])
	{
		try
		{
			InetAddress indirizzo = InetAddress.getByName("www.dmi.unict.it");
			Socket client = new Socket(indirizzo, 80);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			
			out.println("GET /pappalardo/prova/16.aux\n");
			
			String tmp = "";
			
			int debug = 1;
			while ((tmp += in.readLine()) != null)
				System.out.println(debug++);
			
			System.out.println("1");
			
			int i;
			for (i = 0; i < tmp.length(); i++)
				if (tmp.charAt(i) != 'x')
					break;
					
			System.out.println("2");
			
			String s = tmp.substring(i, tmp.length());
			
			in.close();
			out.close();
			client.close();
			
			client = new Socket(indirizzo, 80);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
			
			out.println(s+"\n");
			tmp = "";
			
			System.out.println("3");
			
			while ((tmp += in.readLine()) != null);
			
			System.out.println(tmp);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
