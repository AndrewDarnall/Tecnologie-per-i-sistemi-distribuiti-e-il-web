import java.rmi.server.*;
import java.rmi.registry.*;
import java.rmi.*;

public class server implements es17
{
 	public int hash(String s, int n)
	{
		int somma = 0;
		for (int i = 0; i < s.length(); i++)
			somma += s.charAt(i);
		return somma % n;
	}
	
	public static void main(String args[])
	{
		try
		{
			server obj = new server();
			es17 stub = (es17) UnicastRemoteObject.exportObject(obj, 0);
			
			Registry reg = LocateRegistry.getRegistry("localhost");
			reg.bind("es17", stub);
			
			System.out.println("Server running... (CTRL-C quits)");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}