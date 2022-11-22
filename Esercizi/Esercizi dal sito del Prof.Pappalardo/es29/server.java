/*
Implementare in Java o C un server che comunica tramite socket.

Il server attende sul port 33333 e memorizza in ordine tutte le stringhe che riceve (di fatto nell'implementazione ci si può limitare a memorizzare solo le ultime 100, se lo si desidera).

In risposta ad ogni nuova stringa proveniente da un client gli risponde con tutte le stringhe ricevute fino a quel momento (compresa l'ultima), con queste eccezioni:

    a partire dalla quinta stringa memorizzata, il server risponderà: LIMITE RAGGIUNTO;
    in risposta alla stringa RESET, il server azzera l'elenco delle stringhe memorizzate fino ad allora e risponde OK.
*/

import java.net.*;
import java.io.*;

public class server
{
	private static int dim, count;
	private static String strings[];
	
	public static void reset()
	{
		strings = new String[dim];
		count = 0;
		return;
	}
	
	public static void insert(String str)
	{
		strings[count] = str;
		count++;
		return;
	}
	
	public static void main(String[] args)
	{
		String tmp;
		dim = 100;
		count = 0;
		strings = new String[dim];
		
		try
		{
			// apertura del socket server
			ServerSocket server = new ServerSocket(33333);
			
			// dichiarazione del socket client e dei buffer
			Socket client;
			BufferedReader in;
			PrintWriter out;
			
			System.out.println("Server ready (CTRL-C quits)\n");
			
			// cicla in attesa di connessioni
			while(true)
			{
				// chiamata bloccante, in attesa di connessioni da parte di un client
				client = server.accept();
				
				// lettura della stringa
				in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				tmp = in.readLine();
				
				// preparazione del buffer in uscita
				out = new PrintWriter(client.getOutputStream(), true);
				
				// elaborazione
				if (tmp.compareTo("RESET") == 0)
				{
					// reset richiesto dal client
					System.out.println("Client connected on "+client+" has requested RESET.");
					reset();
					
					// risposta
					out.println("OK");
				}
				else
				{
					System.out.println("Client connected on "+client+" has sent \""+tmp+"\".");
					// memorizzazione della stringa ricevuta
					insert(tmp);
					
					// risposta
					if (count >= 5)
						out.println("LIMITE RAGGIUNTO");
					else
					{
						tmp = "";
						for (int i = 0; i < count; i ++)
							tmp += strings[i]+"; ";
						
						out.println(tmp);
					}
				}
				
				// chiusura del client socket
				client.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
