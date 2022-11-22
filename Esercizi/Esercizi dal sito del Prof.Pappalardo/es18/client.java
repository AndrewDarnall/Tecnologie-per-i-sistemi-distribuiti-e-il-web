import java.rmi.*;
import java.rmi.registry.*;

public class client
{
	public static void main(String args[])
	{
		try
		{
			Registry reg = LocateRegistry.getRegistry("localhost");
			es18 stub = (es18)reg.lookup("es18");
			
			System.out.println(stub.ask("151.97.252.4", 80, "GET /pappalardo/prova/18.aux"));

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}