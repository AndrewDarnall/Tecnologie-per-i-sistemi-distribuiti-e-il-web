import java.net.*;
import java.io.*;

public class server
{
	public static Integer sommaTotale;
	public static void main(String args[])
	{
		try
		{
			ServerSocket server = new ServerSocket(33333);
			sommaTotale = 0;
			
			System.out.println("Server in attesa di connessioni (CTRL-C per terminare)");
			
			// ciclo infinito, in attesa di connessioni
			while(true)
			{
				// chiamata bloccante, in attesa di una nuova connessione
				Socket client = server.accept();
				
				// la nuova richiesta viene gestita da un thread indipendente, si ripete il ciclo
				connect nuovaConnessione = new connect(client);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

// definisce il thread che andrà a gestire separatamente le singole connessioni
class connect extends Thread
{
	// dichiarazione delle variabili socket e dei buffer
	Socket client;
	BufferedReader in;
	PrintWriter out;
	
	public connect(Socket client)
	{
		this.client = client;
		
		// invoca automaticamente il metodo run()
		this.start();
	}
	
	public void run()
	{
		try
		{
			String tmp;
			int i = 0, somma = 0;
			
			// inizializzo i buffer in entrata e uscita
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
			
			// leggo la stringa in input
			tmp = in.readLine();
			
			// scorro fino a trovare il primo carattere cifra
			while(!isInt(tmp.charAt(i)+""))
				i++;
				
			// sommo tutte le cifre
			while(i < tmp.length())
			{
				somma += Integer.parseInt(tmp.charAt(i)+"");
				i++;
			}
			
			// garantisco che un solo thread alla volta abbia accesso alla variabile sommaTotale
			synchronized(server.sommaTotale)
			{
				server.sommaTotale += somma;
			}
			
			out.println("Somma: "+somma+"; Somma totale: "+server.sommaTotale);

			// chiusura dei buffer e del socket
			in.close();
 			out.close();
 			client.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean isInt(String str)
	{
	    try
	    {
		Integer.parseInt(str);
		return true;
	    }
	    catch (NumberFormatException nfe) {}
	    return false;
	}
}
