/*
Implementare in C/RPC o Java/RMI, su localhost, un servizio costituito da una funzione/metodo hash() che:

    prende per unico argomento una stringa,
    restituisce la somma dei codici ASCII dei caratteri dell'argomento stringa, modulo 26.

Scrivere un semplice cliente che invochi la funzione remota passandole come argomento il proprio cognome. 
*/

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;

public class server implements hashing
{
	public server()	{}
	
	public int hash(String surname)
	{
		int sum = 0;
		for (int i = 0; i < surname.length(); i++)
			sum += surname.charAt(i);
			
		return(sum % 26);
	}
	
	public static void main(String args[])
	{
		try
		{
			server obj = new server();
			hashing stub = (hashing) UnicastRemoteObject.exportObject(obj, 0);
			
			Registry reg = LocateRegistry.getRegistry("localhost");
			reg.bind("hashing", stub);
			
			System.out.println("Server ready (CTRL-C quits).");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
