/*
	Realizzare un server che tiene traccia della disponibilità di 10 libri, che possono essere disponibili o in prestito;
	riceve delle richieste da parte dei client del tipo "titolo del libro"
	e risponde "Disponibile", "In prestito" o "Inesistente" a seconda del titolo richiesto.
*/

import java.io.*;
import java.net.*;

public class EsSocket
{
	public static void main(String args[])
    {
        try
        {
            ServerSocket server = new ServerSocket(12345);
            Socket client;
            
            String richiesta = "";
            int j;
            
            String libreria[] = new String[10];
            boolean disp[] = new boolean[10];
            
            BufferedReader in;
            PrintWriter out;
            
            libreria[0] = "Amore e Odio";
            libreria[1] = "Il Principe Azzurro";
            libreria[2] = "Manuale Java";
            libreria[3] = "La Seconda Guerra Mondiale";
            libreria[4] = "La Prima Guerra Mondiale";
            libreria[5] = "Alice nel paese delle meraviglie";
            libreria[6] = "Manuale di cucina";
            libreria[7] = "Essere o non essere";
            libreria[8] = "Manuale C";
            libreria[9] = "Manuale di Programmazione Web";
            
            disp[0] = true;
            disp[1] = false;
            disp[2] = true;
            disp[3] = true;
            disp[4] = true;
            disp[5] = false;
            disp[6] = false;
            disp[7] = true;
            disp[8] = true;
            disp[9] = true;
                  
            System.out.println("Server ready (CTRL+C per terminare)");
                    
            while(true)
            {
                j = -1;
                client = server.accept();
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
                
                richiesta = in.readLine();
                
                for (int i = 0; i < 10; i ++)
                {
                    if (richiesta.equals(libreria[i]))
                    {
                        j = i;
                        if (disp[j])
                            out.println("Disponibile.");
                        else
                            out.println("In prestito.");
                    }
                }
                
                if (j < 0)
                    out.println("Inesistente.");
                
                in.close();
                out.close();
                client.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
