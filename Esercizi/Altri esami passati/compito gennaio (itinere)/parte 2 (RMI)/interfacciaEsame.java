/*
	Realizzare, tramite Java RMI, un server banca che tiene traccia di 20 conti e mette a disposizione due metodi remoti:
	- saldo(int n): restituisce il conto del saldo "n";
	- versamento(int n, int cifra): versa "cifra" sul conto "n";
*/

import java.rmi.*;
import java.rmi.registry.*;

public interface interfacciaEsame extends Remote
{
	public int saldo(int n) throws RemoteException;
        public void versamento(int n, int cifra) throws RemoteException;
}
