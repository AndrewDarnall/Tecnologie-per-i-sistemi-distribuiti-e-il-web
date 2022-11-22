/*
Si implementi in C o Java un cliente che riceve da standard input una stringa, la invia al server su localhost, port 33333 e infine attende da esso come risposta una stringa che visualizzerà sulla standard output.
*/

import java.net.*;
import java.io.*;

public class client
{
	public static void main(String args[])
	{
		if (args.length != 1)
		{
			System.out.println(">> Usage: java client \"message to send\"");
			return;
		}
		
		try
		{
			// preparazione dell'indirizzo del server
			InetAddress address = InetAddress.getByName("localhost");
			
			// creazione socket
			Socket client = new Socket(address, 33333);
			
			// creazione buffer di lettura e scrittura
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			// invio della stringa
			out.println(args[0]);
			
			// stampa risposta del server
			System.out.println(">> "+in.readLine()+"\n");
			
			// chiusura socket
			client.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
