import java.net.*;
import java.io.*;

public class client
{
	public static void main(String[] args)
	{
		/*if (args.length != 1)
		{
			System.out.println("Utilizzo: java client [comando]\n");
			return;
		}*/
		
		try
		{
			InetAddress address = InetAddress.getByName("localhost");
			Socket client = new Socket(address, 12345);
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			out.println("[S]");
			
			System.out.println(in.readLine());
			
			client.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
