/*
Scrivere in C o Java un programma cliente che:

    apra una connessione verso l'host www.dmi.unict.it, port 80,
    invii a tale server la stringa "GET /pappalardo/prova/09.aux\n",
    nello stream testo ricevuto, legga il numero che rappresenta il (solo) contenuto della riga 12,
    moltiplichi tale numero per il valore restituito dalla funzione remota hash(), eseguita su localhost e applicata al proprio cognome; sia n il risultato,
    invii a 151.97.252.130, port 80 la stringa "GET /pappalardo/prova/09b.aux\n",
    dallo stream testo ricevuto, individui la riga n e ne scriva il contenuto sulla standard output.
*/

import java.io.*;
import java.net.*;
import java.rmi.*;
import java.rmi.registry.*;

public class parte2
{
	public static void main(String args[])
	{
		int n, i = 1;
		try
		{
			InetAddress address = InetAddress.getByName("www.dmi.unict.it");
			Socket client = new Socket(address, 80);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			
			out.println("GET /pappalardo/prova/09.aux\n");
			
			while (i < 12)
			{
				in.readLine();
				i++;
			}
			
			n = Integer.parseInt(in.readLine());
			
			Registry reg = LocateRegistry.getRegistry("localhost");
			hashing stub = (hashing)reg.lookup("hashing");
			
			n *= stub.hash("borzì");
			
			client.close();
			client = new Socket(address, 80);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
			
			out.println("GET /pappalardo/prova/09b.aux\n");
			
			for (i = 1; i < n; i++)
				in.readLine();
				
			System.out.println(in.readLine());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
