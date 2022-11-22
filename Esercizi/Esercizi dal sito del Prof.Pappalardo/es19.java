/*
Scrivere un programma (C o Java) che, dato un intero k, estragga la k-esima parola da un elenco e restituisca in output su console il numero di occorrenze della parola estratta presenti in un file di testo.

In particolare il programma dovrà:

    riconoscere il suo primo argomento sulla riga di comando come intero, sia k, compreso tra 1 e /*
Scrivere un programma (C o Java) che, dato un intero k, estragga la k-esima parola da un elenco e restituisca in output su console il numero di occorrenze della parola estratta presenti in un file di testo.

In particolare il programma dovrà:

    riconoscere il suo primo argomento sulla riga di comando come intero, sia k, compreso tra 1 e 100000.
    Effettuare una connessione con il server 151.97.252.130, port 80.
    Inviare al server la richiesta: "GET /pappalardo/prova/dizionario\n\n".
    Il testo ricevuto conterrà una lista di parole, una per riga: individuare e memorizzare la k-esima, sia w.
    Effettuare una nuova connessione con lo stesso server di cui al punto 2, imviargli la stringa "GET /pappalardo/prova/19.aux\n\n" e analizzare lo stream ricevuto, contando il numero di occorrenze in esso della parola w.
    Scrivere sulla standard output il numero di occorrenze contate.

Durata della prova: 40 minuti

*/

import java.io.*;
import java.net.*;

public class es19
{
	public static void main(String args[])
	{
		if (args.length != 1 || !isOk(args[0]))
			System.out.println(">>Utilizzo: java client k\n(k compreso tra 1 e 100000)\n");
		
		try
		{
			InetAddress indirizzo = InetAddress.getByName("www.dmi.unict.it");
			Socket client = new Socket(indirizzo, 80);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			
			out.println("GET /pappalardo/prova/dizionario\n\n");
			
			int i = 0;
			int k = Integer.parseInt(args[0]);
			
			while ((i < k - 1) && (in.readLine() != null))
				i++;
			
			String w;
			if ((w = in.readLine()) == null)
			{
				System.out.println("k troppo grande");
				return;
			}
			
			
			in.close();
			out.close();
			client.close();
			
			client = new Socket(indirizzo, 80);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
			
			String testo = "";
			String tmp;
			
			out.println("GET /pappalardo/prova/19.aux\n\n");
			
			while ((tmp = in.readLine()) != null)
				testo += tmp;
			
			int len = w.length();
			int count = 0;
			
			for (i = 0; i < testo.length() - len; i++)
				if (testo.substring(i, i+len).compareTo(w) == 0)
					count++;
			
			System.out.println(count);
			
			in.close();
			out.close();
			client.close();
		}
		catch(Exception e)
		{
		}
	}
	
	public static boolean isOk(String param)
	{
		try
		{
			int x = Integer.parseInt(param);
			
			if (x >= 1 && x <= 100000)
				return true;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
		
		return false;
	}
}
