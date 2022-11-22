package BOOKServer;
import java.io.*;
import java.util.*;
import java.net.*;
import BOOKServer.*;

public class Server
{
	public static final int porta=39939;
	public static void stampaSocket(Socket socket)
	{
		System.out.println("Local address: " + socket.getLocalAddress());
		System.out.println("Local port: " + socket.getLocalPort());
		System.out.println("Remote address: " + socket.getInetAddress());
		System.out.println("Remote port: " + socket.getPort());
	}
	public static void main(String[] args)
	{
		try
        {
            ServerSocket local_sock=new ServerSocket(porta);
            while(true)
            {
                try
                {
                    System.out.println(">>Wait to connections on port " + porta + "…");
                    Socket remote_sock=local_sock.accept();
                    System.out.println("Connection accepted!");
                    stampaSocket(remote_sock);
                    InputStreamReader input1=new InputStreamReader(remote_sock.getInputStream());
                    BufferedReader in=new BufferedReader(input1);
                    OutputStreamWriter output1=new OutputStreamWriter(remote_sock.getOutputStream());
                    PrintWriter out=new PrintWriter(output1,true);
                    Biblioteca libro=new Biblioteca();
                    String str=in.readLine();
                    String[] vett=null;
                    vett=str.split(",");
                    if(libro.isAvailable(vett[0],Integer.parseInt(vett[1])))
                    {
                        out.println("True");
                    }
                    else
                    {
                        out.println("False");
                    }
                    remote_sock.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    continue;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
	}
}
