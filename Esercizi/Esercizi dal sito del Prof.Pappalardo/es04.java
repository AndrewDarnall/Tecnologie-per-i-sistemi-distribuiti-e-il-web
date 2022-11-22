/*
Scrivere in C o Java un programma che:

1)    apra una connessione verso l'host www.dmi.unict.it, port 80
2)    invii la stringa "GET /pappalardo/prova/04.aux\n"
3)    dallo stream di byte ricevuti memorizzi quelli dal 397.568 al 397.613
4)    scriva questi byte sulla standard output
*/

import java.io.*;
import java.net.*;

public class es04
{
	public static void main(String args[])
	{
		String tmp;
		try
		{
			// preparo l'indirizzo
			InetAddress address = InetAddress.getByName("www.dmi.unict.it");

			// creo il socket
			Socket client = new Socket(address, 80);

			// preparo i buffer in ingresso e uscita
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);

			// invio il messaggio
			out.println("GET /pappalardo/prova/04.aux\n");

			// memorizzo la risposta
			in.skip(397568); // sbagliato
			tmp = in.readLine();
			System.out.println(tmp);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
