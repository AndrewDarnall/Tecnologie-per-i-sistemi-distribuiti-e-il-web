import java.net.*;
import java.io.*;

public class client
{
	public static void main(String[] args)
	{
		/* inizio verifica della correttezza dei parametri */
		
		String utilizzo = ">> Utilizzo: java client XX...XNN...N\nDove:\n - XX...X è una sequenza di caratteri non cifra\n - NN...N è una sequenza di cifre (da 0 a 9)";
		int i;
		
		// controllo se il numero di parametri è 1 e se il primo carattere non è una cifra
		if (args.length != 1 || isInt(args[0].substring(0,1)))
		{
			System.out.println(utilizzo);
			return;
		}
		
		// scorro tutti i caratteri non cifre
		i = 1;
		while(i < args[0].length() && !isInt(args[0].substring(i,i+1)))
			i++;
		
		// controllo se sono già arrivato alla fine della stringa senza trovare numeri
		if (i == args[0].length())
		{
			System.out.println(utilizzo);
			return;
		}
		
		// controllo se tutti i caratteri dalla prima cifra fino alla fine della stringa sono a loro volta cifre
		while(i < args[0].length())
		{
			if (!isInt(args[0].substring(i,i+1)))
			{
				System.out.println(utilizzo);
				return;
			}
			i++;
		}
			
		/* fine verifica della correttezza dei parametri*/
		
		try
		{
			// creo il socket
			InetAddress indirizzo = InetAddress.getByName("localhost");
			Socket client = new Socket(indirizzo, 33333);
			
			// creo i buffer
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			
			// invio stringa
			out.println(args[0]);
			
			// ricevo e stampo la risposta
			System.out.println(in.readLine());
			
			// chiusura dei buffer e del socket
			in.close();
 			out.close();
 			client.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static boolean isInt(String str)
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
