/*
Implementare in C/RPC o Java/RMI, su localhost, un servizio costituito da una funzione/metodo hash() che:

    prende per unico argomento una stringa,
    restituisce la somma dei codici ASCII dei caratteri dell'argomento stringa, modulo 26.

Scrivere un semplice cliente che invochi la funzione remota passandole come argomento il proprio cognome. 
*/

import java.rmi.*;
import java.rmi.registry.*;

public class client
{
	public static void main(String args[])
	{
		if (args.length != 1)
		{
			System.out.println("Usage: java client \"surname\"");
			return;
		}
		try
		{
			Registry reg = LocateRegistry.getRegistry("localhost");
			hashing stub = (hashing)reg.lookup("hashing");
			System.out.println(stub.hash(args[0]));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
