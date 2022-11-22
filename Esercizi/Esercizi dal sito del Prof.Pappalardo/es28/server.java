import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class server implements periodChecker
{
	public boolean finiscePeriodo(String str)
	{
		for (int i = 0; i < str.length(); i++)
			if (str.charAt(i) == '.')
				return true;
		return false;
	}
	
	public static void main(String[] args)
	{
		try
		{
			server obj = new server();
			periodChecker stub = (periodChecker) UnicastRemoteObject.exportObject(obj, 0);
			
			Registry reg = LocateRegistry.getRegistry("localhost");
			reg.bind("pck", stub);
			
			System.out.println("Server ready (CTRL-C quits)");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
