/*
Implementare in C/RPC o Java/RMI, su localhost, un servizio costituito da una funzione/metodo hash() che:

    prende per unico argomento una stringa,
    restituisce la somma dei codici ASCII dei caratteri dell'argomento stringa, modulo 26.

Scrivere un semplice cliente che invochi la funzione remota passandole come argomento il proprio cognome. 
*/

import java.rmi.*;

public interface hashing extends Remote
{
	public int hash(String surname) throws RemoteException;
}
