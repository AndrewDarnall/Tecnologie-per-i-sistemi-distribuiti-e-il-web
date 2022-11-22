/*
Implementare in C o Java, su localhost, un programma server banca, connection-oriented, che:

    mantenga un array di 10 interi conto che rappresentano il saldo dei conti da 0 a 9;
    risponda, su localhost, port 7777 ai seguenti messaggi:
        [Un] dove n, da 0 a 9, è il numero del conto;
        l'effetto sarà di rendere il conto n quello attuale, cioè su cui operano implicitamente gli altri comandi;
        [Vxyzw] dove xyzw sono 4 cifre intere;
        l'effetto sarà di versare la somma xyzw sul conto attuale (cioè l'ultimo selezionato con il comando [Un])
        [Pxyzw] dove xyzw sono 4 cifre intere;
        l'effetto sarà di prelevare la somma xyzw dal conto attuale (cioè l'ultimo selezionato con il comando [Un])
        [S] l'effetto sarà di inviare al cliente il saldo depositato sul conto attuale (cioè l'ultimo selezionato con il comando [Un])
    risponda ERROR a ogni altro dato ricevuto dal cliente
*/

import java.io.*;
import java.net.*;

public class server
{
	private int saldo[];
	private int current;
	private ServerSocket server;
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	
	public server()
	{
		saldo = new int[10];
		current = 0;
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
	
	public boolean caller(String cmd)
	{
		int len = cmd.length();
		
		if (cmd.charAt(0) != '[' || cmd.charAt(len-1) != ']')
			return false;
		
		if (len == 4)
		{
			if (cmd.charAt(1) == 'U' && isInt(cmd.substring(2,3)))
			{
				funcU(cmd.substring(2,3));
				return true;
			}
			else
				return false;
		}
		else if (len == 7 && isInt(cmd.substring(2,6)))
		{
			if(cmd.charAt(1) == 'V')
			{
				funcV(cmd.substring(2,6));
				return true;
			}
			else if (cmd.charAt(1) == 'P')
			{
				funcP(cmd.substring(2,6));
				return true;
			}
			else
				return false;
		}
		else if (len == 3)
		{
			if (cmd.charAt(1) == 'S')
			{
				funcS();
				return true;
			}
			else
				return false;
		}
		
		return false;
	}
	
	public void funcU(String cmd)
	{
		current = Integer.parseInt(cmd);
		out.println("Conto attuale: "+current);
	}
	
	public void funcV(String cmd)
	{
		int x = Integer.parseInt(cmd);
		saldo[current] += x;
		out.println("Versati "+x+" sul conto "+current);
	}
	
	public void funcP(String cmd)
	{
		int x = Integer.parseInt(cmd);
		saldo[current] -= x;
		out.println("Prelevati "+x+" dal conto "+current);
	}
	
	public void funcS()
	{
		out.println("Saldo del conto "+current+": "+saldo[current]);
	}
	
	public void process()
	{
		try
		{
			server = new ServerSocket(12345);
			System.out.println("Server ready (CTRL-C quits)");
			while (true)
			{
				client = server.accept();
				in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				out = new PrintWriter(client.getOutputStream(), true);
				
				if (!caller(in.readLine()))
					out.println("ERROR");
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		server srv = new server();
		srv.process();
	}
}
